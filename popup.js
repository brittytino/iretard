(() => {
  const dailyCapEl = document.getElementById("dailyCap");
  const usageProgressEl = document.getElementById("usageProgress");
  const usedTodayEl = document.getElementById("usedToday");
  const remainingEl = document.getElementById("remaining");
  const statusEl = document.getElementById("status");
  const feedbackEl = document.getElementById("feedback");
  let refreshTimer = null;

  function sendMessage(type, payload = {}) {
    return new Promise((resolve) => {
      try {
        chrome.runtime.sendMessage({ type, ...payload }, (response) => {
          if (chrome.runtime.lastError) {
            resolve(null);
            return;
          }
          resolve(response || null);
        });
      } catch (_error) {
        resolve(null);
      }
    });
  }

  function formatMinutes(ms) {
    const totalMinutes = Math.max(0, Math.ceil(ms / globalThis.IretardStorage.ONE_MINUTE_MS));
    if (totalMinutes >= 60) {
      const hours = Math.floor(totalMinutes / 60);
      const minutes = totalMinutes % 60;
      return minutes > 0 ? `${hours}h ${minutes}m` : `${hours}h`;
    }
    return `${totalMinutes}m`;
  }

  function setFeedback(text, isError = false) {
    feedbackEl.textContent = text;
    feedbackEl.classList.toggle("error", isError);
  }

  function updateUsageProgress(state, metrics) {
    const limitMs = Math.max(1, metrics.dailyLimitMs || globalThis.IretardStorage.getDailyLimitMs(state));
    const ratio = Math.max(0, Math.min(1, state.usedToday / limitMs));
    usageProgressEl.style.width = `${Math.round(ratio * 100)}%`;
  }

  function renderState(stateInput, metricsInput) {
    const state = globalThis.IretardStorage.normalizeState(stateInput);
    const metrics = metricsInput && typeof metricsInput === "object"
      ? {
        ...globalThis.IretardStorage.buildMetrics(state),
        ...metricsInput
      }
      : globalThis.IretardStorage.buildMetrics(state);

    dailyCapEl.textContent = formatMinutes(metrics.dailyLimitMs);
    usedTodayEl.textContent = formatMinutes(state.usedToday);
    remainingEl.textContent = formatMinutes(metrics.remainingMs);
    updateUsageProgress(state, metrics);

    const limitExceeded = globalThis.IretardBlocker.isLimitExceeded(state);
    statusEl.textContent = limitExceeded ? "Locked until tomorrow" : "Policy enforced";
  }

  async function refreshState() {
    try {
      const response = await sendMessage("GET_STATE", {
        url: "https://www.instagram.com/"
      });

      if (!response || !response.ok || !response.state) {
        const now = Date.now();
        const fallbackState = await globalThis.IretardStorage.getState(now);
        renderState(fallbackState, globalThis.IretardStorage.buildMetrics(fallbackState, now));
        setFeedback(response && response.error ? response.error : "Background unavailable. Loaded local state.", true);
        return;
      }

      renderState(response.state, response.metrics);
      setFeedback("");
    } catch (_error) {
      try {
        const now = Date.now();
        const fallbackState = await globalThis.IretardStorage.getState(now);
        renderState(fallbackState, globalThis.IretardStorage.buildMetrics(fallbackState, now));
        setFeedback("Background unavailable. Loaded local state.", true);
      } catch (_storageError) {
        setFeedback("Could not load extension state.", true);
      }
    }
  }

  chrome.runtime.onMessage.addListener((message) => {
    if (!message || message.type !== "STATE_UPDATED") {
      return;
    }

    if (message.state) {
      renderState(message.state, message.metrics);
    }
  });

  void refreshState();

  refreshTimer = setInterval(() => {
    void refreshState();
  }, 15000);

  window.addEventListener("unload", () => {
    if (refreshTimer) {
      clearInterval(refreshTimer);
      refreshTimer = null;
    }
  });
})();

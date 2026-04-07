(() => {
  const BLOCKED_PATH_PREFIXES = ["/reels", "/reel", "/video/unified_cvc"];
  const ALLOWED_PATH_PREFIXES = ["/stories", "/direct/inbox", "/direct/t"];
  const INSTAGRAM_HOSTNAMES = new Set([
    "www.instagram.com",
    "i.instagram.com",
    "graph.instagram.com"
  ]);

  const NETWORK_BLOCK_RULES = [
    {
      id: "feed_timeline",
      test: (pathname) => pathname.includes("/feed/timeline")
    },
    {
      id: "discover_topical_explore",
      test: (pathname) => pathname.includes("/discover/topical_explore")
    },
    {
      id: "clips_discover",
      test: (pathname) => pathname.includes("/clips/discover")
    },
    {
      id: "logging",
      test: (pathname) => pathname.includes("/logging")
    },
    {
      id: "async_ads_privacy",
      test: (pathname) => pathname.includes("/async_ads_privacy")
    },
    {
      id: "async_critical_notices",
      test: (pathname) => pathname.includes("/async_critical_notices")
    },
    {
      id: "media_seen",
      test: (pathname) => pathname.includes("/api/v1/media/") && pathname.includes("/seen")
    },
    {
      id: "fbupload",
      test: (pathname) => pathname.includes("/api/v1/fbupload")
    },
    {
      id: "stats",
      test: (pathname) => pathname.includes("/api/v1/stats")
    },
    {
      id: "commerce",
      test: (pathname) => pathname.includes("/api/v1/commerce")
    },
    {
      id: "shopping",
      test: (pathname) => pathname.includes("/api/v1/shopping")
    },
    {
      id: "sellable_items",
      test: (pathname) => pathname.includes("/api/v1/sellable_items")
    }
  ];

  function toUrl(input) {
    try {
      if (input instanceof URL) {
        return input;
      }

      const fallbackOrigin = typeof location !== "undefined" && location.origin
        ? location.origin
        : "https://www.instagram.com";
      return new URL(input, fallbackOrigin);
    } catch (_error) {
      return null;
    }
  }

  function normalizePath(pathname) {
    if (!pathname) {
      return "/";
    }

    const lower = pathname.toLowerCase();
    return lower.endsWith("/") && lower !== "/" ? lower.slice(0, -1) : lower;
  }

  function startsWithPath(pathname, prefix) {
    return pathname === prefix || pathname.startsWith(`${prefix}/`);
  }

  function isInstagramUrl(input) {
    const url = toUrl(input);
    return Boolean(url) && INSTAGRAM_HOSTNAMES.has(url.hostname);
  }

  function isBlockedPath(pathname) {
    const normalized = normalizePath(pathname);
    return BLOCKED_PATH_PREFIXES.some((prefix) => startsWithPath(normalized, prefix));
  }

  function isExplicitlyAllowedPath(pathname) {
    const normalized = normalizePath(pathname);
    return ALLOWED_PATH_PREFIXES.some((prefix) => startsWithPath(normalized, prefix));
  }

  function getNetworkBlockReason(input) {
    const url = toUrl(input);
    if (!url || !isInstagramUrl(url)) {
      return null;
    }

    const pathname = normalizePath(url.pathname);
    const match = NETWORK_BLOCK_RULES.find((rule) => rule.test(pathname));
    return match ? match.id : null;
  }

  function isBlockedNetworkUrl(input) {
    return Boolean(getNetworkBlockReason(input));
  }

  function shouldRedirectToDirectTab(input) {
    const url = toUrl(input);
    if (!url || url.hostname !== "www.instagram.com") {
      return false;
    }

    const pathname = normalizePath(url.pathname);
    const hash = String(url.hash || "").toLowerCase();
    const lowerHref = String(url.href || "").toLowerCase();
    const directFragment = hash.includes("fragment_clips")
      || url.searchParams.get("fragment") === "fragment_clips"
      || lowerHref.includes("fragment_clips");

    return startsWithPath(pathname, "/reels") || startsWithPath(pathname, "/reel") || directFragment;
  }

  function isLimitExceeded(state, now = Date.now()) {
    if (globalThis.IretardStorage.isEmergencyActive(state, now)) {
      return false;
    }

    return state.usedToday >= globalThis.IretardStorage.getDailyLimitMs(state);
  }

  function getBlockReason(input, state, now = Date.now()) {
    const url = toUrl(input);
    if (!url || !isInstagramUrl(url)) {
      return null;
    }

    if (globalThis.IretardStorage.isEmergencyActive(state, now)) {
      return null;
    }

    if (isBlockedPath(url.pathname)) {
      return "route";
    }

    if (isLimitExceeded(state, now)) {
      return "limit";
    }

    return null;
  }

  function shouldTrackUsage(input, state, now = Date.now()) {
    const url = toUrl(input);
    if (!url || !isInstagramUrl(url)) {
      return false;
    }

    return getBlockReason(url, state, now) === null;
  }

  globalThis.IretardBlocker = {
    BLOCKED_PATH_PREFIXES,
    ALLOWED_PATH_PREFIXES,
    NETWORK_BLOCK_RULES,
    toUrl,
    normalizePath,
    isInstagramUrl,
    isBlockedPath,
    getNetworkBlockReason,
    isBlockedNetworkUrl,
    shouldRedirectToDirectTab,
    isExplicitlyAllowedPath,
    isLimitExceeded,
    getBlockReason,
    shouldTrackUsage
  };
})();

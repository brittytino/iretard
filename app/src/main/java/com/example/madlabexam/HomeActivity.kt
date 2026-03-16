package com.example.madlabexam

import com.example.madlabexam.R

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.madlabexam.exercise1.ConverterActivity
import com.example.madlabexam.exercise2.CookieActivity
import com.example.madlabexam.mock1.MainMenuActivity
import com.example.madlabexam.ps2.BasicCalculatorActivity
import com.example.madlabexam.ps2.VisitingCardActivity
import com.example.madlabexam.ps3.CalcToastActivity
import com.example.madlabexam.ps3.FeedbackActivity
import com.example.madlabexam.ps4.PS4FormActivity
import com.example.madlabexam.ps5.StudentRegistrationActivity
import com.example.madlabexam.ps6.HospitalDirectoryActivity
import com.example.madlabexam.ps7.SecurityScenariosActivity

data class ModuleItem(
    val title: String,
    val subtitle: String,
    val iconResId: Int,
    val activityClass: Class<*>
)

class HomeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val recyclerView = findViewById<RecyclerView>(R.id.rvModules)

        val modules = listOf(
            ModuleItem("Converter", "Temperature", R.drawable.ic_module_converter, ConverterActivity::class.java),
            ModuleItem("Hungry Cookie", "Feed the cookie", R.drawable.ic_module_cookie, CookieActivity::class.java),
            ModuleItem("Visiting Card", "Business card", R.drawable.ic_module_card, VisitingCardActivity::class.java),
            ModuleItem("Calculator", "Basic math", R.drawable.ic_module_calc, BasicCalculatorActivity::class.java),
            ModuleItem("Feedback", "Rate the app", R.drawable.ic_module_feedback, FeedbackActivity::class.java),
            ModuleItem("Calc + Toast", "Toast result", R.drawable.ic_module_toast, CalcToastActivity::class.java),
            ModuleItem("Patient Form", "Form & intent", R.drawable.ic_module_form, PS4FormActivity::class.java),
            ModuleItem("Registration", "Student form", R.drawable.ic_module_student, StudentRegistrationActivity::class.java),
            ModuleItem("Hospital Dir", "Doctor search", R.drawable.ic_module_hospital, HospitalDirectoryActivity::class.java),
            ModuleItem("Security", "Device settings", R.drawable.ic_module_security, SecurityScenariosActivity::class.java),
            ModuleItem("Mock Test", "Menu driven", R.drawable.ic_module_menu, MainMenuActivity::class.java)
        )

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = ModuleAdapter(modules) { item ->
            startActivity(Intent(this, item.activityClass))
        }
    }

    private class ModuleAdapter(
        private val items: List<ModuleItem>,
        private val onClick: (ModuleItem) -> Unit
    ) : RecyclerView.Adapter<ModuleAdapter.ViewHolder>() {

        class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {
            val icon: ImageView = itemView.findViewById(R.id.ivModuleIcon)
            val title: TextView = itemView.findViewById(R.id.tvModuleTitle)
            val subtitle: TextView = itemView.findViewById(R.id.tvModuleSubtitle)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_module_card, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = items[position]
            holder.icon.setImageResource(item.iconResId)
            holder.title.text = item.title
            holder.subtitle.text = item.subtitle
            holder.itemView.setOnClickListener { onClick(item) }
        }

        override fun getItemCount() = items.size
    }
}

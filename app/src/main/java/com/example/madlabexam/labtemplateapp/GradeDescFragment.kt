package com.example.labtemplateapp

import com.example.madlabexam.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment

class GradeDescFragment : Fragment() {

    // 1. Connect Layout XML. TODO: Change R.layout.fragment_grade if you rename XML
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_grade, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 2. Find Views by ID. TODO: Change IDs if you renamed them in XML
        val editStudentName = view.findViewById<EditText>(R.id.editStudentName)
        val editStudentMarks = view.findViewById<EditText>(R.id.editStudentMarks)
        val btnCalculate = view.findViewById<Button>(R.id.btnCalculate)
        val textResult = view.findViewById<TextView>(R.id.textResult)

        // 3. Set Button Click
        btnCalculate.setOnClickListener {
            // Get text from inputs
            val nameText = editStudentName.text.toString()
            val marksText = editStudentMarks.text.toString()

            // Basic Error Check
            if (nameText.isEmpty() || marksText.isEmpty()) {
                Toast.makeText(requireContext(), "Fill required fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convert marks to Double number
            val marksValue = marksText.toDouble()

            // Validate marks range
            if (marksValue < 0 || marksValue > 100) {
                Toast.makeText(requireContext(), "Marks must be 0 to 100", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Super simple Grade Logic. TODO: Change thresholds based on exam rules.
            var simpleGradeResult = ""
            if (marksValue >= 90) {
                simpleGradeResult = "Grade A"
            } else if (marksValue >= 80) {
                simpleGradeResult = "Grade B"
            } else if (marksValue >= 70) {
                simpleGradeResult = "Grade C"
            } else if (marksValue >= 60) {
                simpleGradeResult = "Grade D"
            } else {
                simpleGradeResult = "Fail"
            }

            // Set the result text on the screen
            textResult.text = "Student: $nameText\nScore: $marksValue\nResult: $simpleGradeResult"
        }
    }
}

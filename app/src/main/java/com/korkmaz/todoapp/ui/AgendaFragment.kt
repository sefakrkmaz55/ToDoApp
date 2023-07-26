package com.korkmaz.todoapp.ui

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.korkmaz.todoapp.databinding.FragmentAgendaBinding
import java.text.SimpleDateFormat
import java.util.*

class AgendaFragment : Fragment(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentAgendaBinding

    // Firestore referansları
    private val db = FirebaseFirestore.getInstance()
    private val tasksCollectionRef: CollectionReference = db.collection("Tasks")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAgendaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etDate.setOnClickListener {
            showDatePicker()
        }

        binding.btnSave.setOnClickListener {
            saveTaskToFirestore()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(requireContext(), this, year, month, day)
        datePickerDialog.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        // Kullanıcının seçtiği tarihi EditText'e yazdırmak için formatlayıp atıyoruz.
        val selectedDate = Calendar.getInstance()
        selectedDate.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate.time)
        binding.etDate.setText(formattedDate)
    }

    private fun saveTaskToFirestore() {
        val taskText = binding.etTask.text.toString().trim()
        val descriptionText = binding.etDescription.text.toString().trim()
        val timeText = binding.etDate.text.toString().trim()

        if (taskText.isEmpty() || descriptionText.isEmpty() || timeText.isEmpty()) {
            Toast.makeText(requireContext(), "Lütfen Tüm Alanları Doldurun", Toast.LENGTH_SHORT)
                .show()
            return
        }

        // Verileri Firestore'a kaydetmek için bir Map oluşturuyoruz.
        val taskMap = hashMapOf(
            "task" to taskText,
            "description" to descriptionText,
            "time" to timeText
        )

        // Firestore'a veriyi ekliyoruz
        tasksCollectionRef.add(taskMap)
            .addOnCompleteListener { task: Task<DocumentReference?> ->
                if (task.isSuccessful) {

                    binding.etTask.text.clear()
                    binding.etDescription.text.clear()
                    binding.etDate.text.clear()

                    Toast.makeText(requireContext(), "Görev Kaydı Başarılı", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(requireContext(), "Görev Kaydı Başarısız", Toast.LENGTH_SHORT)
                        .show()
                }
            }
    }
}

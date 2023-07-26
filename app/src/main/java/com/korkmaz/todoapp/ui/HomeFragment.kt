package com.korkmaz.todoapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.korkmaz.todoapp.R
import com.korkmaz.todoapp.adapter.TaskAdapter
import com.korkmaz.todoapp.databinding.FragmentHomeBinding
import com.korkmaz.todoapp.model.Task

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var taskAdapter: TaskAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var taskArrayList: ArrayList<Task>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView: RecyclerView = binding.rvTask
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.setHasFixedSize(true)

        taskArrayList = arrayListOf()
        taskAdapter = TaskAdapter(taskArrayList)
        recyclerView.adapter = taskAdapter

        EventChangeListener()
    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Tasks").orderBy("task", Query.Direction.ASCENDING)
            .addSnapshotListener(object : EventListener<QuerySnapshot> {
                override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                    if (error != null) {
                        Log.e("Firestore Error", error.message.toString())
                        return
                    }
                    for (dc: DocumentChange in value?.documentChanges!!) {
                        if (dc.type == DocumentChange.Type.ADDED) {
                            taskArrayList.add(dc.document.toObject(Task::class.java))
                        }
                    }
                    taskAdapter.notifyDataSetChanged()
                }
            })
    }
}

package com.korkmaz.todoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.korkmaz.todoapp.R
import com.korkmaz.todoapp.model.Task

class TaskAdapter(private val taskList: ArrayList<Task>) :
    RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder {

        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        val task: Task = taskList[position]
        holder.taskName.text=task.task
        holder.taskDescription.text=task.description
        holder.taskDay.text=task.time
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    public class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val taskName: TextView = itemView.findViewById(R.id.tv_taskName)
        val taskDescription: TextView = itemView.findViewById(R.id.tv_taskDescription)
        val taskDay: TextView = itemView.findViewById(R.id.tv_taskDay)
    }
}



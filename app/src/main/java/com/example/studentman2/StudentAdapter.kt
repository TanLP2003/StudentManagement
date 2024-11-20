package com.example.studentman2

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class StudentAdapter(val students: MutableList<StudentModel>): RecyclerView.Adapter<StudentAdapter.StudentViewHolder>() {
    private var deletedStudent: StudentModel? = null
    private var deletedPosition: Int = -1

    class StudentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val textStudentName: TextView = itemView.findViewById(R.id.text_student_name)
        val textStudentId: TextView = itemView.findViewById(R.id.text_student_id)
        val imageEdit: ImageView = itemView.findViewById(R.id.image_edit)
        val imageRemove: ImageView = itemView.findViewById(R.id.image_remove)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.layout_student_item,
            parent, false)
        return StudentViewHolder(itemView)
    }

    override fun getItemCount(): Int = students.size

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        val student = students[position]

        holder.textStudentName.text = student.studentName
        holder.textStudentId.text = student.studentId

        holder.imageEdit.setOnClickListener {
            showEditDialog(holder.itemView.context, student, position)
        }

        holder.imageRemove.setOnClickListener {
            showDeleteDialog(holder.itemView, position)
        }
    }

    private fun showEditDialog(context: Context, student: StudentModel, position: Int) {
        val dialog = Dialog(context)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_student)

        val edtName = dialog.findViewById<EditText>(R.id.edtName)
        val edtId = dialog.findViewById<EditText>(R.id.edtId)
        val btnSave = dialog.findViewById<Button>(R.id.btnSave)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)

        edtName.setText(student.studentName)
        edtId.setText(student.studentId)

        btnSave.setOnClickListener {
            val newName = edtName.text.toString()
            val newId = edtId.text.toString()

            if (newName.isNotEmpty() && newId.isNotEmpty()) {
                students[position] = StudentModel(newName, newId)
                notifyItemChanged(position)
                dialog.dismiss()
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun showDeleteDialog(view: View, position: Int) {
        AlertDialog.Builder(view.context)
            .setTitle("Xác nhận xóa")
            .setMessage("Bạn có chắc chắn muốn xóa sinh viên này?")
            .setPositiveButton("Có") { _, _ ->
                deleteStudent(view, position)
            }
            .setNegativeButton("Không", null)
            .show()
    }

    private fun deleteStudent(view: View, position: Int) {
        deletedStudent = students[position]
        deletedPosition = position

        students.removeAt(position)
        notifyItemRemoved(position)

        Snackbar.make(view, "Đã xóa sinh viên", Snackbar.LENGTH_LONG)
            .setAction("Hoàn tác") {
                deletedStudent?.let { student ->
                    students.add(deletedPosition, student)
                    notifyItemInserted(deletedPosition)
                }
            }
            .show()
    }

    fun addStudent(student: StudentModel){
        students.add(student)
        notifyItemInserted(students.size - 1)
    }
}
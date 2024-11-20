package com.example.studentman2

import android.app.Dialog
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var studentAdapter: StudentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val students = mutableListOf(
            StudentModel("Nguyễn Văn An", "SV001"),
            StudentModel("Trần Thị Bảo", "SV002"),
            StudentModel("Lê Hoàng Cường", "SV003"),
            StudentModel("Phạm Thị Dung", "SV004"),
            StudentModel("Đỗ Minh Đức", "SV005"),
            StudentModel("Vũ Thị Hoa", "SV006"),
            StudentModel("Hoàng Văn Hải", "SV007"),
            StudentModel("Bùi Thị Hạnh", "SV008"),
            StudentModel("Đinh Văn Hùng", "SV009"),
            StudentModel("Nguyễn Thị Linh", "SV010"),
            StudentModel("Phạm Văn Long", "SV011"),
            StudentModel("Trần Thị Mai", "SV012"),
            StudentModel("Lê Thị Ngọc", "SV013"),
            StudentModel("Vũ Văn Nam", "SV014"),
            StudentModel("Hoàng Thị Phương", "SV015"),
            StudentModel("Đỗ Văn Quân", "SV016"),
            StudentModel("Nguyễn Thị Thu", "SV017"),
            StudentModel("Trần Văn Tài", "SV018"),
            StudentModel("Phạm Thị Tuyết", "SV019"),
            StudentModel("Lê Văn Vũ", "SV020")
        )

        studentAdapter = StudentAdapter(students)

        findViewById<RecyclerView>(R.id.recycler_view_students).run {
            adapter = studentAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        val addBtn = findViewById<Button>(R.id.btn_add_new);
        addBtn.setOnClickListener {
            showAddStudentDialog()
        }
    }

    private fun showAddStudentDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_student)

        val edtName = dialog.findViewById<EditText>(R.id.edtName)
        val edtId = dialog.findViewById<EditText>(R.id.edtId)
        val btnSave = dialog.findViewById<Button>(R.id.btnSave)
        val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)

        btnSave.setOnClickListener {
            val studentName = edtName.text.toString()
            val studentId = edtId.text.toString()

            if (studentName.isNotEmpty() && studentId.isNotEmpty()) {
                studentAdapter.addStudent(StudentModel(studentName, studentId))
                Toast.makeText(this, "Thêm sinh viên thành công!", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
}
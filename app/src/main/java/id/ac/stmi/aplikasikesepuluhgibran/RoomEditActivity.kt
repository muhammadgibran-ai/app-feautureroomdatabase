package id.ac.stmi.aplikasikesepuluhgibran

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RoomEditActivity : AppCompatActivity() {

    private lateinit var database: TodoDatabase
    private lateinit var todoAktif: Todo // Menyimpan data todo yang sedang diedit

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_room_edit)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = TodoDatabase.getDatabase(this)

        val etJudul = findViewById<EditText>(R.id.etEditJudul)
        val etDeskripsi = findViewById<EditText>(R.id.etEditDeskripsi)
        val etKategori = findViewById<EditText>(R.id.etEditKategori)
        val etTanggal = findViewById<EditText>(R.id.etEditTanggal)
        val btnUpdate = findViewById<Button>(R.id.btnUpdate)
        val btnHapus = findViewById<Button>(R.id.btnHapus)

        val idTodo = intent.getIntExtra("ID_TODO", -1)

        if (idTodo != -1) {
            // Ambil data 1 todo dari Room
            todoAktif = database.todoDao().getTodoById(idTodo)

            etJudul.setText(todoAktif.judul)
            etDeskripsi.setText(todoAktif.deskripsi)
            etKategori.setText(todoAktif.kategori)
            etTanggal.setText(todoAktif.tanggal)
        }

        btnUpdate.setOnClickListener {
            val judul = etJudul.text.toString().trim()
            val deskripsi = etDeskripsi.text.toString().trim()
            val kategori = etKategori.text.toString().trim()
            val tanggal = etTanggal.text.toString().trim()

            if (judul.isEmpty()) {
                Toast.makeText(this, "Judul tugas wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Update ke Room
            val todoUpdate = todoAktif.copy(
                judul = judul,
                deskripsi = deskripsi,
                kategori = kategori,
                tanggal = tanggal
            )
            database.todoDao().update(todoUpdate)

            Toast.makeText(this, "Tugas berhasil diupdate!", Toast.LENGTH_SHORT).show()
            finish()
        }

        btnHapus.setOnClickListener {
            // Hapus dari Room
            database.todoDao().Delete(todoAktif)
            Toast.makeText(this, "Tugas berhasil dihapus!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
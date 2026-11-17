package id.ac.stmi.aplikasikesepuluhgibran

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RoomAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_room_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val database = TodoDatabase.getDatabase(this)

        val etJudul = findViewById<EditText>(R.id.etJudul)
        val etDeskripsi = findViewById<EditText>(R.id.etDeskripsi)
        val etKategori = findViewById<EditText>(R.id.etKategori)
        val etTanggal = findViewById<EditText>(R.id.etTanggal)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        btnSimpan.setOnClickListener {
            val judul = etJudul.text.toString().trim()
            val deskripsi = etDeskripsi.text.toString().trim()
            val kategori = etKategori.text.toString().trim()
            val tanggal = etTanggal.text.toString().trim()

            if (judul.isEmpty()) {
                Toast.makeText(this, "Judul tugas wajib diisi!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses Insert ke Room
            val todoBaru = Todo(
                judul = judul,
                deskripsi = deskripsi,
                kategori = kategori,
                tanggal = tanggal
            )
            database.todoDao().insert(todoBaru)

            Toast.makeText(this, "Tugas berhasil disimpan!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
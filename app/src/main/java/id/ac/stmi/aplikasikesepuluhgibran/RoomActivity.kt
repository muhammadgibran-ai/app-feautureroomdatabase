package id.ac.stmi.aplikasikesepuluhgibran

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RoomActivity : AppCompatActivity() {

    private lateinit var database: TodoDatabase
    private lateinit var adapter: ArrayAdapter<String>
    private var daftarTodo = ArrayList<String>()
    private var daftarId = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_room)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = TodoDatabase.getDatabase(this)

        val btnTambahTodo = findViewById<Button>(R.id.btnTambahTodo)
        val lvTodo = findViewById<ListView>(R.id.lvTodo)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, daftarTodo)
        lvTodo.adapter = adapter

        btnTambahTodo.setOnClickListener {
            startActivity(Intent(this, RoomAddActivity::class.java))
        }

        lvTodo.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, RoomEditActivity::class.java)
            intent.putExtra("ID_TODO", daftarId[position])
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        muatDataTodo()
    }

    private fun muatDataTodo() {
        daftarTodo.clear()
        daftarId.clear()

        // Mengambil data menggunakan Room DAO
        val todos = database.todoDao().getAllTodos()

        for (todo in todos) {
            daftarId.add(todo.id)
            val infoKategori = if (todo.kategori.isNotEmpty()) "[${todo.kategori}] " else ""
            val infoTanggal = if (todo.tanggal.isNotEmpty()) "\nTenggat: ${todo.tanggal}" else ""
            daftarTodo.add("${todo.judul} $infoKategori\n${todo.deskripsi}$infoTanggal")
        }

        adapter.notifyDataSetChanged()
    }
}
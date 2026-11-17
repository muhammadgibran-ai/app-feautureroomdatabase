package id.ac.stmi.aplikasikesepuluhgibran

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val judul: String,
    val deskripsi: String,
    val kategori: String,
    val tanggal: String
)

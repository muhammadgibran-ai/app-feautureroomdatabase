package id.ac.stmi.aplikasikesepuluhgibran

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert
    fun insert(todo: Todo)

    @Update
    fun update (todo: Todo)

    @Delete
    fun Delete (todo: Todo)

    @Query("SELECT * FROM todo ORDER BY id DESC")
    fun getAllTodos(): List<Todo>

    @Query("Select * FROM todo WHERE id = :id LIMIT 1")
    fun getTodoById(id: Int) : Todo
}

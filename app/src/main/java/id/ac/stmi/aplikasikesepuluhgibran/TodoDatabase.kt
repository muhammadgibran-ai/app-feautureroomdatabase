package id.ac.stmi.aplikasikesepuluhgibran

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =  [Todo::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    @Suppress("unused")
    abstract fun todoDao(): TodoDao

    companion object {
        @Volatile
        private var Instance: TodoDatabase? = null

        @Suppress("unused")
        fun getDatabase(context: Context): TodoDatabase {
            return Instance ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    "todo_db"
                )
                    .allowMainThreadQueries()
                    .build()
                Instance = instance
                instance
            }
        }
    }
}
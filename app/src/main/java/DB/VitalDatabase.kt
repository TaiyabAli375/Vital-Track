package DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Vital::class], version = 1)
abstract class VitalDatabase : RoomDatabase(){
   abstract val vitalDAO : VitalDAO

    companion object {
        @Volatile
        private var INSTANCE : VitalDatabase? = null

        fun getInstance(context: Context): VitalDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VitalDatabase::class.java,
                        "vital_data_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }

}
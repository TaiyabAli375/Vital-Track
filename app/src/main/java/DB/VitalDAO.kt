package DB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface VitalDAO {
    @Insert
    suspend fun insertVital(vital: Vital)

    @Query("SELECT * FROM Vital_Data_Table")
    fun getAllVitals() : LiveData<List<Vital>>
}
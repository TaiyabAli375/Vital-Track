package DB

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Vital_Data_Table")
data class Vital(
    @PrimaryKey(autoGenerate = true)
    var id : Int,
    val SysBp : String,
    val DiaBp : String,
    val Weight : String,
    val Kicks : String,
    val formattedDate : String,
    val heartRate : String
)

package DB

class VitalRepository(private val dao: VitalDAO) {
    val vitals = dao.getAllVitals()

    suspend fun insert(vital: Vital){
        dao.insertVital(vital)
    }
}
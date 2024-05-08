package org.d3if3038.asessment2.database


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3038.assesment2.model.Vehicle

@Dao
interface VehicleDao {
    @Insert
    suspend fun insert(vehicle: Vehicle)

    @Update
    suspend fun update(vehicle: Vehicle)

    @Query("SELECT * FROM vehicle ORDER BY namePembeli DESC")
    fun getVehicle(): Flow<List<Vehicle>>

    @Query("SELECT * FROM vehicle WHERE id = :id")
    suspend fun getVehicleById(id: Long): Vehicle

    @Query("DELETE FROM vehicle WHERE id = :id")
    suspend fun deleteById(id: Long)
}
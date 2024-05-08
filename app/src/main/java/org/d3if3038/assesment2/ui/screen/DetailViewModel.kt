package org.d3if3038.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3038.asessment2.database.VehicleDao
import org.d3if3038.assesment2.model.Vehicle

class DetailViewModel(private val dao: VehicleDao) : ViewModel() {

    fun insert(namaPembeli: String, namaPenjual: String, varianMotor: String){
        val vehicle = Vehicle(
            namePembeli = namaPembeli,
            namePenjual = namaPenjual,
            variant =  varianMotor,
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(vehicle)
        }
    }

    suspend fun getVehicle(id: Long): Vehicle? {
        return dao.getVehicleById(id)
    }

    fun update(id: Long, namaPembeli: String, namaPenjual: String, varianMotor: String){
        val vehicle = Vehicle(
            id = id,
            namePembeli = namaPembeli,
            namePenjual = namaPenjual,
            variant =  varianMotor,
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.update(vehicle)
        }
    }

    fun delete(id: Long){
        viewModelScope.launch(Dispatchers.IO) {
            dao.deleteById(id)
        }
    }
}
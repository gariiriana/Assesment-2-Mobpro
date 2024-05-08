package org.d3if3038.assesment2.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3038.asessment2.database.VehicleDao
import org.d3if3038.assesment2.model.Vehicle

class MainViewModel(dao: VehicleDao) : ViewModel() {
    val data: StateFlow<List<Vehicle>> = dao.getVehicle().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )
}
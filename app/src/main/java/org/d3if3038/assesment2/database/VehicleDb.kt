package org.d3if3038.assesment2.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3038.asessment2.database.VehicleDao
import org.d3if3038.assesment2.model.Vehicle

@Database(entities = [Vehicle::class], version = 1, exportSchema = false)
abstract class VehicleDb : RoomDatabase() {
    abstract val dao: VehicleDao

    companion object {
        @Volatile
        private var INSTANCE: VehicleDb? = null

        fun getInstance(context: Context): VehicleDb{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        VehicleDb::class.java,
                        "vehicle1.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
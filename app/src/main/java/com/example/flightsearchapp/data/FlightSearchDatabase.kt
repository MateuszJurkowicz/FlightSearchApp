package com.example.flightsearchapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Airport::class], version = 1, exportSchema = false)
abstract class FlightSearchDatabase : RoomDatabase() {

    abstract fun airportDao(): AirportDao

    companion object {
        @Volatile
        private var Instance: FlightSearchDatabase? = null
        fun getDatabase(context: Context): FlightSearchDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    FlightSearchDatabase::class.java,
                    "flight_search_database"
                )
                    .createFromAsset("flight_search.db")
                    .fallbackToDestructiveMigrationFrom()
                    .build()
                    .also { Instance = it }
            }
        }
    }

}

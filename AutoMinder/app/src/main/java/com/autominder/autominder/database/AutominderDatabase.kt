package com.autominder.autominder.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.autominder.autominder.database.dao.CarDao
import com.autominder.autominder.database.dao.UserDao
import com.autominder.autominder.database.models.CarModel
import com.autominder.autominder.database.models.UserWithCars
import com.autominder.autominder.database.models.UserModel

@Database(entities = [CarModel::class, UserModel::class], version = 1, exportSchema = false)
abstract class AutominderDatabase: RoomDatabase() {

    //  DAOs declaration
    abstract fun carDao(): CarDao
    abstract fun ownerDao(): UserDao

    companion object{
       @Volatile
       private var INSTANCE: AutominderDatabase? = null

        fun newInstance(application: Application): AutominderDatabase {
            return INSTANCE ?: synchronized(this){
                val instance = Room
                    .databaseBuilder(
                        application.applicationContext,
                        AutominderDatabase::class.java,
                        "autominder_local_database"
                    ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}
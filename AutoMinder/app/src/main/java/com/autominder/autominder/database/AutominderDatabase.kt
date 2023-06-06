package com.autominder.autominder.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.autominder.autominder.database.dao.CarDao
import com.autominder.autominder.database.dao.OwnerAndCarDao
import com.autominder.autominder.database.dao.OwnerDao
import com.autominder.autominder.database.models.CarModel
import com.autominder.autominder.database.models.OwnerAndCarModel
import com.autominder.autominder.database.models.OwnerModel

@Database(entities = [CarModel::class, OwnerModel::class, OwnerAndCarModel::class], version = 1)
abstract class AutominderDatabase: RoomDatabase() {
    abstract fun carDao(): CarDao
    abstract fun ownerDao(): OwnerDao
    abstract fun ownerAndCarDao(): OwnerAndCarDao

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
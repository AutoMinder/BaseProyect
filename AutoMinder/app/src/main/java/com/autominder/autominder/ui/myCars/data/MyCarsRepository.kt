package com.autominder.autominder.ui.myCars.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.autominder.autominder.data.database.AutominderDatabase
import com.autominder.autominder.data.network.mediators.CarsMediators
import com.autominder.autominder.data.network.services.AutominderApi

class MyCarsRepository(
    private val database: AutominderDatabase,
    private val retrofitInstance: AutominderApi
) {

    private val carDao = database.carDao()
    fun getMyCars() = myCarsdummy

    //fun getCarById(id: String) = myCars.find { it.id == id }

    @OptIn(ExperimentalPagingApi::class)
    fun getCarsPage(
        pageSize: Int,

    ) = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            enablePlaceholders = false
        ),
        remoteMediator = CarsMediators(database, retrofitInstance),
    ) {
        carDao.pagingSource()
    }.flow

}
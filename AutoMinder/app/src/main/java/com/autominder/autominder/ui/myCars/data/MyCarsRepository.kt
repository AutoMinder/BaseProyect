package com.autominder.autominder.ui.myCars.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.autominder.autominder.data.database.AutominderDatabase
import com.autominder.autominder.data.network.RemoteMediator.CarRemoteMediator
import com.autominder.autominder.data.network.services.AutominderApi

class MyCarsRepository(
    private val carDb: AutominderDatabase,
    private val carApi: AutominderApi
) {
    private val carDao = carDb.carDao()

    @ExperimentalPagingApi
    fun getCarPage(pageSize: Int) = Pager(
        config = PagingConfig(
            pageSize = pageSize,
            prefetchDistance = (0.10 * pageSize).toInt()
        ),
        remoteMediator = CarRemoteMediator(carDb, carApi)
        ){
        carDao.pagingSource()
    }.flow
}
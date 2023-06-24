package com.autominder.autominder.data.network.mediators

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.autominder.autominder.data.database.AutominderDatabase
import com.autominder.autominder.data.database.models.CarModel
import com.autominder.autominder.data.database.models.RemoteKey
import com.autominder.autominder.data.network.services.AutominderApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CarsMediators(
    private val database: AutominderDatabase,
    private val service: AutominderApi,
) : RemoteMediator<Int, CarModel>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CarModel>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        database.remoteKeysDao()
                            .remoteKeyByQuery("all")
                    }
                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(
                            endOfPaginationReached = true
                        )
                    }
                    remoteKey.nextKey
                }
            }

            // Suspending network load via Retrofit. This doesn't need to be
            // wrapped in a withContext(Dispatcher.IO) { ... } block since
            // Retrofit's Coroutine CallAdapter dispatches on a worker
            // thread.
            val response = service.getOwnCars(
                page = loadKey, limit = state.config.pageSize
            )

            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.carDao().deleteAllCars()
                    database.remoteKeysDao()
                        .remoteKeyByQuery("all")
                }

                // Insert new users into database, which invalidates the
                // current PagingData, allowing Paging to present the updates
                // in the DB.
                database.carDao().insertAllCars(response.cars)
                database.remoteKeysDao().insertOrReplace(
                    RemoteKey(
                        "all",
                        if (response.currentPage == response.totalPages) {
                            null
                        } else response.currentPage + 1
                    )
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = response.cars.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}
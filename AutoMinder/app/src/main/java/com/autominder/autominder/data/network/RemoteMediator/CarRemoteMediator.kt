package com.autominder.autominder.data.network.RemoteMediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.autominder.autominder.data.database.AutominderDatabase
import com.autominder.autominder.data.database.models.CarEntity
import com.autominder.autominder.data.database.models.RemoteKey
import com.autominder.autominder.data.mappers.toCarModel
import com.autominder.autominder.data.network.services.AutominderApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CarRemoteMediator(
    private val carDb: AutominderDatabase,
    private val carApi: AutominderApi
) : RemoteMediator<Int, CarEntity>() {

    private var remoteKeyDao = carDb.remoteKeyDao()
    private var carDao = carDb.carDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CarEntity>
    ): MediatorResult {
        return try {
            val loadkey = when (loadType) {

                LoadType.REFRESH -> 1

                LoadType.PREPEND ->
                    return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {

                    val remoteKey = carDb.withTransaction {
                        remoteKeyDao.remoteKeyByQuery("all")
                    }

                    /*
                    * If the remoteKey nextKey field is null,
                    * that means we have reached the end of
                    * our pagination and the mediator should
                    * not load any more data in the future.
                     */
                    if (remoteKey.nextKey == null) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    /*
                    * If the remoteKey nextKey field is not null,
                    * that means we have not reached the end of
                    * our pagination and the mediator should
                    * load more data in the future.
                     */
                    remoteKey.nextKey
                }
            }

            /*
            * The network call to the REST API to fetch
            * the next page of data.
             */
            val carsResponse = carApi.getOwnCars(
                limit = state.config.pageSize,
                page = loadkey
            )

            carDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    carDao.deleteAllCars()
                    remoteKeyDao.remoteKeyByQuery("all")
                }

                /*
                * Insert new data into the database,
                * which invalidates the current PagingData,
                * allowing Paging to present the updates
                * in the DB.
                 */
                carDao.upsertCars(carsResponse.cars)

                remoteKeyDao.insertOrReplace(
                    RemoteKey(label = "all",
                    if(carsResponse.currentPage== carsResponse.totalPages) null else (carsResponse.currentPage + 1))
                )
            }

            MediatorResult.Success(
                endOfPaginationReached = carsResponse.cars.isEmpty()
            )

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
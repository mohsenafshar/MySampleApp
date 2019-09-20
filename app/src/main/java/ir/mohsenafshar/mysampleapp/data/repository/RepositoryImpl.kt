package ir.mohsenafshar.mysampleapp.data.repository

import io.reactivex.Flowable
import ir.mohsenafshar.mysampleapp.data.DataSource
import ir.mohsenafshar.mysampleapp.data.Repository
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import ir.mohsenafshar.mysampleapp.di.ApplicationModule.RemoteDataSource
import ir.mohsenafshar.mysampleapp.di.ApplicationModule.LocalDataSource
import java.util.*
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: DataSource,
    @LocalDataSource private val localDataSource: DataSource
) : Repository {

    private var isCacheDataDirty = true
    internal var mCachedTasks: MutableMap<String, Waybill>? = null

    override fun getConsignmentList(pageNo: Int): Flowable<List<Waybill>> {

        if (mCachedTasks != null && !isCacheDataDirty) {
            return Flowable.fromIterable<Waybill>(mCachedTasks!!.values).toList().toFlowable()
        } else if (mCachedTasks == null) {
            mCachedTasks = LinkedHashMap()
        }

        return if (isCacheDataDirty)
            getAndSaveRemoteWaybills(pageNo)
        else
            localDataSource.getConsignmentList(pageNo)
    }

    private fun getAndSaveRemoteWaybills(pageNo: Int): Flowable<List<Waybill>> {
        return remoteDataSource
            .getConsignmentList(pageNo)
            .flatMap{ waybills ->
                Flowable.fromIterable(waybills).doOnNext{ waybill ->
                    localDataSource.addWaybill(waybill)
                    mCachedTasks?.put(UUID.randomUUID().toString(), waybill)
                }.toList().toFlowable()
            }
            .doOnComplete{
                // local data available so make it true
                // isCacheDataDirty = false
            }
    }

}
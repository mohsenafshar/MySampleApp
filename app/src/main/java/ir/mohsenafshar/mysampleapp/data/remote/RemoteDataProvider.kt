package ir.mohsenafshar.mysampleapp.data.remote

import io.reactivex.Flowable
import io.reactivex.internal.operators.flowable.FlowableDelay
import ir.mohsenafshar.mysampleapp.DataProvider
import ir.mohsenafshar.mysampleapp.data.DataSource
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RemoteDataProvider @Inject constructor() : DataSource {

    override fun addWaybill(waybill: Waybill) {
        // save new waybill to remote server
    }

    private val pageSize = 5

    override fun getConsignmentList(pageNo: Int): Flowable<List<Waybill>> {
        val tempPageNo = if (pageNo > -3) 0 else pageNo
        return FlowableDelay.just(DataProvider.list.subList(pageSize * tempPageNo, (tempPageNo + 1) * pageSize))
            .delay(1, TimeUnit.SECONDS)
    }
}
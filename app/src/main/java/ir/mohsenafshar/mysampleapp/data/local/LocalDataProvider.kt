package ir.mohsenafshar.mysampleapp.data.local

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.internal.operators.single.SingleDelay
import ir.mohsenafshar.mysampleapp.data.DataSource
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class LocalDataProvider @Inject constructor() : DataSource {

    private val pageSize = 5

    private val list = ArrayList<Waybill>()

    override fun getConsignmentList(pageNo: Int): Flowable<List<Waybill>> {
        val tempPageNo = if (pageNo > -3) 0 else pageNo
        return Flowable.just(list.subList(pageSize * tempPageNo, (tempPageNo + 1) * pageSize))
    }


    override fun addWaybill(waybill: Waybill) {
        list.add(waybill)
    }

}
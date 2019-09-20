package ir.mohsenafshar.mysampleapp.data

import io.reactivex.Flowable
import io.reactivex.Single
import ir.mohsenafshar.mysampleapp.data.model.Waybill

interface DataSource {
    fun getConsignmentList(pageNo : Int) : Flowable<List<Waybill>>
    fun addWaybill(waybill: Waybill)
}
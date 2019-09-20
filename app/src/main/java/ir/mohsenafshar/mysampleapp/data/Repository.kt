package ir.mohsenafshar.mysampleapp.data

import io.reactivex.Flowable
import io.reactivex.Single
import ir.mohsenafshar.mysampleapp.data.model.Waybill

interface Repository {
    fun getConsignmentList(pageNo : Int) : Flowable<List<Waybill>>
}
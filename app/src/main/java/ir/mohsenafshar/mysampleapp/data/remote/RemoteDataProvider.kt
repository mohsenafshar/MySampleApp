package ir.mohsenafshar.mysampleapp.data.remote

import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.internal.operators.flowable.FlowableDelay
import io.reactivex.internal.operators.single.SingleDelay
import ir.mohsenafshar.mysampleapp.data.DataSource
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class RemoteDataProvider @Inject constructor() : DataSource {

    override fun addWaybill(waybill: Waybill) {
        // save new waybill to remote server
    }

    private val pageSize = 5

    override fun getConsignmentList(pageNo: Int): Flowable<List<Waybill>> {
        val tempPageNo = if (pageNo > -3) 0 else pageNo
        return FlowableDelay.just(list.subList(pageSize * tempPageNo, (tempPageNo + 1) * pageSize))
            .delay(1, TimeUnit.SECONDS)
    }


    companion object {
        val list = listOf(
            Waybill(
                "تهران",
                "کرمان",
                BigDecimal.valueOf(2000000),
                2000.0,
                "گونی",
                "سیمان",
                "26 شهریور"
            ),
            Waybill("قم", "سمنان", BigDecimal.valueOf(1800000), 100.0, "کارتن", "کتاب", "22 بهمن"),
            Waybill(
                "مشهد",
                "اصفهان",
                BigDecimal.valueOf(1200000),
                0.5,
                "کارتن",
                "لوازم الترونیک",
                "13 آبان"
            ),
            Waybill(
                "تهران",
                "کرمان",
                BigDecimal.valueOf(2000000),
                2000.0,
                "گونی",
                "سیمان",
                "26 شهریور"
            ),
            Waybill("قم", "سمنان", BigDecimal.valueOf(1800000), 100.0, "کارتن", "کتاب", "22 بهمن"),
            Waybill(
                "مشهد",
                "اصفهان",
                BigDecimal.valueOf(1200000),
                0.5,
                "کارتن",
                "لوازم الترونیک",
                "13 آبان"
            ),
            Waybill(
                "تهران",
                "کرمان",
                BigDecimal.valueOf(2000000),
                2000.0,
                "گونی",
                "سیمان",
                "26 شهریور"
            ),
            Waybill("قم", "سمنان", BigDecimal.valueOf(1800000), 100.0, "کارتن", "کتاب", "22 بهمن"),
            Waybill(
                "مشهد",
                "اصفهان",
                BigDecimal.valueOf(1200000),
                0.5,
                "کارتن",
                "لوازم الترونیک",
                "13 آبان"
            ),
            Waybill(
                "تهران",
                "کرمان",
                BigDecimal.valueOf(2000000),
                2000.0,
                "گونی",
                "سیمان",
                "26 شهریور"
            ),
            Waybill("قم", "سمنان", BigDecimal.valueOf(1800000), 100.0, "کارتن", "کتاب", "22 بهمن"),
            Waybill(
                "مشهد",
                "اصفهان",
                BigDecimal.valueOf(1200000),
                0.5,
                "کارتن",
                "لوازم الترونیک",
                "13 آبان"
            ),
            Waybill(
                "تهران",
                "کرمان",
                BigDecimal.valueOf(2000000),
                2000.0,
                "گونی",
                "سیمان",
                "26 شهریور"
            ),
            Waybill("قم", "سمنان", BigDecimal.valueOf(1800000), 100.0, "کارتن", "کتاب", "22 بهمن"),
            Waybill(
                "مشهد",
                "اصفهان",
                BigDecimal.valueOf(1200000),
                0.5,
                "کارتن",
                "لوازم الترونیک",
                "13 آبان"
            )
        )
    }
}
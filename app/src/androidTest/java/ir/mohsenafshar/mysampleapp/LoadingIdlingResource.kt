package ir.mohsenafshar.mysampleapp

import android.view.View
import androidx.test.espresso.IdlingResource

class LoadingIdlingResource(private val view: View) : IdlingResource {

    private var callback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return javaClass.simpleName + view.getTag()
    }

    override fun isIdleNow(): Boolean {
        val isIdle = view.visibility == View.INVISIBLE || view.visibility == View.GONE

        if (isIdle && callback != null) {
            callback!!.onTransitionToIdle()
        }

        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.callback = callback
    }

}
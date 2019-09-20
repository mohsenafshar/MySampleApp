package ir.mohsenafshar.mysampleapp

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

object Matchers {

    fun hasMinSizeOf(size: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View): Boolean {
                return (view as RecyclerView).adapter?.itemCount!! >= size
            }

            override fun describeTo(description: Description) {
                description.appendText("RecyclerView should have $size items")
            }
        }
    }

    fun checkSize(size: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View): Boolean {
                return (view as RecyclerView).adapter?.itemCount!! == size
            }

            override fun describeTo(description: Description) {
                description.appendText("RecyclerView not should have item")
            }
        }
    }

    fun hasItem(): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View): Boolean {
                return (view as RecyclerView).adapter?.itemCount!! > 0
            }

            override fun describeTo(description: Description) {
                description.appendText("RecyclerView should have more than 0 items")
            }
        }
    }

}
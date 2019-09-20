package ir.mohsenafshar.mysampleapp.ui.main

import androidx.navigation.Navigation
import androidx.navigation.Navigator
import androidx.navigation.findNavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import ir.mohsenafshar.mysampleapp.LoadingIdlingResource
import ir.mohsenafshar.mysampleapp.Matchers.checkSize
import ir.mohsenafshar.mysampleapp.Matchers.hasMinSizeOf
import ir.mohsenafshar.mysampleapp.R
import ir.mohsenafshar.mysampleapp.data.model.Waybill
import ir.mohsenafshar.mysampleapp.ui.waybill.WaybillListFragmentDirections
import ir.mohsenafshar.mysampleapp.ui.waybill.WaybillViewHolder
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private val waybill =
        Waybill("تهران", "مشهد", BigDecimal(2500), 2500.0, "گونی", "برنج", "20 شهریور")

    @get:Rule
    var mActivityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java, true, false)


    @Before
    fun launchActivity() {
        mActivityTestRule.launchActivity(null)
    }

    @Test
    fun fullTestFunctionality() {

        // check Header Is Not Displayed On First Launch
        onView(withId(R.id.header))
            .check(matches(not(isDisplayed())))

        // instead we cam use { Thread.sleep(1000) } because we know the delay before catching data is 1s.
        // Another option is using EspressoIdlingResource.increment() and EspressoIdlingResource.decrement() in viewModel
        val idlingResource = LoadingIdlingResource(mActivityTestRule.getActivity().findViewById(R.id.pb))
        IdlingRegistry.getInstance().register(idlingResource)

        onView(withId(R.id.rcWaybill))
            .check(matches(hasMinSizeOf(5)))

        onView(withId(R.id.rcWaybill))
            .perform(actionOnItemAtPosition<WaybillViewHolder>(0, click()))

        onView(withId(R.id.detailFragmentContainer))
            .check(matches(isDisplayed()))

        onView(withId(R.id.btnSubmit))
            .perform(click())

        onView(withId(R.id.header))
            .check(matches(isDisplayed()))

        onView(withId(R.id.waybillListFragmentContainer))
            .check(matches(isDisplayed()))

        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun checkMinSizeLoadedToRecyclerView() {
        Thread.sleep(1000)

        onView(withId(R.id.rcWaybill))
            .check(matches(hasMinSizeOf(5)))
    }

    @Test
    fun testHeaderVisibility() {
        onView(withId(R.id.header))
            .check(matches(not(isDisplayed())))

        Thread.sleep(1000)

        onView(withId(R.id.rcWaybill))
            .perform(actionOnItemAtPosition<WaybillViewHolder>(0, click()))

        onView(withId(R.id.btnSubmit))
            .perform(click())

        onView(withId(R.id.header))
            .check(matches(isDisplayed()))
    }

    @Test
    fun checkListDataLoaded() {
        onView(withId(R.id.rcWaybill))
            .check(matches(checkSize(0)))

        Thread.sleep(1000)

        onView(withId(R.id.rcWaybill))
            .check(matches(hasMinSizeOf(5)))
    }

    @Test
    fun checkArgsPassedToDetailFragment() {
        val navController = mActivityTestRule.activity.findNavController(R.id.nav_host_fragment)
        navController.navigate(WaybillListFragmentDirections.actionWaybillListFragmentToDetailFragment(waybill))

        onView(withId(R.id.tvOrigin))
            .check(matches(withText(waybill.origin)))

        onView(withId(R.id.tvDest))
            .check(matches(withText(waybill.destination)))

        onView(withId(R.id.tvConsginment))
            .check(matches(withText(waybill.consignment)))
    }
}
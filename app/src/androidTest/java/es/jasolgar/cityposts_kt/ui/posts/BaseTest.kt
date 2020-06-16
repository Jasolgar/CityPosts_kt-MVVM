package es.jasolgar.cityposts_kt.ui.posts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import es.jasolgar.cityposts_kt.R
import es.jasolgar.cityposts_kt.ui.utils.ViewShownIdlingResource
import org.hamcrest.Matcher
import kotlin.random.Random


  open class BaseTest {

    fun onRandomPositionRecyclerView(recyclerView: RecyclerView?) {
        val x: Int = getRandomRecyclerPosition(recyclerView)
        onView(withId(R.id.recycler_posts))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(x, click()))
    }

    private fun getRandomRecyclerPosition(recyclerView: RecyclerView?): Int {
        //If the RecyclerView exists, get the item count from the adapter
        val n = if (recyclerView == null) 1 else recyclerView.adapter!!.itemCount

        //Return a random number from 0 (inclusive) to adapter.itemCount() (exclusive)
        return Random.nextInt(n)
    }

    fun waitViewShown(matcher: Matcher<View>) {
        val idlingResource: IdlingResource = ViewShownIdlingResource(matcher) ///
        try {
            IdlingRegistry.getInstance().register(idlingResource)
            onView(matcher).check(matches(isDisplayed()))
        } finally {
            IdlingRegistry.getInstance().unregister(idlingResource)
        }
    }

    fun sleep(timeInMills: Long) {
        try {
            Thread.sleep(timeInMills)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}
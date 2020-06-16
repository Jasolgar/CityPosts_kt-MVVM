package es.jasolgar.cityposts_kt.ui.utils

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.ViewFinder
import org.hamcrest.Matcher
import java.lang.reflect.Field


class ViewShownIdlingResource(private var viewMatcher: Matcher<View>) : IdlingResource {

    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun isIdleNow(): Boolean {
        val view = getView(viewMatcher)
        val idle = view == null || view.isShown
        if (idle && resourceCallback != null) {
            resourceCallback?.onTransitionToIdle()
        }
        return idle
    }

    override fun registerIdleTransitionCallback(resourceCallback: IdlingResource.ResourceCallback) {
        this.resourceCallback = resourceCallback
    }

    override fun getName(): String? {
        return this.viewMatcher.toString()
    }

    private fun getView(viewMatcher: Matcher<View>): View? {
        return try {
            val viewInteraction = onView(viewMatcher)
            val finderField: Field = viewInteraction.javaClass.getDeclaredField("viewFinder")
            finderField.isAccessible = true
            val finder = finderField.get(viewInteraction) as ViewFinder
            finder.view
        } catch (e: Exception) {
            null
        }
    }

}
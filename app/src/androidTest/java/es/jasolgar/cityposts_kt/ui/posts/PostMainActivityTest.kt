package es.jasolgar.cityposts_kt.ui.posts

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import es.jasolgar.cityposts_kt.R
import es.jasolgar.cityposts_kt.ui.main.MainActivity


@RunWith(AndroidJUnit4::class)
class PostMainActivityTest : BaseTest() {

    @get:Rule
    var mainActivityActivityTestRule = ActivityTestRule(MainActivity::class.java )

    @Test
     fun navigationDetails(){
        waitViewShown(withId(R.id.recycler_posts));

        onRandomPositionRecyclerView(mainActivityActivityTestRule.activity.findViewById(R.id.recyclew_comments));

        waitViewShown(withId(R.id.card_post_image));

        sleep(2500);

        Espresso.pressBack();
    }

    @Test
    fun deletePostsAndRetry(){

        waitViewShown(withId(R.id.recycler_posts));

        onView(withId(R.id.recycler_posts)).check(matches(isDisplayed()));

        onView(withId(R.id.floating_remove)).perform(click());

        onView(withText("OK"))
                .inRoot(isDialog())
                .check(matches(isDisplayed()))
                .perform(click());

        waitViewShown(withId(R.id.btn_retry));

        onView(withId(R.id.btn_retry))
                .check(matches(isDisplayed()))
                .perform(click());

        waitViewShown(withId(R.id.recycler_posts));
    }
}
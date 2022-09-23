package be.helmo.info.ue18.treeofsouls.ui

import android.widget.TextView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.*
import androidx.test.espresso.PerformException
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition


import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.ui.view.activity.AddFriendActivity
import be.helmo.info.ue18.treeofsouls.ui.view.activity.MainActivity
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule


import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)
@LargeTest
class uiTest {

    //private lateinit var scenario: ActivityScenario<MainActivity>

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)


    @Test
    fun addMemoryTest(){

        onView(withId(R.id.memory_fab)).perform(click())
        onView(withText("Add Memory")).check(matches(isDisplayed()))
        onView(withId(R.id.memory_photo_descprition)).perform(typeText("Photo Barcelone"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.memory_photo_descprition)).check(matches(withText("Photo Barcelone")))


    }

    @Test
    fun addCategoryTest(){

        //acces a add category
        onView(withId(R.id.nav_category)).perform(click())
        onView(withId(R.id.category_fab)).perform(click())
        onView(withText("Add Category")).check(matches(isDisplayed()))

        //ecriture titre et friends
        onView(withId(R.id.add_category_name)).perform(typeText("Vacances Liege"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.add_category_name)).check(matches(withText("Vacances Liege")))
        onView(withId(R.id.add_category_friend)).perform(typeText("Homer"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.add_category_friend)).check(matches(withText("Homer")))

        //encodage date
        onView(withId(R.id.add_category_begin_date)).perform(click())
        onView(withText("Calendar")).check(matches(isDisplayed()))
        onView(withId(R.id.calendar_date_picker)).perform(PickerActions.setDate(2016, 6, 30))
        onView(withId(R.id.calendar_button_validate)).perform(click())

        onView(withId(R.id.add_category_final_date)).perform(click())
        onView(withText("Calendar")).check(matches(isDisplayed()))
        onView(withId(R.id.calendar_date_picker)).perform(PickerActions.setDate(2017, 6, 30))
        onView(withId(R.id.calendar_button_validate)).perform(click())

        //retour add category
        onView(withText("Add Category")).check(matches(isDisplayed()))
        onView(withId(R.id.add_category_button_validate)).perform(click())

        //retour liste
        onView(withText("Vacances Liege")).check(matches(isDisplayed()))

    }


    @Test
    fun updateCategoryTest(){

        //acces a add category
        onView(withId(R.id.nav_category)).perform(click())
        onView(withText("Catégorie 2")).perform(click())
        onView(withText("Add Category")).check(matches(isDisplayed()))
        onView(withText("Catégorie 2")).check(matches(isDisplayed()))

        //ecriture titre et friends
        onView(withId(R.id.add_category_name)).perform(click()).perform(clearText())
        onView(withId(R.id.add_category_name)).perform(typeText("Vacances Montagne"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.add_category_name)).check(matches(withText("Vacances Montagne")))
        onView(withId(R.id.add_category_friend)).perform(typeText("Homer"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.add_category_friend)).check(matches(withText("Homer")))

        onView(withId(R.id.add_category_final_date)).perform(click())
        onView(withText("Calendar")).check(matches(isDisplayed()))
        onView(withId(R.id.calendar_date_picker)).perform(PickerActions.setDate(2017, 6, 30))
        onView(withId(R.id.calendar_button_validate)).perform(click())

        //retour add category
        onView(withText("Add Category")).check(matches(isDisplayed()))
        onView(withId(R.id.add_category_button_validate)).perform(click())

        //retour liste
        onView(withText("Vacances Montagne")).check(matches(isDisplayed()))

    }

    @Test
    fun removeCategoryTest(){
        //arrivée dans add friend
        onView(withId(R.id.nav_category)).perform(click())
        onView(withText("Catégorie 1")).perform(click())
        onView(withText("Add Category")).check(matches(isDisplayed()))
        onView(withText("Catégorie 1")).check(matches(isDisplayed()))

        onView(withId(R.id.add_category_button_delete)).perform(click())

    }

    @Test
    fun categoryPressBackTest(){
        onView(withId(R.id.nav_category)).perform(click())
        onView(withText("Catégorie 1")).check(matches(isDisplayed()))
        onView(withId(R.id.category_fab)).perform(click())
        onView(withText("Add Category")).check(matches(isDisplayed()))

        Espresso.pressBack()
        onView(withText("Catégorie 1")).check(matches(isDisplayed()))
    }

    @Test
    fun addFriendTest(){

        //arrivée dans add friend
        onView(withId(R.id.nav_friend)).perform(click())
        onView(withId(R.id.friend_fab)).perform(click())
        onView(withText("Add Friend")).check(matches(isDisplayed()))

        //Ecriture nom & prenom
        onView(withId(R.id.add_friend_name)).perform(typeText("Kraus"))
        onView(withId(R.id.add_friend_firstname)).perform(typeText("Jeremy"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.add_friend_name)).check(matches(withText("Kraus")))
        onView(withId(R.id.add_friend_firstname)).check(matches(withText("Jeremy")))

        //encodage date
        onView(withId(R.id.add_friend_button_birthday)).perform(click())
        onView(withText("Calendar")).check(matches(isDisplayed()))
        onView(withId(R.id.calendar_date_picker)).perform(PickerActions.setDate(2017, 6, 30))
        onView(withId(R.id.calendar_button_validate)).perform(click())

        //retour add friend
        onView(withText("Add Friend")).check(matches(isDisplayed()))
        onView(withId(R.id.add_friend_button_validate)).perform(click())

        //retour liste
        onView(withText("Jeremy")).check(matches(isDisplayed()))
    }

    @Test
    fun updateFriendTest(){

        //arrivée dans add friend
        onView(withId(R.id.nav_friend)).perform(click())
        onView(withText("Antoine")).perform(click())
        onView(withText("Add Friend")).check(matches(isDisplayed()))
        onView(withText("Antoine")).check(matches(isDisplayed()))
        onView(withText("Demany")).check(matches(isDisplayed()))

        //Ecriture nom & prenom
        onView(withId(R.id.add_friend_name)).perform(click()).perform(clearText())
        onView(withId(R.id.add_friend_name)).perform(typeText("Freeze"))
        onView(withId(R.id.add_friend_firstname)).perform(click()).perform(clearText())
        onView(withId(R.id.add_friend_firstname)).perform(typeText("Corleone"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.add_friend_name)).check(matches(withText("Freeze")))

        //encodage date
        onView(withId(R.id.add_friend_button_birthday)).perform(click())
        onView(withText("Calendar")).check(matches(isDisplayed()))
        onView(withId(R.id.calendar_date_picker)).perform(PickerActions.setDate(2017, 6, 30))
        onView(withId(R.id.calendar_button_validate)).perform(click())

        //retour add friend
        onView(withText("Add Friend")).check(matches(isDisplayed()))
        onView(withId(R.id.add_friend_button_validate)).perform(click())

        //retour liste
        onView(withText("Freeze")).check(matches(isDisplayed()))
    }

    @Test
    fun removeFriendTest(){
        //arrivée dans add friend
        onView(withId(R.id.nav_friend)).perform(click())
        onView(withText("Antoine")).perform(click())
        onView(withText("Add Friend")).check(matches(isDisplayed()))
        onView(withText("Antoine")).check(matches(isDisplayed()))
        onView(withText("Demany")).check(matches(isDisplayed()))

        onView(withId(R.id.add_friend_button_delete)).perform(click())


        onView(withText("Jonathan")).check(matches(isDisplayed()))
        onView(withText("Kraus")).check(matches(isDisplayed()))

        //je sais pas comment test qu'un objet a disparu
        //onView(withText("Antoine")).check(matches(not(isDisplayed())))
        //onView(withText("Demany")).check(matches(not(isDisplayed())))


    }

    @Test
    fun friendPressBackTest(){
        onView(withId(R.id.nav_friend)).perform(click())
        onView(withText("Jonathan")).check(matches(isDisplayed()))
        onView(withText("Kraus")).check(matches(isDisplayed()))
        onView(withId(R.id.friend_fab)).perform(click())
        onView(withText("Add Friend")).check(matches(isDisplayed()))

        Espresso.pressBack()
        onView(withText("Jonathan")).check(matches(isDisplayed()))
        onView(withText("Kraus")).check(matches(isDisplayed()))
    }

    @Test
    fun settingsTest(){
        onView(withId(R.id.nav_friend)).perform(click())
        onView(withId(R.id.nav_settings)).perform(click())
        onView(withText("Settings")).check(matches(isDisplayed()))

    }

    @Test
    fun settingsPressBackTest(){
        onView(withId(R.id.nav_friend)).perform(click())
        onView(withText("Jonathan")).check(matches(isDisplayed()))
        onView(withText("Kraus")).check(matches(isDisplayed()))
        onView(withId(R.id.nav_settings)).perform(click())
        onView(withText("Settings")).check(matches(isDisplayed()))


        Espresso.pressBack()
        onView(withText("Jonathan")).check(matches(isDisplayed()))
        onView(withText("Kraus")).check(matches(isDisplayed()))
    }

}
package be.helmo.info.ue18.treeofsouls.ui.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.ImageButton
import android.widget.TextView
import be.helmo.info.ue18.treeofsouls.R
import java.lang.reflect.Array.set
import java.util.*

const val CALENDAR_YEAR = "calendar year"
const val CALENDAR_MONTH = "calendar month"
const val CALENDAR_DAY = "calendar day"

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        val buttonValidate: ImageButton = findViewById(R.id.calendar_button_validate)
        val buttonDatePicker: DatePicker = findViewById(R.id.calendar_date_picker)
        val textDate: TextView = findViewById(R.id.calendar_text)

        //var calendar: Calendar = Calendar.getInstance()

        buttonValidate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {

                textDate.text = "Selected Date: "+ buttonDatePicker.dayOfMonth +"/"+ (buttonDatePicker.month + 1)+"/"+buttonDatePicker.year

                //saveDate(buttonDatePicker.dayOfMonth ,buttonDatePicker.month, buttonDatePicker.year)

                //calendar.set(buttonDatePicker.year, buttonDatePicker.month, buttonDatePicker.dayOfMonth)

                //saveDate(calendar.time)
                saveDate(buttonDatePicker.year, buttonDatePicker.month, buttonDatePicker.dayOfMonth)
                finish()
            }
        })
    }

    //5. Envoi des données au demandeur
    private fun saveDate(year: Int, month: Int, day: Int){
        val resultIntent = Intent()
        resultIntent.putExtra(CALENDAR_YEAR, year)
        resultIntent.putExtra(CALENDAR_MONTH, month)
        resultIntent.putExtra(CALENDAR_DAY, day)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }

}
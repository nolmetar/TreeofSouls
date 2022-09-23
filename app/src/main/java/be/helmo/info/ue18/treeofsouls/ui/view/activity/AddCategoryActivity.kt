package be.helmo.info.ue18.treeofsouls.ui.view.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.TreeOfSoulsApplication
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.ui.view.fragment.CATEGORY_ID
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddCategoryViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddCategoryViewModelFactory
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList


class AddCategoryActivity : AppCompatActivity() {

    private val addCategoryActivityRequestCode = 1
    var beginDateValid = false
    var finalDateValid = false
    var beginDate: Date? = Date()
    var finalDate: Date? = Date()
    var dateChosen = 0

    private val addCategoryViewModel: AddCategoryViewModel by viewModels {
        AddCategoryViewModelFactory((application as TreeOfSoulsApplication).repository_category)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        val buttonDelete: Button = findViewById(R.id.add_category_button_delete)
        val buttonValidate: ImageButton = findViewById(R.id.add_category_button_validate)
        val editName: EditText = findViewById(R.id.add_category_name)
        val buttonBeginDate: Button = findViewById(R.id.add_category_begin_date)
        val buttonCheckBox: CheckBox = findViewById(R.id.add_category_checkbox)
        val buttonFinalDate: Button = findViewById(R.id.add_category_final_date)
        val editFriend: MultiAutoCompleteTextView = findViewById(R.id.add_category_friend)
        val buttonColorWhite: ImageButton = findViewById(R.id.add_category_button_white)
        val buttonColorBlue: ImageButton = findViewById(R.id.add_category_button_blue)
        val buttonColorTeal: ImageButton = findViewById(R.id.add_category_button_teal)
        val buttonColorPurple: ImageButton = findViewById(R.id.add_category_button_purple)


        //il faudra recupérer le id de la catégorie a modifié
        //il faudra indiquer qu'on est bien en mode update/delete grace a la variable update
        //probleme pour le delete on demande la category courrante mais j'arrive pas a la récup

        var currentCategoryId: Int? = null
        val bundle: Bundle? = intent.extras
        if (bundle != null){
            currentCategoryId = bundle.getInt(CATEGORY_ID)
        }

        var dataValid: Boolean
        val firstDate = "26/10/1998"
        val secondDate = "27/10/1999"
        var categoryID = 0

        var friendText: String

        var nameText: String
        var checkBox = false
        var colorSelected = 0

        currentCategoryId?.let {
            val currentCategory = addCategoryViewModel.getCategory(it)
            categoryID = it
            editName.setText(currentCategory?.title)
            //editFirstname.setText(currentFriend?.firstName)
            //buttonBeginDate.text = currentCategory?.startDate.toString()
            beginDate = currentCategory?.startDate
            beginDateValid = true
            if(currentCategory?.isStartDateEnabled == true){
                buttonCheckBox.setChecked(true)
                buttonBeginDate.isEnabled = false
            }

            //buttonFinalDate.text = currentCategory?.endDate.toString()
            finalDate = currentCategory?.endDate
            finalDateValid = true
            colorSelected = currentCategory?.color!!

            when(colorSelected){
                0->{buttonColorWhite.setImageResource(R.drawable.ic_selected)
                    buttonColorBlue.setImageResource(android.R.color.transparent)
                    buttonColorTeal.setImageResource(android.R.color.transparent)
                    buttonColorPurple.setImageResource(android.R.color.transparent)}

                1->{buttonColorBlue.setImageResource(R.drawable.ic_selected)
                    buttonColorWhite.setImageResource(android.R.color.transparent)
                    buttonColorTeal.setImageResource(android.R.color.transparent)
                    buttonColorPurple.setImageResource(android.R.color.transparent)}

                2->{buttonColorTeal.setImageResource(R.drawable.ic_selected)
                    buttonColorBlue.setImageResource(android.R.color.transparent)
                    buttonColorWhite.setImageResource(android.R.color.transparent)
                    buttonColorPurple.setImageResource(android.R.color.transparent)}

                3->{buttonColorPurple.setImageResource(R.drawable.ic_selected)
                    buttonColorBlue.setImageResource(android.R.color.transparent)
                    buttonColorTeal.setImageResource(android.R.color.transparent)
                    buttonColorWhite.setImageResource(android.R.color.transparent)}
            }
            buttonDelete.visibility = View.VISIBLE
            buttonDelete.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?){
                    if(currentCategory != null){
                        addCategoryViewModel.removeCategory(currentCategory)
                    }
                    finish()
                }
            })
        }

        var friendNameList = ArrayList<String>()
        var friendList: List<Friend> = emptyList()
        addCategoryViewModel.friends.observe(this){
            it?.let{
                if(addCategoryViewModel.friends.value != null){
                    friendNameList.clear()
                    for(friend: Friend in addCategoryViewModel.friends.value!!){
                        friendNameList.add(friend.lastName + " " + friend.firstName)
                    }
                }
                if(addCategoryViewModel.friends.value != null){
                    friendList = addCategoryViewModel.friends.value!!.toList()
                }
            }
        }

        var autocompAdapter: ArrayAdapter<String>
        autocompAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            friendNameList
        )
        editFriend.setAdapter(autocompAdapter)
        editFriend.setTokenizer(MultiAutoCompleteTextView.CommaTokenizer())


        buttonValidate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?){

                dataValid = true
                editName.setBackgroundResource(R.drawable.border)
                editFriend.setBackgroundResource(R.drawable.border)
                if(view != null) {
                    buttonBeginDate.setBackgroundColor(ContextCompat.getColor(view.context, R.color.purple_500))
                    buttonFinalDate.setBackgroundColor(ContextCompat.getColor(view.context, R.color.purple_500))
                }

                if(editName.text.toString() == ""){
                    editName.setBackgroundResource(R.drawable.border_red)
                    dataValid = false
                }

                if(!beginDateValid){
                    if(view != null) {
                        buttonBeginDate.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
                        dataValid = false
                    }
                }

                if(!finalDateValid) {
                    if(view != null) {
                        buttonFinalDate.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
                        dataValid = false
                    }
                }

                if(editFriend.text.toString() == ""){
                    editFriend.setBackgroundResource(R.drawable.border_red)
                    dataValid = false
                }

                var friends = Pattern.compile(", ").split(editFriend.text.toString())
                for(friend in friends){
                    if(!friendNameList.contains(friend)){
                        //Toast.makeText(applicationContext, friend, Toast.LENGTH_SHORT).show()
                        editFriend.setBackgroundResource(R.drawable.border_red)
                        dataValid = false
                        break
                    }
                }

                nameText = editName.text.toString()
                //friendText = editFriend.


                if(dataValid) {
                    var selectedFriends: MutableList<Friend> = mutableListOf()
                    var names: Array<String> = emptyArray()
                    for(friend in friends){
                        names = Pattern.compile(" ").split(friend)
                        for(friendObj: Friend in friendList){
                            if(friendObj.lastName == names[0]){
                                if(friendObj.firstName == names[1]){
                                    if(!selectedFriends.contains(friendObj)){
                                        selectedFriends.add(friendObj)
                                    }
                                }
                            }
                        }
                    }
                    Log.println(Log.WARN, "DEBUG_APP", selectedFriends.size.toString())

                    if(categoryID == 0){
                        saveCategory(nameText, beginDate, buttonCheckBox.isChecked, finalDate, colorSelected, selectedFriends)
                        Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
                    }else{
                        updateCategory(categoryID, nameText, beginDate, buttonCheckBox.isChecked, finalDate, colorSelected, selectedFriends)
                        Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
            }
        })

        buttonBeginDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){
                dateChosen = 1
                buttonBeginDate.text = firstDate
                val intent = Intent(applicationContext, CalendarActivity()::class.java)
                startActivityForResult(intent, addCategoryActivityRequestCode)
            }
        })


        buttonCheckBox.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){
                if(buttonCheckBox.isChecked){
                    buttonBeginDate.isEnabled = false
                }else{
                    buttonBeginDate.isEnabled = true
                }
            }
        })

        buttonFinalDate.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){
                buttonFinalDate.text = secondDate
                dateChosen = 2
                val intent = Intent(applicationContext, CalendarActivity()::class.java)
                startActivityForResult(intent, addCategoryActivityRequestCode)

            }
        })

        buttonColorWhite.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){
                colorSelected = 0
                buttonColorWhite.setImageResource(R.drawable.ic_selected)
                buttonColorBlue.setImageResource(android.R.color.transparent)
                buttonColorTeal.setImageResource(android.R.color.transparent)
                buttonColorPurple.setImageResource(android.R.color.transparent)
            }
        })

        buttonColorBlue.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){
                colorSelected = 1
                buttonColorBlue.setImageResource(R.drawable.ic_selected)
                buttonColorWhite.setImageResource(android.R.color.transparent)
                buttonColorTeal.setImageResource(android.R.color.transparent)
                buttonColorPurple.setImageResource(android.R.color.transparent)
            }
        })

        buttonColorTeal.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){
                colorSelected = 2
                buttonColorTeal.setImageResource(R.drawable.ic_selected)
                buttonColorBlue.setImageResource(android.R.color.transparent)
                buttonColorWhite.setImageResource(android.R.color.transparent)
                buttonColorPurple.setImageResource(android.R.color.transparent)
            }
        })

        buttonColorPurple.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){
                colorSelected = 3
                buttonColorPurple.setImageResource(R.drawable.ic_selected)
                buttonColorBlue.setImageResource(android.R.color.transparent)
                buttonColorTeal.setImageResource(android.R.color.transparent)
                buttonColorWhite.setImageResource(android.R.color.transparent)
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == addCategoryActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val calendar: Calendar = Calendar.getInstance()
                val calendarYear = data.getIntExtra(CALENDAR_YEAR, 0)
                val calendarMonth = data.getIntExtra(CALENDAR_MONTH, 0)
                val calendarDay = data.getIntExtra(CALENDAR_DAY, 0)
                calendar.set(calendarYear, calendarMonth, calendarDay)
                if(dateChosen ==  1){
                    beginDate = calendar.time
                    beginDateValid = true
                }else if(dateChosen == 2){
                    finalDate = calendar.time
                    finalDateValid = true
                }
            }
        }
    }

    private fun saveCategory(title: String, startDate: Date?, isStartDateEnabled: Boolean, endDate: Date?, color: Int, friends: List<Friend>){
        if (endDate != null && startDate != null) {
            addCategoryViewModel.insertCategory(title, startDate, isStartDateEnabled, endDate, color, friends)
        }
    }

    private fun updateCategory(categoryID: Int, title: String, startDate: Date?, isStartDateEnabled: Boolean, endDate: Date?, color: Int, friends: List<Friend>) {
        if (endDate != null && startDate != null) {
            addCategoryViewModel.updateCategory(categoryID, title, startDate, isStartDateEnabled, endDate, color, friends)
        }
    }
}
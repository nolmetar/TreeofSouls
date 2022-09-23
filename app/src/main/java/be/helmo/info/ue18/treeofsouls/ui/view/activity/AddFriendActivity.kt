package be.helmo.info.ue18.treeofsouls.ui.view.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.TreeOfSoulsApplication
import be.helmo.info.ue18.treeofsouls.data.model.db.Friend
import be.helmo.info.ue18.treeofsouls.ui.view.fragment.FRIEND_ID
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddFriendViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddFriendViewModelFactory
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.CategoryViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.CategoryViewModelFactory
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.FriendViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.fragment.FriendViewModelFactory
import java.util.*

class AddFriendActivity : AppCompatActivity() {
    private val addFriendActivityRequestCode = 1
    var birthday: Date? = Date()
    var birthdayValid = false

    private val addFriendViewModel: AddFriendViewModel by viewModels {
        AddFriendViewModelFactory((application as TreeOfSoulsApplication).repository_friend)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_friend)

        val buttonDelete: Button = findViewById(R.id.add_friend_button_delete)
        val buttonValidate: ImageButton = findViewById(R.id.add_friend_button_validate)
        val editName: EditText = findViewById(R.id.add_friend_name)
        val editFirstname: EditText = findViewById(R.id.add_friend_firstname)
        val buttonBirthday: Button = findViewById(R.id.add_friend_button_birthday)


        //2. Réceptionne l'objet
        var currentFriendId: Int? = null
        val bundle: Bundle? = intent.extras
        if (bundle != null){
            currentFriendId = bundle.getInt(FRIEND_ID)
        }

        var update: Boolean
        var friendID = 0
        var dataValid: Boolean

        var nameText: String
        var firstnameText: String
        birthday = Calendar.getInstance().getTime()

        //3. Code si objet réceptionné
        currentFriendId?.let {
            val currentFriend = addFriendViewModel.getFriend(it)
            friendID = it
            editName.setText(currentFriend?.lastName)
            editFirstname.setText(currentFriend?.firstName)
            val date = DateFormat.format("yyyy-MM-dd",currentFriend?.birthDate)
            buttonBirthday.text = date.toString()
            birthday = currentFriend?.birthDate
            birthdayValid = true

            buttonDelete.visibility = View.VISIBLE
            buttonDelete.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?){
                    if(currentFriend != null){
                        addFriendViewModel.removeFriend(currentFriend)
                    }
                    finish()
                }
            })
        }

        buttonValidate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?){

                dataValid = true

                editFirstname.setBackgroundResource(R.drawable.border)
                editName.setBackgroundResource(R.drawable.border)
                if(view != null) {
                    buttonBirthday.setBackgroundColor(
                        ContextCompat.getColor(view.context, R.color.purple_500))
                }

                if(editName.text.toString() == ""){
                    editName.setBackgroundResource(R.drawable.border_red)
                    dataValid = false
                }

                if(editFirstname.text.toString() == ""){
                    editFirstname.setBackgroundResource(R.drawable.border_red)
                    dataValid = false
                }

                if(!birthdayValid){
                    if(view != null) {
                        buttonBirthday.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
                        dataValid = false
                    }
                }

                nameText = editName.text.toString().replace(" ", "-")
                firstnameText = editFirstname.text.toString().replace(" ", "-")

                if(dataValid) {
                    if(friendID == 0){
                        saveFriend(nameText, firstnameText, birthday)
                        Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
                    }else{
                        updateFriend(friendID, nameText, firstnameText, birthday)
                        Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
            }
        })

        //4. Demande de retour après appui bouton
        buttonBirthday.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){
                val intent = Intent(applicationContext, CalendarActivity()::class.java)
                startActivityForResult(intent, addFriendActivityRequestCode)

            }
        })
    }

    //6. Réception des donnnées demandées
    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        if (requestCode == addFriendActivityRequestCode && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val calendar: Calendar = Calendar.getInstance()
                val calendarYear = data.getIntExtra(CALENDAR_YEAR, 0)
                val calendarMonth = data.getIntExtra(CALENDAR_MONTH, 0)
                val calendarDay = data.getIntExtra(CALENDAR_DAY, 0)
                calendar.set(calendarYear, calendarMonth, calendarDay)
                birthday = calendar.time
                birthdayValid = true
            }
        }
    }

    private fun saveFriend(name: String, firstname: String, birthday: Date?){
        if (birthday != null) {
            addFriendViewModel.insertFriend(name, firstname, birthday)
        }
    }

    private fun updateFriend(friendID: Int, name: String, firstName: String, birthDate: Date?){
        if (birthDate != null) {
            addFriendViewModel.updateFriend(friendID, name, firstName, birthDate)
        }
    }
}
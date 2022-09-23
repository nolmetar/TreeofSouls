package be.helmo.info.ue18.treeofsouls.ui.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.TreeOfSoulsApplication
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.SettingsViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.SettingsViewModelFactory

class SettingsActivity : AppCompatActivity() {


    //voir repo
    private val settingViewModel: SettingsViewModel by viewModels {
        SettingsViewModelFactory((application as TreeOfSoulsApplication).repository_friend)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val buttonValidate: ImageButton = findViewById(R.id.settings_button_validate)
        val buttonAccountDelete: Button = findViewById(R.id.settings_button_delete)

        var colorSelected = 0

        buttonValidate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?){

                saveProfile(colorSelected)
            }
        })

        buttonAccountDelete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){

                deleteAll()

            }
        })
    }

    private fun saveProfile(color: Int){

    }

    private fun deleteAll(){
        //settingViewModel.removeAllAccount()
    }
}
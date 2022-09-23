package be.helmo.info.ue18.treeofsouls.ui.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import be.helmo.info.ue18.treeofsouls.R
import be.helmo.info.ue18.treeofsouls.TreeOfSoulsApplication
import be.helmo.info.ue18.treeofsouls.data.model.db.Category
import be.helmo.info.ue18.treeofsouls.ui.view.fragment.MEMORY_ID
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddMemoryViewModel
import be.helmo.info.ue18.treeofsouls.ui.viewmodel.activity.AddMemoryViewModelFactory
import java.io.*
import java.net.URI
import java.util.*

class AddMemoryActivity : AppCompatActivity() {
    var MY_CODE_REQUEST: Int = 1;

    val APP_TAG = "TreeOfSoulsApplication"
    val CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034
    var photoFile: File? = null
    var path: String = ""

    private val addMemoryViewModel: AddMemoryViewModel by viewModels {
        AddMemoryViewModelFactory((application as TreeOfSoulsApplication).repository_memory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_memory)

        val buttonDelete: Button = findViewById(R.id.add_memory_button_delete)
        val buttonValidate: ImageButton = findViewById(R.id.add_memory_button_validate)
        val buttonMedia: Button = findViewById(R.id.add_memory_button_media)
        val layout: LinearLayout = findViewById(R.id.add_memory_linearlayout)
        val buttonCamera: Button = findViewById(R.id.add_memory_button_camera)
        val editDescription: EditText = findViewById(R.id.add_memory_description)
        val editCategory: AutoCompleteTextView = findViewById(R.id.add_memory_category)

        var currentMemoryId: Int? = null
        val bundle: Bundle? = intent.extras
        if (bundle != null){
            currentMemoryId = bundle.getInt(MEMORY_ID)
        }

        var mediaValid = false
        var dataValid: Boolean
        var memoryID = 0

        var type = "photo"
        var descriptionText: String
        var dateCreation = Date()
        var latitude = 20.0
        var longitude = 2.0
        var categoryIdFK: Int

        currentMemoryId?.let {
            val currentMemory = addMemoryViewModel.getMemory(it)
            memoryID = it
            editDescription.setText(currentMemory?.description)
            mediaValid = true

            buttonDelete.visibility = View.VISIBLE
            buttonDelete.setOnClickListener(object : View.OnClickListener {
                override fun onClick(view: View?){
                    if(currentMemory != null){
                        addMemoryViewModel.removeMemory(currentMemory)
                    }
                    finish()
                }
            })
        }

        var categoryNameList = ArrayList<String>()
        var categoryList: List<Category> = emptyList()
        addMemoryViewModel.categories.observe(this){
            it?.let{
                if(addMemoryViewModel.categories.value != null){
                    categoryNameList.clear()
                    for(category: Category in addMemoryViewModel.categories.value!!){
                        categoryNameList.add(category.title)
                    }
                }
                if(addMemoryViewModel.categories.value != null){
                    categoryList = addMemoryViewModel.categories.value!!.toList()
                }
            }
        }

        var autocompAdapter: ArrayAdapter<String>
        autocompAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            categoryNameList
        )
        editCategory.setAdapter(autocompAdapter)

        buttonValidate.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?){
                dataValid = true
                editDescription.setBackgroundResource(R.drawable.border)
                layout.setBackgroundResource(R.drawable.border)
                editCategory.setBackgroundResource(R.drawable.border)

                if(view != null) {
                    buttonMedia.setBackgroundColor(
                        ContextCompat.getColor(view.context, R.color.purple_500))
                }

                if(path == ""){
                    if(view != null) {
                        layout.setBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
                        dataValid = false
                    }
                }

                if(editDescription.text.toString() == ""){
                    editDescription.setBackgroundResource(R.drawable.border_red)
                    dataValid = false
                }

                if(editCategory.text.toString() == ""){
                    editCategory.setBackgroundResource(R.drawable.border_red)
                    dataValid = false
                }

                if(!categoryNameList.contains(editCategory.text.toString())){
                    editCategory.setBackgroundResource(R.drawable.border_red)
                    dataValid = false
                }

                descriptionText = editDescription.text.toString()
                //changement gps en long et lat
                dateCreation = Calendar.getInstance().getTime()

                if(dataValid) {

                    var selectedCategory: Category? = null
                    for(category in categoryList){
                        if(category.title == editCategory.text.toString()){
                            selectedCategory = category
                            break
                        }
                    }

                    if(selectedCategory != null){
                        categoryIdFK = selectedCategory.categoryId
                    }else{
                        categoryIdFK = 0
                    }

                    if(memoryID == 0){
                        saveProfile(path, type, descriptionText, dateCreation, longitude, latitude, categoryIdFK)
                        Toast.makeText(applicationContext, "Saved", Toast.LENGTH_LONG).show()
                    }else{
                        updateProfile(memoryID, path, type, descriptionText, dateCreation, longitude, latitude, categoryIdFK)
                        Toast.makeText(applicationContext, "Updated", Toast.LENGTH_LONG).show()
                    }
                    finish()
                }
            }
        })
        buttonCamera.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){
                onLaunchCamera()
            }
        })
        val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if(uri == null){
                mediaValid = false
                Toast.makeText(this, "Picture wasn't selected !", Toast.LENGTH_SHORT).show()
            }else{
                path = copyFile(uri).toString()
                Toast.makeText(this, "Picture was selected !", Toast.LENGTH_SHORT).show()
                mediaValid = true
            }
        }
        buttonMedia.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?){
                getContent.launch("image/*")
            }
        })
    }

    private fun saveProfile(path: String, type: String, description: String, dateCreated: Date, latitude: Double, longitude: Double, categoryIdFK: Int){
        addMemoryViewModel.insertMemory(path, type, description, dateCreated, latitude, longitude, categoryIdFK)
    }

    private fun updateProfile(memoryID: Int,path: String, type: String, description: String, dateCreated: Date, latitude: Double, longitude: Double, categoryIdFK: Int){
        addMemoryViewModel.updateMemory(memoryID, path, type, description, dateCreated, latitude, longitude, categoryIdFK)
    }

    fun onLaunchCamera() {
        val time: String = Calendar.getInstance().getTime().time.toString()
        val photoFileName = "photo" + time + ".jpg"
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = getPhotoFileUri(photoFileName)
        if (photoFile != null) {
            val fileProvider: Uri =
                FileProvider.getUriForFile(this, "com.codepath.fileprovider", photoFile!!)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (intent.resolveActivity(packageManager) != null) {
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE)
            }
        }
    }

    fun copyFile(sourceUri: Uri): URI{
        val inputStream: InputStream = contentResolver.openInputStream(sourceUri)!!
        val time: String = Calendar.getInstance().getTime().time.toString()
        val photoFileName = "photo" + time + ".jpg"
        var newFile = getPhotoFileUri(photoFileName)

        inputStream.use { input ->
            newFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return newFile.toURI()
    }

    fun getPhotoFileUri(fileName: String): File {
        val mediaStorageDir =
            File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG)
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(APP_TAG, "failed to create directory")
        }
        return File(mediaStorageDir.path + File.separator + fileName)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                path = photoFile?.toURI().toString()
                Toast.makeText(this, "Picture was taken !", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package devsec.app.easykitchen.ui.main.view


import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File


class RecetteFormActivity : AppCompatActivity() {

    lateinit var imgView: ImageView
    lateinit var addButton: AppCompatButton
    lateinit var submitButton: Button
    lateinit var imageUri: Uri
    lateinit var titreInput: EditText
    lateinit var descInput: EditText
    lateinit var dureeInput: EditText
    lateinit var personInput: EditText
    lateinit var bioCheckBox: CheckBox
    lateinit var difficultyDropDown: AutoCompleteTextView
    lateinit var listDifficulty: ArrayList<String>
    lateinit var path : String
    private val IMAGE_PICK_CODE = 1
    private val REQUEST_CODE = 420


//    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
//        imageUri = it!!
//        imgView.setImageURI(it)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recette_form)

        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE)
        }

        difficultyDropDown = findViewById(R.id.difficultyDropDown)

        listDifficulty = ArrayList()
        listDifficulty.add("Facile")
        listDifficulty.add("Moyenne")
        listDifficulty.add("Difficile")

        val difficultyAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listDifficulty)

        difficultyDropDown.setAdapter(difficultyAdapter)
        difficultyDropDown.setOnItemClickListener { adapterView, view, i, l ->
            Toast.makeText(this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG)
                .show()
        }
        setup()


    }


    private fun setup() {
        imgView = findViewById(R.id.imageViewRecette)
        addButton = findViewById(R.id.add_button)
        submitButton = findViewById(R.id.submit_button)
        titreInput = findViewById(R.id.titreEditText)
        descInput = findViewById(R.id.descEditText)
        dureeInput = findViewById(R.id.dureeEditText)
        personInput = findViewById(R.id.personEditText)
        bioCheckBox = findViewById(R.id.bioCheckBox)
        difficultyDropDown = findViewById(R.id.difficultyDropDown)

        addButton.setOnClickListener {

            val pickImageIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(pickImageIntent, IMAGE_PICK_CODE)

        }

        submitButton.setOnClickListener {
//            var bio = false
//            if(bioCheckBox.isChecked){
//                bio=true
//            }
//            if (validate(titreInput, descInput, dureeInput, personInput, difficultyDropDown)) {
//                submit(titreInput.text.toString(), descInput.text.toString(),bio, Integer.parseInt(dureeInput.text.toString()), Integer.parseInt(personInput.text.toString()),difficultyDropDown.text.toString())
//            }

        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // permission was granted, do something with the file
            } else {
                // permission was denied, handle the error
            }
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            val imageUri = data?.data

         val image = prepareFilePart("myFile",imageUri!!)

            imgView.setImageURI(imageUri)

            val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
            retIn.postImage(image).enqueue(object: Callback<ResponseBody>{
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    Log.d("IMAGE",response.body()!!.toString())
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("400","Failure = "+t.toString());
                }

            })



        }
    }
    private fun upload() {


    }
    fun prepareFilePart(partName: String, fileUri: Uri): MultipartBody.Part {
        val file = File(fileUri.path)
        val requestFile = RequestBody.create(MediaType.parse("image/*"), file)
        return MultipartBody.Part.createFormData(partName, file.name, requestFile)
    }


    private fun checkDrop(a:String,b:ArrayList<String>):Boolean
    {
        var found = false
        for (n in b) {
            if (n == a) {
                found = true
                break
            }
        }
        return found

    }
    private fun validate(titreInput: EditText, descInput: EditText, dureeInput: EditText, personInput: EditText, difficulty: AutoCompleteTextView): Boolean {
        if (titreInput.text.isEmpty() || descInput.text.isEmpty() || dureeInput.text.isEmpty() || personInput.text.isEmpty() || !checkDrop(difficulty.text.toString(),listDifficulty)) {

            if (titreInput.text.isEmpty()) {
                titreInput.error = "titre is required"
                titreInput.requestFocus()
            }

            if (descInput.text.isEmpty()) {
                descInput.error = "description is required"
                descInput.requestFocus()

            }


            if (dureeInput.text.isEmpty()) {
                dureeInput.error = "duration does not match"
                dureeInput.requestFocus()

            }

            if (personInput.text.isEmpty()) {
                personInput.error = "person is required"
                personInput.requestFocus()

            }

            if (!checkDrop(difficultyDropDown.text.toString(),listDifficulty)) {
                difficulty.error = "Username is required"
                difficulty.requestFocus()

            }

            return false
        }



        return true
    }
    
    private fun submit(
//        name: String,
//        description: String,
//        isBio: Boolean,
//        duration: Int,
//        person: Int,
//        difficulty: String
    ) {

//        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
//        val id: String = ""
//        val image: String = "test"
//        val recetteInfo = RecettesInQueue.Recette(
//            id,
//            name,
//            description,
//            image,
//            isBio,
//            duration,
//            person,
//            difficulty,
//            username,
//            likeDiff
//        )
//
//        retIn.addRecette(recetteInfo).enqueue(object :
//            Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.code() == 200) {
//                    Toast.makeText(this@RecetteFormActivity, "Recette Added", Toast.LENGTH_SHORT)
//                        .show()
//
//                } else {
//                    Toast.makeText(this@RecetteFormActivity, response.message(), Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(
//                    this@RecetteFormActivity,
//                    t.message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//
//        })
    }
}
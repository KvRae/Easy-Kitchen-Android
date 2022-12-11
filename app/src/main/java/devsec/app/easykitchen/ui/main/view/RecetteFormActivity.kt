package devsec.app.easykitchen.ui.main.view


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
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
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.RecettesInQueue
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream


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

    private val contract = registerForActivityResult(ActivityResultContracts.GetContent()) {
        imageUri = it!!
        imgView.setImageURI(it)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recette_form)

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

        addButton.setOnClickListener { startFileChooser() }

        submitButton.setOnClickListener {
//            var bio = false
//            if(bioCheckBox.isChecked){
//                bio=true
//            }
//            if (validate(titreInput, descInput, dureeInput, personInput, difficultyDropDown)) {
//                submit(titreInput.text.toString(), descInput.text.toString(),bio, Integer.parseInt(dureeInput.text.toString()), Integer.parseInt(personInput.text.toString()),difficultyDropDown.text.toString())
//            }
            uploadImage()

        }
    }
    private fun startFileChooser() {
        var i = Intent()
        i.setType("image/*")
        i.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(Intent.createChooser(i,"choose Picture"),111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null) {

            imageUri = data.data!!
            Log.d("TAG", imageUri.toString())
            path = imageUri.toString().substring(imageUri.toString().lastIndexOf("%") + 3)
            var bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
            imgView.setImageBitmap(bitmap)
        }
    }

    @SuppressLint("Range")
    private fun uploadImage(){

        val fileName = contentResolver.query(imageUri, null, null, null, null).use {
            if (it != null) {
                it.moveToFirst()
            }
            it?.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        val inputStream = contentResolver.openInputStream(imageUri)
        val bytes = ByteArrayOutputStream().use {
            if (inputStream != null) {
                inputStream.copyTo(it)
            }
            it.toByteArray()
        }
        val type = contentResolver.getType(imageUri)
        val requestFile = RequestBody.create(MediaType.parse(type), bytes)
        val image = MultipartBody.Part.createFormData("myFile", fileName, requestFile)

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        retIn.postImage(image).enqueue(object : Callback<ResponseBody>{
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(this@RecetteFormActivity,"Successfully Added",Toast.LENGTH_SHORT).show()
                Log.d("TEST",response.body().toString())
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@RecetteFormActivity,"Failure Added",Toast.LENGTH_SHORT).show()            }
            })

    }




//    private fun upload() {
//
//        val filesDir = applicationContext.filesDir
//        val file = File(filesDir, "image.png")
//
//        val inputStream = contentResolver.openInputStream(imageUri)
//        val outputStream = FileOutputStream(file)
//        inputStream!!.copyTo(outputStream)
//
//        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
//        val part = MultipartBody.Part.createFormData("myFile", file.name, requestBody)
//
//
//        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
//        CoroutineScope(Dispatchers.IO).launch {
//            val response = retIn.postImage(part)
//            Log.d("IMAGE", response.filename)
//        }
//    }

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
        name: String,
        description: String,
        isBio: Boolean,
        duration: Int,
        person: Int,
        difficulty: String
    ) {

        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val id: String = ""
        val image: String = "test"
        val recetteInfo = RecettesInQueue.Recette(
            id,
            name,
            description,
            image,
            isBio,
            duration,
            person,
            difficulty
        )

        retIn.addRecette(recetteInfo).enqueue(object :
            Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.code() == 200) {
                    Toast.makeText(this@RecetteFormActivity, "Recette Added", Toast.LENGTH_SHORT)
                        .show()

                } else {
                    Toast.makeText(this@RecetteFormActivity, response.message(), Toast.LENGTH_SHORT)
                        .show()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(
                    this@RecetteFormActivity,
                    t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }

        })
    }



    @SuppressLint("NewApi")
    fun getRealPathFromURIAPI19(context: Context, uri: Uri): String? {

        val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                if ("primary".equals(type, ignoreCase = true)) {
                    return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                }
            } else if (isDownloadsDocument(uri)) {
                var cursor: Cursor? = null
                try {
                    cursor = context.contentResolver.query(uri, arrayOf(MediaStore.MediaColumns.DISPLAY_NAME), null, null, null)
                    cursor!!.moveToNext()
                    val fileName = cursor.getString(0)
                    val path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName
                    if (!TextUtils.isEmpty(path)) {
                        return path
                    }
                } finally {
                    cursor?.close()
                }
                val id = DocumentsContract.getDocumentId(uri)
                if (id.startsWith("raw:")) {
                    return id.replaceFirst("raw:".toRegex(), "")
                }
                val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads"), java.lang.Long.valueOf(id))

                return getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                val type = split[0]

                var contentUri: Uri? = null
                when (type) {
                    "image" -> contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    "video" -> contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    "audio" -> contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                }

                val selection = "_id=?"
                val selectionArgs = arrayOf(split[1])

                return getDataColumn(context, contentUri, selection, selectionArgs)
            }// MediaProvider
            // DownloadsProvider
        } else if ("content".equals(uri.scheme!!, ignoreCase = true)) {

            // Return the remote address
            return if (isGooglePhotosUri(uri)) uri.lastPathSegment else getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme!!, ignoreCase = true)) {
            return uri.path
        }// File
        // MediaStore (and general)

        return null
    }

    private fun getDataColumn(context: Context, uri: Uri?, selection: String?,
                              selectionArgs: Array<String>?): String? {

        var cursor: Cursor? = null
        val column = "_data"
        val projection = arrayOf(column)

        try {
            cursor = context.contentResolver.query(uri!!, projection, selection, selectionArgs, null)
            if (cursor != null && cursor.moveToFirst()) {
                val index = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(index)
            }
        } finally {
            cursor?.close()
        }
        return null
    }

    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    private fun isGooglePhotosUri(uri: Uri): Boolean {
        return "com.google.android.apps.photos.content" == uri.authority

    }
}
package devsec.app.easykitchen.ui.main.view



import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toFile
import com.google.android.material.textfield.TextInputLayout
import devsec.app.easykitchen.R
import devsec.app.easykitchen.api.RestApiService
import devsec.app.easykitchen.api.RetrofitInstance
import devsec.app.easykitchen.data.models.Ingredients
import devsec.app.easykitchen.data.models.Recette
import devsec.app.easykitchen.databinding.ActivityRecetteFormBinding
import devsec.app.easykitchen.utils.session.SessionPref
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


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

    private lateinit var binding : ActivityRecetteFormBinding
    lateinit var ingredientsArray:ArrayList<String>
    lateinit var ingredientsTypeArray:ArrayList<String>

    lateinit var dialog:Dialog

    lateinit var editTextIngredient:EditText
    lateinit var listViewIngredient:ListView
    lateinit var addIngredient:AppCompatButton
    lateinit var removeIngredient:AppCompatButton

    lateinit var layoutArray:ArrayList<ConstraintLayout>
    lateinit var linearlayoutIngredients:LinearLayout
    //textView inputs
    lateinit var tv1:TextView
    lateinit var tv2:TextView
    lateinit var tv3:TextView
    lateinit var tv4:TextView
    lateinit var tv5:TextView
    lateinit var tv6:TextView
    lateinit var tv7:TextView
    lateinit var tv8:TextView
    lateinit var tv9:TextView
    lateinit var tv10:TextView
    lateinit var tv11:TextView
    lateinit var tv12:TextView
    lateinit var tv13:TextView
    lateinit var tv14:TextView
    lateinit var tv15:TextView
    lateinit var tv16:TextView
    lateinit var tv17:TextView
    lateinit var tv18:TextView
    lateinit var tv19:TextView
    lateinit var tv20:TextView
    //measure EditText
    lateinit var et1:EditText
    lateinit var et2:EditText
    lateinit var et3:EditText
    lateinit var et4:EditText
    lateinit var et5:EditText
    lateinit var et6:EditText
    lateinit var et7:EditText
    lateinit var et8:EditText
    lateinit var et9:EditText
    lateinit var et10:EditText
    lateinit var et11:EditText
    lateinit var et12:EditText
    lateinit var et13:EditText
    lateinit var et14:EditText
    lateinit var et15:EditText
    lateinit var et16:EditText
    lateinit var et17:EditText
    lateinit var et18:EditText
    lateinit var et19:EditText
    lateinit var et20:EditText
    //Type TextView
    lateinit var t1:TextView
    lateinit var t2:TextView
    lateinit var t3:TextView
    lateinit var t4:TextView
    lateinit var t5:TextView
    lateinit var t6:TextView
    lateinit var t7:TextView
    lateinit var t8:TextView
    lateinit var t9:TextView
    lateinit var t10:TextView
    lateinit var t11:TextView
    lateinit var t12:TextView
    lateinit var t13:TextView
    lateinit var t14:TextView
    lateinit var t15:TextView
    lateinit var t16:TextView
    lateinit var t17:TextView
    lateinit var t18:TextView
    lateinit var t19:TextView
    lateinit var t20:TextView

    lateinit var sessionPref: SessionPref
    lateinit var idUser : String
    lateinit var user : HashMap<String, String>
    var inc:Int = 0
    var checkIngredients:Boolean=false




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recette_form)
        sessionPref = SessionPref(this)
        user = sessionPref.getUserPref()
        idUser = user.get(SessionPref.USER_ID).toString()
        initIngredients()
        ingredientsTypeArray=ArrayList()
        val measureType: List<String> = Arrays.asList("Mg", "G", "Kg","Ml","L","unit")
        ingredientsTypeArray.addAll(measureType)


        // Image Picker
        addButton = findViewById(R.id.add_button)
        imgView = findViewById(R.id.imageViewRecette)
        addButton.setOnClickListener {

            Log.d("TAG", "onClick: ")


            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permissions, REQUEST_CODE)
            } else {
                pickImageFromGallery()
            }
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

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, IMAGE_PICK_CODE)

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            imageUri = data?.data!!
            imgView.setImageURI(imageUri)
        }
    }


    private fun setup() {
        //TextViews Ingredients
        tv1= findViewById(R.id.IngredientTxt1)
        tv2= findViewById(R.id.IngredientTxt2)
        tv3= findViewById(R.id.IngredientTxt3)
        tv4= findViewById(R.id.IngredientTxt4)
        tv5= findViewById(R.id.IngredientTxt5)
        tv6= findViewById(R.id.IngredientTxt6)
        tv7= findViewById(R.id.IngredientTxt7)
        tv8= findViewById(R.id.IngredientTxt8)
        tv9= findViewById(R.id.IngredientTxt9)
        tv10= findViewById(R.id.IngredientTxt10)
        tv11= findViewById(R.id.IngredientTxt11)
        tv12= findViewById(R.id.IngredientTxt12)
        tv13= findViewById(R.id.IngredientTxt13)
        tv14= findViewById(R.id.IngredientTxt14)
        tv15= findViewById(R.id.IngredientTxt15)
        tv16= findViewById(R.id.IngredientTxt16)
        tv17= findViewById(R.id.IngredientTxt17)
        tv18= findViewById(R.id.IngredientTxt18)
        tv19= findViewById(R.id.IngredientTxt19)
        tv20= findViewById(R.id.IngredientTxt20)
        //EditText Measures
        et1= findViewById(R.id.MeasureTxt1)
        et2= findViewById(R.id.MeasureTxt2)
        et3= findViewById(R.id.MeasureTxt3)
        et4= findViewById(R.id.MeasureTxt4)
        et5= findViewById(R.id.MeasureTxt5)
        et6= findViewById(R.id.MeasureTxt6)
        et7= findViewById(R.id.MeasureTxt7)
        et8= findViewById(R.id.MeasureTxt8)
        et9= findViewById(R.id.MeasureTxt9)
        et10= findViewById(R.id.MeasureTxt10)
        et11= findViewById(R.id.MeasureTxt11)
        et12= findViewById(R.id.MeasureTxt12)
        et13= findViewById(R.id.MeasureTxt13)
        et14= findViewById(R.id.MeasureTxt14)
        et15= findViewById(R.id.MeasureTxt15)
        et16= findViewById(R.id.MeasureTxt16)
        et17= findViewById(R.id.MeasureTxt17)
        et18= findViewById(R.id.MeasureTxt18)
        et19= findViewById(R.id.MeasureTxt19)
        et20= findViewById(R.id.MeasureTxt20)
        //TextView Measures
        t1= findViewById(R.id.MeasureTxtType1)
        t2= findViewById(R.id.MeasureTxtType2)
        t3= findViewById(R.id.MeasureTxtType3)
        t4= findViewById(R.id.MeasureTxtType4)
        t5= findViewById(R.id.MeasureTxtType5)
        t6= findViewById(R.id.MeasureTxtType6)
        t7= findViewById(R.id.MeasureTxtType7)
        t8= findViewById(R.id.MeasureTxtType8)
        t9= findViewById(R.id.MeasureTxtType9)
        t10= findViewById(R.id.MeasureTxtType10)
        t11= findViewById(R.id.MeasureTxtType11)
        t12= findViewById(R.id.MeasureTxtType12)
        t13= findViewById(R.id.MeasureTxtType13)
        t14= findViewById(R.id.MeasureTxtType14)
        t15= findViewById(R.id.MeasureTxtType15)
        t16= findViewById(R.id.MeasureTxtType16)
        t17= findViewById(R.id.MeasureTxtType17)
        t18= findViewById(R.id.MeasureTxtType18)
        t19= findViewById(R.id.MeasureTxtType19)
        t20= findViewById(R.id.MeasureTxtType20)

        imgView = findViewById(R.id.imageViewRecette)
        addButton = findViewById(R.id.add_button)
        submitButton = findViewById(R.id.submit_button)
        titreInput = findViewById(R.id.titreEditText)
        descInput = findViewById(R.id.descEditText)
        dureeInput = findViewById(R.id.dureeEditText)
        personInput = findViewById(R.id.personEditText)
        bioCheckBox = findViewById(R.id.bioCheckBox)
        difficultyDropDown = findViewById(R.id.difficultyDropDown)
        addIngredient=findViewById(R.id.addIngredient)
        removeIngredient=findViewById(R.id.removeIngredient)
        linearlayoutIngredients=findViewById(R.id.ingredientInputs)

        removeIngredient.visibility=View.GONE


        for (i in 1 until linearlayoutIngredients.childCount-1) {
            val view = linearlayoutIngredients.getChildAt(i)
            view.visibility = View.GONE
        }

//addIngredient Button
        addIngredient.setOnClickListener {
            Log.d("+ INC",inc.toString())
            inc++
            linearlayoutIngredients.getChildAt(inc).visibility=View.VISIBLE

            if (inc==1){
              removeIngredient.visibility=View.VISIBLE
            }
            if (inc==19){
                addIngredient.visibility=View.GONE
            }
            Log.d("++ INC",inc.toString())
        }

        //removeIngredient Button
        removeIngredient.setOnClickListener {
        Log.d("- INC",inc.toString())
            val ingredientInput = linearlayoutIngredients.getChildAt(inc) as ConstraintLayout
            val ingredientText= ingredientInput.getChildAt(0) as TextView
            val ingredientMeasureText= ingredientInput.getChildAt(2) as TextView
            val measureInput = ingredientInput.getChildAt(1) as TextInputLayout
            val measureEditText = measureInput.getChildAt(0) as FrameLayout
            val measureTxt =measureEditText.getChildAt(0) as EditText

            ingredientInput.visibility=View.GONE

            if(!ingredientText.text.isEmpty()){
                ingredientsArray.add(ingredientText.text.toString())
                ingredientsArray.sorted()

                ingredientText.text=""

            }
            ingredientMeasureText.text=""
            measureTxt.text.clear()

            inc--
            if (inc == 0){
                removeIngredient.visibility=View.GONE
            }
            if (inc==18){
                addIngredient.visibility=View.VISIBLE
            }
            Log.d("-- INC",inc.toString())

        }

        for (i in 0 until linearlayoutIngredients.childCount-1) {
            val CL = linearlayoutIngredients.getChildAt(i) as ConstraintLayout
            val TV= CL.getChildAt(0) as TextView
            TV.setOnClickListener {
                showDialogSpinner(TV,ingredientsArray,0)
            }

        }
        for (i in 0 until linearlayoutIngredients.childCount-1) {
            val CL = linearlayoutIngredients.getChildAt(i) as ConstraintLayout
            val TV= CL.getChildAt(2) as TextView
            TV.setOnClickListener {
                showDialogSpinner(TV,ingredientsTypeArray,1)
            }

        }

        addButton.setOnClickListener {
            pickImageFromGallery()


        }

        submitButton.setOnClickListener {

            var bio = false
            if(bioCheckBox.isChecked){
                bio=true
            }
            for (i in 0 until inc ){
                val ingredientInput = linearlayoutIngredients.getChildAt(inc) as ConstraintLayout
                val ingredientText= ingredientInput.getChildAt(0) as TextView
                val measureInput = ingredientInput.getChildAt(1) as TextInputLayout
                val measureEditText = measureInput.getChildAt(0) as FrameLayout
                val measureTxt =measureEditText.getChildAt(0) as EditText
                val measureTypeTxt=ingredientInput.getChildAt(2) as TextView

               checkIngredients= validateIngredients(ingredientText,measureTxt,measureTypeTxt)
            }
            if (validate(titreInput, descInput, dureeInput, personInput, difficultyDropDown) && checkIngredients) {
                submit(titreInput.text.toString(), descInput.text.toString(),bio, Integer.parseInt(dureeInput.text.toString()), Integer.parseInt(personInput.text.toString()),difficultyDropDown.text.toString(),idUser,
                    tv1.text.toString(),tv2.text.toString(),tv3.text.toString(),tv4.text.toString(),tv5.text.toString(),tv6.text.toString(),tv7.text.toString(),tv8.text.toString(),tv9.text.toString(),tv10.text.toString(),tv11.text.toString(),tv12.text.toString(),tv13.text.toString(),tv14.text.toString(),tv15.text.toString(),tv16.text.toString(),tv17.text.toString(),tv18.text.toString(),tv19.text.toString(),tv20.text.toString(),
                    measureFuse(et1,t1),measureFuse(et2,t2),measureFuse(et3,t3),measureFuse(et4,t4),measureFuse(et5,t5),measureFuse(et6,t6),measureFuse(et7,t7),measureFuse(et8,t8),measureFuse(et9,t9),measureFuse(et10,t10),measureFuse(et11,t11),measureFuse(et12,t12),measureFuse(et13,t13),measureFuse(et14,t14),measureFuse(et15,t15),measureFuse(et16,t16),measureFuse(et17,t17),measureFuse(et18,t18),measureFuse(et19,t19),measureFuse(et20,t20))
                retrofitUploadImage(imageUri)
            }

        }
    }

    private fun measureFuse(et: EditText, t: TextView): String {
        val measure=""
        if (et.text.isEmpty() || t.text.isEmpty() ){
            return measure
        }else{
            return et.text.toString() + " " + t.text.toString()
        }

    }

    private fun showDialogSpinner(tv:TextView,list:ArrayList<String>,iM:Int) {
        val dialog = Dialog(this@RecetteFormActivity)
        dialog.setContentView(R.layout.dialog_searchable_spinner)
        dialog.window?.setLayout(1200, 1600)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        if (iM==0){
            dialog.findViewById<TextView>(R.id.spinnerHeader).text="Select an ingredient"
        }else if (iM==1){
            dialog.findViewById<TextView>(R.id.spinnerHeader).text="Select an Measure Type"
        }

        editTextIngredient = dialog.findViewById(R.id.searchIngredient)
        listViewIngredient = dialog.findViewById(R.id.listViewIngredients)
        

        val adapter = ArrayAdapter(
            this@RecetteFormActivity,
            android.R.layout.simple_list_item_1,
            list
        )
        listViewIngredient.adapter = adapter

        editTextIngredient.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                adapter.filter.filter(s)
            }

            override fun afterTextChanged(s: Editable) {
            }
        })

        if (iM==0){
            listViewIngredient.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                    if (tv.text.isEmpty()){
                        tv.text = (adapter.getItem(i) as String)
                        list.remove(adapter.getItem(i))
                        dialog.dismiss()
                    }else{
                        list.add(tv.text.toString())
                        tv.text = (adapter.getItem(i) as String)
                        list.remove(adapter.getItem(i))
                        dialog.dismiss()
                    }

                }
        }else if (iM==1){
            listViewIngredient.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, i, l ->
                    tv.text = (adapter.getItem(i) as String)
                    dialog.dismiss()
                }
        }


    }
    private fun retrofitUploadImage(imageUri: Uri) {
        val file = File(imageUri.path)
        val requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file)
        val body = MultipartBody.Part.createFormData("myFile", file.name, requestFile)
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.uploadImage(body)
        call.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@RecetteFormActivity, "Image Uploaded", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@RecetteFormActivity, "Image Not Uploaded", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(this@RecetteFormActivity, "Image Not Uploaded", Toast.LENGTH_SHORT)
                    .show()
            }
        })



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

    private fun validateIngredients(ingredientTxt: TextView,measureTxt:EditText,ingredientMeasureText:TextView ):Boolean{
        if(ingredientTxt.text.isEmpty() || measureTxt.text.isEmpty() || ingredientMeasureText.text.isEmpty()){
            if (ingredientTxt.text.isEmpty()){
                ingredientTxt.error="ingredient is required"
                ingredientTxt.requestFocus()
            }
            if (measureTxt.text.isEmpty()){
                measureTxt.error="measure is required"
                measureTxt.requestFocus()
            }
            if (ingredientMeasureText.text.isEmpty()){
                ingredientMeasureText.error="measure Type is required"
                ingredientMeasureText.requestFocus()
            }
         return false
        }
         return true

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
        difficulty: String,
        user: String,
        strIngredient1: String,strIngredient2: String,strIngredient3: String,strIngredient4: String,strIngredient5: String,strIngredient6: String,strIngredient7: String,strIngredient8: String,strIngredient9: String,strIngredient10: String,strIngredient11: String,strIngredient12: String,strIngredient13: String,strIngredient14: String,strIngredient15: String,strIngredient16: String,strIngredient17: String,strIngredient18: String,strIngredient19: String,strIngredient20: String,
        strMeasure1: String,strMeasure2: String,strMeasure3: String,strMeasure4: String,strMeasure5: String,strMeasure6: String,strMeasure7: String,strMeasure8: String,strMeasure9: String,strMeasure10: String,strMeasure11: String,strMeasure12: String,strMeasure13: String,strMeasure14: String,strMeasure15: String,strMeasure16: String,strMeasure17: String,strMeasure18: String,strMeasure19: String,strMeasure20: String
    )
    {


        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val image =  imageUri.toString()
        val recetteInfo = Recette(
            name,
            description,
            image,
            isBio,
            duration,
            person,
            difficulty,
            user,
            strIngredient1,strIngredient2,strIngredient3,strIngredient4,strIngredient5,strIngredient6,strIngredient7,strIngredient8,strIngredient9,strIngredient10,strIngredient11,strIngredient12,strIngredient13,strIngredient14,strIngredient15,strIngredient16,strIngredient17,strIngredient18,strIngredient19,strIngredient20,
            strMeasure1,strMeasure2,strMeasure3,strMeasure4,strMeasure5,strMeasure6,strMeasure7,strMeasure8,strMeasure9,strMeasure10,strMeasure11,strMeasure12,strMeasure13,strMeasure14,strMeasure15,strMeasure16,strMeasure17,strMeasure18,strMeasure19,strMeasure20


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

    private fun initIngredients() {
        ingredientsArray = ArrayList()
        val retIn = RetrofitInstance.getRetrofitInstance().create(RestApiService::class.java)
        val call = retIn.getIngredientsList()
        call.enqueue(object : Callback<List<Ingredients>> {
            override fun onResponse(
                call: Call<List<Ingredients>>,
                response: Response<List<Ingredients>>
            ) {
                if (response.isSuccessful) {
                    val ingredientsList = response.body()
                    if (ingredientsList != null) {
                        for (ingredient in ingredientsList) {
                            ingredientsArray.add(ingredient.name)
                        }
                        Log.d("TAG", "onResponse: $ingredientsArray")
                    }
                }
            }

            override fun onFailure(call: Call<List<Ingredients>>, t: Throwable) {
                Log.d("Error", t.message.toString())
            }
        })
    }
}




//        ingredientInput1=findViewById(R.id.ingredientInput1)
//        ingredientInput2=findViewById(R.id.ingredientInput2)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput4=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
//        ingredientInput3=findViewById(R.id.ingredientInput3)
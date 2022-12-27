package devsec.app.easykitchen.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

    data  class Recette (
    @SerializedName("_id")
    @Expose
    val  id :  String ,
    @SerializedName("name")
    @Expose
    val  name :  String ,
    @SerializedName("description")
    @Expose
    val  description :  String ,
    @SerializedName("image")
    @Expose
    val  image :  String ,
    @SerializedName("isBio")
    @Expose
    val  isBio :  Boolean,
    @SerializedName("duration")
    @Expose
    val  duration :  Number,
    @SerializedName("person")
    @Expose
    val  person :  Number,
    @SerializedName("difficulty")
    @Expose
    val  difficulty :  String,
    @SerializedName("userId")
    val  user :  String,
    @SerializedName("comments")
    val  comments :  ArrayList<String>,
    @SerializedName("likes")
    val  likes :  Number,
    @SerializedName("dislikes")
    val  dislikes :  Number,
    @SerializedName("usersLiked")
    val  usersLiked :  ArrayList<String>,
    @SerializedName("usersDisliked")
    val  usersDisliked :  ArrayList<String>,
    @SerializedName("strIngredient1")
    val strIngredient1: String,
    @SerializedName("strIngredient2")
    val strIngredient2: String,
    @SerializedName("strIngredient3")
    val strIngredient3: String,
    @SerializedName("strIngredient4")
    val strIngredient4: String,
    @SerializedName("strIngredient5")
    val strIngredient5: String,
    @SerializedName("strIngredient6")
    val strIngredient6: String,
    @SerializedName("strIngredient7")
    val strIngredient7: String,
    @SerializedName("strIngredient8")
    val strIngredient8: String,
    @SerializedName("strIngredient9")
    val strIngredient9: String,
    @SerializedName("strIngredient10")
    val strIngredient10: String,
    @SerializedName("strIngredient11")
    val strIngredient11: String,
    @SerializedName("strIngredient12")
    val strIngredient12: String,
    @SerializedName("strIngredient13")
    val strIngredient13: String,
    @SerializedName("strIngredient14")
    val strIngredient14: String,
    @SerializedName("strIngredient15")
    val strIngredient15: String,
    @SerializedName("strIngredient16")
    val strIngredient16: String,
    @SerializedName("strIngredient17")
    val strIngredient17: String,
    @SerializedName("strIngredient18")
    val strIngredient18: String,
    @SerializedName("strIngredient19")
    val strIngredient19: String,
    @SerializedName("strIngredient20")
    val strIngredient20: String,
    @SerializedName("strMeasure1")
    val strMeasure1: String,
    @SerializedName("strMeasure2")
    val strMeasure2: String,
    @SerializedName("strMeasure3")
    val strMeasure3: String,
    @SerializedName("strMeasure4")
    val strMeasure4: String,
    @SerializedName("strMeasure5")
    val strMeasure5: String,
    @SerializedName("strMeasure6")
    val strMeasure6: String,
    @SerializedName("strMeasure7")
    val strMeasure7: String,
    @SerializedName("strMeasure8")
    val strMeasure8: String,
    @SerializedName("strMeasure9")
    val strMeasure9: String,
    @SerializedName("strMeasure10")
    val strMeasure10: String,
    @SerializedName("strMeasure11")
    val strMeasure11: String,
    @SerializedName("strMeasure12")
    val strMeasure12: String,
    @SerializedName("strMeasure13")
    val strMeasure13: String,
    @SerializedName("strMeasure14")
    val strMeasure14: String,
    @SerializedName("strMeasure15")
    val strMeasure15: String,
    @SerializedName("strMeasure16")
    val strMeasure16: String,
    @SerializedName("strMeasure17")
    val strMeasure17: String,
    @SerializedName("strMeasure18")
    val strMeasure18: String,
    @SerializedName("strMeasure19")
    val strMeasure19: String,
    @SerializedName("strMeasure20")
    val strMeasure20: String){
        constructor(name: String, description: String,image: String,isBio:Boolean,duration:Number,person:Number,difficulty: String,user: String,strIngredient1: String,strIngredient2: String,strIngredient3: String,strIngredient4: String,strIngredient5: String,strIngredient6: String,strIngredient7: String,strIngredient8: String,strIngredient9: String,strIngredient10: String,strIngredient11: String,strIngredient12: String,strIngredient13: String,strIngredient14: String,strIngredient15: String,strIngredient16: String,strIngredient17: String,strIngredient18: String,strIngredient19: String,strIngredient20: String,strMeasure1: String,strMeasure2: String,strMeasure3: String,strMeasure4: String,strMeasure5: String,strMeasure6: String,strMeasure7: String,strMeasure8: String,strMeasure9: String,strMeasure10: String,strMeasure11: String,strMeasure12: String,strMeasure13: String,strMeasure14: String,strMeasure15: String,strMeasure16: String,strMeasure17: String,strMeasure18: String,strMeasure19: String,strMeasure20: String) :
                this("", name, description, image, isBio,duration,person,difficulty,user,ArrayList<String>(),0,0,ArrayList<String>(),ArrayList<String>(),strIngredient1,strIngredient2,strIngredient3,strIngredient4,strIngredient5,strIngredient6,strIngredient7,strIngredient8,strIngredient9,strIngredient10,strIngredient11,strIngredient12,strIngredient13,strIngredient14,strIngredient15,strIngredient16,strIngredient17,strIngredient18,strIngredient19,strIngredient20,strMeasure1,strMeasure2,strMeasure3,strMeasure4,strMeasure5,strMeasure6,strMeasure7,strMeasure8,strMeasure9,strMeasure10,strMeasure11,strMeasure12,strMeasure13,strMeasure14,strMeasure15,strMeasure16,strMeasure17,strMeasure18,strMeasure19,strMeasure20)

    }


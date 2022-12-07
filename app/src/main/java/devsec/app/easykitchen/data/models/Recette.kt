package devsec.app.easykitchen.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
data class RecettesInQueue(
    var recettes: ArrayList<Recette>

){
//    operator fun get(position: Int): Any {
//        val recettesList:ArrayList<Recette>
//        for (item in recettes){
//
//            recettesList.add()
//        }
//    }

    data  class Recette (
    @SerializedName("_id")
    @Expose
    val  _id :  String ,
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


)}
//{
//    constructor(name: String, description: String) : this("", name, description,"", false,0,0,"")
//
//
//}
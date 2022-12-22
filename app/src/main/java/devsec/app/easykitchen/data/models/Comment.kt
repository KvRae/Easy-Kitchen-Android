package devsec.app.easykitchen.data.models

import com.google.gson.annotations.SerializedName

data class Comment (
    @SerializedName("_id")
    val id: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("recetteId")
    val recette:String,
    @SerializedName("userId")
    val user: String
){
    constructor(text:String,recette:String,user:String):this("","",text,recette,user)
}
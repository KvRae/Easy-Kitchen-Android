package devsec.app.easykitchen.data.models


import com.google.gson.annotations.SerializedName

data class Ingredients(
    @SerializedName("_id")
    val id: String,
    @SerializedName("idIngredient")
    val idIngredient: String,
    @SerializedName("strDescription")
    val strDescription: String,
    @SerializedName("strIngredient")
    val strIngredient: String,
    @SerializedName("strType")
    val strType: Any
)
package devsec.app.easykitchen.data.models


import com.google.gson.annotations.SerializedName

data class Ingredients(
    @SerializedName("_id")
    val id: String,
    @SerializedName("strIngredient")
    val name: String,
    @SerializedName("strDescription")
    val description: String
)
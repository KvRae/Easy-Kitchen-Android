package devsec.app.easykitchen.data.models

import com.google.gson.annotations.SerializedName

data class Category (
                         @SerializedName("_id")
                         var id: String,
                         @SerializedName("strCategory")
                         var name: String,
                         @SerializedName("strCategoryDescription")
                         var description: String,
                         @SerializedName("strCategoryThumb")
                         var image: String )

//    var recettes: List<RecettesInQueue.Recette> = emptyList()

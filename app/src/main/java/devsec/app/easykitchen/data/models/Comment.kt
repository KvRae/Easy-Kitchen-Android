package devsec.app.easykitchen.data.models

import com.google.gson.annotations.SerializedName

data class Comment (
    @SerializedName("_id")
    val id: String,
    @SerializedName("text")
    val text: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("recette")
    val recette:RecettesInQueue.Recette,
    @SerializedName("user")
    val user: User
)
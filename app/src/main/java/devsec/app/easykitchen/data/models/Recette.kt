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
    @SerializedName("user")
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
    val  usersDisliked :  ArrayList<String> )


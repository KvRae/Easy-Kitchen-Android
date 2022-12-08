package devsec.app.easykitchen.data.models

import com.google.gson.annotations.SerializedName

data class Food(    @SerializedName("_id")
                    val id: String,
                    @SerializedName("strArea")
                    val area: String,
                    @SerializedName("strCategory")
                    val category: String,
                    @SerializedName("strIngredient1")
                    val strIngredient1: String,
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
                    @SerializedName("strIngredient2")
                    val strIngredient2: String,
                    @SerializedName("strIngredient20")
                    val strIngredient20: String,
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
                    @SerializedName("strInstructions")
                    val instructions: String,
                    @SerializedName("strMeal")
                    val name: String,
                    @SerializedName("strMealThumb")
                    val image: String,
                    @SerializedName("strSource")
                    val source: Any,
                    @SerializedName("strYoutube")
                    val youtube: String)

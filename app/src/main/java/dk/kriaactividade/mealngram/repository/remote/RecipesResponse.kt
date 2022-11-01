package dk.kriaactividade.mealngram.repository.remote

import com.google.gson.annotations.SerializedName

data class RecipesResponse(
    val id : Int,
    val name: String,
    val description:String,
    val ingredientes: MutableList<String>,
    @SerializedName("images_url")
    val imagesUrl: MutableList<String>,
    @SerializedName("video_url")
    val videoUrl:String,
    @SerializedName("created_at")
    val createdAt:String,
    @SerializedName("update_at")
    val updateAt:String
)


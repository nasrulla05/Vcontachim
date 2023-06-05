import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Users(
    val response: List<Response>
): Serializable {

    data class Response(
        val id: Long,
        val city: City,
        val status: String,
        @SerializedName("followers_count")
        val followersCount: Long,
        val career: List<Career>,
        @SerializedName("photo_100")
        val photo100: String,
        val online: Long,
        val verified: Long,
        @SerializedName("friend_status")
        val friendStatus: Long,
        @SerializedName("first_name")
        val firstName: String,
        @SerializedName("last_name")
        val lastName: String,
    ):Serializable

    data class City(
        val id: Long,
        val title: String,
    ):Serializable

    data class Career(
        @SerializedName("city_id")
        val cityId: Long,
        @SerializedName("country_id")
        val countryId: Long,
        val from: Long,
        @SerializedName("group_id")
        val groupId: Long,
    ):Serializable
}
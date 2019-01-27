package my.dzeko.SportsApi.entities

import java.util.*

data class News(
        var title :String,
        var summary :String,
        var date :Date,
        var tags :List<Tag>,
        var content :String,
        var originalUrl :String
) {
    companion object {
        var lastUsedId :Long = 0
        var lastAddedNews :News? = null
    }
}
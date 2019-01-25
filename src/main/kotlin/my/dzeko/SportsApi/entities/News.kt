package my.dzeko.SportsApi.entities

import java.time.LocalDateTime

data class News(
        val title :String,
        val summary :String,
        val date :LocalDateTime,
        val tags :List<Tag>,
        val content :String,
        val originalUrl :String,
        val id: Int = 0
)
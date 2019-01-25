package my.dzeko.SportsApi.services

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class NewsServiceTest {

    @Test
    fun getNews() {
        NewsService().getNews()
    }
}
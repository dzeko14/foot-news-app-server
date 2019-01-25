package my.dzeko.SportsApi.utils

import org.junit.Test

internal class NetworkUtilsTest {

    @Test
    fun getNewsTitleDocument() {
        val doc = NetworkUtils.getNewsTitleDocument()
        val element = doc.body()

        println(element.html())
    }
}
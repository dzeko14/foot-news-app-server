package my.dzeko.SportsApi.utils

import org.junit.Test

internal class NewsParserNetworkUtilsTest {

    @Test
    fun getNewsTitleDocument() {
        val doc = NewsParserNetworkUtils.getNewsTitleDocument()
        val element = doc.body()

        println(element.html())
    }
}
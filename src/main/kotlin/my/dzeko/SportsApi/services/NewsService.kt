package my.dzeko.SportsApi.services

import my.dzeko.SportsApi.utils.NetworkUtils
import my.dzeko.SportsApi.utils.NewsParserUtils
import org.springframework.stereotype.Service

@Service
class NewsService {

    fun getNews() {
        val doc = NetworkUtils.getNewsTitleDocument()
        NewsParserUtils.parseNews(doc)
    }
}
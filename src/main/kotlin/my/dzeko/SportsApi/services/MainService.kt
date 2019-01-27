package my.dzeko.SportsApi.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MainService(
        @Autowired private val newsService: NewsService
){

    fun update(){
        val allNews = newsService.getNews()
        if (allNews.isEmpty()) return
        val news = newsService.removeAlreadySavedNewsInFirebase(allNews)
        newsService.saveNews(news)
    }
}
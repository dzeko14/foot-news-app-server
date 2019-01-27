package my.dzeko.SportsApi.services

import my.dzeko.SportsApi.entities.News
import my.dzeko.SportsApi.utils.NewsFirebaseUtil
import my.dzeko.SportsApi.utils.NewsParserNetworkUtils
import my.dzeko.SportsApi.utils.NewsParserUtils
import org.springframework.stereotype.Service

@Service
class NewsService {

    fun getNews() :List<News> {
        val doc = NewsParserNetworkUtils.getNewsTitleDocument()
        return NewsParserUtils.parseNews(doc)
    }

    fun removeAlreadySavedNewsInFirebase(newsToUpdate :List<News>) :List<News> {
        if(News.lastAddedNews == null) return newsToUpdate

        val lastSavedNewsIndex = newsToUpdate.indexOf(News.lastAddedNews!!)
        if (lastSavedNewsIndex == -1) return newsToUpdate
        if(lastSavedNewsIndex == newsToUpdate.size - 1) return emptyList()

        return newsToUpdate.subList(lastSavedNewsIndex+1, newsToUpdate.size)
    }

    fun saveNews(news :List<News>) {
        NewsFirebaseUtil.saveNewsList(news)
    }
}
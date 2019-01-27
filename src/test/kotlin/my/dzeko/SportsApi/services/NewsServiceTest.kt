package my.dzeko.SportsApi.services

import my.dzeko.SportsApi.entities.News
import my.dzeko.SportsApi.utils.FirebaseUtils
import org.junit.Before
import org.junit.Test
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit


internal class NewsServiceTest {
    lateinit var service :NewsService
    @Before
    fun init(){
        service = NewsService()
    }

    @Test
    fun getNews() {
        val news = service.getNews()
        news.forEach { n ->
            println("TEST:getNews(): $n")
        }
    }

    @Test
    fun removeAlreadySavedNewsInFirebase(){
        News.lastAddedNews = getTargetMockNews()

       val news = service.removeAlreadySavedNewsInFirebase(getMockNews())

        assert(news.size == 2)

        News.lastAddedNews = null
    }

    @Test
    fun saveNews(){
        FirebaseUtils.initAdminFirebaseSdk()
        service.saveNews(getMockNews())
        TimeUnit.SECONDS.sleep(5)
    }

    private fun getMockNews() :List<News> {
        return listOf(
                News("Before target news title", "news summary",
                        Date(),
                        emptyList(),
                        "news content",
                        "news url"),
                News("Before target news title", "news summary",
                        Date(),
                        emptyList(),
                        "news content",
                        "news url"),
                News("Before target news title", "news summary",
                        Date(),
                        emptyList(),
                        "news content",
                        "news url"),
                News("Before target news title", "news summary",
                        Date(),
                        emptyList(),
                        "news content",
                        "news url"),
                getTargetMockNews(),
                News("After target news title", "news summary",
                        Date(),
                        emptyList(),
                        "news content",
                        "news url"),
                News("After target news title", "news summary",
                        Date(),
                        emptyList(),
                        "news content",
                        "news url")
        )
    }

    private val targetDateTime: Date = Date()
    private fun getTargetMockNews() : News{
        return News("Target news", "Target news",
                targetDateTime,
                emptyList(),
                "Target news content",
                "Target news url")
    }
}
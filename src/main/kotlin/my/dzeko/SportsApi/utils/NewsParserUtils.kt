package my.dzeko.SportsApi.utils

import my.dzeko.SportsApi.entities.News
import my.dzeko.SportsApi.entities.Tag
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.lang.StringBuilder
import java.time.LocalDateTime

object NewsParserUtils {

    fun parseNews(doc :Document) :List<News> {
        val newsElements = doc.getElementsByClass("short-news")
        val newsBlocks = newsElements[0]

        val dateString: String = newsBlocks.child(0).text()
        val dateAndTime = DateAndTimeUtils.getDateFromString(dateString)

        val newsList = mutableListOf<News>()
        for(elem in newsBlocks.children().reversed()) {
            if (isTagWithAds(elem) || isTagWithBrake(elem) || isTagWithDate(elem)) continue
            val news = parseNewsContent(elem, dateAndTime)
            news?.let {
                newsList.add(news)
            }
        }
        return newsList
    }

    private fun isTagWithBrake(element: Element) :Boolean {
        return element.tagName() == "br"
    }

    private fun isTagWithDate(element: Element) :Boolean {
        return element.tagName() == "b"
    }

    private fun isTagWithAds(element: Element) :Boolean {
        return element.tagName() == "div"
    }

    private fun parseNewsContent(elem: Element, localDateTime: LocalDateTime) :News?  {
        if (elem.getElementsByClass("live").first() != null) return null

        val timeStr = elem.getElementsByClass("time").first().text()
        val date = DateAndTimeUtils.getDateWithTime(timeStr, localDateTime)
        val content = elem.getElementsByClass("short-text").first()

        val title = content.text()
        val summary = content.attr("title")

        val resource = content.attr("href")
        if (resource.contains("https://")) return null

        val document = NewsParserNetworkUtils.getNewsConentDocument(resource)

        val article = document.getElementsByTag("article").first()

        val header = article.getElementsByTag("header").first()
        val tagsElement = header.getElementsByClass("news-item__tags-line").first()
        val tags = getTagsFromElement(tagsElement)

        val body = article.getElementsByClass("news-item__content").first()
        val bodyContent = getNewsBodyContentFromElement(body) ?: return null

        val originalUrl = NewsParserNetworkUtils.getOriginalUrl(resource)

        return News(
                title,
                summary,
                DateAndTimeUtils.convertLocalDateTimeToDate(date),
                tags,
                bodyContent,
                originalUrl
        )
    }

    private fun getNewsBodyContentFromElement(body: Element): String? {
        val totalBlock = body.getElementsByClass("total-block")
        if (totalBlock.size > 0) return null

        val stringBuilder = StringBuilder()

        loop@ for (p in body.children()) {
            val text = when {
                p.children().size > 0 && p.child(0).tagName() == "img"
                    -> p.child(0).toString()

                p.children().size > 0
                        && p.child(0).attr("href")
                        .contains("https://ua.tribuna.com/tribuna/blogs")
                    -> continue@loop

                p.children().size > 0
                        && p.child(0).children().size > 0
                        && p.child(0).child(0)
                        .attr("href") == "https://t.me/ua_tribunacom"
                -> continue@loop

                p.tagName() == "p" -> p.text()

                else -> continue@loop
            }

            stringBuilder.append("$text/n/n")
        }
        return stringBuilder.toString()
    }

    private fun getTagsFromElement(element: Element): List<Tag> {
        val tagList = mutableListOf<Tag>()
        for (tag in element.children()) {
            if (tag.tagName() == "span") continue
            val tagName = tag.text()
            tagList.add(Tag(
                    tagName.hashCode(),
                    tagName
            ))
        }
        return tagList
    }


}
package my.dzeko.SportsApi.utils

import my.dzeko.SportsApi.entities.News
import my.dzeko.SportsApi.entities.Tag
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.lang.StringBuilder
import java.time.LocalDateTime

object NewsParserUtils {

    fun parseNews(doc :Document) {
        val newsElements = doc.getElementsByClass("short-news")
        val newsBlocks = newsElements[0]

        val dateString: String = newsBlocks.child(0).text()
        val dateAndTime = DateAndTimeUtils.getDateFromString(dateString)


        for(elem in newsBlocks.children()) {
            if (isTagWithAds(elem)) continue
            if (isTagWithDate(elem)) continue

            parseNewsContent(elem, dateAndTime)
        }
    }

    private fun isTagWithAds(element: Element) :Boolean {
        return element.tagName() == "br"
    }

    private fun isTagWithDate(element: Element) :Boolean {
        return element.tagName() == "b"
    }

    private fun parseNewsContent(elem: Element, localDateTime: LocalDateTime) {
        val timeStr = elem.getElementsByClass("time").first().text()
        val date = DateAndTimeUtils.getDateWithTime(timeStr, localDateTime)
        val content = elem.getElementsByClass("short-text").first()

        val title = content.text()
        val summary = content.attr("title")

        val resource = content.attr("href")
        val document = NetworkUtils.getNewsConentDocument(resource)

        val article = document.getElementsByTag("article").first()

        val header = article.getElementsByTag("header").first()
        val tagsElement = header.getElementsByClass("news-item__tags-line").first()
        val tags = getTagsFromElement(tagsElement)

        val body = article.getElementsByClass("news-item__content").first()
        val bodyContent = getNewsBodyContentFromElement(body)

        val originalUrl = NetworkUtils.getOriginalUrl(resource)

        val news = News(
                title,
                summary,
                date,
                tags,
                bodyContent,
                originalUrl
        )
        println(news)
    }

    private fun getNewsBodyContentFromElement(body: Element): String {
        val stringBuilder = StringBuilder()
        for (p in body.children()) {

            val text = if (p.children().size > 0 && p.child(0).tagName() == "img") {
                p.child(0).toString()
            } else {
                p.text()
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
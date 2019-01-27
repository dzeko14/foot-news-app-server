package my.dzeko.SportsApi.utils

import com.google.firebase.database.*
import my.dzeko.SportsApi.entities.News

object NewsFirebaseUtil {
    fun initLastAddedNews(){
        val ref = getDatabaseNewsreference()
        ref.orderByKey().limitToLast(1).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError?) {
                TODO("not implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot?) {
                snapshot?.let {
                    News.lastAddedNews = snapshot.getValue(News::class.java)
                    snapshot.key?.let {
                        News.lastUsedId = it.toLong()
                    }
                }
            }

        })
    }

    fun saveNewsList(news: List<News>) {
        val newsRef = FirebaseDatabase.getInstance().reference.child("news")
        val map = mutableMapOf<String, News>()

        for (n in news) {
            val id = ++News.lastUsedId
            map[id.toString()] = n
        }
        News.lastAddedNews = map[News.lastUsedId.toString()]
        newsRef.setValueAsync(map)
    }

    private fun getDatabaseNewsreference() :DatabaseReference{
        return FirebaseDatabase.getInstance().getReference("/news")
    }
}
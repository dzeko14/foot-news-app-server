package my.dzeko.SportsApi.utils

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import java.io.FileInputStream

object FirebaseUtils {
    fun initAdminFirebaseSdk(){
        val serviceAccount = FileInputStream("./ServiceAccountKey.json")
        val options = (FirebaseOptions.builder().apply {
            setCredentials(GoogleCredentials.fromStream(serviceAccount))
            setDatabaseUrl("https://sports-news-app-8bfd5.firebaseio.com")
        }).build()

        FirebaseApp.initializeApp(options)
    }
}
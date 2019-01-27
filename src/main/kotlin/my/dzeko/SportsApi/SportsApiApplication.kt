package my.dzeko.SportsApi

import my.dzeko.SportsApi.utils.FirebaseUtils.initAdminFirebaseSdk
import my.dzeko.SportsApi.utils.NewsFirebaseUtil.initLastAddedNews
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class SportsApiApplication

fun main(args: Array<String>) {
	runApplication<SportsApiApplication>(*args)

	initAdminFirebaseSdk()
	initLastAddedNews()
}


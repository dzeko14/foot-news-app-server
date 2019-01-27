package my.dzeko.SportsApi.schedule

import my.dzeko.SportsApi.services.MainService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ScheduledTasks(
        @Autowired private val mainService :MainService
) {

    @Scheduled(fixedDelay = 1800000)
    fun startParsingSchedule() {
        mainService.update()
    }

}
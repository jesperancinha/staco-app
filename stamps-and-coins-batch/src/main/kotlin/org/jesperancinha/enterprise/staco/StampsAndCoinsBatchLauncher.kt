package org.jesperancinha.enterprise.staco

import org.jesperancinha.enterprise.staco.jobs.StaCoS3LoaderJob
import org.quartz.JobBuilder.newJob
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder.simpleSchedule
import org.quartz.Trigger
import org.quartz.TriggerBuilder.newTrigger
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication


@SpringBootApplication
class StampsAndCoinsBatchLauncher(
    val scheduler: Scheduler
) :ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val job: JobDetail = newJob(StaCoS3LoaderJob::class.java)
            .withIdentity("StacoJob1", "StacoGroup1")
            .build()
        val trigger: Trigger = newTrigger()
            .withIdentity("StacoTrigger1", "StacoGroup1")
            .startNow()
            .withSchedule(
                simpleSchedule()
                    .withIntervalInSeconds(1)
                    .repeatForever()
            )
            .build()
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(StampsAndCoinsBatchLauncher::class.java, *args)
}


package org.jesperancinha.enterprise.staco.jobs

import org.quartz.CronTrigger.MISFIRE_INSTRUCTION_DO_NOTHING
import org.quartz.Job
import org.quartz.JobDetail
import org.quartz.Trigger
import org.quartz.spi.TriggerFiredBundle
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.config.AutowireCapableBeanFactory
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.quartz.CronTriggerFactoryBean
import org.springframework.scheduling.quartz.JobDetailFactoryBean
import org.springframework.scheduling.quartz.SchedulerFactoryBean
import org.springframework.scheduling.quartz.SpringBeanJobFactory
import java.util.Calendar
import java.util.Properties
import java.util.UUID


class AutowiringSpringBeanJobFactory : SpringBeanJobFactory(), ApplicationContextAware {
    @Transient
    private var beanFactory: AutowireCapableBeanFactory? = null

    override fun setApplicationContext(context: ApplicationContext) {
        beanFactory = context.autowireCapableBeanFactory
    }

    override fun createJobInstance(bundle: TriggerFiredBundle): Any {
        val job: Any = super.createJobInstance(bundle)
        beanFactory?.autowireBean(job)
        return job
    }
}

@Configuration
class QuartzConfig(private val applicationContext: ApplicationContext) {
    @Bean
    fun springBeanJobFactory(): SpringBeanJobFactory {
        val jobFactory = AutowiringSpringBeanJobFactory()
        jobFactory.setApplicationContext(applicationContext)
        return jobFactory
    }

    @Bean
    fun scheduler(triggers: Array<Trigger>, springBeanJobFactory: SpringBeanJobFactory): SchedulerFactoryBean {
        val schedulerFactory = SchedulerFactoryBean()
        val properties = Properties()
        properties.setProperty("org.quartz.scheduler.instanceName", "StacoSchedulerInstance")
        properties.setProperty("org.quartz.scheduler.instanceId", "STACO_SCHEDULER_${UUID.randomUUID()}")
        schedulerFactory.setOverwriteExistingJobs(true)
        schedulerFactory.isAutoStartup = true
        schedulerFactory.setQuartzProperties(properties)
        schedulerFactory.setJobFactory(springBeanJobFactory)
        schedulerFactory.setWaitForJobsToCompleteOnShutdown(true)
        schedulerFactory.setTriggers(*triggers)
        return schedulerFactory
    }

}


fun createCronTrigger(jobDetail: JobDetail, cronExpression: String, triggerName: String): CronTriggerFactoryBean {
    val calendar: Calendar = Calendar.getInstance()
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val factoryBean = CronTriggerFactoryBean()
    factoryBean.setJobDetail(jobDetail)
    factoryBean.setCronExpression(cronExpression)
    factoryBean.setStartTime(calendar.time)
    factoryBean.setStartDelay(0L)
    factoryBean.setName(triggerName)
    factoryBean.setMisfireInstruction(MISFIRE_INSTRUCTION_DO_NOTHING)
    return factoryBean
}

fun <T : Job> createJobDetail(jobClass: Class<T>, jobName: String): JobDetailFactoryBean {
    val factoryBean = JobDetailFactoryBean()
    factoryBean.setName(jobName)
    factoryBean.setJobClass(jobClass)
    factoryBean.setDurability(true)
    return factoryBean
}

@Configuration
class QuartzSubmitJobs {
    @Bean(name = ["staCoS3LoaderBeanJob"])
    fun staCoS3LoaderBeanJob(): JobDetailFactoryBean {
        return createJobDetail(StaCoS3LoaderJob::class.java, "Class StaCoS3Loader Job")
    }

    @Bean(name = ["staCoS3LoaderTrigger"])
    fun staCoS3LoaderTrigger(@Qualifier("staCoS3LoaderBeanJob") jobDetail: JobDetail): CronTriggerFactoryBean {
        return createCronTrigger(jobDetail, CRON_EVERY_MINUTE, "Class StaCoS3Loader Trigger")
    }

    @Bean(name = ["staCoDynamoDBLoaderBeanJob"])
    fun staCoDynamoDBLoaderBeanJob(): JobDetailFactoryBean {
        return createJobDetail(StaCoDynamoDBLoaderJob::class.java, "Class StaCoDynamoDBLoader Job")
    }

    @Bean(name = ["staCoDynamoDBLoaderTrigger"])
    fun staCoDynamoDBLoaderTrigger(@Qualifier("staCoDynamoDBLoaderBeanJob") jobDetail: JobDetail): CronTriggerFactoryBean {
        return createCronTrigger(jobDetail, CRON_EVERY_MINUTE, "Class StaCoDynamoDBLoader Trigger")
    }

    companion object {
        private const val CRON_EVERY_MINUTE = "0 0/1 * ? * * *"
    }

}

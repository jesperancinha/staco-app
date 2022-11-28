package org.jesperancinha.enterprise.staco.jobs

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import mu.KotlinLogging
import org.jesperancinha.enterprise.staco.jpa.domain.StaCoRepository
import org.jesperancinha.enterprise.staco.s3.AwsStacoFileService
import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobExecutionContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Component

@Component
@DisallowConcurrentExecution
class StaCoS3LoaderJob : Job {

    @Autowired
    lateinit var awsStacoFileService: AwsStacoFileService

    @Autowired
    lateinit var staCoRepository: StaCoRepository

    private val logger = KotlinLogging.logger {}

    override fun execute(context: JobExecutionContext) {
        logger.info { "Creating S3 file has started..." }
        CoroutineScope(IO).launch {
            awsStacoFileService.createCompressAndUploadToS3(staCoRepository.findAll(Sort.unsorted()).toList())
        }
    }
}

@Component
@DisallowConcurrentExecution
class StaCoDynamoDBLoaderJob : Job {

    private val logger = KotlinLogging.logger {}

    @Autowired
    lateinit var awsStacoFileService: AwsStacoFileService

    override fun execute(context: JobExecutionContext) {
        logger.info { "Downloading from S3 file has started..." }
        CoroutineScope(IO).launch {
            awsStacoFileService.downloadFileFromS3UpdateDynamoDBAndDelete()
        }

    }
}

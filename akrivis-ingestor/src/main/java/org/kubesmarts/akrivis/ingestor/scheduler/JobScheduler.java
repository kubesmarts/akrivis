package org.kubesmarts.akrivis.ingestor.scheduler;

import io.quarkus.runtime.StartupEvent;
import io.quarkus.scheduler.Scheduler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.kubesmarts.akrivis.dbmodel.Job;
import org.kubesmarts.akrivis.dbmodel.JobRepository;
import org.kubesmarts.akrivis.dbmodel.JobStatus;

import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@ApplicationScoped
public class JobScheduler {

    private static final Logger LOG = Logger.getLogger(JobScheduler.class.getName());

    @Inject
    Scheduler scheduler;

    @Inject
    JobRepository jobRepository;

    @Inject
    JobExecutor fetchJobExecutor;

    void schedule(@Observes StartupEvent ev) {

        List<Job> activeJobs = jobRepository.findActiveJobs();

        if (activeJobs.isEmpty()) {
            LOG.info("There are active jobs, not scheduling new job");
            return;
        }

        for (Job job : activeJobs) {
            addJob(job);
        }
    }

    public void addJob(Job job) {
        if (Objects.equals(job.status, JobStatus.SCHEDULED)) {
            IngestorHttpClient httpClient = IngestorHttpClient.findHttpClient(job.type);

            scheduler.newJob(job.id + job.endpoint)
                    .setCron(job.cron)
                    .setTask(executionContext -> fetchJobExecutor.run(job.id, httpClient))
                    .schedule();
        }
    }
}

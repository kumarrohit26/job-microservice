package com.jobs.jobms.job;

import java.util.List;

public interface JobService {
    List<Job> findAll();

    Job getJobById(Long id);

    void createJob(Job job);

    boolean deleteJobById(Long id);

    Job updateJobById(Job job, Long id);
}

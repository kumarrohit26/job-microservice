package com.jobs.jobms.job;

import com.jobs.jobms.job.dto.JobWithCompanyDTO;

import java.util.List;

public interface JobService {
    List<JobWithCompanyDTO> findAll();

    Job getJobById(Long id);

    void createJob(Job job);

    boolean deleteJobById(Long id);

    Job updateJobById(Job job, Long id);
}

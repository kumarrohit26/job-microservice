package com.jobs.jobms.job;

import com.jobs.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();

    JobDTO getJobById(Long id);

    void createJob(Job job);

    boolean deleteJobById(Long id);

    Job updateJobById(Job job, Long id);
}

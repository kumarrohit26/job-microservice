package com.jobs.jobms.job.mapper;

import com.jobs.jobms.job.Job;
import com.jobs.jobms.job.dto.JobDTO;
import com.jobs.jobms.job.external.Company;
import com.jobs.jobms.job.external.Review;

import java.util.List;

public class JobMapper {

    public static JobDTO map(Job job, Company company, List<Review> reviews){
        JobDTO jobDTO = new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;
    }
}

package com.jobs.jobms.job.impl;

import com.jobs.jobms.job.Job;
import com.jobs.jobms.job.JobRepository;
import com.jobs.jobms.job.JobService;
import com.jobs.jobms.job.dto.JobWithCompanyDTO;
import com.jobs.jobms.job.external.Company;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    @Value("${company_url}")
    private String company_url;

    JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        /*List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();
        for(Job job: jobs){
            jobWithCompanyDTOS.add(convertToDTO(job));
        }*/

        return jobs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private JobWithCompanyDTO convertToDTO(Job job){
        RestTemplate restTemplate = new RestTemplate();
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        Company company =  restTemplate.getForObject(company_url+job.getCompanyId(), Company.class);
        jobWithCompanyDTO.setJob(job);
        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
    }

    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public boolean deleteJobById(Long id) {
        if(jobRepository.existsById(id)){
            jobRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public Job updateJobById(Job updatedJob, Long id) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if(jobOptional.isPresent()){
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setLocation(updatedJob.getLocation());
            job.setCompanyId(updatedJob.getCompanyId());
            jobRepository.save(job);
            return job;
        }
        return null;
    }
}

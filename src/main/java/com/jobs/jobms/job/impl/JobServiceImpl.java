package com.jobs.jobms.job.impl;

import com.jobs.jobms.job.Job;
import com.jobs.jobms.job.JobRepository;
import com.jobs.jobms.job.JobService;
import com.jobs.jobms.job.clients.CompanyClient;
import com.jobs.jobms.job.clients.ReviewClient;
import com.jobs.jobms.job.dto.JobDTO;
import com.jobs.jobms.job.external.Company;
import com.jobs.jobms.job.external.Review;
import com.jobs.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {
    /*@Value("${company_url}")
    private String company_url;*/

    JobRepository jobRepository;
    CompanyClient companyClient;
    ReviewClient reviewClient;

    @Autowired
    RestTemplate restTemplate;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient,
                          ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
    public List<JobDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();

        /*List<JobWithCompanyDTO> jobWithCompanyDTOS = new ArrayList<>();
        for(Job job: jobs){
            jobWithCompanyDTOS.add(convertToDTO(job));
        }*/

        return jobs.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private JobDTO convertToDTO(Job job){

        Company company =  companyClient.getCompany(job.getCompanyId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());

        return JobMapper.map(job, company, reviews);
    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        if(job != null){
            return convertToDTO(job);
        }
        return null;
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

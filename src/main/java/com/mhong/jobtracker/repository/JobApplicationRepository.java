package com.mhong.jobtracker.repository;

import com.mhong.jobtracker.domain.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    // save(entity) - saves entity
    // findById(entity) - finds entity by ID, returns Optional<T>
    // findAll(id) - returns a list of all entities
    // delete(entity) - deletes an entity
    // deleteById(id) - deletes by ID
    // existsById(id) - checks if an entity exists

    List<JobApplication> findByUserId(Long userId);
}

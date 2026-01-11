package com.mhong.jobtracker.repository;

import com.mhong.jobtracker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // save(entity) - saves entity
    // findById(entity) - finds entity by ID, returns Optional<T>
    // findAll(id) - returns a list of all entities
    // delete(entity) - deletes an entity
    // deleteById(id) - deletes by ID
    // existsById(id) - checks if an entity exists

    public boolean existsByEmail(String email);
}

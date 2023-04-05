package com.microservice.usermanagement.repository;

import com.microservice.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<User, Integer> {
    User save(User user);
    Optional<User> findOneByEmail(String email);
}

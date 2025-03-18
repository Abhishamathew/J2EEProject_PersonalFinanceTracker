package com.rm.project.repository;

import com.rm.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);

    User findByUsernameAndPassword(String username, String password);
}

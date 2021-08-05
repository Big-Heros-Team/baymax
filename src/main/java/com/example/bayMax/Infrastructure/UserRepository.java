package com.example.bayMax.Infrastructure;

import com.example.bayMax.Domain.Roles;
import com.example.bayMax.Domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findUsersByUsername(String username);

    Users findUsersByRoles(Roles role);

    List<Users> findAllByRoles(Roles role);

    Users findUsersByFirstname(String firstname);

    Users findUsersById(Long userId);
}

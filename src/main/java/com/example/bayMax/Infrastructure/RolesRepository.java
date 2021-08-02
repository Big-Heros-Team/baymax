package com.example.bayMax.Infrastructure;

import com.example.bayMax.Domain.Roles;
import com.example.bayMax.Domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findRolesByName(String role);
}

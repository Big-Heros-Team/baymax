package com.example.bayMax.Infrastructure;

import com.example.bayMax.Domain.Requests;
import com.example.bayMax.Domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestsRepository extends JpaRepository<Requests,Long> {
    List<Requests> findByPatient(Users patient);
}

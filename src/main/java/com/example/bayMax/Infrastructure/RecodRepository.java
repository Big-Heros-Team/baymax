package com.example.bayMax.Infrastructure;

import com.example.bayMax.Domain.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecodRepository extends JpaRepository<Record, Long> {

}

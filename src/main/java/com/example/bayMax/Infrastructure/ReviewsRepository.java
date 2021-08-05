package com.example.bayMax.Infrastructure;

import com.example.bayMax.Domain.Reviews;
import com.example.bayMax.Domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    Reviews findReviewsByUserAndDoctor(Users user, String doctor);
}
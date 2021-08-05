package com.example.bayMax.Infrastructure;

import com.example.bayMax.Domain.Blog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogsRepository extends JpaRepository<Blog, Long> {
}

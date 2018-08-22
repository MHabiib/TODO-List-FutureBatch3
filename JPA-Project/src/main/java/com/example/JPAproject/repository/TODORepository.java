package com.example.JPAproject.repository;

import com.example.JPAproject.model.TODO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TODORepository extends JpaRepository<TODO, Long> {
    List<TODO> findAllByUser_Username(String username);
}

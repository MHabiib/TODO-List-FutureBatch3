package com.example.JPAproject.repository;

import com.example.JPAproject.model.USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface USERRepository extends JpaRepository<USER, String> {
    List<USER> findByUsername(String username);
}

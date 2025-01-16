package com.alexwave.restful.repositories;

import com.alexwave.restful.entities.Paper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Integer> {
}

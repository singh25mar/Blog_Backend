package com.BlogApplaction.backend.repository;

import com.BlogApplaction.backend.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,Integer> {
}

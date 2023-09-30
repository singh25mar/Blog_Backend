package com.BlogApplaction.backend.repository;

import com.BlogApplaction.backend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepo extends JpaRepository<Role,Integer> {
}

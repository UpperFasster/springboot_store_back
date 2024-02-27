package com.UpperFasster.Magazine.authorization.repositories;

import com.UpperFasster.Magazine.authorization.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}

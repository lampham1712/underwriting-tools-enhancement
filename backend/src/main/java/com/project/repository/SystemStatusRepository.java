package com.project.repository;

import com.project.model.SystemStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SystemStatusRepository extends JpaRepository<SystemStatus, UUID> {
    Optional<SystemStatus> findBySystemCode(String systemCode);
    boolean existsBySystemCode(String systemCode);
}

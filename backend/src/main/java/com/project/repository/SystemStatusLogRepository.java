package com.project.repository;

import com.project.model.SystemStatusLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemStatusLogRepository extends JpaRepository<SystemStatusLog, String> {
    List<SystemStatusLog> findBySystemCode(String systemCode);
    List<SystemStatusLog> findBySystemCodeOrderByCreatedAtDesc(String systemCode);
}

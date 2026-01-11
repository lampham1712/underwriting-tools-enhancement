package com.project.repository;

import com.project.model.SystemStatusLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SystemStatusLogRepository extends MongoRepository<SystemStatusLog, String> {
    List<SystemStatusLog> findBySystemCode(String systemCode);
    List<SystemStatusLog> findBySystemCodeOrderByCreatedAtDesc(String systemCode);
}

package com.championship.classificationsTable.repository;

import com.championship.classificationsTable.classificationsTable.ClassificationsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationTableRepository extends JpaRepository<ClassificationsTable, Integer> {
}

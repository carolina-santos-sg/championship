package com.championship.classificationsTable.repository;

import com.championship.classificationsTable.classificationsTable.ClassificationsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassificationTableRepository extends JpaRepository<ClassificationsTable, Integer> {

    @Query(nativeQuery = true,
                 value = " SELECT * " +
                         " FROM classifications_table ct " +
                         " WHERE ct.team = :teamId AND ct.championship_id = :championshipId ")
    ClassificationsTable selectByTeamAndChampionshipId(@Param("teamId") Integer teamId,
                                                       @Param("championshipId") Integer championshipId);

    @Query(nativeQuery = true,
                 value = "  SELECT * " +
                         " FROM classifications_table ct " +
                         " WHERE championship_id = :championshipId " +
                         " ORDER BY points DESC ")
    void listByChampionship(@Param("championshipId") Integer championshipId);
}

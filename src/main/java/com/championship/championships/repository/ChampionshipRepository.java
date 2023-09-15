package com.championship.championships.repository;

import com.championship.championships.championships.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChampionshipRepository extends JpaRepository<Championship, Integer> {

    @Query(nativeQuery = true,
                 value = "SELECT COUNT(*) > 0 " +
                         "FROM championship c " +
                         "WHERE championship_year  > :year AND championship_name = :championshipName ")
    boolean countChampionshipsByChampionshipYearAndChampionshipName(@Param("year") Integer year,
                                                                         @Param("championshipName") String championshipName);

    @Query(nativeQuery = true,
                 value = " SELECT c.championship_started " +
                         " FROM championship c " +
                         " WHERE c.id  = :championshipId ")
    boolean championshipStartedById(@Param("championshipId") Integer championshipId);
    @Query(nativeQuery = true,
                 value = " SELECT c.championship_finished " +
                         " FROM championship c " +
                         " WHERE c.id  = :championshipId ")
    boolean championshipFinishedById(@Param("championshipId") Integer championshipId);

    @Query(nativeQuery = true,
                 value = " SELECT COUNT(*) " +
                         " FROM classifications_table ct " +
                         " WHERE ct.championship_id = :championshipId")
    Integer countByChampionshipId(@Param("championshipId") Integer championshipId);

    @Query(nativeQuery = true,
                 value = " SELECT COUNT(*) " +
                         " FROM matches m " +
                         " WHERE m.championship_id = :championshipId ")
    Integer countByMatches(@Param("championshipId") Integer championshipId);
}

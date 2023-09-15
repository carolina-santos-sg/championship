package com.championship.matches.repository;

import com.championship.matches.matches.Matches;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Calendar;

@Repository
public interface MatchesRepository extends JpaRepository<Matches, Integer> {

    @Query(nativeQuery = true,
                 value = " SELECT COUNT(*) > 0 " +
                         "FROM matches m " +
                         "WHERE home_team = :homeTeamId " +
                         "AND visiting_team = :visitingTeamId " +
                         "AND championship_id = :championshipId ")
    boolean countByTeamsAndChampionshipId(@Param("homeTeamId") Integer homeTeamId,
                                          @Param("visitingTeamId") Integer visitingTeamId,
                                          @Param("championshipId") Integer championshipId);

    @Query(nativeQuery = true,
                 value = " SELECT COUNT(*) > 0 " +
                         " FROM matches m " +
                         " WHERE m.match_date = :matchDate " +
                         " AND (m.home_team = :teamId OR m.visiting_team = :teamId) ")
    boolean countDateByMatch(@Param("matchDate") Calendar matchDate,
                             @Param("teamId") Integer teamId);

}
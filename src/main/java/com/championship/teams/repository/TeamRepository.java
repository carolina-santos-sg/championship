package com.championship.teams.repository;

import com.championship.teams.teams.Teams;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TeamRepository extends JpaRepository<Teams, Integer> {
    @Query(nativeQuery = true,
                 value = " SELECT COUNT(*) > 0" +
                         " FROM teams t " +
                         " WHERE t.team_name = :teamName ")
    boolean countByTeamName(@Param("teamName") String teamName);

    @Query(nativeQuery = true,
            value = " SELECT COUNT(*) > 0 " +
                    " FROM classifications_table ct " +
                    " JOIN teams t ON ct.team = t.id " +
                    " JOIN championship c ON ct.championship_id = c.id " +
                    " WHERE t.id = :teamId " +
                    " AND c.championship_started = true ")
    boolean countByStartedChampionship(@Param("teamId") Integer teamId);

    @Query(nativeQuery = true,
            value = " SELECT COUNT(*) > 0 " +
                    " FROM teams t " +
                    " JOIN matches m ON (m.home_team = t.id OR m.visiting_team = t.id) " +
                    " JOIN championship c ON c.championship_started = TRUE " +
                    " WHERE t.id = :teamId ")
    boolean countByChampionshipIdAndMatches(@Param("teamId") Integer teamId);


}

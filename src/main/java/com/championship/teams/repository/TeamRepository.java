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
}

package com.championship.matches.matches;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "matches")
public class Matches {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int matchId;

    @Column(name = "home_team")
    private String homeTeam;

    @Column(name = "visiting_team")
    private String visitingTeam;

    @Column(name = "home_team_goals")
    private int homeTeamGoals;

    @Column(name = "visiting_team_goals")
    private int visitingTeamGoals;

    @Column(name = "championship")
    private String championship;

    @Column(name = "match_date")
    private Date matchDate = new Date();

    @Column(name = "started")
    private boolean started;

    @Column(name = "finished")
    private boolean finished;
}

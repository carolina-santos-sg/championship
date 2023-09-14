package com.championship.matches.matches;

import com.championship.championships.championships.Championship;
import com.championship.teams.teams.Teams;
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
    private Integer matchId;

    @OneToOne
    @JoinColumn(name = "home_team")
    private Teams homeTeamId;

    @OneToOne
    @JoinColumn(name = "visiting_team")
    private Teams visitingTeamId;

    @Column(name = "home_team_goals")
    private int homeTeamGoals;

    @Column(name = "visiting_team_goals")
    private int visitingTeamGoals;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championshipId;

    @Column(name = "match_date")
    private Date matchDate = new Date();

    @Column(name = "started")
    private boolean started;

    @Column(name = "finished")
    private boolean finished;
}

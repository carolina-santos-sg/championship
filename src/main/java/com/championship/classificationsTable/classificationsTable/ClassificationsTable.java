package com.championship.classificationsTable.classificationsTable;

import com.championship.championships.championships.Championship;
import com.championship.teams.teams.Teams;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "classifications_table")
public class ClassificationsTable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int classificationTableId;

    @ManyToOne
    @JoinColumn(name = "team")
    private Teams team;

    @ManyToOne
    @JoinColumn(name = "championship_id")
    private Championship championshipId;

    @Column(name = "points")
    private int points;

    @Column(name = "win")
    private int win;

    @Column(name = "draw")
    private int draw;

    @Column(name = "loss")
    private int loss;

    @Column(name = "goals_scored")
    private int goalsScored;

    @Column(name = "goals_conceded")
    private int goalsConceded;

    @Column(name = "goals_difference")
    private int goalsDifference;
}

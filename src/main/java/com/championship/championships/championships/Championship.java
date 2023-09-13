package com.championship.championships.championships;

import com.championship.classificationsTable.classificationsTable.ClassificationsTable;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "championship")
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int championshipId;

    @Column(name = "championship_name")
    private String championshipName;

    @Column(name = "championship_year")
    private int championshipYear;

    @Column(name = "championship_started")
    private boolean championshipStarted;

    @Column(name = "championship_finished")
    private boolean championshipFinished;

}

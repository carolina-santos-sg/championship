package com.championship.teams.teams;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@Table(name = "teams")
public class Teams {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer teamId;

    @Column(name = "team_name")
    private String teamName;
}

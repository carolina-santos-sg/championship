package com.championship.matches.dto;

import com.championship.championships.championships.Championship;
import com.championship.teams.teams.Teams;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

@Data
@NoArgsConstructor
public class MatchesDto {
    private Integer homeTeamId;

    private Integer visitingTeamId;

    private Integer championshipId;

    private Date matchDate = new Date();

}

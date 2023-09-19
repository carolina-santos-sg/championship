package com.championship.matches.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@NoArgsConstructor
public class MatchesDto {

    private Integer homeTeamId;

    private Integer visitingTeamId;

    private Integer championshipId;

    private Calendar matchDate = Calendar.getInstance();

}

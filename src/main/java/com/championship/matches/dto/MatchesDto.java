package com.championship.matches.dto;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;
import java.util.Date;

@Data
@NoArgsConstructor
public class MatchesDto {

    private Integer homeTeamId;

    private Integer visitingTeamId;

    private Integer championshipId;

    private Calendar matchDate = Calendar.getInstance();

}

package com.championship.classificationsTable.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.ArrayList;

@Data
public class StartChampionshipDto {
    @NotNull
    private ArrayList<Integer> teams = new ArrayList<>();

    @NotNull
    private Integer championshipId;
}

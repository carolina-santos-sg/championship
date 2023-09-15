package com.championship.classificationsTable.dto;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.ArrayList;

@Data
public class ListTeamsDto {
    @NotNull
    private ArrayList<Integer> teams = new ArrayList<>();

    private Integer championshipId;
}

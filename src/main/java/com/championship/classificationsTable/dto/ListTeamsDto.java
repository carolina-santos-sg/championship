package com.championship.classificationsTable.dto;

import lombok.Data;

import java.util.ArrayList;

@Data
public class ListTeamsDto {
    private ArrayList<Integer> teams = new ArrayList<>();
}

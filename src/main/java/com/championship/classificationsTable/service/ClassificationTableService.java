package com.championship.classificationsTable.service;

import com.championship.championships.repository.ChampionshipRepository;
import com.championship.classificationsTable.classificationsTable.ClassificationsTable;
import com.championship.classificationsTable.dto.ListTeamsDto;
import com.championship.classificationsTable.repository.ClassificationTableRepository;

import com.championship.teams.repository.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ClassificationTableService {
    private final ClassificationTableRepository classificationTableRepository;
    private final TeamRepository teamRepository;
    private final ChampionshipRepository championshipRepository;


    public ClassificationTableService(ClassificationTableRepository classificationTableRepository, TeamRepository teamRepository, ChampionshipRepository championshipRepository) {
        this.classificationTableRepository = classificationTableRepository;
        this.teamRepository = teamRepository;
        this.championshipRepository = championshipRepository;
    }

    //LISTAR
    public ResponseEntity<Object> listClassificationTable(){
        return ResponseEntity.ok(this.classificationTableRepository.findAll());
    }

    public void listById(Integer championshipId){
        this.classificationTableRepository.listByChampionship(championshipId);
    }

    //CRIAR
    public void createClassificationTable(ListTeamsDto listTeamsDto){

        List<Integer> teams = listTeamsDto.getTeams();

        for (Integer team : teams){
            if (Objects.isNull(listTeamsDto.getChampionshipId())){
                break;
            }

            if(Objects.nonNull(this.classificationTableRepository.selectByTeamAndChampionshipId(team, listTeamsDto.getChampionshipId()))){
                throw new RuntimeException("Time já cadastrado nesse campeonato");
            }

            ClassificationsTable classificationTable = new ClassificationsTable();

            classificationTable.setTeam(this.teamRepository.findById(team).get());
            classificationTable.setChampionshipId(this.championshipRepository.findById(listTeamsDto.getChampionshipId()).get());
            classificationTable.setLoss(0);
            classificationTable.setDraw(0);
            classificationTable.setWin(0);
            classificationTable.setPoints(0);
            classificationTable.setGoalsConceded(0);
            classificationTable.setGoalsScored(0);
            classificationTable.setGoalsDifference(0);

            this.classificationTableRepository.save(classificationTable);
        }
    }

}

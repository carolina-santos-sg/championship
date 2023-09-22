package com.championship.teams.service;

import com.championship.teams.repository.TeamRepository;
import com.championship.teams.teams.Teams;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    //LISTAR TIMES
    public ResponseEntity<Object> listTeams(){ return ResponseEntity.ok(this.teamRepository.findAll()); }


    //REGISTRAR TIMES
    @Transactional
    public Teams registerTeam(Teams team){
        if (Objects.isNull(team.getTeamName())){
            throw new RuntimeException("É preciso informar um nome!");
        }

        this.existsTeam(team.getTeamName().toUpperCase());

        team.setTeamName(team.getTeamName().toUpperCase());

        return this.teamRepository.save(team);
    }

    //DELETAR
    public void deleteTeam(Integer id){
        if (this.teamRepository.countByChampionshipIdAndMatches(id)){
            throw new RuntimeException("Não é possível deletar esse time");
        }

        Teams team = this.teamRepository.findById(id).get();

        this.teamRepository.delete(team);
    }

    //ATUALIZAR
    @Transactional
    public Teams updateTeam(Integer teamId, Teams nameTeam){

        if(this.teamRepository.countByStartedChampionship(teamId)){
            throw new RuntimeException("Não é possível fazer a alteração, pois o time está participando de um campeonato que já começou.");
        }

        existsTeam(nameTeam.getTeamName().toUpperCase());

        Teams team1 = this.teamRepository.findById(teamId).get();
        if(Objects.nonNull(team1.getTeamName().toUpperCase())){
            team1.setTeamName(nameTeam.getTeamName().toUpperCase());
        }

        return this.teamRepository.save(team1);
    }

    private void existsTeam(String teamName){
        if (this.teamRepository.countByTeamName(teamName)){
            throw new RuntimeException("Time já registrado!");
        }
    }

}

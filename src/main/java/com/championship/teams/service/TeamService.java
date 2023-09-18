package com.championship.teams.service;

import com.championship.teams.repository.TeamRepository;
import com.championship.teams.teams.Teams;
import com.sun.org.apache.bcel.internal.generic.PUSH;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class TeamService { ;
    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    //LISTAR TIMES
    public ResponseEntity<Object> listTeams(){ return ResponseEntity.ok(this.teamRepository.findAll()); }

//    public void listByTeamId(Integer teamId){
//        this.teamRepository.listByTeamId(teamId);
//    }

    //REGISTRAR TIMES
    @Transactional
    public Teams registerTeam(Teams team){
        if (Objects.isNull(team.getTeamName())){
            throw new RuntimeException("É preciso informar um nome!");
        }

        if (this.teamRepository.countByTeamName(team.getTeamName())){
            throw new RuntimeException("Time já registrado!");
        }

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
    public Teams updateTeam(Integer teamId){
        Teams team1 = this.teamRepository.findById(teamId).get();

        if(this.teamRepository.countByStartedChampionship(teamId)){
            throw new RuntimeException("Não é possível fazer a alteração, pois o time está participando de um campeonato que já começou.");
        }

        if(Objects.nonNull(team1.getTeamName())){
            team1.setTeamName(team1.getTeamName().toUpperCase());
        }
        if (this.teamRepository.countByTeamName(team1.getTeamName())){
            throw new RuntimeException("Time já registrado!");
        }

        return this.teamRepository.save(team1);
    }
}

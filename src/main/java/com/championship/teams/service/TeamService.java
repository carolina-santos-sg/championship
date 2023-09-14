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

    //REGISTRAR TIMES
    @Transactional
    public Teams registerTeam(Teams team){
        if (Objects.isNull(team.getTeamName())){
            throw new RuntimeException("É preciso informar um nome!");
        }

        if (this.teamRepository.countByTeamName(team.getTeamName())){
            throw new RuntimeException("Time já registrado!");
        }

        return this.teamRepository.save(team);
    }

    //DELETAR
    public void deleteTeam(Integer id){
        Teams team = this.teamRepository.findById(id).get();

        this.teamRepository.delete(team);
    }

    //ATUALIZAR
    @Transactional
    public Teams updateTeam(Teams team){
        Teams team1 = new Teams();

        if(Objects.nonNull(team.getTeamName())){
            team1.setTeamName(team.getTeamName());
        }

        return this.teamRepository.save(team1);
    }
}

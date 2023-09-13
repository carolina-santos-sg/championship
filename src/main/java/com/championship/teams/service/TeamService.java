package com.championship.teams.service;

import com.championship.teams.repository.TeamRepository;
import com.championship.teams.teams.Teams;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.xml.ws.Response;

@Service
public class TeamService {
    private Teams teams;
    private TeamRepository teamRepository;

    //LISTAR TIMES
    public ResponseEntity<Object> listTeams(){ return ResponseEntity.ok(this.teamRepository.findAll()); }

    //REGISTRAR TIMES
    @Transactional
    public Object registerTeam(@RequestBody Teams team){
        if (this.teamRepository.countByTeamName(team.getTeamName())){
            throw new RuntimeException("Time já registrado!");
        }

        return this.teamRepository.save(team);
    }
}

package com.championship.teams.APIs;

import com.championship.teams.repository.TeamRepository;
import com.championship.teams.service.TeamService;
import com.championship.teams.teams.Teams;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamsAPI {
    private Teams team;
    private TeamService teamService;

    @GetMapping("/list")
    public ResponseEntity<Object> listTeams(){ return ResponseEntity.ok(this.teamService.listTeams()); }

    @PostMapping("/register")
    public ResponseEntity<Object> registerTeam(@RequestBody Teams team){
        return ResponseEntity.ok(this.teamService.registerTeam(team));
    }
}

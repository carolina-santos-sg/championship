package com.championship.matches.APIs;

import com.championship.matches.dto.MatchesDto;
import com.championship.matches.matches.Matches;
import com.championship.matches.service.MatchesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/matches")
public class MatchesAPI {
    private final MatchesService matchesService;

    public MatchesAPI(MatchesService matchesService) {
        this.matchesService = matchesService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> listMatches(){
        return ResponseEntity.ok(this.matchesService.listMatches());
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createMatch(@RequestBody MatchesDto matchDto){
        return ResponseEntity.ok(this.matchesService.createMatch(matchDto));
    }

    @PutMapping("/start/{id}")
    public void startMatch(@PathVariable("id") Integer matchId){this.matchesService.startMatch(matchId);};

    @PutMapping("/finish/{id}")
    public void finishMatch(@PathVariable("id") Integer matchId){this.matchesService.finishMatch(matchId);};

    @DeleteMapping("/delete/{id}")
    public void deleteMatch(@PathVariable("id") Integer matchId){
        this.matchesService.deleteMatch(matchId);
    }

    @PutMapping("/matchResult/{id}")
    public ResponseEntity<Object> matchResult(@RequestBody Matches match, @PathVariable("id") Integer matchId){
        return ResponseEntity.ok(this.matchesService.matchResult(match, matchId));
    }
}

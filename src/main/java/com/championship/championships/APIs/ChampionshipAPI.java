package com.championship.championships.APIs;

import com.championship.championships.championships.Championship;
import com.championship.championships.service.ChampionshipService;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/championship")
public class ChampionshipAPI {
    private final ChampionshipService championshipService;

    public ChampionshipAPI(ChampionshipService championshipService) {
        this.championshipService = championshipService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> listChampionship(){
        return ResponseEntity.ok(this.championshipService.listChampionship());
    }

    @PostMapping("/register")
    public ResponseEntity<Object> registerChampionship(@RequestBody Championship championship){
        return ResponseEntity.ok(this.championshipService.registerChampionship(championship));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateChampionship(@RequestBody Championship championship, @PathVariable("id") Integer id){
        return ResponseEntity.ok(this.championshipService.updateChampionship(championship, id));
    }
}

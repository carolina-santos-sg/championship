package com.championship.matches.service;

import com.championship.championships.repository.ChampionshipRepository;
import com.championship.matches.dto.MatchesDto;
import com.championship.matches.matches.Matches;
import com.championship.matches.repository.MatchesRepository;
import com.championship.teams.repository.TeamRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Objects;

@Service
public class MatchesService {
    private final MatchesRepository matchesRepository;
    private final TeamRepository teamRepository;
    private final ChampionshipRepository championshipRepository;

    public MatchesService(MatchesRepository matchesRepository, TeamRepository teamRepository, ChampionshipRepository championshipRepository) {
        this.matchesRepository = matchesRepository;
        this.teamRepository = teamRepository;
        this.championshipRepository = championshipRepository;
    }

    public ResponseEntity<Object> listMatches(){ return ResponseEntity.ok(this.matchesRepository.findAll()); }

    @Transactional
    public ResponseEntity<Object> createMatch(MatchesDto matchDto){
        if (Objects.isNull(matchDto.getHomeTeamId())){ throw new RuntimeException("É necessário informar o Id do time da casa!"); }
        if (Objects.isNull(matchDto.getVisitingTeamId())){ throw new RuntimeException("É necessário informar o Id do time visitante!"); }
        if (Objects.isNull(matchDto.getMatchDate())){ throw new RuntimeException("É necessário informar a data da partida!"); }
        if (matchDto.getHomeTeamId().equals(matchDto.getVisitingTeamId())){ throw new RuntimeException("Um time não pode disputar uma partida contra si mesmo!"); }

        Calendar minimumDate = Calendar.getInstance();
        minimumDate.add(Calendar.DAY_OF_MONTH, 3);

        if (matchDto.getMatchDate().compareTo(minimumDate.getTime()) <= 0 ) { throw new RuntimeException("A partida deve acontecer em pelo menos 3 dias!"); }

        if (Objects.nonNull(matchDto.getChampionshipId())){
            if (!this.championshipRepository.championshipStartedById(matchDto.getChampionshipId()) || this.championshipRepository.championshipFinishedById(matchDto.getChampionshipId())){
                throw new RuntimeException("Só é possível realizar jogos em campeonatos que estejam com status inicializados e não finalizados!");
            }
        }

        Matches match = new Matches();
        match.setMatchDate(matchDto.getMatchDate());
        match.setChampionshipId(this.championshipRepository.findById(matchDto.getChampionshipId()).get());
        match.setHomeTeamId(this.teamRepository.findById(matchDto.getHomeTeamId()).get());
        match.setVisitingTeamId(this.teamRepository.findById(matchDto.getVisitingTeamId()).get());

        return ResponseEntity.ok(this.matchesRepository.save(match));
    }

    public void deleteMatch(Integer matchId){
        this.matchesRepository.deleteById(matchId);
    }

    @Transactional
    public ResponseEntity<Object> matchResult(Matches match, Integer matchId){
        Matches match1 = this.matchesRepository.findById(matchId).get();

        if (match.getVisitingTeamGoals() < 0|| match.getHomeTeamGoals() < 0) {throw new RuntimeException("O número de gols deve ser positivo!");}

        match1.setStarted(match.isStarted());
        match1.setFinished(match.isFinished());
        match1.setHomeTeamGoals(match.getHomeTeamGoals());
        match1.setVisitingTeamGoals(match.getVisitingTeamGoals());

        return ResponseEntity.ok(this.matchesRepository.save(match1));
    }

}
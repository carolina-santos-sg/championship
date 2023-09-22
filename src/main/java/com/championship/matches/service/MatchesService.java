package com.championship.matches.service;

import com.championship.championships.repository.ChampionshipRepository;
import com.championship.classificationsTable.classificationsTable.ClassificationsTable;
import com.championship.classificationsTable.repository.ClassificationTableRepository;
import com.championship.matches.dto.MatchesDto;
import com.championship.matches.dto.UpdateScoreMatchDto;
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
    private final ClassificationTableRepository classificationTableRepository;

    public MatchesService(MatchesRepository matchesRepository, TeamRepository teamRepository, ChampionshipRepository championshipRepository, ClassificationTableRepository classificationTableRepository) {
        this.matchesRepository = matchesRepository;
        this.teamRepository = teamRepository;
        this.championshipRepository = championshipRepository;
        this.classificationTableRepository = classificationTableRepository;
    }

    public ResponseEntity<Object> listMatches(){ return ResponseEntity.ok(this.matchesRepository.findAll()); }

    @Transactional
    public ResponseEntity<Object> createMatch(MatchesDto matchDto){

        this.validationsCreateMatch(matchDto);
        this.dateValidations(matchDto);

        Matches match = new Matches();
        match.setMatchDate(matchDto.getMatchDate());
        match.setChampionshipId(this.championshipRepository.findById(matchDto.getChampionshipId()).get());
        match.setHomeTeamId(this.teamRepository.findById(matchDto.getHomeTeamId()).get());
        match.setVisitingTeamId(this.teamRepository.findById(matchDto.getVisitingTeamId()).get());

        return ResponseEntity.ok(this.matchesRepository.save(match));
    }

    @Transactional
    public void startMatch(Integer matchId) {
        Matches match = this.matchesRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Essa partida não existe!"));

        if (match.isStarted()){
            throw new RuntimeException("Não se pode começar uma partida já iniciada.");
        }
        if (match.isFinished()){
            throw new RuntimeException("Não se pode começar uma partida já finalizada.");
        }

        match.setStarted(true);
        this.matchesRepository.save(match);
    }

    @Transactional
    public void finishMatch(Integer matchId) {
        Matches match = this.matchesRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Essa partida não existe!"));

        if (match.isStarted()){
            throw new RuntimeException("Não se pode encerrar uma partida não iniciada.");
        }
        if (match.isFinished()){
            throw new RuntimeException("Não se pode encerrar uma partida já finalizada.");
        }

        match.setFinished(true);
        this.matchesRepository.save(match);

        if(Objects.nonNull(match.getChampionshipId())){
            this.updateClassificationTable(match);
        }

    }

    @Transactional
    public ResponseEntity<Object> matchResult(UpdateScoreMatchDto updateScoreMatchDto, Integer matchId){
        Matches match1 = this.matchesRepository.findById(matchId).orElseThrow(() -> new RuntimeException("Essa partida não existe!"));

        if (!match1.isStarted()){throw new RuntimeException("Só é possível alterar uma partida iniciada!");}
        if (match1.isFinished()){throw new RuntimeException("Só é possível alterar uma partida não finalizada!");}
        if (updateScoreMatchDto.getVisitingTeamGoals() < 0|| updateScoreMatchDto.getHomeTeamGoals() < 0) {throw new RuntimeException("O número de gols deve ser positivo!");}

        match1.setHomeTeamGoals(updateScoreMatchDto.getHomeTeamGoals());
        match1.setVisitingTeamGoals(updateScoreMatchDto.getVisitingTeamGoals());

        return ResponseEntity.ok(this.matchesRepository.save(match1));
    }

    @Transactional
    public void updateClassificationTable(Matches match){
        ClassificationsTable classificationTableHomeTeamId = this.classificationTableRepository.selectByTeamAndChampionshipId
                (match.getHomeTeamId().getTeamId(), match.getChampionshipId().getChampionshipId());

        ClassificationsTable classificationTableVisitingTeamId = this.classificationTableRepository.selectByTeamAndChampionshipId
                (match.getVisitingTeamId().getTeamId(), match.getChampionshipId().getChampionshipId());

        if(match.getHomeTeamGoals() > match.getVisitingTeamGoals()) {
            classificationTableHomeTeamId.setWin(classificationTableHomeTeamId.getWin() + 1);
            classificationTableHomeTeamId.setPoints(classificationTableHomeTeamId.getPoints() + 3);
            classificationTableVisitingTeamId.setLoss(classificationTableVisitingTeamId.getLoss() + 1);
        }

        if(match.getHomeTeamGoals() < match.getVisitingTeamGoals()) {
            classificationTableVisitingTeamId.setWin(classificationTableVisitingTeamId.getWin() + 1);
            classificationTableVisitingTeamId.setPoints(classificationTableVisitingTeamId.getPoints() + 3);
            classificationTableHomeTeamId.setLoss(classificationTableHomeTeamId.getLoss() + 1);
        }

        if(match.getHomeTeamGoals() == match.getVisitingTeamGoals()) {
            classificationTableHomeTeamId.setDraw(classificationTableHomeTeamId.getDraw() + 1);
            classificationTableVisitingTeamId.setDraw(classificationTableVisitingTeamId.getDraw() + 1);
            classificationTableVisitingTeamId.setPoints(classificationTableVisitingTeamId.getPoints() + 1);
            classificationTableHomeTeamId.setPoints(classificationTableHomeTeamId.getPoints() + 1);
        }

        classificationTableHomeTeamId.setGoalsScored(classificationTableHomeTeamId.getGoalsScored() + match.getHomeTeamGoals());
        classificationTableHomeTeamId.setGoalsConceded(classificationTableHomeTeamId.getGoalsConceded() + match.getVisitingTeamGoals());
        classificationTableHomeTeamId.setGoalsDifference(classificationTableHomeTeamId.getGoalsDifference() + (classificationTableHomeTeamId.getGoalsScored() - classificationTableHomeTeamId.getGoalsConceded()));

        classificationTableVisitingTeamId.setGoalsScored(classificationTableVisitingTeamId.getGoalsScored() + match.getVisitingTeamGoals());
        classificationTableVisitingTeamId.setGoalsConceded(classificationTableVisitingTeamId.getGoalsConceded() + match.getHomeTeamGoals());
        classificationTableVisitingTeamId.setGoalsDifference(classificationTableVisitingTeamId.getGoalsDifference() + (classificationTableVisitingTeamId.getGoalsScored() - classificationTableVisitingTeamId.getGoalsConceded()));
    }

    public void dateValidations(MatchesDto matchDto){
        Calendar minimumDate = (Calendar) Calendar.getInstance().clone();
        minimumDate.add(Calendar.DAY_OF_MONTH, 3);

        if (matchDto.getMatchDate().compareTo(minimumDate) <= 0 ) {
            throw new RuntimeException("A partida deve acontecer em pelo menos 3 dias, " +
                                       "a partir da data atual!");
        }

        Calendar dayBefore = (Calendar) matchDto.getMatchDate().clone();
        dayBefore.add(Calendar.DAY_OF_MONTH, -1);
        Calendar dayAfter = (Calendar) matchDto.getMatchDate().clone();
        dayAfter.add(Calendar.DAY_OF_MONTH,1);

        if (this.matchesRepository.countDateByMatch(dayBefore, matchDto.getHomeTeamId())){
            throw new RuntimeException("O time da casa já tem jogo no dia seguinte!");
        }

        if (this.matchesRepository.countDateByMatch(matchDto.getMatchDate(), matchDto.getHomeTeamId())){
            throw new RuntimeException("O time da casa já tem jogo no dia!");
        }

        if (this.matchesRepository.countDateByMatch(dayAfter, matchDto.getHomeTeamId())){
            throw new RuntimeException("O time da casa já tem jogo no dia anterior!");
        }

        if (this.matchesRepository.countDateByMatch(dayBefore, matchDto.getVisitingTeamId())){
            throw new RuntimeException("O time visitante já tem jogo no dia seguinte!");
        }

        if (this.matchesRepository.countDateByMatch(matchDto.getMatchDate(), matchDto.getVisitingTeamId())){
            throw new RuntimeException("O time visitante já tem jogo no dia!");
        }

        if (this.matchesRepository.countDateByMatch(dayAfter, matchDto.getVisitingTeamId())){
            throw new RuntimeException("O time visitante já tem jogo no dia anterior!");
        }
    }

    private void validationsCreateMatch(MatchesDto matchDto){
        //validações - nulas
        if (Objects.isNull(matchDto.getHomeTeamId())){
            throw new RuntimeException("É necessário informar o Id do time da casa!"); }
        if (Objects.isNull(matchDto.getVisitingTeamId())){
            throw new RuntimeException("É necessário informar o Id do time visitante!"); }
        if (Objects.isNull(matchDto.getMatchDate())){
            throw new RuntimeException("É necessário informar a data da partida!"); }

        //validação - jogo contra si mesmo
        if (matchDto.getHomeTeamId().equals(matchDto.getVisitingTeamId())){
            throw new RuntimeException("Um time não pode disputar uma partida contra si mesmo!"); }



        //validações - campeonato
        if(Objects.nonNull(matchDto.getChampionshipId())) {
            //validações - jogos de ida e volta
            if (this.matchesRepository.countByTeamsAndChampionshipId(matchDto.getHomeTeamId(), matchDto.getVisitingTeamId(), matchDto.getChampionshipId())) {
                if (this.matchesRepository.countByTeamsAndChampionshipId(matchDto.getVisitingTeamId(), matchDto.getHomeTeamId(), matchDto.getChampionshipId())) {
                    throw new RuntimeException("As partidas de ida e volta já aconteceram!");
                } else {
                    throw new RuntimeException("Essa partida já ocorreu!");
                }
            }
            if (!this.championshipRepository.championshipStartedById(matchDto.getChampionshipId()) || this.championshipRepository.championshipFinishedById(matchDto.getChampionshipId())){
                throw new RuntimeException("Só é possível realizar jogos em campeonatos que estejam com status inicializados e não finalizados!");
            }
            if (!this.matchesRepository.countByChampionshipIdAndTeamId(matchDto.getChampionshipId(),matchDto.getHomeTeamId())
                    || !this.matchesRepository.countByChampionshipIdAndTeamId(matchDto.getChampionshipId(), matchDto.getVisitingTeamId())){
                throw new RuntimeException("Não se pode realizar jogos entre times de campeonatos diferentes");
            }
        }
    }
}
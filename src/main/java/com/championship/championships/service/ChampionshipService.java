package com.championship.championships.service;

import com.championship.championships.championships.Championship;
import com.championship.championships.repository.ChampionshipRepository;
import com.championship.classificationsTable.dto.ListTeamsDto;
import com.championship.classificationsTable.service.ClassificationTableService;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Objects;

@Service
public class ChampionshipService {
    private final ChampionshipRepository championshipRepository;
    private final ClassificationTableService classificationTableService;



    //CONSTRUCTOR
    public ChampionshipService(ChampionshipRepository championshipRepository, ClassificationTableService classificationTableService) {
        this.championshipRepository = championshipRepository;
        this.classificationTableService = classificationTableService;
    }

    //LISTAR
    public ResponseEntity<Object> listChampionship(){
        return ResponseEntity.ok(this.championshipRepository.findAll());
    }

    //REGISTRAR
    @Transactional
    public Championship registerChampionship( Championship championship){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        championship.setChampionshipName(championship.getChampionshipName().toUpperCase());

        if (championship.getChampionshipYear() < year){ throw new RuntimeException("Só é permitido criar campeonato no ano atual em diante!"); }
        if (this.championshipRepository.countChampionshipsByChampionshipYearAndChampionshipName(year, championship.getChampionshipName())){
            throw new RuntimeException("Não é permitido criar um campeonato com mesmo nome e ano!");
        }

        championship.setChampionshipStarted(false);
        championship.setChampionshipFinished(false);


        return this.championshipRepository.save(championship);
    }

    //ATUALIZAR
    @Transactional
    public Championship updateChampionship(Championship championship, Integer id){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);



        Championship championship1 = this.championshipRepository.findById(id).orElseThrow(() -> new RuntimeException("Campeonato não encontrado!"));
        championship1.setChampionshipName(championship1.getChampionshipName().toUpperCase());

        if (championship.getChampionshipYear() > 0){
            championship1.setChampionshipYear(championship.getChampionshipYear());
        }

        if (Objects.nonNull(championship.getChampionshipName())){
            championship1.setChampionshipName(championship.getChampionshipName().toUpperCase());
        }

        if (championship1.getChampionshipYear() < year){
            throw new RuntimeException("Só é permitido criar campeonato no ano atual em diante!"); }

        if (this.championshipRepository.countChampionshipsByChampionshipYearAndChampionshipName(year, championship.getChampionshipName().toUpperCase())){
            throw new RuntimeException("Não é permitido criar um campeonato com mesmo nome e ano!");
        }

        if (championship1.isChampionshipStarted()) {
            throw new RuntimeException("Não é permitido alterar um campeonato que já começou.");
        }

        return this.championshipRepository.save(championship1);
    }

    @Transactional
    public void startChampionship(ListTeamsDto listTeamsDto){
        Championship championship = this.championshipRepository.findById(listTeamsDto.getChampionshipId()).get();
        championship.setChampionshipStarted(true);
        this.championshipRepository.save(championship);
        this.classificationTableService.createClassificationTable(listTeamsDto);
    }
    @Transactional
    public void finishChampionship(Integer championshipId){
        //O campeonato só poderá ser finalizado se todos os times se enfrentarem duas vezes
        matchesPlayed(championshipId);

        Championship championship = this.championshipRepository.findById(championshipId).get();
        championship.setChampionshipFinished(true);

        if (!championship.isChampionshipStarted()){
            throw new RuntimeException("Só é possível finalizar um campeonato iniciado!");
        }

        this.championshipRepository.save(championship);
    }

    public void matchesPlayed (Integer championshipId){
        int possibleMatches, matchesPlayed;

        possibleMatches = this.championshipRepository.countByChampionshipId(championshipId) *
                         (this.championshipRepository.countByChampionshipId(championshipId) - 1);

        matchesPlayed = this.championshipRepository.countByMatches(championshipId);

        if (possibleMatches != matchesPlayed){
            throw new RuntimeException("Ainda há partidas para acontecer antes de encerrar o campeonato!");
        }
    }

    public void deleteChampionship(Integer championshipId){
        Championship championship = this.championshipRepository.findById(championshipId).orElseThrow(() -> new RuntimeException("Campeonato não encontrado."));

        if (championship.isChampionshipStarted()){
            throw new RuntimeException("Esse campeonato não pode ser deletado.");
        }

        this.championshipRepository.deleteById(championshipId);
    }

}

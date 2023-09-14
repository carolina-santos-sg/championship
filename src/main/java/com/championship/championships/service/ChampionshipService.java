package com.championship.championships.service;

import com.championship.championships.championships.Championship;
import com.championship.championships.repository.ChampionshipRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;

@Service
public class ChampionshipService {
    private final ChampionshipRepository championshipRepository;

    //CONSTRUCTOR
    public ChampionshipService(ChampionshipRepository championshipRepository) {
        this.championshipRepository = championshipRepository;
    }

    //LISTAR
    public ResponseEntity<Object> listChampionship(){
        return ResponseEntity.ok(this.championshipRepository.findAll());
    }

    //REGISTRAR
    public Championship registerChampionship( Championship championship){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        if (championship.getChampionshipYear() < year){ throw new RuntimeException("Só é permitido criar campeonato no ano atual em diante!"); }
        if (this.championshipRepository.countChampionshipsByChampionshipYearAndChampionshipName(year, championship.getChampionshipName())){
            throw new RuntimeException("Não é permitido criar um campeonato com mesmo nome e ano!");
        }

        return this.championshipRepository.save(championship);
    }

    //ATUALIZAR
    public Championship updateChampionship(Championship championship, Integer id){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        Championship championship1 = this.championshipRepository.findById(id).orElseThrow(() -> new RuntimeException("Campeonato não encontrado!"));
        if (championship.getChampionshipYear() > 0){
            championship1.setChampionshipYear(championship.getChampionshipYear());
        }
        if (Objects.nonNull(championship.getChampionshipName())){
            championship1.setChampionshipName(championship.getChampionshipName());
        }

        if (championship1.getChampionshipYear() < year){ throw new RuntimeException("Só é permitido criar campeonato no ano atual em diante!"); }
        if (this.championshipRepository.countChampionshipsByChampionshipYearAndChampionshipName(year, championship.getChampionshipName())){
            throw new RuntimeException("Não é permitido criar um campeonato com mesmo nome e ano!");
        }


        return this.championshipRepository.save(championship1);
    }
}

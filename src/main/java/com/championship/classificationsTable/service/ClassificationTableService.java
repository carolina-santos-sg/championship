package com.championship.classificationsTable.service;

import com.championship.classificationsTable.classificationsTable.ClassificationsTable;
import com.championship.classificationsTable.dto.ListTeamsDto;
import com.championship.classificationsTable.repository.ClassificationTableRepository;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static jdk.nashorn.internal.objects.NativeArray.forEach;

@Service
public class ClassificationTableService {
    private final ClassificationTableRepository classificationTableRepository;


    public ClassificationTableService(ClassificationTableRepository classificationTableRepository) {
        this.classificationTableRepository = classificationTableRepository;
    }

    //LISTAR
    public ResponseEntity<Object> listClassificationTable(){
        return ResponseEntity.ok(this.classificationTableRepository.findAll());
    }

    //CRIAR
    public ResponseEntity<Object> createClassificationTable(ListTeamsDto listTeamsDto){
        ClassificationsTable classificationTable = new ClassificationsTable();

        List<Integer> teams = listTeamsDto.getTeams();

//        teams.forEach();

        return ResponseEntity.ok(this.classificationTableRepository.save(classificationTable));
    }
}

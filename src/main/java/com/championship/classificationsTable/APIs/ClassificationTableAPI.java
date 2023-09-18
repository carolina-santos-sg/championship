package com.championship.classificationsTable.APIs;

import com.championship.classificationsTable.service.ClassificationTableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classificationtable")
public class ClassificationTableAPI {
    private final ClassificationTableService classificationTableService;


    public ClassificationTableAPI(ClassificationTableService classificationTableService) {
        this.classificationTableService = classificationTableService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> listClassificationTable(){
        return ResponseEntity.ok(this.classificationTableService.listClassificationTable());
    }

    @GetMapping("/listbyid/{id}")
    public void listById(@PathVariable("id") Integer championshipId){
        this.classificationTableService.listById(championshipId);
    }
}

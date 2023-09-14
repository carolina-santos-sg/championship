package com.championship.classificationsTable.APIs;

import com.championship.classificationsTable.dto.ListTeamsDto;
import com.championship.classificationsTable.service.ClassificationTableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/classificationtable")
public class ClassificationTableAPI {
    private final ClassificationTableService classificationTableService;
//    private ClassificationTableDto classificationTableDto;

    public ClassificationTableAPI(ClassificationTableService classificationTableService) {
        this.classificationTableService = classificationTableService;
    }

    @GetMapping("/list")
    public ResponseEntity<Object> listClassificationTable(){
        return ResponseEntity.ok(this.classificationTableService.listClassificationTable());
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createClassificationTable(@RequestBody ListTeamsDto listTeamsDto){
        return ResponseEntity.ok(this.classificationTableService.createClassificationTable(listTeamsDto));
    }
}

package com.example.progettotest.controller;

import com.example.progettotest.service.TNzDpReService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.progettotest.model.TNzDpRe;
import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping("/api/tnz-dp-re")
public class TNzDpReController {

    private final TNzDpReService tnzDpReService;

    @Autowired
    public TNzDpReController(TNzDpReService tnzDpReService) {
        this.tnzDpReService = tnzDpReService;
    }

    // Endpoint per tutte le descrizioni
    @GetMapping("/descriptions")
    @Operation(summary = "Get all descriptions from TNzDpRe records")
    public ResponseEntity<List<String>> getAllDescriptions() {
        List<String> descriptions = tnzDpReService.getAllDescriptions();
        return ResponseEntity.ok(descriptions);
    }

    // Endpoint per recuperare un record per ID
    @GetMapping("/{id}")
    @Operation(summary = "Get TNzDpRe record by ID")
    public ResponseEntity<TNzDpRe> getById(@PathVariable Long id) {
        TNzDpRe record = tnzDpReService.getById(id).orElse(null);
        if (record == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(record);
    }

    // Endpoint per cancellare un record per ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete TNzDpRe record by ID")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        tnzDpReService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Endpoint per recuperare tutti i record in base alla data di riferimento

@GetMapping("/all")
@Operation(summary = "Get all TNzDpRe records by reference date")
public ResponseEntity<List<TNzDpRe>> getAllRecords(
        @Parameter(
            description = "Reference date in format yyyy-MM-dd",
            schema = @Schema(type = "string", format = "date")
        )
        @RequestParam 
        @DateTimeFormat(pattern = "yyyy-MM-dd") String referenceDate) {
    
    List<TNzDpRe> records = tnzDpReService.getAllRecords(referenceDate);
    return ResponseEntity.ok(records);
}

}

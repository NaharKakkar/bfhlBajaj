package com.example.bfhl.controller;

import com.example.bfhl.dto.RequestDto;
import com.example.bfhl.dto.ResponseDto;
import com.example.bfhl.service.BfhlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    private final BfhlService service;

    public BfhlController(BfhlService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> process(@RequestBody RequestDto request) {
        ResponseDto resp = service.process(request);
        return ResponseEntity.ok(resp);
    }

    @GetMapping
    public ResponseEntity<Map<String, Integer>> getOperationCode() {
        return ResponseEntity.ok(Map.of("operation_code", 1));
    }
}

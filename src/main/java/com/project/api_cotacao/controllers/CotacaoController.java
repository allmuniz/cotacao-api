package com.project.api_cotacao.controllers;

import com.project.api_cotacao.services.CotacaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cotacao")
public class CotacaoController {

    private final CotacaoService cotacaoService;

    public CotacaoController(CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }

    @GetMapping("/moedas")
    public ResponseEntity getCotacao(@RequestParam String moedas) {
        Map response = cotacaoService.getCotacao(moedas);
        return ResponseEntity.ok(response);
    }
}

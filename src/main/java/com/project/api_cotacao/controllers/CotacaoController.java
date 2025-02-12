package com.project.api_cotacao.controllers;

import com.project.api_cotacao.services.CotacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/cotacao")
@Tag(name = "Quotation", description = "Quotation Manager")
public class CotacaoController {

    private final CotacaoService cotacaoService;

    public CotacaoController(CotacaoService cotacaoService) {
        this.cotacaoService = cotacaoService;
    }

    @GetMapping("/moedas")
    @Operation(description = "Endpoint responsavel por buscar as cotações",
            summary = "Cotação das moedas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cotação efetuada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Erro na Cotação das moedas"),
            @ApiResponse(responseCode = "403", description = "Erro na autenticação"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity getCotacao(@RequestParam String moedas) {
        Map response = cotacaoService.getCotacao(moedas);
        return ResponseEntity.ok(response);
    }
}

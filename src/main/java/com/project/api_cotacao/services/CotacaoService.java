package com.project.api_cotacao.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CotacaoService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, Map<String, Object>> getCotacao(String moedas) {
        String url = "https://economia.awesomeapi.com.br/json/last/" + moedas;
        Map<String, Map<String, Object>> cotacaoOriginal = restTemplate.getForObject(url, Map.class);

        if (cotacaoOriginal != null) {
            cotacaoOriginal.forEach((chave, valor) -> {
                valor.keySet().removeIf(key -> !key.equals("code") && !key.equals("codein") && !key.equals("name") && !key.equals("bid") && !key.equals("ask") && !key.equals("create_date"));
            });
        }
        return cotacaoOriginal;
    }
}

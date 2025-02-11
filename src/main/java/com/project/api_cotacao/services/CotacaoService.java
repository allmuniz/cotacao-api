package com.project.api_cotacao.services;

import com.project.api_cotacao.entities.exchange.dtos.ExchangeDto;
import com.project.api_cotacao.entities.exchange.exceptions.ExchangeNotFoundException;
import com.project.api_cotacao.producer.EmailProducer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.Optional;

@Service
public class CotacaoService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final EmailProducer emailProducer;

    public CotacaoService(EmailProducer emailProducer) {
        this.emailProducer = emailProducer;
    }

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

    public Optional<ExchangeDto> requestExchange(String sender, String receiver) {
        Map<String, Map<String, Object>> cotacaoOriginal = getCotacao(sender + "-" + receiver);

        if (cotacaoOriginal != null && !cotacaoOriginal.isEmpty()) {
            Map<String, Object> valores = cotacaoOriginal.values().iterator().next();

            if (valores.containsKey("bid") && valores.containsKey("ask")) {
                BigDecimal bid = new BigDecimal(valores.get("bid").toString()).setScale(2, RoundingMode.HALF_UP);
                BigDecimal ask = new BigDecimal(valores.get("ask").toString()).setScale(2, RoundingMode.HALF_UP);

                return Optional.of(new ExchangeDto(bid.doubleValue(), ask.doubleValue()));
            }
        }
        return Optional.empty();
    }

    @Scheduled(fixedRate = 5000) // Executa a cada 5 segundos
    public void executeTask() {
        ExchangeDto cotacao = requestExchange("BRL", "USD")
                .orElseThrow(() -> new ExchangeNotFoundException("Cotação não encontrada."));
        System.out.println("1 BRL equivale a: "+cotacao.bid()+" USD");

        if (cotacao.bid() <= 1){
            emailProducer.checkConditionAndSendMessage("contajogosmuniz@hotmail.com", "USD", cotacao.bid());
        }
    }
}

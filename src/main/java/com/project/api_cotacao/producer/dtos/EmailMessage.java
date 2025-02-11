package com.project.api_cotacao.producer.dtos;

import java.io.Serializable;

public record EmailMessage(String email, String coin, String message) implements Serializable {
}

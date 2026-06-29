package br.com.ortona.spring_boot_itau.controller;

import br.com.ortona.spring_boot_itau.dto.TransacaoRequest;
import br.com.ortona.spring_boot_itau.service.TransacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void criar(@Valid @RequestBody TransacaoRequest request) {
        transacaoService.criar(request);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public void limpar() {
        transacaoService.limpar();
    }
}

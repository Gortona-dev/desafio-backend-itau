package br.com.itau.desafiobackend.controller;

import br.com.itau.desafiobackend.dto.EstatisticaResponse;
import br.com.itau.desafiobackend.service.TransacaoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatistica")
public class EstatisticaController {

    private final TransacaoService transacaoService;

    public EstatisticaController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @GetMapping
    public EstatisticaResponse buscar() {
        return transacaoService.calcularEstatisticas();
    }
}

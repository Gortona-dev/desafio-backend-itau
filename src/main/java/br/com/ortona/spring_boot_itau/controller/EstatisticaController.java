package br.com.ortona.spring_boot_itau.controller;

import br.com.ortona.spring_boot_itau.dto.EstatisticaResponse;
import br.com.ortona.spring_boot_itau.service.TransacaoService;
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

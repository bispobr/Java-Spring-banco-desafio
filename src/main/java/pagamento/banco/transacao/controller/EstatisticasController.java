package pagamento.banco.transacao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pagamento.banco.transacao.dto.EstatisticaRespostaDTO;
import pagamento.banco.transacao.service.EstatisticaService;


import java.time.Duration;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/estatistica")
public class EstatisticasController {

    @Autowired
    EstatisticaService estatisticaService;

    @GetMapping
    @Operation(description = "Endpoint responsável por retornar as estatísticas das transações")
    @ApiResponse(responseCode = "200", description = "Estatisticas retornadas com sucesso")
    @ApiResponse(responseCode = "422", description = "intervalo inválido ")
    public ResponseEntity<EstatisticaRespostaDTO> GerarEstatisticas(@RequestParam(name = "intervalSeconds", required = false) Long IntervaloEmSegundos) {
        log.info("Solicitação para gerar estatisticas recebida");
        long interval = Objects.requireNonNullElse(IntervaloEmSegundos, 60L);
        return ResponseEntity.ok(estatisticaService.calcularEstatisticas(Duration.ofSeconds(interval)));
    }
}

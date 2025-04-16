package pagamento.banco.transacao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pagamento.banco.transacao.dto.EstatisticaRespostaDTO;
import pagamento.banco.transacao.service.EstatisticaServico;


import java.util.DoubleSummaryStatistics;

@RestController
@RequestMapping("/estatistica")
public class EstatisticasController {

    @Autowired
    EstatisticaServico estatisticaServico;

    @GetMapping
    @Operation(description = "Endpoint responsável por retornar as estatísticas das transações")
    @ApiResponse(responseCode = "200", description = "Estatisticas retornadas com sucesso")
    public ResponseEntity<EstatisticaRespostaDTO> getEstatistica(@RequestParam(value = "interavoDeBusca", required = false,defaultValue = "60") Integer interavoDeBusca){
        DoubleSummaryStatistics estatistica =  estatisticaServico.gerarEstatisticas(interavoDeBusca);

        if (estatistica.getCount() != 0) {
            return ResponseEntity.ok(new EstatisticaRespostaDTO(estatistica));
        }
        return ResponseEntity.ok(new EstatisticaRespostaDTO(0,0.0,0.0,0.0,0.0));

    }
}

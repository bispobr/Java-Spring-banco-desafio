package pagamento.banco.transacao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pagamento.banco.transacao.dto.TransacaoRequisicaoDTO;
import pagamento.banco.transacao.service.TransacaoService;


import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    TransacaoService transacaoService;

    @PostMapping
    @Operation(description = "Endpoint responsável por cadastrar novas transações")
    @ApiResponse(responseCode = "201", description = "Transação criada com sucesso")
    @ApiResponse(responseCode = "422", description = "validação falhou ")
    @ApiResponse(responseCode = "400", description = "JSON inválido")
    public ResponseEntity<Objects> criarTransacao(@RequestBody @Valid TransacaoRequisicaoDTO requisicaoDTO) {
        log.info("Solicitação para adicionar transação recebida");
       return   transacaoService.adicionarTransacao(requisicaoDTO);

    }

    @DeleteMapping
    @Operation(description = "Endpoint responsável por remover todos os dados de transação")
    @ApiResponse(responseCode = "200", description = "transações removidas com sucesso")
    public ResponseEntity<Void> limparTransacoes() {
        log.info("Solicitação para limpar transações recebida");
        transacaoService.limparEstatisticas();
        return ResponseEntity.ok().build();
    }
}

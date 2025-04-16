package pagamento.banco.transacao.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pagamento.banco.transacao.dto.TransacaoRequisicaoDTO;
import pagamento.banco.transacao.model.Trasnsacao;
import pagamento.banco.transacao.service.TransacaoServico;

import java.time.OffsetDateTime;
@Slf4j
@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    TransacaoServico transacaoServico;

    @PostMapping
    @Operation(description = "Endpoint responsável por cadastrar novas transações")
    @ApiResponse(responseCode = "201", description = "Transação criada com sucesso")
    @ApiResponse(responseCode = "422", description = "Transação não aceita campos não atendem os requisitos ")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Void> criarTransacao(@Valid @RequestBody TransacaoRequisicaoDTO requisicao){
        if (requisicao.dataHora().isAfter(OffsetDateTime.now())|| requisicao.valor() <= 0){
            log.error("Transação não aceita");
            return ResponseEntity.unprocessableEntity().build();
        }
        transacaoServico.AdicionarTransacao(new Trasnsacao(requisicao.valor(), requisicao.dataHora()));
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    @Operation(description = "Endpoint responsável por remover todos os dados de transação")
    @ApiResponse(responseCode = "200", description = "transações removidas com sucesso")
    public ResponseEntity<Void> apagarTransacao(){
        transacaoServico.limparTransacao();
        return  ResponseEntity.ok().build();
    }
}

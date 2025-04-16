package pagamento.banco.transacao.dto;

import jakarta.validation.constraints.NotNull;

import java.time.OffsetDateTime;

public record TransacaoRequisicaoDTO (@NotNull double valor, @NotNull OffsetDateTime dataHora ) {

}

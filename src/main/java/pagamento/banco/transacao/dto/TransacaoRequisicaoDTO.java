package pagamento.banco.transacao.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record TransacaoRequisicaoDTO(@NotNull @DecimalMin(value = "0.00") BigDecimal valor , @NotNull @PastOrPresent OffsetDateTime dataHora) {
}

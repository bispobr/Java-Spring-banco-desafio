package pagamento.banco.transacao.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Getter
@AllArgsConstructor
public class Transacao {

    private BigDecimal valor;
    private OffsetDateTime dataHora;
}

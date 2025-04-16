package pagamento.banco.transacao.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.OffsetDateTime;
@Getter
@AllArgsConstructor
public class Trasnsacao {

    private Double valor;
    private OffsetDateTime dataHora;


}

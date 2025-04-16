package pagamento.banco.transacao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pagamento.banco.transacao.model.Trasnsacao;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
@Slf4j
@Service
public class EstatisticaServico {

    @Autowired
    TransacaoServico transacaoServico;

    public DoubleSummaryStatistics gerarEstatisticas(Integer interavoDeBusca){
        log.info("Novas estatisticas geradas intervalo de busca " + interavoDeBusca);
        OffsetDateTime now = OffsetDateTime.now();
        return transacaoServico.transacao.stream()
                .filter(t->t.getDataHora().isAfter(now.minusSeconds(interavoDeBusca)))
                .mapToDouble(Trasnsacao::getValor)
                .summaryStatistics();

    }
}

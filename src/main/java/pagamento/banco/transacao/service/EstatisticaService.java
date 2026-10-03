package pagamento.banco.transacao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pagamento.banco.transacao.dto.EstatisticaRespostaDTO;
import pagamento.banco.transacao.exception.IntervaloInvalido;
import pagamento.banco.transacao.mapper.EstatisticaMapper;
import pagamento.banco.transacao.model.Transacao;
import pagamento.banco.transacao.repository.TransacaoRepository;


import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.Objects;

@Slf4j
@Service
public class EstatisticaService {

    @Autowired
    TransacaoRepository repository;

    @Autowired
    EstatisticaMapper eMapper;




    public EstatisticaRespostaDTO calcularEstatisticas(Duration intervalo) {
        if (intervalo.isNegative() || intervalo.isZero()) {
            log.info("Intervalo  de estatisticas invalido");
            throw new IntervaloInvalido(null);

        } else {

            OffsetDateTime horarioAtual = OffsetDateTime.now();
            OffsetDateTime tempo = horarioAtual.minus(intervalo);

            DoubleSummaryStatistics estatisticas = new DoubleSummaryStatistics();

            repository.findAll()
                    .stream()
                    .filter(tr -> !tr.getDataHora().isBefore(tempo) && !tr.getDataHora().isAfter(horarioAtual))
                    .map(Transacao::getValor)
                    .filter(Objects::nonNull)
                    .mapToDouble(BigDecimal::doubleValue)
                    .forEach(estatisticas::accept);

            if (estatisticas.getCount() == 0) {
                log.info("Estatisticas de zeros gerada");
                return new EstatisticaRespostaDTO(0L, 0.0, 0.0, 0.0, 0.0);
            } else {
                log.info("Estatisticas geradas");
                return eMapper.paraRespostaDTO(estatisticas);
            }
        }
    }
}

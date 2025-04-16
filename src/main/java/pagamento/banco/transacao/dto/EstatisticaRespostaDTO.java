package pagamento.banco.transacao.dto;

import java.util.DoubleSummaryStatistics;

public record EstatisticaRespostaDTO (long count, double sum, double avg, double min, double max) {
    public EstatisticaRespostaDTO(DoubleSummaryStatistics estatisticas ) {
        this(estatisticas.getCount(), estatisticas.getSum(),estatisticas.getAverage(), estatisticas.getMin(), estatisticas.getMax());
    }
}

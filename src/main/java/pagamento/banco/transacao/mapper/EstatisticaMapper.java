package pagamento.banco.transacao.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pagamento.banco.transacao.dto.EstatisticaRespostaDTO;

import java.util.DoubleSummaryStatistics;

@Mapper(componentModel = "spring")
public interface EstatisticaMapper {

    @Mapping(target = "avg", source = "average")
    EstatisticaRespostaDTO paraRespostaDTO(DoubleSummaryStatistics estatisticas);
}

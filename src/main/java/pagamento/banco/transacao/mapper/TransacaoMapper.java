package pagamento.banco.transacao.mapper;

import org.mapstruct.Mapper;
import pagamento.banco.transacao.dto.TransacaoRequisicaoDTO;
import pagamento.banco.transacao.model.Transacao;

@Mapper(componentModel = "spring")
public interface TransacaoMapper {

    Transacao paraEntidade (TransacaoRequisicaoDTO dto);


}

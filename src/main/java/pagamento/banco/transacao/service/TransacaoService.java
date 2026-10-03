package pagamento.banco.transacao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pagamento.banco.transacao.dto.TransacaoRequisicaoDTO;
import pagamento.banco.transacao.mapper.TransacaoMapper;
import pagamento.banco.transacao.model.Transacao;
import pagamento.banco.transacao.repository.TransacaoRepository;

import java.util.Objects;



@Slf4j
@Service
public class TransacaoService {

    @Autowired
    TransacaoRepository repository;

    @Autowired
    TransacaoMapper mapper;

    public ResponseEntity<Objects> adicionarTransacao(TransacaoRequisicaoDTO requisicaoDTO) {

        Transacao transacao = mapper.paraEntidade(requisicaoDTO);

        repository.save(transacao);
        log.info("Transação Adicionada");
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public void limparEstatisticas() {
        log.info("Apagando estatisticas da base de dados");
        repository.clear();
    }

}


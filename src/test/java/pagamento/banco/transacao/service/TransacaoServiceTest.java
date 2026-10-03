package pagamento.banco.transacao.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pagamento.banco.transacao.dto.TransacaoRequisicaoDTO;
import pagamento.banco.transacao.mapper.TransacaoMapper;
import pagamento.banco.transacao.model.Transacao;
import pagamento.banco.transacao.repository.TransacaoRepository;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TransacaoServiceTest {

    @Mock
    private TransacaoRepository repository;

    @Mock
    private TransacaoMapper mapper;

    @Autowired
    @InjectMocks
    private TransacaoService transacaoService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void adicionarTransacao_dadosValidos_transacaoCriadaComSucesso() {

        OffsetDateTime dataHora = OffsetDateTime.now();
        BigDecimal valor = new BigDecimal(100);

        TransacaoRequisicaoDTO dto = new TransacaoRequisicaoDTO(valor, dataHora);
        Transacao entidade = new Transacao(valor,dataHora);

        when(mapper.paraEntidade(dto)).thenReturn(entidade);

        ResponseEntity<Objects> resposta = transacaoService.adicionarTransacao(dto);

        verify(mapper, times(1)).paraEntidade(dto);
        verify(repository, times(1)).save(entidade);

        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
    }


    @Test
    void limparEstatisticas_baseComDados_limpaComSucesso() {
        doNothing().when(repository).clear();

        transacaoService.limparEstatisticas();

        verify(repository, times(1)).clear();
    }




}
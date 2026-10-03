package pagamento.banco.transacao.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import pagamento.banco.transacao.dto.EstatisticaRespostaDTO;
import pagamento.banco.transacao.exception.IntervaloInvalido;
import pagamento.banco.transacao.mapper.EstatisticaMapper;
import pagamento.banco.transacao.model.Transacao;
import pagamento.banco.transacao.repository.TransacaoRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EstatisticaServiceTest {

    @Mock
    private TransacaoRepository repository;

    @Mock
    private EstatisticaMapper eMapper;

    @Autowired
    @InjectMocks
    private EstatisticaService estatisticaService;


    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void calcularEstatisticas_intervaloNegativo_throwIntervaloInvalido() {
        Duration intervalo = Duration.ofSeconds(-10);

        assertThrows(IntervaloInvalido.class,
                () -> estatisticaService.calcularEstatisticas(intervalo)
        );
    }

    @Test
    void calcularEstatisticas_intervaloZero_throwIntervaloInvalido() {
        Duration intervalo = Duration.ZERO;

        assertThrows(IntervaloInvalido.class,
                () -> estatisticaService.calcularEstatisticas(intervalo)
        );
    }

    @Test
    void calcularEstatisticas_semTransacoesNoIntervalo_retornaEstatisticasZeradas() {
        // Mock
        when(repository.findAll()).thenReturn(Collections.emptyList());

        Duration intervalo = Duration.ofSeconds(30);

        EstatisticaRespostaDTO resposta = estatisticaService.calcularEstatisticas(intervalo);

        assertEquals(0L, resposta.count());
        assertEquals(0.0, resposta.sum());
        assertEquals(0.0, resposta.avg());
        assertEquals(0.0, resposta.min());
        assertEquals(0.0, resposta.max());
    }

    @Test
    void calcularEstatisticas_transacoesForaDoIntervalo_retornaEstatisticasZeradas() {
        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime velho = agora.minusMinutes(10);

        Transacao antiga = new Transacao(new BigDecimal(10),velho);

        when(repository.findAll()).thenReturn(List.of(antiga));

        Duration intervalo = Duration.ofSeconds(30);

        EstatisticaRespostaDTO resposta = estatisticaService.calcularEstatisticas(intervalo);

        assertEquals(0L, resposta.count());
    }

    @Test
    void calcularEstatisticas_transacoesValidas_retornaDTOMapeado() {
        OffsetDateTime agora = OffsetDateTime.now();

        Transacao t1 = new Transacao(new BigDecimal(10),agora.minusSeconds(5));
        Transacao t2 = new Transacao(new BigDecimal(20),agora.minusSeconds(10));


        when(repository.findAll()).thenReturn(List.of(t1, t2));

        DoubleSummaryStatistics estatisticas = new DoubleSummaryStatistics();
        estatisticas.accept(10.0);
        estatisticas.accept(20.0);

        EstatisticaRespostaDTO esperado = new EstatisticaRespostaDTO(2L, 30.0, 15.0, 10.0, 20.0);

        when(eMapper.paraRespostaDTO(any(DoubleSummaryStatistics.class)))
                .thenReturn(esperado);

        Duration intervalo = Duration.ofSeconds(30);

        EstatisticaRespostaDTO resposta = estatisticaService.calcularEstatisticas(intervalo);

        assertEquals(esperado, resposta);
        verify(eMapper, times(1)).paraRespostaDTO(any());
    }

    @Test
    void calcularEstatisticas_transacaoComValorNulo_ignoraValorNulo() {
        OffsetDateTime agora = OffsetDateTime.now();

        Transacao t1 = new Transacao(null,agora.minusSeconds(10));
        Transacao t2 = new Transacao(new BigDecimal(50),agora.minusSeconds(5));


        when(repository.findAll()).thenReturn(List.of(t1, t2));

        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        stats.accept(50.0);

        EstatisticaRespostaDTO dto = new EstatisticaRespostaDTO(1L, 50.0, 50.0, 50.0, 50.0);

        when(eMapper.paraRespostaDTO(any())).thenReturn(dto);

        Duration intervalo = Duration.ofSeconds(30);

        EstatisticaRespostaDTO resposta = estatisticaService.calcularEstatisticas(intervalo);

        assertEquals(1L, resposta.count());
        assertEquals(50.0, resposta.sum());
    }



}
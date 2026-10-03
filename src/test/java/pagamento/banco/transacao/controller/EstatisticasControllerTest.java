package pagamento.banco.transacao.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pagamento.banco.transacao.dto.EstatisticaRespostaDTO;
import pagamento.banco.transacao.exception.IntervaloInvalido;
import pagamento.banco.transacao.service.EstatisticaService;



import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EstatisticasControllerTest {

    @Mock
    private EstatisticaService estatisticaService;


    @InjectMocks
    private EstatisticasController estatisticasController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(estatisticasController).build();
    }

    @Test
    void GerarEstatisticas_intervaloValido_retornaOk() throws Exception {
        EstatisticaRespostaDTO resposta = new EstatisticaRespostaDTO(
                5L, 100.0, 10.0, 50.0, 200.0
        );

        when(estatisticaService.calcularEstatisticas(Duration.ofSeconds(30)))
                .thenReturn(resposta);

        mockMvc.perform(
                        get("/estatistica?intervalSeconds=30")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(5))
                .andExpect(jsonPath("$.sum").value(100.0));
    }

    @Test
    void GerarEstatisticas_semIntervaloUtilizaPadrao60s_retornaOk() throws Exception {
        EstatisticaRespostaDTO resposta = new EstatisticaRespostaDTO(
                0L, 0.0, 0.0, 0.0, 0.0
        );

        when(estatisticaService.calcularEstatisticas(Duration.ofSeconds(60)))
                .thenReturn(resposta);

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0));
    }

    @Test
    void GerarEstatisticas_intervaloInvalidoLancaExcecao_retornaUnprocessableEntity() throws Exception {
        when(estatisticaService.calcularEstatisticas(any()))
                .thenThrow(new IntervaloInvalido("Intervalo inválido"));

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(get("/estatistica?intervalSeconds=-5"))
                    .andExpect(status().isUnprocessableEntity());

        });

    }


}



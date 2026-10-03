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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import pagamento.banco.transacao.service.TransacaoService;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(MockitoExtension.class)
class TransacaoControllerTest {

    @Mock
    private TransacaoService transacaoService;


    @InjectMocks
    private TransacaoController transacaoController;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;


    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(transacaoController).build();
    }

    @Test
    void criarTransacao_dadosValidos_retornaCreated() throws Exception {

        when(transacaoService.adicionarTransacao(any()))
                .thenReturn(ResponseEntity.status(HttpStatus.CREATED).build());

        mockMvc.perform(
                post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"valor":"10","dataHora":"2025-12-04T12:11:39.084Z"}
                        """)
        ).andExpect(status().isCreated());
    }

    @Test
    void criarTransacao_jsonInvalido_retornaBadRequest() throws Exception {
        String jsonInvalido = "{ valor: 10, dataHora: }";

        mockMvc.perform(
                post("/transacao")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInvalido)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void criarTransacao_serviceLancaErro_retornaErroInterno() throws Exception {


        when(transacaoService.adicionarTransacao(any()))
                .thenThrow(new RuntimeException("erro inesperado"));



        assertThrows(ServletException.class, () -> {
            mockMvc.perform(
                    post("/transacao")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                        {"valor":"10","dataHora":"2025-12-04T12:11:39.084Z"}
                        """)
            ).andExpect(status().isInternalServerError());

        });

    }

    @Test
    void limparTransacoes_chamadaValida_retornaOk() throws Exception {
        mockMvc.perform(
                delete("/transacao")
        ).andExpect(status().isOk());

        verify(transacaoService, times(1)).limparEstatisticas();
    }

    @Test
    void limparTransacoes_serviceLancaErro_retornaErroInterno() throws Exception {
        doThrow(new RuntimeException("falha"))
                .when(transacaoService).limparEstatisticas();

        assertThrows(ServletException.class, () -> {
            mockMvc.perform(delete("/transacao"))
                    .andExpect(status().isInternalServerError());

        });


    }





}
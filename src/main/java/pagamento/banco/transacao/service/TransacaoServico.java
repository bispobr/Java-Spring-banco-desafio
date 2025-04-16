package pagamento.banco.transacao.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pagamento.banco.transacao.model.Trasnsacao;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
@Slf4j
public class TransacaoServico {

    public final Queue<Trasnsacao> transacao = new ConcurrentLinkedQueue<>();

    public void AdicionarTransacao (Trasnsacao trasnsacao){

        transacao.add(trasnsacao);
        log.info("Nova Transação adicionada");
    }

    public void limparTransacao(){
        transacao.clear();
        log.info("Fila de Transações apagadas");
    }
}

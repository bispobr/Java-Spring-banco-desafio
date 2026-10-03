package pagamento.banco.transacao.repository;

import org.springframework.stereotype.Repository;
import pagamento.banco.transacao.model.Transacao;


import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedQueue;

@Repository
public class TransacaoRepository {


    private final ConcurrentLinkedQueue<Transacao> queue = new ConcurrentLinkedQueue<>();

    public void save(Transacao transacao) {
        queue.add(transacao);
    }

    public Collection<Transacao> findAll() {
        return queue;
    }

    public void clear() {
        queue.clear();
    }


}

package pagamento.banco.transacao.exception;

public class IntervaloInvalido extends RuntimeException {
    public IntervaloInvalido(String message) {
        super(message);
    }
}

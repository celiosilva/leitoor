package br.com.delogic.leitoor.exception;


@SuppressWarnings("serial")
public class LeitoorException extends Exception {

    public LeitoorException(String message, Throwable cause) {
        super(message, cause);
    }

    public LeitoorException(String message) {
        super(message);
    }

    public static LeitoorException conviteInvalidoException(String convite) {
        return new LeitoorException("Este convite não possui validade em nosso sistema:" + convite);
    }

    public static LeitoorException contaInvalidaException(String mensagem) {
        return new LeitoorException(mensagem);
    }

    public static LeitoorException autenticacaoException(String mensagem) {
        return new LeitoorException(mensagem);
    }

}

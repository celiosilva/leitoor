package br.com.delogic.leitoor.exception;

@SuppressWarnings("serial")
public class RegraNegocioException extends ViolacaoException {

    public RegraNegocioException(String message) {
        super(message);
    }

    public RegraNegocioException() {
        super(null);
    }

    public static RegraNegocioException of(String message) {
        return new RegraNegocioException(message);
    }

    public RegraNegocioException withMessage(String message, Object... params) {
        return new RegraNegocioException(String.format(message, params)) {
            @Override
            public boolean equals(Object obj) {
                return RegraNegocioException.this == obj;
            }
        };
    }

    public RegraNegocioException thrownIf(boolean condition) throws RegraNegocioException {
        if (condition) {
            throw this;
        }
        return this;
    }

}

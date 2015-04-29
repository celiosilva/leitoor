package br.com.delogic.leitoor.util;

import br.com.delogic.jfunk.Has;

/**
 * Representa um resultado de execução de um método. Pode ser definido como
 * sucesso ou falha, contendo uma mensagem e valor de retorno.
 *
 * @author celio@delogic.com.br
 *
 * @since 25/05/2014
 * @param <E>
 */
public class Result<E> {

    public enum Status {
        SUCCESS, FAILURE
    }

    private final String    message;
    private final Status    status;
    private final E         value;
    private final Violation violation;

    private Result(String message, E value) {
        this.message = message;
        this.status = Status.SUCCESS;
        this.value = value;
        this.violation = null;
    }

    private Result(Violation violation, E value) {
        this.message = violation.getMessage();
        this.status = Status.FAILURE;
        this.value = value;
        this.violation = violation;
    }

    public static final <E> Result<E> success(String message) {
        return new Result<E>(message, null);
    }

    public static final <E> Result<E> success(String message, E value) {
        return new Result<E>(message, value);
    }

    public static final <E> Result<E> failure(Violation violation) {
        return new Result<E>(violation, null);
    }

    public static final <E> Result<E> failure(Violation violation, E value) {
        return new Result<E>(violation, value);
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    public boolean isFailure() {
        return status == Status.FAILURE;
    }

    public boolean hasValue() {
        return Has.content(value);
    }

    public E getValue() {
        return value;
    }

    public Violation getViolation() {
        return violation;
    }

    public Status getStatus() {
        return status;
    }

    public boolean failsAt(Violation violation, Violation... violations) {
        if (this.violation == null || this.status == Status.SUCCESS) {
            return false;
        }

        if (this.violation == violation || violation.equals(this.violation)) {
            return true;
        }

        if (violations != null && violations.length > 0) {
            for (Violation v : violations) {
                if (this.violation == v || this.violation.equals(v)) {
                    return true;
                }
            }
        }

        return false;
    }

}

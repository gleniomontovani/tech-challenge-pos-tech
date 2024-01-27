package br.com.postech.software.architecture.techchallenge.infrastructure.exception;

public class NotFoundException extends ApplicationException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String mensagem) {
        super(mensagem);
    }

    public NotFoundException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }

}

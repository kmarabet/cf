package com.cf.core.exeption;


public class TaException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public TaException() {
    }

    /**
     * {@inheritDoc}
     */
    public TaException(String message) {
        super(message);
    }

    /**
     * Constructor allow to create message using {@link String#format(String, Object...)} method.
     *
     * Example: {@code new TaException("Topic %s is illegal", topicName)}
     *
     * @param   message   template for {@link String#format(String, Object...)}
     * @param   args      arguments for {@link String#format(String, Object...)}
     */
    public TaException(String message, Object... args) {
        super(String.format(message, args));
    }

    public TaException(Throwable cause, String message) {
        super(message, cause);
    }

    /**
     * Constructor allow to create message using {@link String#format(String, Object...)} method and set cause exception
     *
     * Example: {@code new TaException("Topic %s is illegal", topicName)}
     *
     * @param cause cause exception
     * @param message template for {@link String#format(String, Object...)}
     * @param args arguments for {@link String#format(String, Object...)}
     */
    public TaException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }

    /**
     * {@inheritDoc}
     */
    public TaException(Throwable cause) {
        super(cause);
    }
}

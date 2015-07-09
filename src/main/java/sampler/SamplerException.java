package sampler;

public class SamplerException extends RuntimeException {
    public SamplerException(final Throwable cause) {
        super(cause);
    }

    public SamplerException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SamplerException(String message) {
        super(message);
    }
}

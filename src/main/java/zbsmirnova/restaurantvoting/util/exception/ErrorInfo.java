package zbsmirnova.restaurantvoting.util.exception;

import lombok.Getter;

@Getter
public class ErrorInfo {
    private final String url;
    private final String cause;
    private final String detail;

    public ErrorInfo(CharSequence url, Throwable e) {
        this.url = url.toString();
        this.cause = e.getClass().getSimpleName();
        this.detail = e.getLocalizedMessage();
    }
}
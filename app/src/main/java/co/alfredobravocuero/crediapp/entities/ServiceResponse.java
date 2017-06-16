package co.alfredobravocuero.crediapp.entities;

/**
 * Created by jggomez on 15-Jun-17.
 */

public class ServiceResponse<T> {

    private boolean success;

    private T result;

    private ErrorResponse error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }
}

package co.alfredobravocuero.crediapp.utilities;

/**
 * Created by jggomez on 15-Jun-17.
 */

public interface IViewBase {

    void showError(String error);

    void showProgress();

    void hideProgress();

    void enableInputs();

    void disableInputs();
}

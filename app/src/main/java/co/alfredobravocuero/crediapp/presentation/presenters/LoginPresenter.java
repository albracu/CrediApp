package co.alfredobravocuero.crediapp.presentation.presenters;

import co.alfredobravocuero.crediapp.entities.LoginResponse;
import co.alfredobravocuero.crediapp.presentation.activities.ILoginView;
import co.alfredobravocuero.crediapp.repository.LoginService;
import co.alfredobravocuero.crediapp.repository.service.IResponseService;

/**
 * Created by jggomez on 15-Jun-17.
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginView view;

    public LoginPresenter(ILoginView view) {
        this.view = view;
    }

    @Override
    public void login(String user, String password) {
        try {

            view.showProgress();
            view.disableInputs();

            LoginService loginService = new LoginService();

            loginService.login(user, password, new IResponseService<LoginResponse>() {
                @Override
                public void response(LoginResponse object) {
                    view.enableInputs();
                    view.hideProgress();
                    view.goToMain();
                    // TODO: solo para mostrar el nombre q trajo pero hay q quitarlo
                    view.showError("Usuario login = " + object.getUserId());
                }

                @Override
                public void error(Exception e) {
                    view.enableInputs();
                    view.hideProgress();
                    view.showError(e.getMessage());
                }
            });


        } catch (Exception e) {
            view.enableInputs();
            view.hideProgress();
            view.showError(e.getMessage());
        }
    }
}

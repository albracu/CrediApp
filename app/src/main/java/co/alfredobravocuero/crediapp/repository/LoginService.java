package co.alfredobravocuero.crediapp.repository;

import android.util.Log;

import co.alfredobravocuero.crediapp.entities.LoginResponse;
import co.alfredobravocuero.crediapp.entities.ServiceResponse;
import co.alfredobravocuero.crediapp.repository.service.ILoginService;
import co.alfredobravocuero.crediapp.repository.service.IResponseService;
import co.alfredobravocuero.crediapp.repository.service.ITokenResponse;
import co.alfredobravocuero.crediapp.utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jggomez on 15-Jun-17.
 */

public class LoginService {

    private static final String TAG = LoginService.class.getName();

    public void login(final String user, final String password, final IResponseService<LoginResponse> loginResponse) {

        try {

            TokenService tokenService = new TokenService();
            tokenService.getToken(user, new ITokenResponse() {
                @Override
                public void getToken(String token) {

                    ILoginService loginService = Utilities.getRetrofitService(ILoginService.class);

                    Call<ServiceResponse<LoginResponse>> call = loginService.login(user, token);

                    call.enqueue(new Callback<ServiceResponse<LoginResponse>>() {
                        @Override
                        public void onResponse(Call<ServiceResponse<LoginResponse>> call, Response<ServiceResponse<LoginResponse>> response) {

                            ServiceResponse<LoginResponse> resultLogin = response.body();
                            if (response.isSuccessful()) {
                                if (response.body().isSuccess() && resultLogin.getResult() != null) {
                                    loginResponse.response(resultLogin.getResult());
                                    return;
                                }
                            }

                            loginResponse.error(new Exception(resultLogin.getError().getMessage()));

                        }

                        @Override
                        public void onFailure(Call<ServiceResponse<LoginResponse>> call, Throwable t) {
                            loginResponse.error(new Exception(t));
                        }
                    });
                }
            });


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

}

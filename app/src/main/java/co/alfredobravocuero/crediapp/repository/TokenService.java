package co.alfredobravocuero.crediapp.repository;

import android.util.Log;

import co.alfredobravocuero.crediapp.entities.ServiceResponse;
import co.alfredobravocuero.crediapp.entities.TokenResponse;
import co.alfredobravocuero.crediapp.repository.service.ITokenResponse;
import co.alfredobravocuero.crediapp.repository.service.ITokenService;
import co.alfredobravocuero.crediapp.utilities.Constantes;
import co.alfredobravocuero.crediapp.utilities.Utilities;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jggomez on 15-Jun-17.
 */

public class TokenService {

    private static final String TAG = TokenService.class.getName();

    public void getToken(final String user, final ITokenResponse tokenResponse) {

        try {

            ITokenService token = Utilities.getRetrofitService(ITokenService.class);

            Call<ServiceResponse<TokenResponse>> call = token.getToken(user);

            call.enqueue(new Callback<ServiceResponse<TokenResponse>>() {
                @Override
                public void onResponse(Call<ServiceResponse<TokenResponse>> call, Response<ServiceResponse<TokenResponse>> response) {
                    ServiceResponse<TokenResponse> respLogin = response.body();

                    if (respLogin.isSuccess()) {
                        String accessKey = Utilities.md5(respLogin.getResult().getToken() + Constantes.ACCESS_KEY);
                        tokenResponse.getToken(accessKey);
                        return;
                    }

                    tokenResponse.getToken(respLogin.getResult().getToken());
                }

                @Override
                public void onFailure(Call<ServiceResponse<TokenResponse>> call, Throwable t) {
                    tokenResponse.getToken(t.getMessage());
                    Log.e(TAG, t.getMessage());
                }
            });


        } catch (Exception e) {
            tokenResponse.getToken(e.getMessage());
            Log.e(TAG, e.getMessage());
        }

    }

}

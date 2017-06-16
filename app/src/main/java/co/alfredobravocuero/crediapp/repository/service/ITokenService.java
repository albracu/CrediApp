package co.alfredobravocuero.crediapp.repository.service;

import co.alfredobravocuero.crediapp.entities.ServiceResponse;
import co.alfredobravocuero.crediapp.entities.TokenResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jggomez on 15-Jun-17.
 */

public interface ITokenService {

    @GET("webservice.php?operation=getchallenge")
    Call<ServiceResponse<TokenResponse>> getToken(@Query("username") String username);

}

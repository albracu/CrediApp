package co.alfredobravocuero.crediapp.repository.service;

import co.alfredobravocuero.crediapp.entities.LoginResponse;
import co.alfredobravocuero.crediapp.entities.ServiceResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by jggomez on 15-Jun-17.
 */

public interface ILoginService {

    @GET("webservice.php?operation=login")
    Call<ServiceResponse<LoginResponse>> login(@Query("username") String username, @Query("accessKey") String accesskey);

}

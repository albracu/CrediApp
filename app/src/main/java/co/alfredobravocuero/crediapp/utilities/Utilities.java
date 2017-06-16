package co.alfredobravocuero.crediapp.utilities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jggomez on 15-Jun-17.
 */

public class Utilities {

    public static String md5(String s) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(), 0, s.length());
            return new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static <T> T getRetrofitService(Class<T> service) {

        try {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constantes.URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();

            return retrofit.create(service);

        } catch (Exception e) {
            throw e;
        }
    }

}

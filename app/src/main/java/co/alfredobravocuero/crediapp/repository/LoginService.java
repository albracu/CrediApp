package co.alfredobravocuero.crediapp.repository;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import co.alfredobravocuero.crediapp.entities.ErrorResponse;
import co.alfredobravocuero.crediapp.entities.LoginResponse;
import co.alfredobravocuero.crediapp.repository.service.IResponseService;
import co.alfredobravocuero.crediapp.repository.service.ITokenResponse;
import co.alfredobravocuero.crediapp.utilities.Constantes;

/**
 * Created by jggomez on 15-Jun-17.
 */

public class LoginService {

    private static final String TAG = LoginService.class.getName();

    public void login(final String user, final String password, final IResponseService<LoginResponse> loginResponse) {

        try {

            TokenService tokenService = new TokenService();
            tokenService.getToken(user, password, new ITokenResponse() {
                @Override
                public void getToken(String token) {

                    new AsyncTask<String, Void, Object>() {

                        @Override
                        protected void onPostExecute(Object resp) {
                            if (resp instanceof LoginResponse) {
                                loginResponse.response((LoginResponse) resp);
                            } else if (resp instanceof Exception) {
                                loginResponse.error((Exception) resp);
                            } else {
                                ErrorResponse errorResponse = (ErrorResponse) resp;
                                loginResponse.error(new Exception(errorResponse.getMessage()));
                            }
                        }

                        @Override
                        protected Object doInBackground(String... params) {

                            String accessKey = params[0];
                            String username = params[1];

                            String data = null;
                            BufferedReader reader = null;

                            try {

                                data = URLEncoder.encode("username", "UTF-8")
                                        + "=" + URLEncoder.encode(username, "UTF-8");
                                data += "&" + URLEncoder.encode("accessKey", "UTF-8") + "="
                                        + URLEncoder.encode(accessKey, "UTF-8");

                                // Defined URL  where to send data
                                URL url = new URL(Constantes.URL_BASE + Constantes.OPERATION_LOGIN);

                                // Send POST data request
                                URLConnection conn = url.openConnection();
                                conn.setDoOutput(true);
                                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                                wr.write(data);
                                wr.flush();

                                // Get the server response
                                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                StringBuilder sb = new StringBuilder();
                                String line = null;

                                // Read Server Response
                                while ((line = reader.readLine()) != null) {
                                    // Append server response in string
                                    sb.append(line + "\n");
                                }

                                JSONObject resp = (JSONObject) new JSONTokener(sb.toString()).nextValue();

                                boolean success = (Boolean) resp.get("success");

                                if (success) {
                                    JSONObject result = resp.getJSONObject("result");
                                    LoginResponse objLoginResponse = new LoginResponse();
                                    objLoginResponse.setSessionName(result.getString("sessionName"));
                                    objLoginResponse.setUserId(result.getString("userId"));
                                    objLoginResponse.setVersion(result.getString("version"));
                                    objLoginResponse.setVtigerVersion(result.getString("vtigerVersion"));
                                    return objLoginResponse;
                                } else {
                                    JSONObject error = resp.getJSONObject("error");
                                    ErrorResponse errorResponse = new ErrorResponse();
                                    errorResponse.setCode(error.getString("code"));
                                    errorResponse.setMessage(error.getString("message"));
                                    return errorResponse;
                                }

                            } catch (Exception ex) {
                                return ex;
                            } finally {
                                try {
                                    reader.close();
                                } catch (Exception ex) {
                                    return ex;
                                }
                            }

                        }
                    }.execute(token, user);
                }
            });


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

    }

}

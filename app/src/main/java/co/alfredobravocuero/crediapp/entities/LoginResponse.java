package co.alfredobravocuero.crediapp.entities;

/**
 * Created by jggomez on 15-Jun-17.
 */

public class LoginResponse {

    private String sessionName;
    private String userId;
    private String version;
    private String vtigerVersion;

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVtigerVersion() {
        return vtigerVersion;
    }

    public void setVtigerVersion(String vtigerVersion) {
        this.vtigerVersion = vtigerVersion;
    }

}

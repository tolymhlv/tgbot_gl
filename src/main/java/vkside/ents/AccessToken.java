package vkside.ents;

public class AccessToken {

    public AccessToken() {
    }

    public String access_token;

    public Integer expires_in;

    public Integer user_id;

    public String getAccessToken() {
        return access_token;
    }

    public Integer getUserID() {
        return user_id;
    }
}

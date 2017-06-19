package live.itrip.app.data.net.request;

/**
 * Created by Feng on 2017/4/26.
 */

public class CreateAuthorization extends BaseParams {
    private String email;// 用户帐号
    private String password;// 用户密码

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

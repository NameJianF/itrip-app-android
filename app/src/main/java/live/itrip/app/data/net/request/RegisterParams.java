package live.itrip.app.data.net.request;

/**
 * Created by Feng on 2017/9/6.
 */

public class RegisterParams extends BaseParams {
    private String userName;
    private String password;
    private int sex;
    private String phoneToken;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhoneToken() {
        return phoneToken;
    }

    public void setPhoneToken(String phoneToken) {
        this.phoneToken = phoneToken;
    }
}

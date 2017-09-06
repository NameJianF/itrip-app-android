package live.itrip.app.data.net.request;

/**
 * Created by Feng on 2017/9/6.
 */

public class ResetPasswordParams extends BaseParams {
    private String newPwd;
    private String phoneToken;

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getPhoneToken() {
        return phoneToken;
    }

    public void setPhoneToken(String phoneToken) {
        this.phoneToken = phoneToken;
    }
}

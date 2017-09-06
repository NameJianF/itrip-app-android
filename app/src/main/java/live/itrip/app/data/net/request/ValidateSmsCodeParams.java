package live.itrip.app.data.net.request;

/**
 * Created by Feng on 2017/9/6.
 */

public class ValidateSmsCodeParams extends BaseParams {
    private String phoneNumber;
    private String smsCode;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }
}

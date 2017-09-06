package live.itrip.app.data.net.request;

/**
 * Created by Feng on 2017/9/6.
 */

public class SendSmsCodeParams extends BaseParams {
    private String phoneNumber;
    private int smsType;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getSmsType() {
        return smsType;
    }

    public void setSmsType(int smsType) {
        this.smsType = smsType;
    }
}

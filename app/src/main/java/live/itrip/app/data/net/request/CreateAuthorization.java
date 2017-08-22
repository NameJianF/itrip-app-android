package live.itrip.app.data.net.request;

/**
 * Created by Feng on 2017/4/26.
 */

public class CreateAuthorization extends BaseParams {

    private LoginData data;

    public LoginData getData() {
        return data;
    }

    public void setData(LoginData data) {
        this.data = data;
    }

    public static class LoginData {
        private String email;// 用户帐号
        private String password;// 用户密码
        /**
         * 是：1，否：0
         */
        private String ciphertext;// 密码是否是密文

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

        public String getCiphertext() {
            return ciphertext;
        }

        public void setCiphertext(String ciphertext) {
            this.ciphertext = ciphertext;
        }
    }

}

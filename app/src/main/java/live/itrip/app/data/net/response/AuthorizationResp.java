package live.itrip.app.data.net.response;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Feng on 2017/4/26.
 */

public class AuthorizationResp implements Parcelable {
    private int id;
    private String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.token);
    }

    public AuthorizationResp() {
    }

    protected AuthorizationResp(Parcel in) {
        this.id = in.readInt();
        this.token = in.readString();
    }

    public static final Parcelable.Creator<AuthorizationResp> CREATOR = new Parcelable.Creator<AuthorizationResp>() {
        @Override
        public AuthorizationResp createFromParcel(Parcel source) {
            return new AuthorizationResp(source);
        }

        @Override
        public AuthorizationResp[] newArray(int size) {
            return new AuthorizationResp[size];
        }
    };
}

package live.itrip.app.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Feng on 2017/4/26.
 */
public class UserExpandModel implements Parcelable, Serializable {
    private Long id;
    private String sex;
    private String realName;
    private String nickName;
    private Integer birthYear;
    private Integer birthMonth;
    private Integer birthday;
    private String qq;
    private String wechat;
    private String fixTel;
    private String job;
    private String country;
    private String province;
    private String city;
    private String area;
    private String street;
    private String address;
    private String companyName;
    private String companyUrl;
    private String companyLinkMan;
    private String companyTel;
    private String companyAddr;
    private String companyDescr;
    private String description;


    protected UserExpandModel(Parcel in) {
        id = in.readLong();
        sex = in.readString();
        realName = in.readString();
        nickName = in.readString();
        birthday = in.readInt();
        birthMonth = in.readInt();
        birthYear = in.readInt();
        qq = in.readString();
        wechat = in.readString();
        fixTel = in.readString();
        job = in.readString();
        country = in.readString();
        province = in.readString();
        city = in.readString();
        area = in.readString();
        street = in.readString();
        address = in.readString();
        companyName = in.readString();
        companyUrl = in.readString();
        companyLinkMan = in.readString();
        companyTel = in.readString();
        companyAddr = in.readString();
        companyDescr = in.readString();
        description = in.readString();
    }

    public static final Creator<UserExpandModel> CREATOR = new Creator<UserExpandModel>() {
        @Override
        public UserExpandModel createFromParcel(Parcel in) {
            return new UserExpandModel(in);
        }

        @Override
        public UserExpandModel[] newArray(int size) {
            return new UserExpandModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(sex);
        dest.writeString(realName);
        dest.writeString(nickName);
        dest.writeInt(birthday);
        dest.writeInt(birthMonth);
        dest.writeInt(birthYear);
        dest.writeString(qq);
        dest.writeString(wechat);
        dest.writeString(fixTel);
        dest.writeString(job);
        dest.writeString(country);
        dest.writeString(province);
        dest.writeString(city);
        dest.writeString(area);
        dest.writeString(street);
        dest.writeString(address);
        dest.writeString(companyName);
        dest.writeString(companyUrl);
        dest.writeString(companyLinkMan);
        dest.writeString(companyTel);
        dest.writeString(companyAddr);
        dest.writeString(companyDescr);
        dest.writeString(description);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public Integer getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(Integer birthMonth) {
        this.birthMonth = birthMonth;
    }

    public Integer getBirthday() {
        return birthday;
    }

    public void setBirthday(Integer birthday) {
        this.birthday = birthday;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getFixTel() {
        return fixTel;
    }

    public void setFixTel(String fixTel) {
        this.fixTel = fixTel;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getCompanyLinkMan() {
        return companyLinkMan;
    }

    public void setCompanyLinkMan(String companyLinkMan) {
        this.companyLinkMan = companyLinkMan;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getCompanyAddr() {
        return companyAddr;
    }

    public void setCompanyAddr(String companyAddr) {
        this.companyAddr = companyAddr;
    }

    public String getCompanyDescr() {
        return companyDescr;
    }

    public void setCompanyDescr(String companyDescr) {
        this.companyDescr = companyDescr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
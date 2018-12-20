package tao.deepbaytech.com.dayupicturesearch.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author IT烟酒僧
 * created   2018/12/19 16:58
 * desc:
 */
public class CouponInfoBean implements Parcelable {

    /**
     * couponMoney : 3
     * couponStartDate : 2018-09-06
     * couponEndDate : 2018-09-13
     */

    private String couponMoney;
    private String couponStartDate;
    private String couponEndDate;
    protected CouponInfoBean(Parcel in) {
        couponMoney = in.readString();
        couponStartDate = in.readString();
        couponEndDate = in.readString();
    }

    public static final Creator<CouponInfoBean> CREATOR = new Creator<CouponInfoBean>() {
        @Override public CouponInfoBean createFromParcel(Parcel in) {
            return new CouponInfoBean(in);
        }

        @Override public CouponInfoBean[] newArray(int size) {
            return new CouponInfoBean[size];
        }
    };

    public String getCouponMoney() {
        return couponMoney;
    }

    public void setCouponMoney(String couponMoney) {
        this.couponMoney = couponMoney;
    }

    public String getCouponStartDate() {
        return couponStartDate;
    }

    public void setCouponStartDate(String couponStartDate) {
        this.couponStartDate = couponStartDate;
    }

    public String getCouponEndDate() {
        return couponEndDate;
    }

    public void setCouponEndDate(String couponEndDate) {
        this.couponEndDate = couponEndDate;
    }

    @Override public int describeContents() {
        return 0;
    }
    public CouponInfoBean(){}
    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(couponMoney);
        dest.writeString(couponStartDate);
        dest.writeString(couponEndDate);
    }
}


package tao.deepbaytech.com.dayupicturesearch.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

import java.util.List;

/**
 * @author IT烟酒僧
 * created   2018/12/19 16:57
 * desc:
 */
@Keep
public class ShopInfoBean implements Parcelable {
    /**
     * name : 黛梦思睡衣馆11
     * scores : ["4.8","4.8","4.8"]
     */

    private String       name;
    private List<String> scores;

    private String icon;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getScores() {
        return scores;
    }

    public void setScores(List<String> scores) {
        this.scores = scores;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public static Creator<ShopInfoBean> getCREATOR() {
        return CREATOR;
    }

    public ShopInfoBean() {
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeStringList(this.scores);
        dest.writeString(this.icon);
    }

    protected ShopInfoBean(Parcel in) {
        this.name = in.readString();
        this.scores = in.createStringArrayList();
        this.icon = in.readString();
    }

    public static final Creator<ShopInfoBean> CREATOR = new Creator<ShopInfoBean>() {
        @Override public ShopInfoBean createFromParcel(Parcel source) {
            return new ShopInfoBean(source);
        }

        @Override public ShopInfoBean[] newArray(int size) {
            return new ShopInfoBean[size];
        }
    };
}


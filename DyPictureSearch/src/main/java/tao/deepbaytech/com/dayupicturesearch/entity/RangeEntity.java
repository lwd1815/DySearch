package tao.deepbaytech.com.dayupicturesearch.entity;

/**
 * @author IT烟酒僧
 * created   2018/12/19 17:44
 * desc:
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * Created by x on 2016/10/27.
 */
@Keep
public class RangeEntity implements Parcelable {

    private int x1;

    private int y1;

    private int x2;

    private int y2;

    public void setX1(int x1){
        this.x1 = x1;
    }
    public int getX1(){
        return this.x1;
    }
    public void setY1(int y1){
        this.y1 = y1;
    }
    public int getY1(){
        return this.y1;
    }
    public void setX2(int x2){
        this.x2 = x2;
    }
    public int getX2(){
        return this.x2;
    }
    public void setY2(int y2){
        this.y2 = y2;
    }
    public int getY2(){
        return this.y2;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.x1);
        dest.writeInt(this.y1);
        dest.writeInt(this.x2);
        dest.writeInt(this.y2);
    }

    public RangeEntity() {
    }

    protected RangeEntity(Parcel in) {
        this.x1 = in.readInt();
        this.y1 = in.readInt();
        this.x2 = in.readInt();
        this.y2 = in.readInt();
    }

    public static final Parcelable.Creator<RangeEntity> CREATOR = new Parcelable.Creator<RangeEntity>() {
        @Override
        public RangeEntity createFromParcel(Parcel source) {
            return new RangeEntity(source);
        }

        @Override
        public RangeEntity[] newArray(int size) {
            return new RangeEntity[size];
        }
    };

    @Override
    public String toString() {
        return "RangeEntity{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }
}


package tao.deepbaytech.com.dayupicturesearch.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

/**
 * @author IT烟酒僧
 * created   2018/12/19 17:46
 * desc:
 */
@Keep
public class CategoryEntity implements Parcelable {
    private int key;

    private String value;

    public void setKey(int key){
        this.key = key;
    }
    public int getKey(){
        return this.key;
    }
    public void setValue(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.key);
        dest.writeString(this.value);
    }

    public CategoryEntity() {
    }

    protected CategoryEntity(Parcel in) {
        this.key = in.readInt();
        this.value = in.readString();
    }

    public static final Parcelable.Creator<CategoryEntity> CREATOR = new Parcelable.Creator<CategoryEntity>() {
        @Override
        public CategoryEntity createFromParcel(Parcel source) {
            return new CategoryEntity(source);
        }

        @Override
        public CategoryEntity[] newArray(int size) {
            return new CategoryEntity[size];
        }
    };

    @Override
    public String toString() {
        return "CategoryEntity{" +
                "key=" + key +
                ", value='" + value + '\'' +
                '}';
    }
}


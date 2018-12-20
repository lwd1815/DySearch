package tao.deepbaytech.com.dayupicturesearch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author IT烟酒僧
 * created   2018/12/19 16:58
 * desc:
 */
public class CommentInfoBean implements Parcelable {

    /**
     * count : 0
     * tags : [{"name":"很好值得买","count":11752},{"name":"质量不错","count":7637},{"name":"物流也超快","count":3059},{"name":"性价比高","count":1230},{"name":"正品","count":164},{"name":"而且还包邮","count":136},{"name":"物流服务好","count":37}]
     */

    private int            count;
    private List<TagsBean> tags;

    protected CommentInfoBean(Parcel in) {
        count = in.readInt();
    }

    public static final Creator<CommentInfoBean> CREATOR = new Creator<CommentInfoBean>() {
        @Override public CommentInfoBean createFromParcel(Parcel in) {
            return new CommentInfoBean(in);
        }

        @Override public CommentInfoBean[] newArray(int size) {
            return new CommentInfoBean[size];
        }
    };

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    @Override public int describeContents() {
        return 0;
    }

    public CommentInfoBean(){}
    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(count);
    }

    public  class TagsBean implements Parcelable{
        /**
         * name : 很好值得买
         * count : 11752
         */

        private String name;
        private int count;

        protected TagsBean(Parcel in) {
            name = in.readString();
            count = in.readInt();
        }

        public  final Creator<TagsBean> CREATOR = new Creator<TagsBean>() {
            @Override public TagsBean createFromParcel(Parcel in) {
                return new TagsBean(in);
            }

            @Override public TagsBean[] newArray(int size) {
                return new TagsBean[size];
            }
        };

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        @Override public int describeContents() {
            return 0;
        }

        public TagsBean(){}
        @Override public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(name);
            dest.writeInt(count);
        }
    }
}


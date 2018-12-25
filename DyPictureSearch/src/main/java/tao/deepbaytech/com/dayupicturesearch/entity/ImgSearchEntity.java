package tao.deepbaytech.com.dayupicturesearch.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Keep;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author IT烟酒僧
 * created   2018/12/19 17:47
 * desc:
 */

@Keep
public class ImgSearchEntity implements Parcelable {

    private String id;

    private int state;

    private String stateSmg;

    private int pageCurr;

    private int pageTotal;

    private String updateTime;

    private int sortId;

    private int sortType;

    private int categoryId;

    private int attribute;

    private ArrayList<CategoryEntity> genderItems;

    private ArrayList<CategoryEntity> categoryItems;

    private ArrayList<CategoryEntity> brandItems;

    private ArrayList<CategoryEntity> labelItems;

    private ArrayList<CategoryEntity> subClassItems;

    private String[] priceRanges;

    private RangeEntity range;

    private ArrayList<ProductItemBean> productItems;

    private String keyword;

    private Integer brandId;

    private  Integer subClassId;

    private  String nowPrice;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateSmg() {
        return stateSmg;
    }

    public void setStateSmg(String stateSmg) {
        this.stateSmg = stateSmg;
    }

    public int getPageCurr() {
        return pageCurr;
    }

    public void setPageCurr(int pageCurr) {
        this.pageCurr = pageCurr;
    }

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getSortId() {
        return sortId;
    }

    public void setSortId(int sortId) {
        this.sortId = sortId;
    }

    public int getSortType() {
        return sortType;
    }

    public void setSortType(int sortType) {
        this.sortType = sortType;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getAttribute() {
        return attribute;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public ArrayList<CategoryEntity> getGenderItems() {
        return genderItems;
    }

    public void setGenderItems(ArrayList<CategoryEntity> genderItems) {
        this.genderItems = genderItems;
    }

    public ArrayList<CategoryEntity> getCategoryItems() {
        return categoryItems;
    }

    public void setCategoryItems(ArrayList<CategoryEntity> categoryItems) {
        this.categoryItems = categoryItems;
    }

    public ArrayList<CategoryEntity> getBrandItems() {
        return brandItems;
    }

    public void setBrandItems(ArrayList<CategoryEntity> brandItems) {
        this.brandItems = brandItems;
    }

    public ArrayList<CategoryEntity> getLabelItems() {
        return labelItems;
    }

    public void setLabelItems(ArrayList<CategoryEntity> labelItems) {
        this.labelItems = labelItems;
    }

    public ArrayList<CategoryEntity> getSubClassItems() {
        return subClassItems;
    }

    public void setSubClassItems(ArrayList<CategoryEntity> subClassItems) {
        this.subClassItems = subClassItems;
    }

    public String[] getPriceRanges() {
        return priceRanges;
    }

    public void setPriceRanges(String[] priceRanges) {
        this.priceRanges = priceRanges;
    }

    public RangeEntity getRange() {
        return range;
    }

    public void setRange(RangeEntity range) {
        this.range = range;
    }

    public ArrayList<ProductItemBean> getProductItems() {
        return productItems;
    }

    public void setProductItems(ArrayList<ProductItemBean> productItems) {
        this.productItems = productItems;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Integer getBrandId() {
        return brandId;
    }

    public void setBrandId(Integer brandId) {
        this.brandId = brandId;
    }

    public Integer getSubClassId() {
        return subClassId;
    }

    public void setSubClassId(Integer subClassId) {
        this.subClassId = subClassId;
    }

    public String getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeInt(this.state);
        dest.writeString(this.stateSmg);
        dest.writeInt(this.pageCurr);
        dest.writeInt(this.pageTotal);
        dest.writeString(this.updateTime);
        dest.writeInt(this.sortId);
        dest.writeInt(this.sortType);
        dest.writeInt(this.categoryId);
        dest.writeInt(this.attribute);
        dest.writeTypedList(this.genderItems);
        dest.writeTypedList(this.categoryItems);
        dest.writeTypedList(this.brandItems);
        dest.writeTypedList(this.labelItems);
        dest.writeTypedList(this.subClassItems);
        dest.writeStringArray(this.priceRanges);
        dest.writeParcelable(this.range, flags);
        dest.writeTypedList(this.productItems);
        dest.writeString(this.keyword);
        dest.writeValue(this.brandId);
        dest.writeValue(this.subClassId);
        dest.writeString(this.nowPrice);
    }

    public ImgSearchEntity() {
    }

    protected ImgSearchEntity(Parcel in) {
        this.id = in.readString();
        this.state = in.readInt();
        this.stateSmg = in.readString();
        this.pageCurr = in.readInt();
        this.pageTotal = in.readInt();
        this.updateTime = in.readString();
        this.sortId = in.readInt();
        this.sortType = in.readInt();
        this.categoryId = in.readInt();
        this.attribute = in.readInt();
        this.genderItems = in.createTypedArrayList(CategoryEntity.CREATOR);
        this.categoryItems = in.createTypedArrayList(CategoryEntity.CREATOR);
        this.brandItems = in.createTypedArrayList(CategoryEntity.CREATOR);
        this.labelItems = in.createTypedArrayList(CategoryEntity.CREATOR);
        this.subClassItems = in.createTypedArrayList(CategoryEntity.CREATOR);
        this.priceRanges = in.createStringArray();
        this.range = in.readParcelable(RangeEntity.class.getClassLoader());
        this.productItems = in.createTypedArrayList(ProductItemBean.CREATOR);
        this.keyword = in.readString();
        this.brandId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.subClassId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.nowPrice = in.readString();
    }

    public static final Parcelable.Creator<ImgSearchEntity> CREATOR = new Parcelable.Creator<ImgSearchEntity>() {
        @Override
        public ImgSearchEntity createFromParcel(Parcel source) {
            return new ImgSearchEntity(source);
        }

        @Override
        public ImgSearchEntity[] newArray(int size) {
            return new ImgSearchEntity[size];
        }
    };

    @Override
    public String toString() {
        return "ImgSearchEntity{" +
                "id='" + id + '\'' +
                ", state=" + state +
                ", stateSmg='" + stateSmg + '\'' +
                ", pageCurr=" + pageCurr +
                ", pageTotal=" + pageTotal +
                ", updateTime='" + updateTime + '\'' +
                ", sortId=" + sortId +
                ", sortType=" + sortType +
                ", categoryId=" + categoryId +
                ", attribute=" + attribute +
                ", genderItems=" + genderItems +
                ", categoryItems=" + categoryItems +
                ", brandItems=" + brandItems +
                ", labelItems=" + labelItems +
                ", subClassItems=" + subClassItems +
                ", priceRanges=" + Arrays.toString(priceRanges) +
                ", range=" + range +
                ", productItems=" + productItems +
                ", keyword='" + keyword + '\'' +
                ", brandId=" + brandId +
                ", subClassId=" + subClassId +
                ", nowPrice='" + nowPrice + '\'' +
                '}';
    }
}

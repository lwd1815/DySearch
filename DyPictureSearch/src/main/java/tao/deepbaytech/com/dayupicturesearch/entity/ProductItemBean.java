package tao.deepbaytech.com.dayupicturesearch.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author IT烟酒僧
 * created   2018/12/19 16:56
 * desc:
 */
public class ProductItemBean implements Parcelable {
    private String          id;
    private String          title;
    private String          shortTitle;
    private String          pfrom;
    private String          pfromId;
    private String          detailUrl;
    private String          coverImage;
    private List<String>    images;
    private String          price;
    private String          nowPrice;
    private int             sales;
    private String          returnIcons;
    private String          commission;
    private int             displayType;
    private String          displayContent;
    private List<String>    detailImages;
    private String          passwordCode;
    private String          debugCommissionRate;
    private ShopInfoBean    shopInfo;
    private CommentInfoBean commentInfo;
    private CouponInfoBean  couponInfo;

    public String getReturnIcons() {
        return returnIcons;
    }

    public void setReturnIcons(String returnIcons) {
        this.returnIcons = returnIcons;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getDisplayContent() {
        return displayContent;
    }

    public void setDisplayContent(String displayContent) {
        this.displayContent = displayContent;
    }

    public String getDebugCommissionRate() {
        return debugCommissionRate;
    }

    public void setDebugCommissionRate(String debugCommissionRate) {
        this.debugCommissionRate = debugCommissionRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortTitle() {
        return shortTitle;
    }

    public void setShortTitle(String shortTitle) {
        this.shortTitle = shortTitle;
    }

    public String getPfrom() {
        return pfrom;
    }

    public void setPfrom(String pfrom) {
        this.pfrom = pfrom;
    }

    public String getPfromId() {
        return pfromId;
    }

    public void setPfromId(String pfromId) {
        this.pfromId = pfromId;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getCoverImage() {
        return coverImage;
    }

    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNowPrice() {
        return nowPrice;
    }

    public void setNowPrice(String nowPrice) {
        this.nowPrice = nowPrice;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getDisplayType() {
        return displayType;
    }

    public void setDisplayType(int displayType) {
        this.displayType = displayType;
    }

    public ShopInfoBean getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopInfoBean shopInfo) {
        this.shopInfo = shopInfo;
    }

    public CommentInfoBean getCommentInfo() {
        return commentInfo;
    }

    public void setCommentInfo(CommentInfoBean commentInfo) {
        this.commentInfo = commentInfo;
    }

    public String getPasswordCode() {
        return passwordCode;
    }

    public void setPasswordCode(String passwordCode) {
        this.passwordCode = passwordCode;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getDetailImages() {
        return detailImages;
    }

    public void setDetailImages(List<String> detailImages) {
        this.detailImages = detailImages;
    }

    public CouponInfoBean getCouponInfo() {
        return couponInfo;
    }

    public void setCouponInfo(CouponInfoBean couponInfo) {
        this.couponInfo = couponInfo;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(shortTitle);
        dest.writeString(pfrom);
        dest.writeString(pfromId);
        dest.writeString(detailUrl);
        dest.writeString(coverImage);
        dest.writeStringList(images);
        dest.writeString(price);
        dest.writeString(nowPrice);
        dest.writeInt(sales);
        dest.writeString(returnIcons);
        dest.writeString(commission);
        dest.writeInt(displayType);
        dest.writeString(displayContent);
        dest.writeStringList(detailImages);
        dest.writeString(passwordCode);
        dest.writeString(debugCommissionRate);
        dest.writeParcelable(shopInfo, flags);
        dest.writeParcelable(commentInfo, flags);
        dest.writeParcelable(couponInfo, flags);
    }
    public ProductItemBean() {
    }
    protected ProductItemBean(Parcel in) {
        id = in.readString();
        title = in.readString();
        shortTitle = in.readString();
        pfrom = in.readString();
        pfromId = in.readString();
        detailUrl = in.readString();
        coverImage = in.readString();
        images = in.createStringArrayList();
        price = in.readString();
        nowPrice = in.readString();
        sales = in.readInt();
        returnIcons = in.readString();
        commission = in.readString();
        displayType = in.readInt();
        displayContent = in.readString();
        detailImages = in.createStringArrayList();
        passwordCode = in.readString();
        debugCommissionRate = in.readString();
        shopInfo = in.readParcelable(ShopInfoBean.class.getClassLoader());
        commentInfo = in.readParcelable(CommentInfoBean.class.getClassLoader());
        couponInfo = in.readParcelable(CouponInfoBean.class.getClassLoader());
    }

    public static final Creator<ProductItemBean> CREATOR = new Creator<ProductItemBean>() {
        @Override public ProductItemBean createFromParcel(Parcel in) {
            return new ProductItemBean(in);
        }

        @Override public ProductItemBean[] newArray(int size) {
            return new ProductItemBean[size];
        }
    };

    @Override
    public String toString() {
        return "ProductItemBean{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", shortTitle='" + shortTitle + '\'' +
                ", pfrom='" + pfrom + '\'' +
                ", pfromId='" + pfromId + '\'' +
                ", detailUrl='" + detailUrl + '\'' +
                ", coverImage='" + coverImage + '\'' +
                ", images=" + images +
                ", price='" + price + '\'' +
                ", nowPrice='" + nowPrice + '\'' +
                ", sales=" + sales +
                ", returnIcons='" + returnIcons + '\'' +
                ", commission='" + commission + '\'' +
                ", displayType=" + displayType +
                ", displayContent='" + displayContent + '\'' +
                ", detailImages=" + detailImages +
                ", passwordCode='" + passwordCode + '\'' +
                ", debugCommissionRate='" + debugCommissionRate + '\'' +
                ", shopInfo=" + shopInfo +
                ", commentInfo=" + commentInfo +
                ", couponInfo=" + couponInfo +
                '}';
    }
}


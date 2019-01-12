package tao.deepbaytech.com.dayupicturesearch.entity;

import android.support.annotation.Keep;

/**
 * @author IT烟酒僧
 * created   2019/1/11 18:31
 * desc:
 */
@Keep
public class SDXA {

    /**
     * cate : 3
     * sdrw : 44.22289729118347
     * sulh : 57.64346718788147
     * image_name : 1547203059415633.jpeg_crop_0.jpg
     * roi_url : http://image.dayuyoupin.com/1547203059415633.jpeg_crop_0.jpg
     * sdrh : 93.7315046787262
     * sulw : 8.228130638599396
     * userbox : 0
     * gender : 1
     */

    private int cate;
    private double sdrw;
    private double sulh;
    private String image_name;
    private String roi_url;
    private double sdrh;
    private double sulw;
    private String userbox;
    private int    gender;

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public double getSdrw() {
        return sdrw;
    }

    public void setSdrw(double sdrw) {
        this.sdrw = sdrw;
    }

    public double getSulh() {
        return sulh;
    }

    public void setSulh(double sulh) {
        this.sulh = sulh;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getRoi_url() {
        return roi_url;
    }

    public void setRoi_url(String roi_url) {
        this.roi_url = roi_url;
    }

    public double getSdrh() {
        return sdrh;
    }

    public void setSdrh(double sdrh) {
        this.sdrh = sdrh;
    }

    public double getSulw() {
        return sulw;
    }

    public void setSulw(double sulw) {
        this.sulw = sulw;
    }

    public String getUserbox() {
        return userbox;
    }

    public void setUserbox(String userbox) {
        this.userbox = userbox;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
}

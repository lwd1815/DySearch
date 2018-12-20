package tao.deepbaytech.com.dayupicturesearch.entity;

import android.support.annotation.Keep;

/**
 * @author IT烟酒僧
 * created   2018/12/19 20:04
 * desc:
 */
@Keep
public class XSdz {

    /**
     * sulw : 35.570162534713745
     * cate : 9
     * sulh : 7.578378915786743
     * sdrh : 89.82856273651123
     * gender : 1
     * sdrw : 66.24314188957214
     */

    private double sulw;
    private int cate;
    private double sulh;
    private double sdrh;
    private int gender;
    private double sdrw;

    public double getSulw() {
        return sulw;
    }

    public void setSulw(double sulw) {
        this.sulw = sulw;
    }

    public int getCate() {
        return cate;
    }

    public void setCate(int cate) {
        this.cate = cate;
    }

    public double getSulh() {
        return sulh;
    }

    public void setSulh(double sulh) {
        this.sulh = sulh;
    }

    public double getSdrh() {
        return sdrh;
    }

    public void setSdrh(double sdrh) {
        this.sdrh = sdrh;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public double getSdrw() {
        return sdrw;
    }

    public void setSdrw(double sdrw) {
        this.sdrw = sdrw;
    }

    @Override public String toString() {
        return "XSdz{"
                + "sulw="
                + sulw
                + ", cate="
                + cate
                + ", sulh="
                + sulh
                + ", sdrh="
                + sdrh
                + ", gender="
                + gender
                + ", sdrw="
                + sdrw
                + '}';
    }
}


package tao.deepbaytech.com.dayupicturesearch.custom;

import android.support.annotation.Keep;

/**
 * @author IT烟酒僧
 * created   2018/12/19 20:12
 * desc:
 */
@Keep
public class TransitionImgParams {
    private int locationX;
    private int locationY;
    private int width;
    private int height;

    public TransitionImgParams(int locationX, int locationY, int width, int height) {
        this.locationX = locationX;
        this.locationY = locationY;
        this.width = width;
        this.height = height;
    }

    public int getLocationX() {
        return locationX;
    }

    public void setLocationX(int locationX) {
        this.locationX = locationX;
    }

    public int getLocationY() {
        return locationY;
    }

    public void setLocationY(int locationY) {
        this.locationY = locationY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}


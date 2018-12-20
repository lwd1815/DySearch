package tao.deepbaytech.com.dayupicturesearch.entity;

import android.support.annotation.Keep;

import java.util.Map;

/**
 * @author IT烟酒僧
 * created   2018/12/19 17:48
 * desc:
 */
@Keep
public class ImgDataEvent {
    private ImgSearchEntity     entity;
    private boolean             hasData;
    private Map<String, Object> normalParams;

    public ImgDataEvent(ImgSearchEntity entity, boolean hasData) {
        this.entity = entity;
        this.hasData = hasData;
    }

    public ImgDataEvent(Map<String, Object> normalParams, boolean hasData) {
        this.hasData = hasData;
        this.normalParams = normalParams;
    }

    public Map<String, Object> getNormalParams() {
        return normalParams;
    }

    public void setNormalParams(Map<String, Object> normalParams) {
        this.normalParams = normalParams;
    }

    public boolean isHasData() {
        return hasData;
    }

    public void setHasData(boolean hasData) {
        this.hasData = hasData;
    }

    public ImgSearchEntity getEntity() {
        return entity;
    }

    public void setEntity(ImgSearchEntity entity) {
        this.entity = entity;
    }
}

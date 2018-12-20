package tao.deepbaytech.com.dayupicturesearch.entity;

import android.support.annotation.Keep;

/**
 * @author IT烟酒僧
 * created   2018/12/19 19:59
 * desc:
 */
@Keep
public class BaseResponse<T> {
    private int state;
    private String stateMsg;
    private int id;
    private int updateTime;
    private T data;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateMsg() {
        return stateMsg;
    }

    public void setStateMsg(String stateMsg) {
        this.stateMsg = stateMsg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override public String toString() {
        return "BaseResponse{"
                + "state="
                + state
                + ", stateMsg='"
                + stateMsg
                + '\''
                + ", id="
                + id
                + ", updateTime="
                + updateTime
                + ", data="
                + data
                + '}';
    }
}


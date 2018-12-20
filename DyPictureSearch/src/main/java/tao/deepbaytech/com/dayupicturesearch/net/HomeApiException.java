package tao.deepbaytech.com.dayupicturesearch.net;

import android.os.Looper;

/**
 * @author IT烟酒僧
 * created   2018/12/19 18:29
 * desc:
 */
public class HomeApiException extends RuntimeException {

    public HomeApiException(int resultCode) {
        this(getApiExceptionMessage(resultCode));
    }

    private static String getApiExceptionMessage(int resultCode) {
        String msg = "" + resultCode;
        switch (resultCode) {
            case 3:
                break;
            default:
                break;
        }
        return msg;
    }

    public HomeApiException(int resultCode, String resultMessage) {
        this(getApiExceptionMessage(resultCode, resultMessage));
    }

    private static String getApiExceptionMessage(int resultCode, String resultMessage) {
        String message = resultMessage;
        boolean flag = true;
        switch (resultCode) {
            case 1:
                break;
            case 3:
                flag = false;
                message = "没有更多数据了";
                break;
            default:
                //message = "未知错误: " + resultCode;
                break;
        }
        if (flag) {
            Looper.prepare();
//            Toast.makeText(MyApplication.getContext(), message, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }

        return message;
    }

    public HomeApiException(String apiExceptionMessage) {
        super(apiExceptionMessage);
    }
}


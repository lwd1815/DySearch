package tao.deepbaytech.com.dayupicturesearch.custom;

/**
 * @author IT烟酒僧
 * created   2019/1/17 16:58
 * desc:
 */
public class Rx {
    private static Rx  rx;
    public         int code;
    public Rx(int codes){
        this.code=codes;
    }

//    public static Rx getInstance(){
//        if (rx==null){
//            synchronized (Rx.class){
//                if (rx==null){
//                    rx=new Rx();
//                }
//            }
//        }
//        return rx;
//    }

//    public void setCode(int code) {
//        this.code = code;
//    }


    public int getCode() {
        return code;
    }

}

package tao.deepbaytech.com.dayupicturesearch.custom;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author IT烟酒僧
 * created   2018/12/25 10:28
 * desc:
 */
public class UrlToFile {

     private UrlToFile() {
    }

    private volatile static UrlToFile urlToFile;

    public static UrlToFile getInstance() {
        if (urlToFile == null) {
            synchronized (UrlToFile.class) {
                if (urlToFile == null) {
                    urlToFile = new UrlToFile();
                }
            }
        }
        return urlToFile;
    }

    /**
     * 网络图片uri转bitmap
     * @param url
     * @return
     */
        public final static Bitmap returnBitMap(String url) {
            boolean rource = getRource(url);

            if (!rource){
                return null;
            }

            URL myFileUrl = null;
            Bitmap bitmap = null;
            try {
                myFileUrl = new URL(url);
                HttpURLConnection conn;
                conn = (HttpURLConnection) myFileUrl.openConnection();
                conn.setDoInput(true);
                int length = conn.getContentLength();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is, length);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize =2;// 设置缩放比例
                Rect rect = new Rect(0, 0,0,0);
                bitmap = BitmapFactory.decodeStream(bis,rect,options);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

    /**
     * 将Bitmap转换成文件
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public static String saveFile(Bitmap bm, String fileName) throws IOException {
        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写  
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        String myCaptureFile = Environment.getExternalStorageDirectory().getAbsolutePath();
        File appDir = new File(myCaptureFile, "DYS");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return file.getAbsolutePath();
    }

    /**
     * 获取sd卡路径
     * @return
     */
    public static String getSDPath(){
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return sdDir.toString();
    }

    /**
     * 判断图片地址是否有效
     * @param source
     * @return
     */
    public static boolean getRource(String source) {
        try {
            URL url = new URL(source);
            URLConnection uc = url.openConnection();
            InputStream in = uc.getInputStream();
            if (source.equalsIgnoreCase(uc.getURL().toString()))
                in.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}


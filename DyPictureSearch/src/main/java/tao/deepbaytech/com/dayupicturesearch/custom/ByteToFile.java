package tao.deepbaytech.com.dayupicturesearch.custom;

import android.os.Environment;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author IT烟酒僧
 * created   2018/12/25 10:28
 * desc:
 */
public class ByteToFile {

     private ByteToFile() {
    }

    private volatile static ByteToFile byteToFile;

    public static ByteToFile getInstance() {
        if (byteToFile == null) {
            synchronized (ByteToFile.class) {
                if (byteToFile == null) {
                    byteToFile = new ByteToFile();
                }
            }
        }
        return byteToFile;
    }

    /**
     * 根据byte数组，生成文件
     */
    public final static String getFilePath(byte[] bfile){

        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写  
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        String myCaptureFile = Environment.getExternalStorageDirectory().getAbsolutePath();

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File appDir = new File(myCaptureFile, "DYS");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
            String fileName="cibn"+System.currentTimeMillis()+".jpg";
             file = new File(appDir, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file.getAbsolutePath();
    }

    /**
     * 获得指定文件的byte数组
     */

    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}


package tao.deepbaytech.com.dayupicturesearch.custom;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

/**
 * 创建者     李文东
 * 创建时间   2018/10/16 12:21
 */
public class CacheJsonUtils {


    private static StringWriter   sw;
    private static BufferedReader br;
    private static File           file;

    public static void savejson(String json, String falg) throws IOException {
        // 创建目录
        //获取内部存储状态  
        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写  
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File appDir = new File(sdCardDir, "TENCENT/deep");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = falg + ".txt";
        file = new File(appDir, fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        BufferedWriter bw = null;
        //保存图片
        try {
            FileWriter fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(json);
            bw.flush();
            bw.close();
            //Toast.makeText(MyApplication.getContext(), "保存成功", Toast.LENGTH_SHORT).show(); //Toast
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
    }

    public static String readjson(String flags) throws IOException {
        // 创建目录
        //获取内部存储状态  
        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写  
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
            File appDir = new File(sdCardDir, "TENCENT/deep");
            if (!appDir.exists()) {
                appDir.mkdir();
            }

            String fileName = flags + ".txt";
            file = new File(appDir, fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            //保存json
            try {
                FileReader fr = new FileReader(file);
                br = new BufferedReader(fr);
                String str = null;
                sw = new StringWriter();
                while ((str = br.readLine()) != null) {
                    sw.write(str);
                }
                return sw.toString();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                if (sw != null) {
                    try {
                        sw.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }


            }
        }
        return null;

    }


    /**
     * * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * *
     */
    public static void deleteFile() {
        String state = Environment.getExternalStorageState();
        //如果状态不是mounted，无法读写  
        if (!state.equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        String sdCardDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        File appDir = new File(sdCardDir, "TENCENT/deep");
        deleteFilesByDirectory(appDir);
    }

    /**
     * * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理 * *
     *
     * @param directory
     */
    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }
}


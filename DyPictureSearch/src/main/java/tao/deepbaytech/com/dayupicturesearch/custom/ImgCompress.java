package tao.deepbaytech.com.dayupicturesearch.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author IT烟酒僧
 * created   2018/12/19 19:45
 * desc:
 */
public class ImgCompress {

    //saveBitmap中图片保存的路径
    public static String SDPATH = Environment.getExternalStorageDirectory().getAbsolutePath()
            + "/deepbay/picture";

    /**
     * 通过参数out对bm进行输出
     * @param bm --被输出的图片
     * @param out --指定图片输出的输出流
     */
    public static void outputBitmap(Bitmap bm, OutputStream out) {
        try {
            if(out!=null){
                bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
                out.flush();
                out.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对图片进行本地保存
     * @param bm --被保存的图片
     * @param picName --图片保存后的文件名
     */
    public static String saveBitmap(Bitmap bm, String picName) {
        try {
            if (!isFileExist("")) {
                System.out.println("创建文件");
                File tempf = createSDDir("");
            }
            File f = null;
            //定义图片被保存的路径
            if(picName.equals("cutphoto")){
                f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/deepbay"
                        , picName + ".jpeg");
                try {
                    f.mkdirs();
                }catch(Exception e){

                }
            }else{
                f = new File(SDPATH, picName + ".jpeg");
            }

            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            //需要对图片进行10%的压缩，否则图片莫名较大
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            //            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            //            bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            //            int ImgSize = baos.toByteArray().length/1024;
            //            Log.e("sampSize---",ImgSize+"");
            //            int sample = 100;
            //            if ( ImgSize>100) {
            //                sample =  Math.round(((float)100/(float)ImgSize*(float)100));
            //            }
            //            baos.reset();//重置bas即清空bas
            //            Log.e("sampSI---",sample+"");
            //            bm.compress(Bitmap.CompressFormat.JPEG, 90, baos);//这里压缩options%，把压缩后的数据存放到baos中
            //            out.write(baos.toByteArray());
            out.flush();
            out.close();
            return f.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //创建文件夹
    public static File createSDDir(String dirName) throws IOException {
        File dir = new File(SDPATH,dirName);
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {

            System.out.println("createSDDir:" + dir.getAbsolutePath());
            System.out.println("createSDDir:" + dir.mkdir());
        }
        return dir;
    }

    //判断文件是否已经创建
    public static boolean isFileExist(String fileName) {
        File file = new File(SDPATH,fileName);
        file.isFile();
        System.out.println(file.exists());
        return file.exists();
    }

    //-----------------------------------------------------------------

    /**
     * 根据设定的期望宽高计算图片压缩的比例
     *
     * @param options--压缩图片的参数
     * @param reqWidth--期望压缩后的宽，与压缩后的实际值有一点的差距
     * @param reqHeight--期望压缩后的高
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            //选择期望宽高与原始宽高中差距比较大的比例，从而保证有一边是期望宽高。
            inSampleSize = heightRatio > widthRatio ? heightRatio : widthRatio;
        }
        Log.e("sampleSize------",inSampleSize+"");
        if(inSampleSize%2!=0 && inSampleSize!=1)
            inSampleSize++;
        return inSampleSize;
    }

    /**
     * 根据路径获得图片并压缩返回bitmap
     * @param filePath  图片的路径
     * @return
     *
     * 注意：此处宽高已被设置为压缩到固定800x800的比列
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true不获取图片，并不为其分配内存，只返回图片的宽高信息
        options.inJustDecodeBounds = true;
        //获取位图，把该图片的参数存到options中
        BitmapFactory.decodeFile(filePath, options);

        //图片压缩时期望的宽高固定为 800x800
        options.inSampleSize = calculateInSampleSize(options, 800, 800);

        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    public static Bitmap getSmallBitmapFromBitmap(Bitmap bitmap) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.outHeight = bitmap.getHeight();
        options.outWidth = bitmap.getWidth();
        //        options.inPreferredConfig = Bitmap.Config.RGB_565;
        //获取位图，把该图片的参数存到options中

        //图片压缩时期望的宽高固定为 800x800
        options.inSampleSize = calculateInSampleSize(options, 800, 800);


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        bitmap.recycle();

        return BitmapFactory.decodeByteArray(baos.toByteArray(),0,baos.toByteArray().length,options);
    }

    public static void zipImg(Context context, String path, OnCompressListener compressListener){
        Luban.with(context)
                .load(path)                     //传人要压缩的图片
                //.putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .ignoreBy(100)                                  // 忽略不压缩图片的大小
                .setTargetDir(getPath())
                .setCompressListener(compressListener).launch();
    }

    private static String getPath() {
        String path = Environment.getExternalStorageDirectory() + "/DAYU/cache/";
        File file = new File(path);
        if (file.mkdirs()) {
            return path;
        }
        return path;
    }

    public void zipRxImg(final Context context, List<String> paths) {
        //Flowable.just(paths)
        //    .observeOn(Schedulers.io())
        //    .map(new Function<List<String>, List<File>>() {
        //        @Override public List<File> apply(@NonNull List<String> list) throws Exception {
        //            // 同步方法直接返回压缩后的文件
        //            return Luban.with(context).load(list).get();
        //        }
        //    })
        //    .observeOn(AndroidSchedulers.mainThread())
        //    .subscribe();
    }
}

package tao.deepbaytech.com.dayupicturesearch.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import tao.deepbaytech.com.dayupicturesearch.R;
import tao.deepbaytech.com.dayupicturesearch.cropper.CropImageView;
import tao.deepbaytech.com.dayupicturesearch.custom.MetaballView;
import tao.deepbaytech.com.dayupicturesearch.net.Content;

/**
 * @author IT烟酒僧
 * created   2018/12/19 19:26
 * desc:
 */
public class CutPhotoActivity extends AppCompatActivity implements Runnable{
    private Button        cutPhotoButtonFail;
    private Button        cutPhotoButtonSucceed;
    private CropImageView cropImageView;
    private String        imagePath;
    private int           imageWidth;
    private int           imageHeight;
    private MetaballView  metaballView;
    private LinearLayout  cutphotoLayout;
    private String        cutPhotoPath;

    private TextView       cutBack;
    private RelativeLayout cutDone;

    private boolean fromXc = false;
    private boolean needCompress = false;
    private float[] nowZuobiao= null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cut_photo);

        initData();
        setImage();
    }

    private void initData() {
        cutPhotoButtonFail = (Button) findViewById(R.id.cutButton_fail);
        cutPhotoButtonSucceed = (Button) findViewById(R.id.cutButton_succeed);
        cropImageView = (CropImageView) findViewById(R.id.cropImageView);
        metaballView = (MetaballView) findViewById(R.id.metaball_view);
        cutphotoLayout = (LinearLayout) findViewById(R.id.cutphoto_layout);

        cutBack = (TextView) findViewById(R.id.img_cut_toolbar_back);
        cutDone = (RelativeLayout) findViewById(R.id.rl_cut_done);

        imagePath = getIntent().getStringExtra("bitmapUriPath");
        Log.w("imgPath",imagePath);
        nowZuobiao = getIntent().getFloatArrayExtra("zuobiao");
        if(imagePath.endsWith("-")){
            fromXc = true;
            imagePath = imagePath.substring(0,imagePath.length()-1);
        }

        cutDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Rect rect = cropImageView.getCropRect();
                System.out.println("Img--------width--" + imageWidth + "--height--" + imageHeight + "--cropLX--"
                        + rect.left
                        + "--cropLY--"
                        + rect.top
                        + "--cropRX--"
                        + rect.right
                        + "--cropRY--"
                        + rect.bottom);
                //回传坐标数据
                Intent intent = new Intent(CutPhotoActivity.this, SearchResultActivity.class);
                intent.putExtra("zuobiao",new int[]{(int) ((rect.left*1.0/imageWidth)*10000),(int) ((rect.top*1.0/imageHeight)*10000),
                        (int) ((rect.right * 1.0 / imageWidth) * 10000),
                        (int) ((rect.bottom * 1.0 / imageHeight) * 10000)
                });
                CutPhotoActivity.this.setResult(110,intent);
                CutPhotoActivity.this.finish();
                //                Intent intent = new Intent(CutPhotoActivity.this,ImgSearchResultActivity.class);
                //                CutPhotoActivity.this.startActivity(intent);
                //                metaballView.setVisibility(View.VISIBLE);
                //                cutphotoLayout.setAlpha(0.3f);
                //                cutPhotoButtonSucceed.setClickable(false);
                //                cutPhotoButtonFail.setClickable(false);
                //                final Map<String, String> map = new HashMap<String, String>();
                //                Bitmap cutBitmap = null;
                //                try{
                //                    cutBitmap = cropImageView.getCroppedImage();
                //
                //                }catch (OutOfMemoryError e){
                //                    Toast.makeText(CutPhotoActivity.this,"图片太大啦，内存不够放啦~",Toast.LENGTH_SHORT).show();
                //                    CutPhotoActivity.this.finish();
                //                }
                //
                //                if (needCompress)
                //                    cutBitmap = ImgCompress.getSmallBitmapFromBitmap(cutBitmap);
                //
                //                baseNetForJson = HttpNetFactory.getNetForJson("POST", new BaseNetOverListener<String>() {
                //                    @Override
                //                    public void onNetOverError() {
                //                        metaballView.setVisibility(View.GONE);
                //                        cutphotoLayout.setAlpha(1f);
                //                        cutPhotoButtonSucceed.setClickable(true);
                //                        cutPhotoButtonFail.setClickable(true);
                //                        Toast.makeText(CutPhotoActivity.this, "网络连接失败", Toast.LENGTH_LONG).show();
                //                    }
                //
                //                    @Override
                //                    public void onNetOverSuccess(String entity) {
                //                        //获取到搜索得到的数据
                //
                //                        metaballView.setVisibility(View.GONE);
                //                        cutphotoLayout.setAlpha(1f);
                //                        cutPhotoButtonSucceed.setClickable(true);
                //                        cutPhotoButtonFail.setClickable(true);
                //
                //                        if (entity == null || entity.equals(""))
                //                            Toast.makeText(CutPhotoActivity.this, "未搜索到相似的图片", Toast.LENGTH_SHORT).show();
                //                        else {
                //                            Intent intent = new Intent(CutPhotoActivity.this, SearchResultActivtiy.class);
                //                            intent.putExtra("resultJson", entity);
                //                            intent.putExtra("cutphotoPath", cutPhotoPath);
                //                            CutPhotoActivity.this.startActivity(intent);
                //                            CutPhotoActivity.this.finish();
                //                        }
                //                    }
                //                }, map, BaseNetForJson.STRING_TAG, Content.CUTPHOTOURL, null);
                //
                //                //被上传服务器的图片
                //                final Bitmap finalCutBitmap = cutBitmap;
                //                new Thread(new Runnable() {
                //                    @Override
                //                    public void run() {
                //                        //获取到压缩后的图片
                ////                        cutPhotoPath = ImgCompress.saveBitmap(finalCutBitmap, "cutphoto");
                //                        MyApp myApp = (MyApp) CutPhotoActivity.this.getApplication();
                //                        myApp.setBitmap(finalCutBitmap);
                //                        //请求参数的拼接
                //                        String imageString = Base64Utils.BitmapToBase64(finalCutBitmap);
                //                        String[] base64Str = imageString.split("\n");
                //                        StringBuffer base64Buffer = new StringBuffer();
                //                        for (int i = 0; i < base64Str.length; i++) {
                //                            base64Buffer.append(base64Str[i]);
                //                        }
                //                        StringBuffer sb = new StringBuffer();
                //                        //进行Json拼接
                //                        sb.append("{");
                //                        sb.append("\"command\" : \"similarSearch\",");
                //                        sb.append("\"result\"  : { \"maxImages\" : 20 },");
                //                        sb.append("\"image\"   : { \"format\" : \"png\", \"content\" : \""
                //                                + base64Buffer.toString() + "\"}");
                //                        sb.append("}");
                //                        map.put("", sb.toString());
                //
                //                        baseNetForJson.downLoadJson();
                //
                //                    }
                //                }).start();
                //                if(fromXc)
                //                    new Thread(CutPhotoActivity.this).start();
            }
        });

        cutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CutPhotoActivity.this.finish();
            }
        });

    }

    private void getImageWidthAndHeight(String imagePath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        imageWidth = options.outWidth;
        imageHeight = options.outHeight;
    }

    private void setImage() {
        //File file = new File(imagePath);
        getImageWidthAndHeight(imagePath);
        //Uri uri = Uri.fromFile(file);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        cropImageView.setImageBitmap(bitmap);
        if (nowZuobiao!=null && nowZuobiao.length==4){
            setImageRect(nowZuobiao);
        }
        //判断文件大小是否超过了110k，如果时的话则进行压缩，否则不进行压缩
        //        if (file.length()/1024>110)
        //            needCompress = true;
        //        else
        //            needCompress = false;
        //        FileInputStream inputStream = null;
        //        try {
        //            inputStream = new FileInputStream(file);
        //            BitmapFactory.Options options = new BitmapFactory.Options();
        //            options.inPreferredConfig = Bitmap.Config.RGB_565;
        //            Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        //            int num = 0;
        //            while(isNeedCloseHardwareAcceleration(bitmap.getWidth(), bitmap.getHeight())){
        //                bitmap.recycle();
        //                System.gc();
        //                bitmap = compressImg(imagePath,num = num+2);
        //            }
        //            cropImageView.setImageBitmap(bitmap);
        //
        //        } catch (FileNotFoundException e) {
        //            e.printStackTrace();
        //        } catch (OutOfMemoryError e) {
        //            //当加载的图片太大时进行压缩
        //            cropImageView.setImageBitmap(compressImg(imagePath, 2));
        //        } finally {
        //            if (inputStream != null) {
        //                try {
        //                    inputStream.close();
        //                } catch (IOException e) {
        //                    e.printStackTrace();
        //                }
        //            }
        //        }
    }

    //判断图片宽高是否超出了限制值
    public boolean isNeedCloseHardwareAcceleration(int w, int h) {

        return 4096 < h || 4096 < w;
    }

    //图片过大时，进行压缩
    public Bitmap compressImg(String path, int sampleSize) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(path, options);
            options.inSampleSize = sampleSize;
            options.inJustDecodeBounds = false;
            return BitmapFactory.decodeFile(path, options);
        } catch (OutOfMemoryError e) {
            return compressImg(path, sampleSize + 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // TODO: 2016/10/19 设置选择框的位置
    public void setImageRect(float[] zuobiao) {
        Rect rect = new Rect((int) (zuobiao[0]*imageWidth), (int) (zuobiao[1]*imageHeight), (int) (zuobiao[2]*imageWidth), (int) (zuobiao[3]*imageHeight));
        cropImageView.setCropRect(rect);
        //        Rect rect = new Rect(cropImageView.getCropRect());
        System.out.println("zuobiao ---> "+zuobiao[0]+"  -  "+zuobiao[1]+"  -  "+zuobiao[2]+"  -  "+zuobiao[3]);
        Log.e("rect-=-=--------", rect.left + "......" + rect.top + "......" + rect.right + "....." + rect.bottom + "....");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cropImageView.clearImage();
    }

    @Override
    public void run() {

        File file = new File(imagePath);
        String[] slipStrs = imagePath.split("\\.");
        File outDir = new File(Content.PHOTOPATH);
        if (!outDir.exists())
            outDir.mkdirs();
        File outFile = new File(outDir,"search."+slipStrs[slipStrs.length-1]);

        FileOutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            byte[] bytes = new byte[1024 * 1024];
            int total = 0;
            inputStream = new FileInputStream(file);
            outputStream = new FileOutputStream(outFile, false);
            while ((total = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, total);
                outputStream.flush();
            }
            MediaScannerConnection.scanFile(this, new String[] { outFile.getAbsolutePath() }, null,
                    null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(inputStream!=null)
                    inputStream.close();
                if (outputStream!=null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

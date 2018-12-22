package tao.deepbaytech.com.dayupicturesearch.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theartofdev.edmodo.cropper.CropImageView;

import tao.deepbaytech.com.dayupicturesearch.R;
import tao.deepbaytech.com.dayupicturesearch.custom.MetaballView;

public class CutPhotoActivity extends AppCompatActivity {
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
        setContentView(R.layout.dy_activity_cut);
        cutPhotoButtonFail = (Button) findViewById(R.id.dy_cutButton_fail);
        cutPhotoButtonSucceed = (Button) findViewById(R.id.dy_cutButton_succeed);
        cropImageView = (CropImageView) findViewById(R.id.dy_cut_coreim);
        metaballView = (MetaballView) findViewById(R.id.dy_metaball_view);
        cutphotoLayout = (LinearLayout) findViewById(R.id.dy_cutphoto_layout);

        cutBack = (TextView) findViewById(R.id.dy_cut_back);
        cutDone = (RelativeLayout) findViewById(R.id.dy_cut_done);

        initdata();
        setImage();
    }

    private void initdata() {
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

            }
        });

        cutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    }

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
}

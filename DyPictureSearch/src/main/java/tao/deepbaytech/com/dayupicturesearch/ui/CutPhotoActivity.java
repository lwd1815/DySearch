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
import tao.deepbaytech.com.dayupicturesearch.net.SearchImpl;

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
    private int mInput;
    private String mName;

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

        initView();
        initdata();
        setImage();
    }

    //进入裁剪页只有一种状态2001
    private void initView() {
        imagePath = getIntent().getStringExtra("bitmapUriPath");
        Log.w("imgPath",imagePath);
        mInput = getIntent().getIntExtra("input", -1);
        nowZuobiao = getIntent().getFloatArrayExtra("zuobiao");
        mName = getIntent().getStringExtra("name");
        System.out.println("路径2==="+imagePath+"==="+mName);
        if(imagePath.endsWith("-")){
            fromXc = true;
            imagePath = imagePath.substring(0,imagePath.length()-1);
        }

        cutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initdata() {
        cutDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //正常2000
                if (mInput==2000){
                    NormalJump();
                }else  if (mInput==2009){
                    ExeceptJump();
                }

            }
        });
    }

    public void NormalJump(){
        Intent eIntent = Jump();
        eIntent.putExtra("flag",2000);
        CutPhotoActivity.this.setResult(110,eIntent);
        CutPhotoActivity.this.finish();
    }


    public void ExeceptJump(){
        Intent eIntent = Jumps();
        eIntent.putExtra("flag",2000);
        //CutPhotoActivity.this.startActivity(eIntent);
        SearchImpl.getInstance().Exrequest(CutPhotoActivity.this,eIntent);
        CutPhotoActivity.this.finish();
    }

    public Intent Jump(){
        Rect rect = cropImageView.getCropRect();
        //回传坐标数据
        Intent intent = new Intent(CutPhotoActivity.this, SearchResultActivity.class);
        intent.putExtra("bitmapUriPath", imagePath);
        intent.putExtra("zuobiao",new int[]{(int) ((rect.left*1.0/imageWidth)*10000),(int) ((rect.top*1.0/imageHeight)*10000),
                (int) ((rect.right * 1.0 / imageWidth) * 10000),
                (int) ((rect.bottom * 1.0 / imageHeight) * 10000)
        });

        return intent;
    }

    public Intent Jumps(){
        Rect rect = cropImageView.getCropRect();
        //回传坐标数据
        Intent intent = new Intent();
        intent.putExtra("bitmapUriPath", imagePath);
        intent.putExtra("name",mName);
        intent.putExtra("zuobiao",new int[]{(int) ((rect.left*1.0/imageWidth)*10000),(int) ((rect.top*1.0/imageHeight)*10000),
                (int) ((rect.right * 1.0 / imageWidth) * 10000),
                (int) ((rect.bottom * 1.0 / imageHeight) * 10000)
        });

        return intent;
    }

    private void getImageWidthAndHeight(String imagePath){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        imageWidth = options.outWidth;
        imageHeight = options.outHeight;
    }

    private void setImage() {
        getImageWidthAndHeight(imagePath);
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        cropImageView.setImageBitmap(bitmap);
        if (nowZuobiao!=null && nowZuobiao.length==4){
            setImageRect(nowZuobiao);
        }
    }

    public void setImageRect(float[] zuobiao) {
        Rect rect = new Rect((int) (zuobiao[0]*imageWidth), (int) (zuobiao[1]*imageHeight), (int) (zuobiao[2]*imageWidth), (int) (zuobiao[3]*imageHeight));
        cropImageView.setCropRect(rect);
        Log.e("rect-=-=--------", rect.left + "......" + rect.top + "......" + rect.right + "....." + rect.bottom + "....");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cropImageView.clearImage();
    }
}

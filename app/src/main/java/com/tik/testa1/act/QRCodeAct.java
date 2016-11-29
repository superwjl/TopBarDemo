package com.tik.testa1.act;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.tik.testa1.MainActivity;
import com.tik.testa1.R;
import com.tik.testa1.act.CustomTopBarAct;
import com.tik.testa1.act.ScanningAct;
import com.tik.testa1.base.BaseActivity;
import com.tik.testa1.zxing.encode.QRCodeEncoder;

import java.util.EnumMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnLongClick;

public class QRCodeAct extends BaseActivity{
    private static final String TAG = "QRCODE";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.input)
    EditText mEtInput;

    @BindView(R.id.qrcode)
    Button mBtnQrCode;

    @BindView(R.id.image)
    ImageView qrcodeImg;

    @OnClick(R.id.qrcode)
    void qrcode(){
        try {
            Bitmap mBitmap = QRCodeEncoder.encodeAsBitmap(mEtInput.getText().toString(), 300);
            qrcodeImg.setImageBitmap(mBitmap);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnLongClick(R.id.image)
    boolean decode(){
        qrcodeImg.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(qrcodeImg.getDrawingCache());
        qrcodeImg.setDrawingCacheEnabled(false);
        decodeQRCode(bitmap);
        return true;
    }

    private void initToolBar(){
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        mToolbar.setTitle("生成二维码");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 解析二维码图片
     *
     * @param bitmap   要解析的二维码图片
     */
    public final Map<DecodeHintType, Object> HINTS = new EnumMap<>(DecodeHintType.class);

    public void decodeQRCode(final Bitmap bitmap) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();
                    int[] pixels = new int[width * height];
                    bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
                    RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                    Result result = new MultiFormatReader().decode(new BinaryBitmap(new HybridBinarizer(source)), HINTS);
                    return result.getText();
                } catch (Exception e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(String result) {
                Log.d("wxl", "result=" + result);
                Toast.makeText(QRCodeAct.this, result, Toast.LENGTH_LONG).show();
            }
        }.execute();

    }

    @Override
    protected void beforeBindViews() {

    }

    @Override
    protected void afterBindViews() {
        initToolBar();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_qrcode;
    }



}

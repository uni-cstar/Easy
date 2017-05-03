package ms.lucio.skin;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.File;

import easy.skin.TypefaceUtils;
import easy.skin.base.BaseSkinActivity;
import easy.skin.SkinManager;
import easy.skin.impl.SkinLoadListener;
import ms.lucio.R;

public class SkinSampleActivity extends BaseSkinActivity implements View.OnClickListener {

    EditText editText;
    Button btn1, btn2;
    AppCompatImageView imgTint;
    ImageView imgTint2;
        boolean isSelect= false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_sample);
        imgTint = (AppCompatImageView) this.findViewById(R.id.imgTint);
        imgTint2 = (ImageView) this.findViewById(R.id.imgTint2);
        editText = (EditText) this.findViewById(R.id.edittext);
        btn1 = (Button) this.findViewById(R.id.btn1);
        btn2 = (Button) this.findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        this.findViewById(R.id.btn3).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        isSelect = !isSelect;

        if (v.getId() == R.id.btn2) {
            SkinManager.getInstance().restoreDefaultSkin();
            SkinManager.getInstance().restoreDefaultFont();

        } else if (v.getId() == R.id.btn1) {
            String suffix = editText.getText().toString();
            if ("red".equals(suffix) || "blue".equals(suffix)) {
                SkinManager.getInstance().changeFont(SkinManager.getInstance().isUseSkin() ? "kt.ttf" : "hksnzt.ttf");
                SkinManager.getInstance().changeSkin(suffix);
            } else {
                Toast.makeText(v.getContext(), "输入错误，必须是red or blue", Toast.LENGTH_SHORT).show();
            }

        } else if (v.getId() == R.id.btn3) {
            String path = Environment.getExternalStorageDirectory().getPath() + File.separator + "ucux-ls.skin";
            SkinManager.getInstance().loadSkin(path, new SkinLoadListener() {
                @Override
                public void onSkinLoadStart() {
                    Log.d("SkinSampleActivity", "onSkinLoadStart");
                }

                @Override
                public void onSkinLoadFailed(Throwable e) {
                    Log.d("SkinSampleActivity", "onSkinLoadFailed:" + e);
                }

                @Override
                public void onSkinLoadSuccess() {
                    Log.d("SkinSampleActivity", "onSkinLoadSuccess");
                    SkinManager.getInstance().changeFont(SkinManager.getInstance().isUseSkin() ? "kt.ttf" : "hksnzt.ttf");
                }
            });
        }
        imgTint.setSelected(isSelect);
        imgTint2.setSelected(!isSelect);
    }
}

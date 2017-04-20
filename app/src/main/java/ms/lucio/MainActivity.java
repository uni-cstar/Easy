package ms.lucio;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import easy.skin.SkinConst;
import easy.skin.SkinManager;
import easy.skin.attr.SkinAttrSupport;
import easy.skin.attr.TextAttr;
import easy.skin.factory.SkinAttrFactory;
import easy.view.gesture.EasyDoubleBack;
import ms.lucio.skin.SkinSampleActivity;
import ms.lucio.tab.EasyTabSampleTExt;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(SkinSampleActivity.class);
        WindowManager mWindowManager = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        WindowManager mWindowManager2 = (WindowManager) this.getApplication().getSystemService(Application.WINDOW_SERVICE);
        doubleBackPressed = EasyDoubleBack.create(this, EasyDoubleBack.TYPE_MOVE_TO_BACK);
        ((Button)this.findViewById(R.id.download)).setText("324234");
    }

    public void onViewClick(View v){
        if(v.getId() == R.id.download){
            startActivity(DownloadSampleActivity.class);
        }
    }

    void startActivity(Class cls){
        Intent it = new Intent(this,cls);
        startActivity(it);
    }

    EasyDoubleBack doubleBackPressed;
    @Override
    public void onBackPressed() {
        doubleBackPressed.onBackPressed();
    }
}

package ms.lucio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import easy.skin.SkinConst;
import easy.skin.SkinManager;
import easy.skin.attr.SkinAttrSupport;
import easy.skin.attr.TextAttr;
import easy.skin.factory.SkinAttrFactory;
import easy.view.gesture.EasyDoubleBackToExit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SkinManager.getInstance().init(this);
        SkinAttrSupport.addSupportAttr(SkinConst.ATTR_NAME_TEXT,new TextAttr());
        SkinManager.getInstance().setSkinAttrFactory(SkinAttrFactory.createPrefixFactory(null));
        startActivity(UiTestActivity.class);
        doubleBackPressed = new EasyDoubleBackToExit(this, EasyDoubleBackToExit.Type.MOVE_TO_BACK);
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

    EasyDoubleBackToExit doubleBackPressed;
    @Override
    public void onBackPressed() {
        doubleBackPressed.onBackPressed();
    }
}

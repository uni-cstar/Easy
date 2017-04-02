package ms.lucio;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import easy.skin.SkinManager;
import easy.widget.QuickAdapter;
import ms.lucio.skin.SkinSampleActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SkinManager.getInstance().init(this);
        startActivity(SkinSampleActivity.class);
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
}

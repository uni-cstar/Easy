package ms.lucio.skin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import easy.skin.BaseSkinActivity;
import easy.skin.SkinManager;
import ms.lucio.R;

public class SkinSampleActivity extends BaseSkinActivity implements View.OnClickListener{

    EditText editText;
    Button btn1,btn2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skin_sample);
        editText = (EditText) this.findViewById(R.id.edittext);
        btn1 = (Button)this.findViewById(R.id.btn1);
        btn2= (Button)this.findViewById(R.id.btn2);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn2){
            SkinManager.getInstance().restoreDefaultSkin();
        }else if(v.getId() == R.id.btn1){
            String suffix = editText.getText().toString();
            if("red".equals(suffix) || "blue".equals(suffix)){
                SkinManager.getInstance().changeSkin(suffix);
            }else{
                Toast.makeText(v.getContext(),"输入错误，必须是red or blue",Toast.LENGTH_SHORT).show();
            }
        }
    }
}

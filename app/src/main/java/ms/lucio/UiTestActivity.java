package ms.lucio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import easy.view.gesture.EasyDoubleBackToExit;
import easy.view.gesture.EasyGesture;

public class UiTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui_test);
        Button button1 = (Button) this.findViewById(R.id.btnTap1);
        EasyGesture.addTapGesture(button1, listener);
        Button button2 = (Button) this.findViewById(R.id.btnTap2);
        EasyGesture.addTapGesture(button2, listener0);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMsg("onClick");
            }
        });
        button2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showMsg("onLongClick");
                return true;
            }
        });
        doubleBackPressed = new EasyDoubleBackToExit(this, EasyDoubleBackToExit.Type.FINISH);
        doubleBackPressed.setOnBackPressedListener(new EasyDoubleBackToExit.OnBackPressedListener() {
            @Override
            public boolean onFirstBackPressed() {
                Toast.makeText(UiTestActivity.this,"自定义toast",Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    private EasyGesture.OnEasyTapListener listener = new EasyGesture.OnEasyTapListener() {
        @Override
        public boolean onEasyDoubleTaped() {
            showMsg("onEasyDoubleTaped");
            return true;
        }

        @Override
        public boolean onEasySingleTaped() {
            showMsg("onEasySingleTaped");
            return true;
        }
    };

    private EasyGesture.OnEasyTapListener0 listener0 = new EasyGesture.OnEasyTapListener0() {
        @Override
        public boolean onEasyDoubleTaped() {
            showMsg("onEasyDoubleTaped");
            return true;
        }

    };

    private void showMsg(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }

    EasyDoubleBackToExit doubleBackPressed;
    @Override
    public void onBackPressed() {
        doubleBackPressed.onBackPressed();
    }
}

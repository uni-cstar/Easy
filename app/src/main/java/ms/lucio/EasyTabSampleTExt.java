package ms.lucio;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import easy.view.tab.EasyTabHost;
import easy.view.tab.EasyTabItem;
import easy.view.tab.EasyTabView;
import ms.badge.HorizontalGravity;
import ms.badge.VerticalGravity;

public class EasyTabSampleTExt extends AppCompatActivity {

    EasyTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_tab_sample_text);
        mTabHost = (EasyTabHost) this.findViewById(R.id.tabhost);
        mTabHost.setOnEasyTabSelectListener(new EasyTabHost.OnEasyTabSelectListener() {
            @Override
            public void onEasyTabSelected(int index) {
                showMsg("onEasyTabSelected:" + index);
            }

            @Override
            public void onEasyTabDoubleClick(int index) {
                showMsg("onEasyTabDoubleClick:" + index);
            }
        });

        List<EasyTabItem> tabItems = new ArrayList<>();

        EasyTabView tabView = new EasyTabView(this);
        tabView.setBackgroundResource(R.drawable.bg_easy_tabhost);
        tabView.getTextView().setText("你好");
        tabView.getTextView().setTextColor(this.getResources().getColor(R.color.bg_easy_tabhost_text));
        tabView.getImageView().setImageResource(R.drawable.bg_easy_tabhost_image);
        tabView.getBadgeConfigHelper().setDragable(true);
//        tabView.getBadgeViewHelper().setBadgeGravity(BadgeViewHelper.BadgeGravity.LeftCenter);
        tabItems.add(tabView);
        tabView.showCirclePointBadge();

        EasyTabView tabView2 = new EasyTabView(this);
        tabView2.setBackgroundResource(R.drawable.bg_easy_tabhost);
        tabView2.getTextView().setText("很好");
        tabView2.getImageView().setImageResource(R.drawable.easy_ic_loading_circle);
        tabView2.getBadgeConfigHelper().setDragable(true);
//        tabView2.getBadgeViewHelper().setBadgeGravity(BadgeViewHelper.BadgeGravity.LeftTop);
        tabView2.getTextView().setTextColor(this.getResources().getColor(R.color.bg_easy_tabhost_text));
        tabItems.add(tabView2);
        tabView2.showTextBadge("99+");


        mTabHost.setup(tabItems,0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTabHost.setDividerStroke(10);
                mTabHost.setDividerColor(getResources().getColor(R.color.colorPrimary));
            }
        },2000);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mTabHost.setDividerStroke(1);
                mTabHost.setDividerColor(getResources().getColor(R.color.transparent));
            }
        },10000);
    }

    private void showMsg(String content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}

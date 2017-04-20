package ms.lucio.tab;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import easy.badge.view.BadgeRelativeLayout;
import easy.view.tab.EasyFragTabView;
import easy.view.tab.EasyFragmentTabHost;
import easy.view.tab.EasyTabHost;
import easy.view.tab.EasyTabView;
import ms.lucio.R;

public class EasyTabSampleTExt extends AppCompatActivity {

    EasyFragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_tab_sample_text);
        mTabHost = (EasyFragmentTabHost) this.findViewById(R.id.tabhost);
        mTabHost.setOnEasyTabChangedListener(new EasyTabHost.OnEasyTabChangedListener() {
            @Override
            public void onEasyTabSelected(int index) {
                showMsg("onEasyTabSelected:" + index);
            }

        });
        mTabHost.setOnEasyTabDoubleClickListener(new EasyTabHost.OnEasyTabDoubleClickListener() {
            @Override
            public void onEasyTabDoubleClick(int index) {
                showMsg("onEasyTabDoubleClick:" + index);
            }
        });
        mTabHost.setup(getSupportFragmentManager());

        EasyFragTabView tabView = new EasyFragTabView(this,"AA1");
        tabView.setBackgroundResource(R.drawable.bg_easy_tabhost);
        tabView.getTextView().setText("你好");
        tabView.getTextView().setTextColor(this.getResources().getColor(R.color.bg_easy_tabhost_text));
        tabView.getImageView().setImageResource(R.drawable.bg_easy_tabhost_image);
        tabView.getBadgeConfigHelper().setDragable(true);
//        tabView.getBadgeViewHelper().setBadgeGravity(BadgeViewHelper.BadgeGravity.LeftCenter);
        tabView.showCirclePointBadge();

        EasyFragTabView tabView2 = new EasyFragTabView(this,"AA@");
        tabView2.setBackgroundResource(R.drawable.bg_easy_tabhost);
        tabView2.getTextView().setText("很好");
        tabView2.getImageView().setImageResource(R.drawable.easy_ic_loading_circle);
        tabView2.getBadgeConfigHelper().setDragable(true);
//        tabView2.getBadgeViewHelper().setBadgeGravity(BadgeViewHelper.BadgeGravity.LeftTop);
        tabView2.getTextView().setTextColor(this.getResources().getColor(R.color.bg_easy_tabhost_text));
        tabView2.showTextBadge("99+");

        mTabHost.addTab(tabView,TabFragment1.class,null);
        mTabHost.addTab(tabView2,TabFragment2.class,null);

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

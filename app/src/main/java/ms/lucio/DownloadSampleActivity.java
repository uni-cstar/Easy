package ms.lucio;

import android.app.Activity;
import android.app.DownloadManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import easy.app.download.DownloadManagerQuery;
import easy.app.download.DownloadTask;
import easy.app.download.Downloader;

public class DownloadSampleActivity extends AppCompatActivity implements View.OnClickListener {

    Activity mActivity = this;
    Button dmBtn;
    TextView tv;
    DownloadTask mDownloadTask;
    DownloadTask.DownloadRequestParams mParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_sample);
        dmBtn = (Button) this.findViewById(R.id.dmBtn);
        tv = (TextView) this.findViewById(R.id.text);
        dmBtn.setOnClickListener(this);
        dmBtn.setText("立即下载");
        mParams = new DownloadTask.DownloadRequestParams();
        mParams.mDesc = "下载中...";
        mParams.mTipTitle = "下载测试";
        mParams.mUrl = "http://ftpapp.dev.ucuxin.com/UXAPP201605201.apk";
        mParams.mOnlyWifi = true;
        mParams.mDir = "AAA/BBA";
        mParams.mFileName = "ccc.apk";
    }


    @Override
    public void onClick(View v) {
        if (mDownloadTask != null) {
            mDownloadTask.onDestroy(mActivity);
            mDownloadTask = null;
        }
        mDownloadTask = Downloader.startRequest(this, mParams, new DownloadTask.OnDownloadListener() {
            @Override
            public void onDownloadChanged(@DownloadTask.TaskStatus int status, int total, int downloaded) {
                Log.d("onDownloadChanged", "status:" + status + " total:" + total + "downloaded:" + downloaded);
                tv.setText("正在下载中：" + DownloadTask.getFormatPercent(downloaded, total));
            }

            @Override
            public void onDownloadSuccess(final String s) {
                String filename = DownloadManagerQuery.getFileName((DownloadManager) mActivity.getSystemService(DOWNLOAD_SERVICE), mDownloadTask.getTaskId());
                Log.d("onDownloadChanged", "filename:" + filename);
                tv.setText("下载完成");
                dmBtn.setText("立即安装");
                dmBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DownloadTask.installApk(mActivity, s);
                    }
                });
            }
        });
        mDownloadTask.registerCompleteReceiver(this);
        mDownloadTask.registerContentResolver(this);
    }
}

package ms.lucio;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import easy.skin.SkinConst;
import easy.skin.SkinManager;
import easy.skin.attr.SkinAttrSupport;
import easy.skin.attr.TextAttr;
import easy.skin.base.BaseSkinApplication;
import easy.skin.factory.SkinAttrFactory;

/**
 * Created by Lucio on 17/4/6.
 */

public class App extends BaseSkinApplication{
    @Override
    public void onCreate() {
        super.onCreate();
        SkinAttrSupport.addSupportAttr(SkinConst.ATTR_NAME_TEXT,new TextAttr());
        SkinManager.getInstance().setSkinAttrFactory(SkinAttrFactory.createPrefixFactory(null));
        SkinManager.getInstance().setEnableFontChange(true);
//        SkinManager.getInstance().setSkinAttrFactory(SkinAttrFactory.createPrefixFactory(null));
        try {
            copyBigDataToSD();
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void copyBigDataToSD() throws IOException
    {
        String path = Environment.getExternalStorageDirectory().toString() + File.separator + "ucux-ls.skin";
        File file = new File(path);
        if(file.exists())
            return;

        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(path);
        myInput = this.getAssets().open("ucux-ls.skin");
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while(length > 0)
        {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }
}

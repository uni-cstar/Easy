package easy.skin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import easy.skin.util.SkinUtil;

/**
 * Created by Lucio on 17/3/31.
 */

public class ResourceManager {
    private Resources mResources;
    private String mPluginPackageName;
    private String mSuffix;
    private Context mContext;

    public ResourceManager(Context context, Resources res, String pluginPackageName, String suffix) {
        update(context, res, pluginPackageName, suffix);
    }

    public void update(Context context, Resources res, String pluginPackageName, String suffix) {
        mContext = context;
        mResources = res;
        mPluginPackageName = pluginPackageName;
        if (suffix == null) {
            suffix = "";
        }
        mSuffix = suffix;
    }

    /**
     * 更改后缀
     *
     * @param suffix
     */
    public void setSuffix(String suffix) {
        mSuffix = suffix;
    }

    /**
     * 获取颜色
     *
     * @param resName
     * @return
     */
    public int getColor(String resName) {
        String suffixName = appendSuffix(resName);
        int color = getColor2(suffixName);
        if (color == 0) {
            return getColor2(resName);
        }
        return color;
    }

    private int getColor2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_COLOR, mPluginPackageName);
            if (resId == 0)
                return 0;

            int color = 0;
            if (Build.VERSION.SDK_INT >= 23) {
                color = mResources.getColor(resId, null);
            } else {
                color = mResources.getColor(resId);
            }

            if (color == 0) {
                return ContextCompat.getColor(mContext, resId);
            }
            return color;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getInteger(String resName) {
        String suffixName = appendSuffix(resName);
        int color = getInteger2(suffixName);
        if (color == 0) {
            return getInteger2(resName);
        }
        return color;
    }

    private int getInteger2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_INTEGER, mPluginPackageName);
            if (resId == 0)
                return 0;

            int color = mResources.getInteger(resId);
            if (color == 0) {
                return mContext.getResources().getInteger(resId);
            }
            return color;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getDimension(String resName) {
        String suffixName = appendSuffix(resName);
        float dimen = getDimension2(suffixName);
        if (dimen == 0) {
            return getDimension2(resName);
        }
        return dimen;
    }

    private float getDimension2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_DIMEN, mPluginPackageName);
            if (resId == 0)
                return 0;

            float dimen = mResources.getDimension(resId);
            if (dimen == 0) {
                return mContext.getResources().getDimension(resId);
            }
            return dimen;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getDimensionPixelOffset(String resName) {
        String suffixName = appendSuffix(resName);
        float dimen = getDimensionPixelOffset2(suffixName);
        if (dimen == 0) {
            return getDimensionPixelOffset2(resName);
        }
        return dimen;
    }

    private float getDimensionPixelOffset2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_DIMEN, mPluginPackageName);
            if (resId == 0)
                return 0;

            float dimen = mResources.getDimensionPixelOffset(resId);
            if (dimen == 0) {
                return mContext.getResources().getDimensionPixelOffset(resId);
            }
            return dimen;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    public float getDimensionPixelSize(String resName) {
        String suffixName = appendSuffix(resName);
        float dimen = getDimensionPixelSize2(suffixName);
        if (dimen == 0) {
            return getDimensionPixelSize2(resName);
        }
        return dimen;
    }

    private float getDimensionPixelSize2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_DIMEN, mPluginPackageName);
            if (resId == 0)
                return 0;

            float dimen = mResources.getDimensionPixelSize(resId);
            if (dimen == 0) {
                return mContext.getResources().getDimensionPixelSize(resId);
            }
            return dimen;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 获取ColorStateList
     *
     * @param resName 资源名字
     * @return
     */
    public ColorStateList getColorStateList(String resName) {
        String suffixName = appendSuffix(resName);
        ColorStateList result = getColorStateList2(suffixName);
        if (result == null) {
            return getColorStateList2(resName);
        }
        return result;
    }


    private ColorStateList getColorStateList2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_COLOR, mPluginPackageName);
            if (resId == 0)
                return null;
            ColorStateList result = null;
            if (Build.VERSION.SDK_INT >= 23) {
                result = mResources.getColorStateList(resId, null);
            } else {
                result = mResources.getColorStateList(resId);
            }
            if (result == null) {
                result = ContextCompat.getColorStateList(mContext, resId);
            }
            return result;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取drawable
     *
     * @param resName
     * @return
     */
    public Drawable getDrawable(String resName) {
        String suffixName = appendSuffix(resName);
        Drawable drawable = getDrawable2(suffixName);
        if (drawable == null) {
            return getDrawable2(resName);
        }
        return drawable;
    }

    private Drawable getDrawable2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_DRAWABLE, mPluginPackageName);
            if (resId == 0) {
                resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_MIPMAP, mPluginPackageName);
            }
            if (resId == 0) {
                resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_COLOR, mPluginPackageName);
            }

            if (resId == 0)
                return null;

            Drawable result = null;
            if (Build.VERSION.SDK_INT >= 21) {
                result = mResources.getDrawable(resId, null);
            } else {
                result = mResources.getDrawable(resId);
            }

            if (result == null) {
                result = ContextCompat.getDrawable(mContext, resId);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CharSequence getText(String resName) {
        String suffixName = appendSuffix(resName);
        CharSequence text = getText2(suffixName);
        if (text == null) {
            return getText2(resName);
        }
        return text;
    }

    private CharSequence getText2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_STRING, mPluginPackageName);
            if (resId == 0)
                return null;

            try {
                return mResources.getText(resId);
            } catch (Exception e) {
                e.printStackTrace();
                return mContext.getResources().getText(resId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String appendSuffix(String name) {
        if (!SkinUtil.isNullOrEmpty(mSuffix))
            return name + "_" + mSuffix;
        return name;
    }

}

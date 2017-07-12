package easy.skin;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;

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
     * 通过id找去换肤资源颜色
     *
     * @param id
     * @return
     */
    public int getColor(@ColorRes int id) {
        String entryName = SkinUtil.getResourceEntryName(mContext, id);
        return getColor(entryName);
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
        if (color == 0 && !resName.equals(suffixName)) {
            return getColor2(resName);
        }
        return color;
    }

    private int getColor2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_COLOR, mPluginPackageName);
            if (resId == 0) {
                resId = mContext.getResources().getIdentifier(resName, SkinConst.RES_TYPE_NAME_COLOR, mContext.getPackageName());
                if (resId != 0) {
                    return ContextCompat.getColor(mContext, resId);
                }
            } else {
                if (Build.VERSION.SDK_INT >= 23) {
                    return mResources.getColor(resId, null);
                } else {
                    return mResources.getColor(resId);
                }
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getInteger(@IntegerRes int id) {
        String entryName = SkinUtil.getResourceEntryName(mContext, id);
        return getInteger(entryName);
    }

    public int getInteger(String resName) {
        String suffixName = appendSuffix(resName);
        int color = getInteger2(suffixName);
        if (color == 0 && !resName.equals(suffixName)) {
            return getInteger2(resName);
        }
        return color;
    }

    private int getInteger2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_INTEGER, mPluginPackageName);
            if (resId == 0) {
                resId = mContext.getResources().getIdentifier(resName, SkinConst.RES_TYPE_NAME_INTEGER, mContext.getPackageName());
                if (resId != 0) {
                    return mContext.getResources().getInteger(resId);
                }
            } else {
                return mResources.getInteger(resId);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getDimension(@DimenRes int id) {
        String entryName = SkinUtil.getResourceEntryName(mContext, id);
        return getDimension(entryName);
    }

    public float getDimension(String resName) {
        String suffixName = appendSuffix(resName);
        float dimen = getDimension2(suffixName);
        if (dimen == 0 && !resName.equals(suffixName)) {
            return getDimension2(resName);
        }
        return dimen;
    }

    private float getDimension2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_DIMEN, mPluginPackageName);
            if (resId == 0) {
                resId = mContext.getResources().getIdentifier(resName, SkinConst.RES_TYPE_NAME_DIMEN, mContext.getPackageName());
                if (resId != 0) {
                    return mContext.getResources().getDimension(resId);
                }
            } else {
                return mResources.getDimension(resId);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getDimensionPixelOffset(@DimenRes int id) {
        String entryName = SkinUtil.getResourceEntryName(mContext, id);
        return getDimensionPixelOffset(entryName);
    }

    public float getDimensionPixelOffset(String resName) {
        String suffixName = appendSuffix(resName);
        float dimen = getDimensionPixelOffset2(suffixName);
        if (dimen == 0 && !resName.equals(suffixName)) {
            return getDimensionPixelOffset2(resName);
        }
        return dimen;
    }

    private float getDimensionPixelOffset2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_DIMEN, mPluginPackageName);
            if (resId == 0) {
                resId = mContext.getResources().getIdentifier(resName, SkinConst.RES_TYPE_NAME_DIMEN, mContext.getPackageName());
                if (resId != 0)
                    return mContext.getResources().getDimensionPixelOffset(resId);
            } else {
                return mResources.getDimensionPixelOffset(resId);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public float getDimensionPixelSize(@DimenRes int id) {
        String entryName = SkinUtil.getResourceEntryName(mContext, id);
        return getDimensionPixelSize(entryName);
    }

    public float getDimensionPixelSize(String resName) {
        String suffixName = appendSuffix(resName);
        float dimen = getDimensionPixelSize2(suffixName);
        if (dimen == 0 && !resName.equals(suffixName)) {
            return getDimensionPixelSize2(resName);
        }
        return dimen;
    }

    private float getDimensionPixelSize2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_DIMEN, mPluginPackageName);
            if (resId == 0) {
                resId = mContext.getResources().getIdentifier(resName, SkinConst.RES_TYPE_NAME_DIMEN, mContext.getPackageName());
                if (resId != 0) {
                    return mContext.getResources().getDimensionPixelSize(resId);
                }
            } else {
                return mResources.getDimensionPixelSize(resId);
            }
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ColorStateList getColorStateList(@ColorRes int id) {
        String entryName = SkinUtil.getResourceEntryName(mContext, id);
        return getColorStateList(entryName);
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
        if (result == null && !resName.equals(suffixName)) {
            return getColorStateList2(resName);
        }
        return result;
    }


    private ColorStateList getColorStateList2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_COLOR, mPluginPackageName);
            if (resId == 0) {
                resId = mContext.getResources().getIdentifier(resName, SkinConst.RES_TYPE_NAME_COLOR, mContext.getPackageName());
                if (resId != 0) {
                    return ContextCompat.getColorStateList(mContext, resId);
                }
            } else {
                if (Build.VERSION.SDK_INT >= 23) {
                    return mResources.getColorStateList(resId, null);
                } else {
                    return mResources.getColorStateList(resId);
                }
            }
            return null;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Drawable getDrawable(@DrawableRes int id) {
        String entryName = SkinUtil.getResourceEntryName(mContext, id);
        return getDrawable(entryName);
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
        if (drawable == null && !resName.equals(suffixName)) {
            return getDrawable2(resName);
        }
        return drawable;
    }

    private Drawable getDrawable2(String resName) {
        try {
            Drawable result = null;
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_DRAWABLE, mPluginPackageName);
            if (resId == 0) {
                resId = mContext.getResources().getIdentifier(resName, SkinConst.RES_TYPE_NAME_DRAWABLE, mContext.getPackageName());
                if (resId != 0) {
                    return ContextCompat.getDrawable(mContext, resId);
                }
            } else {
                if (Build.VERSION.SDK_INT >= 21) {
                    return mResources.getDrawable(resId, null);
                } else {
                    return mResources.getDrawable(resId);
                }
            }
            return null;
//            if (resId == 0) {
//                resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_MIPMAP, mPluginPackageName);
//            }
//            if (resId == 0) {
//                resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_COLOR, mPluginPackageName);
//            }
//
//            if (resId == 0)
//                return null;

//            Drawable result = null;
//            if (Build.VERSION.SDK_INT >= 21) {
//                result = mResources.getDrawable(resId, null);
//            } else {
//                result = mResources.getDrawable(resId);
//            }
//
//            if (result == null) {
//                result = ContextCompat.getDrawable(mContext, resId);
//            }
//            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CharSequence getText(@StringRes int id) {
        String entryName = SkinUtil.getResourceEntryName(mContext, id);
        return getText(entryName);
    }

    public CharSequence getText(String resName) {
        String suffixName = appendSuffix(resName);
        CharSequence text = getText2(suffixName);
        if (text == null && !resName.equals(suffixName)) {
            return getText2(resName);
        }
        return text;
    }

    private CharSequence getText2(String resName) {
        try {
            int resId = mResources.getIdentifier(resName, SkinConst.RES_TYPE_NAME_STRING, mPluginPackageName);
            if (resId == 0) {
                resId = mContext.getResources().getIdentifier(resName, SkinConst.RES_TYPE_NAME_STRING, mContext.getPackageName());
                if (resId != 0) {
                    return mContext.getResources().getText(resId);
                }
            } else {
                return mResources.getText(resId);
            }
            return null;
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

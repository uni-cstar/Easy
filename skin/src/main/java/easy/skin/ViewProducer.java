package easy.skin;

import android.content.Context;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.View;

import java.lang.reflect.Constructor;
import java.util.Map;

/**
 * 创建View
 */
class ViewProducer {
    private final Object[] mConstructorArgs = new Object[2];
    private final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap<>();
    private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final String[] sClassPrefixList = {
            "android.widget.",
            "android.view.",
            "android.webkit."
    };

    View createViewFromTag(Context context, String name, AttributeSet attrs) {
        if ("view".equals(name)) {
            name = attrs.getAttributeValue(null, "class");
        }

        try {
            mConstructorArgs[0] = context;
            mConstructorArgs[1] = attrs;

            if (-1 == name.indexOf('.')) {
                for (String classPrefix : sClassPrefixList) {
                    View view = tryCreateView(context, name, classPrefix);
                    if (view != null) {
                        return view;
                    }
                }
                return null;
            } else {
                return tryCreateView(context, name, null);
            }
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the actual LayoutInflater try
            return null;
        } finally {
            // Don't retain references on context.
            mConstructorArgs[0] = null;
            mConstructorArgs[1] = null;
        }
    }

    private View tryCreateView(Context context, String name, String prefix) {
        try {
            Constructor<? extends View> constructor = sConstructorMap.get(name);

            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to add it
                Class<? extends View> clazz = context.getClassLoader().loadClass(
                        prefix != null ? (prefix + name) : name).asSubclass(View.class);

                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            return constructor.newInstance(mConstructorArgs);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

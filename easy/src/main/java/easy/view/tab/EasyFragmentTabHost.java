package easy.view.tab;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;

import easy.R;

/**
 * Created by Lucio on 17/4/18.
 */

public class EasyFragmentTabHost extends EasyTabHost implements EasyTabHost.OnEasyTabChangedListener {

    FragmentManager mFragmentManager;
    private int mContainerId;
    ViewGroup mRealTabContent;
    OnEasyTabChangedListener mOnTabChangeListener;
    private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
    private TabInfo mLastTab;
    private boolean mAttached;

    public EasyFragmentTabHost(Context context) {
        super(context);
    }

    public EasyFragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
        initFragmentTabHost(context, attrs);
    }

    public EasyFragmentTabHost(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initFragmentTabHost(context, attrs);
    }

    private void initFragmentTabHost(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs,
                new int[]{android.R.attr.inflatedId}, 0, 0);
        mContainerId = a.getResourceId(0, 0);
        a.recycle();
        if (mContainerId == 0)
            mContainerId = R.id.easyTabContent;
        super.setOnEasyTabChangedListener(this);
    }

    private void ensureHierarchy() {
        if ((mRealTabContent = (ViewGroup) findViewById(R.id.easyTabContent)) == null) {
            mRealTabContent = new FrameLayout(getContext());
            mRealTabContent.setId(mContainerId);
            this.addView(mRealTabContent, 0, new LayoutParams(LayoutParams.MATCH_PARENT, 0, 1));
        }
    }

    private void ensureContent() {
        if (mRealTabContent == null) {
            mRealTabContent = (ViewGroup) findViewById(mContainerId);
            if (mRealTabContent == null) {
                throw new IllegalStateException(
                        "No tab content FrameLayout found for id " + mContainerId);
            }
        }
    }


    @Override
    @Deprecated
    public void setup() {
        throw new IllegalStateException(
                "Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(FragmentManager manager) {
        ensureHierarchy();
        mFragmentManager = manager;
        super.setup();
    }

    public void setup(FragmentManager manager, int containerId) {
        ensureHierarchy();
        mFragmentManager = manager;
        mContainerId = containerId;
        ensureContent();
        mRealTabContent.setId(containerId);
        super.setup();
        if (getId() == View.NO_ID) {
            setId(android.R.id.tabhost);
        }
    }

    public void addTab(IFragTabItem item, Class<?> clss, Bundle args) {
        String tag = item.getFragTag();
        TabInfo info = new TabInfo(tag, clss, args);

        if (mAttached) {
            // If we are already attached to the window, then check to make
            // sure this tab's fragment is inactive if it exists.  This shouldn't
            // normally happen.
            info.fragment = mFragmentManager.findFragmentByTag(tag);
            if (info.fragment != null && !info.fragment.isDetached()) {
                FragmentTransaction ft = mFragmentManager.beginTransaction();
                ft.detach(info.fragment);
                ft.commit();
            }
        }

        mTabs.add(info);
        addTab(item);
    }


    static final class TabInfo {
        private final String tag;
        private final Class<?> clss;
        private final Bundle args;
        private Fragment fragment;

        TabInfo(String _tag, Class<?> _class, Bundle _args) {
            tag = _tag;
            clss = _class;
            args = _args;
        }
    }

    @Override
    public void setOnEasyTabChangedListener(OnEasyTabChangedListener listener) {
        mOnTabChangeListener = listener;
    }

    public String getCurrentTabTag() {
        return getCurrentTabTag(mCurrentTab);
    }

    private String getCurrentTabTag(int index) {
        if (index >= 0 && index < mTabs.size()) {
            return mTabs.get(index).tag;
        }
        return null;
    }

    public void setCurrentTabByTag(String tag) {
        int i;
        for (i = 0; i < mTabs.size(); i++) {
            if (mTabs.get(i).tag.equals(tag)) {
                setCurrentTab(i);
                break;
            }
        }
    }


    @Override
    public void onEasyTabSelected(int index) {
        String tabTag = getCurrentTabTag(index);
        FragmentTransaction ft = doTabChanged(tabTag, null);
        if (ft != null) {
            ft.commit();
        }
        if (mOnTabChangeListener != null) {
            mOnTabChangeListener.onEasyTabSelected(index);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        String currentTab = getCurrentTabTag();

        // Go through all tabs and make sure their fragments match
        // the correct state.
        FragmentTransaction ft = null;
        for (int i = 0; i < mTabs.size(); i++) {
            TabInfo tab = mTabs.get(i);
            tab.fragment = mFragmentManager.findFragmentByTag(tab.tag);
            if (tab.fragment != null && !tab.fragment.isDetached()) {
                if (tab.tag.equals(currentTab)) {
                    // The fragment for this tab is already there and
                    // active, and it is what we really want to have
                    // as the current tab.  Nothing to do.
                    mLastTab = tab;
                } else {
                    // This fragment was restored in the active state,
                    // but is not the current tab.  Deactivate it.
                    if (ft == null) {
                        ft = mFragmentManager.beginTransaction();
                    }
                    ft.detach(tab.fragment);
                }
            }
        }

        // We are now ready to go.  Make sure we are switched to the
        // correct tab.
        mAttached = true;
        ft = doTabChanged(currentTab, ft);
        if (ft != null) {
            ft.commit();
            mFragmentManager.executePendingTransactions();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mAttached = false;
    }


    private FragmentTransaction doTabChanged(String tabTag, FragmentTransaction ft) {


        TabInfo newTab = null;
        for (int i = 0; i < mTabs.size(); i++) {
            TabInfo tab = mTabs.get(i);
            if (tab.tag.equals(tabTag)) {
                newTab = tab;
            }
        }
        if (newTab == null) {
            throw new IllegalStateException("No tab known for tag " + tabTag);
        }

        if (ft == null) {
            ft = mFragmentManager.beginTransaction();
        }

        if (mLastTab != newTab) {

            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.detach(mLastTab.fragment);
                }
            }

            if (newTab.fragment == null) {
                newTab.fragment = Fragment.instantiate(getContext(), newTab.clss.getName(), newTab.args);
                ft.add(mContainerId, newTab.fragment, newTab.tag);
            } else {
                ft.attach(newTab.fragment);
            }

            mLastTab = newTab;
        }
        return ft;
    }


    static class SavedState extends BaseSavedState {
        String curTab;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel in) {
            super(in);
            curTab = in.readString();
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            super.writeToParcel(out, flags);
            out.writeString(curTab);
        }

        @Override
        public String toString() {
            return "FragmentTabHost.SavedState{"
                    + Integer.toHexString(System.identityHashCode(this))
                    + " curTab=" + curTab + "}";
        }

        public static final Parcelable.Creator<SavedState> CREATOR
                = new Parcelable.Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.curTab = getCurrentTabTag();
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setCurrentTabByTag(ss.curTab);
    }
}

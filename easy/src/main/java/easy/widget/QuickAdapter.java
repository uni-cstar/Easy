package easy.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import easy.R;


/**
 * Created by Lucio on 2016/7/27.
 * 快速实现adapter
 */
public abstract class QuickAdapter<T, VH extends QuickAdapter.ViewHolder> extends BaseAdapter {

    protected List<T> mDatas;

    protected Context mContext;

    public QuickAdapter(Context context) {
        this.mContext = context;
        mDatas = new ArrayList<T>();
    }

    public QuickAdapter(Context context, List<T> datas) {
        this.mContext = context;
        mDatas = datas == null ? new ArrayList<T>() : datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(T elem) {
        mDatas.add(elem);
        notifyDataSetChanged();
    }

    public void addAll(List<T> elem) {
        mDatas.addAll(elem);
        notifyDataSetChanged();
    }

    public void set(T oldElem, T newElem) {
        set(mDatas.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
        mDatas.set(index, elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        mDatas.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mDatas.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        mDatas.clear();
        mDatas.addAll(elem);
        notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return mDatas.contains(elem);
    }

    /**
     * Clear data list
     */
    public void clear() {
        mDatas.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder = null;
        if (convertView == null) {
            holder = onCreateViewHolder(parent, position);
            convertView = holder.mView;
        } else {
            holder = (VH) convertView.getTag(R.id.easy_quick_vh_tag);
        }
        bindViewHolder(holder, position);
        return convertView;
    }

    private void bindViewHolder(VH holder, int position) {
        holder.setPosition(position);
        onBindViewHolder(holder, position);
    }

    /**
     * 创建一个ViewHolder
     *
     * @param parent
     * @param position
     * @return
     */
    public abstract VH onCreateViewHolder(ViewGroup parent, int position);

    /**
     * 绑定ViewHolder的值
     *
     * @param holder
     * @param position
     */
    public abstract void onBindViewHolder(VH holder, int position);

    public static class ViewHolder {

        protected View mView;

        private int position;

        public ViewHolder(View itemView) {
            this.mView = itemView;
            this.mView.setTag(R.id.easy_quick_vh_tag,this);
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }
}

package com.wh.learnapplication.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.wh.learnapplication.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewRvAdapter extends RecyclerArrayAdapter<String> {

    public WebViewRvAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new WebViewRvViewHolder(parent);
    }

    class WebViewRvViewHolder extends BaseViewHolder<String> {

        @BindView(R.id.tv_name)
        TextView tvName;

        public WebViewRvViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_webview_comment);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void setData(String data) {
            tvName.setText(data);
        }
    }
}

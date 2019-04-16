package com.wh.learnapplication.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cheng.channel.Channel;
import com.cheng.channel.ChannelView;
import com.socks.library.KLog;
import com.wh.learnapplication.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ChannelViewActivity extends AppCompatActivity implements ChannelView.OnChannelListener {
    private String TAG = getClass().getSimpleName();
    @BindView(R.id.channelView)
    ChannelView channelView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_view);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        String[] myChannel = {"要闻", "视频", "新时代", "娱乐", "体育", "军事", "NBA", "国际", "科技", "财经", "汽车", "电影", "游戏", "独家", "房产",
                "图片", "时尚", "呼和浩特", "三打白骨精"};
        String[] recommendChannel1 = {"综艺", "美食", "育儿", "冰雪", "必读", "政法网事", "都市",
                "NFL", "韩流"};
//        String[] recommendChannel2 = {"问答", "文化", "佛学", "股票", "动漫", "理财", "情感", "职场", "旅游"};
//        String[] recommendChannel3 = {"家居", "电竞", "数码", "星座", "教育", "美容", "电视剧",
//                "搏击", "健康"};

        List<Channel> myChannelList = new ArrayList<>();
        List<Channel> recommendChannelList1 = new ArrayList<>();
//        List<Channel> recommendChannelList2 = new ArrayList<>();
//        List<Channel> recommendChannelList3 = new ArrayList<>();

        for (int i = 0; i < myChannel.length; i++) {
            String aMyChannel = myChannel[i];
            Channel channel;
//            if (i > 2 && i < 6) {
//                //可设置频道归属板块（channelBelong），当前设置此频道归属于第二板块，当删除该频道时该频道将回到第二板块
//                channel = new Channel(aMyChannel, 2, i);
//            } else if (i > 7 && i < 10) {
//                //可设置频道归属板块（channelBelong），当前设置此频道归属于第三板块，当删除该频道时该频道将回到第三板块中
//                channel = new Channel(aMyChannel, 3, i);
//            } else {
                channel = new Channel(aMyChannel, (Object) i);
//            }
            myChannelList.add(channel);
        }

        for (String aMyChannel : recommendChannel1) {
            Channel channel = new Channel(aMyChannel);
            recommendChannelList1.add(channel);
        }

//        for (String aMyChannel : recommendChannel2) {
//            Channel channel = new Channel(aMyChannel);
//            recommendChannelList2.add(channel);
//        }
//
//        for (String aMyChannel : recommendChannel3) {
//            Channel channel = new Channel(aMyChannel);
//            recommendChannelList3.add(channel);
//        }

        channelView.setChannelFixedCount(3);
        channelView.addPlate("我的频道 拖拽可以排序", myChannelList);
        channelView.addPlate("更多频道 点击添加频道", recommendChannelList1);
//        channelView.addPlate("国内", recommendChannelList2);
//        channelView.addPlate("国外", recommendChannelList3);
        channelView.inflateData();
        channelView.setChannelNormalBackground(R.drawable.bg_channel_normal);//设置频道正常状态下背景
        channelView.setChannelEditBackground(R.drawable.bg_channel_edit);//设置频道编辑状态下背景
        channelView.setChannelFocusedBackground(R.drawable.bg_channel_focused);//设置频道编辑且点击状态下背景
        channelView.setOnChannelItemClickListener(this);
    }

    @Override
    public void channelItemClick(int position, Channel channel) {
        KLog.i(TAG, position + ".." + channel);
    }

    @Override
    public void channelEditFinish(List<Channel> channelList) {
        KLog.i(TAG, channelList.toString());
        KLog.i(TAG, channelView.getMyChannel().toString());
    }

    @Override
    public void channelEditStart() {
        KLog.i(TAG, "channelEditStart");
    }
}

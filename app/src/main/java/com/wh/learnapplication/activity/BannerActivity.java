package com.wh.learnapplication.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.jude.rollviewpager.RollPagerView;
import com.socks.library.KLog;
import com.wh.learnapplication.R;
import com.wh.learnapplication.adapter.TestBannerAdapter;
import com.wh.learnapplication.adapter.TestBannerLoopAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerActivity extends AppCompatActivity {

    @BindView(R.id.roll_view_pager)
    RollPagerView rollViewPager;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_number)
    TextView tvNumber;
    private String[] images = {
            "http://zw-resource.oss-cn-shenzhen.aliyuncs.com/hx/uploadFiles/default/2019012618385476693.jpg",
            "http://zw-resource.oss-cn-shenzhen.aliyuncs.com/hx/uploadFiles/default/2019020111515386846.jpg",
            "http://zw-resource.oss-cn-shenzhen.aliyuncs.com/hx/uploadFiles/healthnews/2019031617014520821.jpg",
            "http://zw-resource.oss-cn-shenzhen.aliyuncs.com/hx/uploadFiles/healthnews/2019030510525368209.png",
            "http://zw-resource.oss-cn-shenzhen.aliyuncs.com/hx/uploadFiles/default/2018121215342318639.jpg"
    };
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);
        initBanner();
    }

    //定义两个变量，这个两个变量是表示滑动时候，positionOffset 是从大到
    //小的变化，还是从小到大的变化。用于在滑动时候，避免多次调用一个方法
    private boolean isSmallToBig = true;
    private boolean isBigToSmall = false;

    //记录上一次滑动的positionOffsetPixels值
    private int lastValue = -1;

    private void initBanner() {
//        rollViewPager.setPlayDelay(1000);//设置播放时间间隔
//        rollViewPager.setAnimationDurtion(500);//设置透明度
//        rollViewPager.setHintView(null);//hide the indicator

        TestBannerAdapter bannerAdapter=new TestBannerAdapter(images,this);
        final TestBannerLoopAdapter bannerLoopAdapter = new TestBannerLoopAdapter(rollViewPager, images,this);
        rollViewPager.setAdapter(bannerAdapter);

        rollViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                KLog.e("addOnPageChangeListener onPageScrolled   i=" + position + "     v= " + positionOffset + "  " + positionOffsetPixels);
//                if (positionOffset>0){
//                    tvTitle.setAlpha(positionOffset);
//                }
//
//                if (positionOffset != 0) {
//                    mCurrentPage = position;
//                    boolean isLeft = true;
//                    if (lastValue >= positionOffsetPixels) {
//                        //右滑
//                        isLeft = false;
//                    } else if (lastValue < positionOffsetPixels) {
//                        //左滑
//                        isLeft = true;
//                    }
//                    setIndiactorView(positionOffset,isLeft);
//                }
//                lastValue = positionOffsetPixels;


            }

            @Override
            public void onPageSelected(int i) {
                KLog.d("addOnPageChangeListener onPageSelected " + i);
//                if (isLeft){
//                    KLog.e("onPageScrolled","--->左划");
//                }else {
//                    KLog.e("onPageScrolled","--->右划");
//
//                }

                if (i==0){
                    tvTitle.setText("习近平同意大利总统马塔雷拉举行会谈");
                }else if (i==1){
                    tvTitle.setText("马塔雷拉举行会谈");
                }else if (i==2) {
                    tvTitle.setText("习近平同意大利总统");
                }else if (i==2) {
                    tvTitle.setText("习近平举行会谈");
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {
                KLog.d("addOnPageChangeListener onPageScrollStateChanged " + i);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        rollViewPager.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rollViewPager.pause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

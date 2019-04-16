package com.wh.learnapplication.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.wh.learnapplication.R;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HtmlSpannerActivity extends AppCompatActivity {

    @BindView(R.id.tv_text)
    TextView tvText;
    private int widthPixels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_html_spanner);
        ButterKnife.bind(this);
        widthPixels = getResources().getDisplayMetrics().widthPixels;
        String str="<div class=\"arti-content\"><p>相信大家都知道，生姜红糖水的功效是非常多的，男友老少皆宜，尤其是女性，经常喝，因为对女性的功效非常好，而且生姜红糖水的做法很简单，学一下就会，但是我们要注意和生姜红糖水的一些注意事项。</p><p><strong>生姜红糖水的作用</strong></p><p style=\"text-align: center;\"><strong><img src=\"https://file.fh21static.com/fhfile1/M00/6D/E6/oYYBAFwtrcCAIib2AAHG44Ntdks629.jpg\"></strong></p><p>1、祛风寒，防感冒：都知道生姜对于预防感冒有很大的作用，而且生姜还可以对发热病人起到帮助排汗的效果，将生姜放进水中煮，放一些红糖，能够防寒保暖，暖胃，使得全身通畅舒适，所以在着凉吹风后喝一些红糖生姜水来预防感冒。</p><p>2、调理月经：红糖的功效对于痛经有治疗效果，生姜可以暖宫，暖胃，二者结合，对于女性的月经方面有很大作用，使月经拍的更加通顺，活血。生姜红糖水还可以改善月经后的气色问题，中晚餐之前喝一杯，能够是气色恢复红润。</p><p>3、体力恢复：生姜红糖水可以补充能量，恢复体力，尤其对产后恢复有帮助，产妇在生完孩子后嗜血过多，力气全无，喝点生姜红糖水可以增加血容量，恢复体力，对子宫收缩，恢复，恶露的排出有很大作用。</p><p>4、去水肿气胀，开胃健脾：生姜性温味辣，含有姜醇等油性挥发物，还有姜辣素、维生素、姜油酚、树脂、淀粉、纤维以及少量矿物质。这些物质可以增强血液循环、刺激胃液分泌、兴奋肠道、促进消化、健胃增进食欲，从而除风邪、治头痛、开脾胃等。</p><p><strong>生姜红糖水的做法</strong></p><p>1、生姜去皮洗净，切丝，大蒜洗净，拍碎，锅中加入一大碗水。</p><p>2、姜丝放进去，开始煮，锅中水烧开后，放入红糖，用勺子搅拌均匀。</p><p>3、大火煮2分钟，加入大蒜，大火煮3分钟即可(如果是风寒感冒还有咳嗽就加上大蒜，没有咳嗽就不用加了)。</p><p><strong>生姜红糖水的注意事项</strong></p><p style=\"text-align: center;\"><strong><img src=\"https://file.fh21static.com/fhfile1/M00/6D/E6/oYYBAFwtrcCAIoxKAAHe7N8gjkU051.jpg\"></strong></p><p>1、服用鲜姜汁可治因受寒引起的呕吐，对其他类型的呕吐则不宜使用。</p><p>2、不建议在睡前喝红糖姜水，那样很可能会使体内糖储量过大，长期如此有可能会导致糖尿病，同时还可能出现的是发胖、龋齿等其他症状。</p><p>3、消化不良者阴虚内热者及患有糖尿病的人群不能饮用。</p><p>4、在服用药物期间，不建议喝红糖水。</p><p>5、孕妇也不适宜饮用红糖姜水，怀孕期间喝生姜红糖水对胎儿可能有一定的影响。</p><p>6、生姜红糖水只适用于风寒感冒或淋雨后有胃寒、发热的患者，不能用于暑热感冒或风热感冒患者，但不能用于治疗中暑。</p><p><strong>生姜红糖水可以天天喝吗</strong></p><p style=\"text-align: center;\"><strong><img src=\"https://file.fh21static.com/fhfile1/M00/6D/EC/o4YBAFwtrcGAHnQ8AAGo1mILHzI777.jpg\"></strong></p><p>可以的，每天喝适量的姜糖水对身体是有好处的，能暖胃、促进血红细胞的分裂，使身体内血液的质量得到保证，可以在里面加上红枣，养血功效就更强了。不要晚上喝，好在早上喝，“早上吃姜赛人参，晚上吃姜赛砒霜”，另外睡前喝糖水容易发胖。</p><p>生姜红糖水的作用是不是很多，尤其对女性来说作用很大，但是不建议在晚上或者睡前喝，容易发胖，而且长期下来，的糖尿病的几率会变得很大，所以好在早上或者中午喝。</p><div><br></div></div>";
        onSuccess(str);
    }

    public void onSuccess(String string) {
        CharSequence charSequence;
        // 这个是自定义的ImageGetter
        DetailImageGetter detailImageGetter = new DetailImageGetter(this,tvText);
        //因为fromHtml在api24开始过时，所以加上版本判断
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            charSequence = Html.fromHtml(string, Html.FROM_HTML_MODE_LEGACY,
                    detailImageGetter, null);
        } else {
            charSequence = Html.fromHtml(string, detailImageGetter, null);
        }
        tvText.setText(charSequence);
    }


    class DetailImageGetter implements Html.ImageGetter {
        private Context context;
        private TextView textView;

        DetailImageGetter(Context context, TextView textView) {
            this.context = context;
            this.textView = textView;

        }

        @Override
        public Drawable getDrawable(String source) {
            final UrlDrawable drawable = new UrlDrawable();
            Glide.with(context)
                    .asBitmap()
                    .load(source)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, Transition<? super Bitmap> glideAnimation) {
                            drawable.setBitmap(resource);
                            drawable.setBounds(0, 0, resource.getWidth(), resource.getHeight());
                            textView.invalidate();
                            textView.setText(textView.getText());
                        }
                    });
            return drawable;
        }

        private class UrlDrawable extends BitmapDrawable {
            private Bitmap bitmap;

            @Override
            public void draw(Canvas canvas) {
                super.draw(canvas);
                if (bitmap != null) {
                    canvas.drawBitmap(bitmap, 0, 0, getPaint());
                }
            }

            void setBitmap(Bitmap bitmap) {
                this.bitmap = bitmap;
            }
        }
    }

}

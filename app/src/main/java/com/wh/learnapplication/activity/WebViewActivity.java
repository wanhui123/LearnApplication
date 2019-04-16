package com.wh.learnapplication.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.socks.library.KLog;
import com.wh.learnapplication.R;
import com.wh.learnapplication.adapter.WebViewRvAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        WebViewRvAdapter adapter = new WebViewRvAdapter(this);
        adapter.addHeader(new RecyclerArrayAdapter.ItemView() {
            @Override
            public View onCreateView(ViewGroup parent) {
                webView = new WebView(getApplicationContext());
                webView.setFocusable(false);
                webView.setLayoutParams(new LinearLayout.LayoutParams(getResources().getDisplayMetrics().widthPixels, LinearLayout.LayoutParams.WRAP_CONTENT));
                String content = getHtml();
                setupWebView(content);
                return webView;
            }

            @Override
            public void onBindView(View headerView) {

            }
        });
        recyclerView.setAdapter(adapter);
        for (int i = 0; i < 10; i++) {
            adapter.add("item  " + i);
        }
    }

    @SuppressLint("JavascriptInterface")
    private void setupWebView(String content) {
        webView.setWebChromeClient(new WebChromeClient());
        WebSettings webSettings = webView.getSettings();//获取webview设置属性
        webSettings.setTextZoom(110);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);//把html中的内容放大webview等宽的一列中
        webSettings.setJavaScriptEnabled(true);//支持js
//        webSettings.setBuiltInZoomControls(true); // 显示放大缩小
//        webSettings.setSupportZoom(true); // 可以缩放
        webView.setWebViewClient(new MyWebViewClient());
//        webView.addJavascriptInterface(new JavaScriptInterface(this),);
//        webView.loadUrl("http://192.168.1.198:8001/award-h5/studyShare/news.html?newsId=28&type=0");
//        webView.loadUrl("https://m.gmw.cn/baijia/2019-04/12/1300296315.html");
        webView.loadDataWithBaseURL(null, getNewContent(content), "text/html", "utf-8", null);
        webView.addJavascriptInterface(new JavaScriptInterface(this), "imagelistner");//这个是给图片设置点击监听的，如果你项目需要webview中图片，点击查看大图功能，可以这么添加

    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            imgReset();//重置webview中img标签的图片大小
            // html加载完成之后，添加监听图片的点击js函数
            addImageClickListner();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    public static class JavaScriptInterface {
        private Context context;

        public JavaScriptInterface(Context context) {
            this.context = context;
        }

        //点击图片回调方法
        //必须添加注解,否则无法响应
        @JavascriptInterface
        public void openImage(String img) {
            KLog.i("TAG", "响应点击事件!  img=  " + img);
        }
    }

    /**
     * 对图片进行重置大小，宽度就是手机屏幕宽度，高度根据宽度比便自动缩放
     **/
    private void imgReset() {
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName('img'); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "var img = objs[i];   " +
                "    img.style.maxWidth = '100%'; img.style.height = 'auto';  " +
                "}" +
                "})()");
    }

    /**
     * 将html文本内容中包含img标签的图片，宽度变为屏幕宽度，高度根据宽度比例自适应
     **/
    public static String getNewContent(String htmltext) {
        try {
            Document doc = Jsoup.parse(htmltext);
            Elements elements = doc.getElementsByTag("img");
            for (Element element : elements) {
                element.attr("width", "100%").attr("height", "auto");
            }

            return doc.toString();
        } catch (Exception e) {
            return htmltext;
        }
    }

    private String getHtml() {
//        return "<p><img src=\"http://zw-resource.oss-cn-shenzhen.aliyuncs.com/uploadFiles/award/img/2019041510351345949.png\" alt=\\\"undefined\\\"><br></p><p>&nbsp; &nbsp; &nbsp; &nbsp; 神经内科分为三个病区，包含一病区、二病区和三病区。</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 神经内科一病区是医院的重点专科及省级临床重点专科、湖北省脑卒中筛查及干预基地、湖北省脑卒中诊疗中心、国家高级卒中中心之一、国家GCP基地、国家住培基地、湖北中医药大学及江汉大学硕士研究生培养点。</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 神经内科一病区包括普通门诊、专家门诊、住院病房、卒中单元、神经功能检查室、眼震图室、脑血管病介入中心及神经康复治疗室，血液稀释治疗室等。病区现开放床位63张，病房宽敞明亮、布局合理，现有医护人员30余名，其中主任医师3名、副主任医师2名、主治医师5名、主管以上护师6名，拥有硕士以上学位者10人。现拥有先进的美国GE核磁共振仪、美国GE Revolution CT、德国西门子大型C臂血管造影机、视频眼震电图、经颅多普勒(TCD)、24小时脑电监测系统、全方位的神经系统的运动、言语、吞咽等康复器材等。我病区开展了脑血管病、各种头痛头晕、帕金森氏病、癫痫、睡眠障碍、中枢神经感染、痴呆及认知功能障碍等神经科疾病的诊治。科室以诊治脑血管病为特色，率先在武汉地区开展了缺血性脑血管病的动脉溶栓治疗、颅内外血管成形术及血管内支架置入等神经介入治疗，在缺血性脑血管病的早期诊断及介入治疗方面达到国内领先水平。常年承担医院的临床、科研、教学任务，近年来，承办了多个国家级及省级继续教育项目，共承担了科技部863重点项目、十一五科技攻关项目及省市级科研课题多项，发表各类论文数百余篇。</p><p><img src=\"http://zw-resource.oss-cn-shenzhen.aliyuncs.com/uploadFiles/award/img/2019041510355516891.png\" alt=\"undefined\\\"><br></p><p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;神经内科二病区开设普通床位57张，监护病床9张。现有主任医师2人，副主任医师1人，主治医师5人，硕士以上11人。是湖北省临床重点专科、湖北省脑卒中筛查及干预基地，连续多年被国家卫生计生委评为示范基地医院，国家GCP基地、国家规培基地、武汉大学和湖北中医药大学硕士研究生培养点。湖北省卫生计生委脑卒中筛查与防治领导小组办公室挂靠单位、国家高级“卒中中心”，“国家心脑血管病联盟”成员单位。2017年被湖北省卫生计生委授予“湖北省脑卒中诊疗中心”，为湖北省卒中急救地图医院之一。</p><p>&nbsp; &nbsp; &nbsp; &nbsp; 神经内科三病区开设床位42张，现有主任医师1人，副主任医师2人，主治医师5人，硕士、博士6人，有多名医师到国内及国外知名大学、医院及研究中心学习及从事研究，与国内外多家研究机构及院校有合作关系 。是医院特色专科，为中国医药生物技术协会常务理事单位；是湖北省临床重点专科、湖北省脑卒中筛查及干预基地，连续多年被国家卫生计生委评为示范基地医院，是国家GCP基地、国家住院医师规范化培训神经内科基地、武汉大学和湖北中医药大学硕士研究生培养点、湖北省卫生计生委脑卒中筛查与防治领导小组办公室挂靠单位、国家“高级卒中中心”、国家心脑血管病联盟、中国医药生物技术协会成员单位。2017年被湖北省卫生计生委授予“湖北省脑卒中诊疗中心”，为湖北省卒中急救地图医院之一。</p>                ";
        return "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\">\n" +
                "<title>SpaceX首次成功回收重型猎鹰火箭所有三个助推器</title>\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1\">\n" +
                "<!-- Terren Statistics Meta================BEGIN -->\n" +
                "\t\t<meta name=\"filetype\" content=\"0\">\n" +
                "\t\t<meta name=\"publishedtype\" content=\"1\">\n" +
                "\t\t<meta name=\"pagetype\" content=\"1\">\n" +
                "\t\t<meta name=\"catalogs\" content=\"baijiahao_introduce\">\n" +
                "\t\t<meta name=\"contentid\" content=\"baijiahao_1300296315\">\n" +
                "\t\t<meta name=\"publishdate\" content=\"2019-04-12\">\n" +
                "\t\t<meta name=\"author\" content=\"季爽\">\n" +
                "\t\t<!-- Terren Statistics Meta================END -->\n" +
                "\t<script>\n" +
                "        window.parent.postMessage({\n" +
                "            event: 'headComplete',\n" +
                "            time: Date.now()\n" +
                "        }, '*');\n" +
                "\t</script>\n" +
                "</head>\n" +
                "<body class=\"font-size-1\" ontouchstart style=\"visibility: hidden;\">\n" +
                "\t<!-- //此段js务必保留 -->\n" +
                "\t<script type=\"text/javascript\">\n" +
                "\t\t(function() {\n" +
                "\t\t\tvar getQueryString = function(name, type) {\n" +
                "\t\t\t\tvar reg = new RegExp(\"(^|&)\" + name + \"=([^&]*)(&|$)\");\n" +
                "\t\t\t\tvar r = type === 1 ? window.location.search.substr(1)\n" +
                "\t\t\t\t\t\t.match(reg) : window.location.hash.substr(1).match(reg);\n" +
                "\t\t\t\tif (r != null) {\n" +
                "\t\t\t\t\treturn unescape(r[2]);\n" +
                "\t\t\t\t}\n" +
                "\t\t\t\treturn null;\n" +
                "\t\t\t};\n" +
                "\t\t\tvar fsize = getQueryString('fontSize', 1) || 'font-size-1';\n" +
                "\t\t\tvar sdkver = getQueryString('sdkver', 1);\n" +
                "\t\t\tvar version = getQueryString('verision', 2);\n" +
                "\t\t\tversion = version && version.slice(0, 8);\n" +
                "\t\t\twindow.BAIDU_VERSION = '9701cc88';\n" +
                "\t\t\twindow.BAIDU_VERSION = sdkver || version;\n" +
                "\t\t\twindow.BAIDU_VERSION = window.BAIDU_VERSION ? '_'\n" +
                "\t\t\t\t\t+ window.BAIDU_VERSION : '';\n" +
                "\t\t\tdocument.body.className = fsize;\n" +
                "\t\t\tvar head = document.getElementsByTagName('head')[0];\n" +
                "\t\t\tvar link = document.createElement('link');\n" +
                "\t\t\tlink.href = \"https://gss0.bdstatic.com/5bd1bjqh_Q23odCf/static/thirdparty/js/wrap/third\"\n" +
                "\t\t\t\t\t+ window.BAIDU_VERSION + \".css\";\n" +
                "\t\t\tlink.rel = 'stylesheet';\n" +
                "\t\t\tlink.type = 'text/css';\n" +
                "\t\t\twindow.addEventListener('message', function(e) {\n" +
                "\t\t\t\tif (e.data.event === 'postFontSize') {\n" +
                "\t\t\t\t\tdocument.body.className = e.data.data.fontSize;\n" +
                "\t\t\t\t\tdocument.body.style.visibility = 'visible';\n" +
                "\t\t\t\t\tconsole.log(document.body.scrollHeight);\n" +
                "\t\t\t\t}\n" +
                "\t\t\t});\n" +
                "\t\t\tlink.onload = function() {\n" +
                "\t\t\t\twindow.parent.postMessage({\n" +
                "\t\t\t\t\tevent : 'getFontSize',\n" +
                "\t\t\t\t\ttime : Date.now()\n" +
                "\t\t\t\t}, '*');\n" +
                "\t\t\t\tsetTimeout(function() {\n" +
                "\t\t\t\t\tdocument.body.style.visibility = 'visible';\n" +
                "\t\t\t\t}, 200);\n" +
                "\t\t\t};\n" +
                "\t\t\tlink.onerror = function() {\n" +
                "\t\t\t\tdocument.body.style.visibility = 'visible';\n" +
                "\t\t\t};\n" +
                "\t\t\thead.appendChild(link);\n" +
                "\t\t}());\n" +
                "\t</script>\n" +
                "\t<!-- 我是分割线－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ -->\n" +
                "\t<div id=\"container\" class=\"container\">\n" +
                "\t\t<div class=\"header\">\n" +
                "\t\t\t<div id=\"title\" class=\"titleFont\">SpaceX首次成功回收重型猎鹰火箭所有三个助推器</div>\n" +
                "\t\t\t<div class=\"info link\" data-href=\"https://m.gmw.cn\">\n" +
                "\t\t\t\t<img src=\"https://m.gmw.cn/baijia/logo.png\" data-href=\"https://m.gmw.cn\"/>\n" +
                "\t\t\t\t<span data-href=\"https://m.gmw.cn\">04-12 10:07</span>\n" +
                "\t\t\t</div>\n" +
                "\t\t</div>\n" +
                "\t\t<article>\n" +
                "\t\t\t<p class=\"contentFont\">据外媒The Verge报道，在第二次成功发射“重型猎鹰”(Falcon Heavy)火箭后，SpaceX公司再次创造了新的历史：该火箭的所有三个核心助推器全部成功着陆。“重型猎鹰”火箭的两个侧推进器着陆在位于佛罗里达州卡纳维拉尔角附近的SpaceX的两个混凝土着陆垫上，靠近发射场。而主助推器则着陆在该公司在大西洋的一艘无人船上。</p><p><img border=\"0\" src=\"http://imgm.gmw.cn/attachement/gif/site215/20190412/4229921955201006620.gif\" data-width=\"400.0\" data-height=\"266.0\" data-index=\"0\"/></p><p class=\"contentFont\">这是SpaceX首次成功回收“重型猎鹰”火箭的所有三个核心助推器。当SpaceX在2018年2月第一次成功发射“重型猎鹰”火箭时，只有两个侧推进器着陆在预定地点。其核心主助推器未能成功降落在目标无人船上，因为燃料不足导致无法启动降落所需要的三台发动机。因此，火箭助推器以300英里/小时的速度掉入海洋。</p><p><img border=\"0\" src=\"http://imgm.gmw.cn/attachement/jpg/site215/20190412/3244361895661290695.jpg\" data-width=\"500.0\" data-height=\"281.0\" data-index=\"1\"/></p><p><img border=\"0\" src=\"http://imgm.gmw.cn/attachement/jpg/site215/20190412/6836541079662803890.jpg\" data-width=\"500.0\" data-height=\"286.0\" data-index=\"2\"/></p><p class=\"contentFont\">但在当地时间周四，SpaceX设法完成了所有三次着陆，并且可能有助于这次飞行所使用的所有三个助推器都是SpaceX火箭的升级版本，被称为Block 5.这个版本是为了优化可重复使用性，使SpaceX更容易降落助推器然后快速转向以备将来的发射。</p><p><img border=\"0\" src=\"http://imgm.gmw.cn/attachement/jpg/site215/20190412/3683251319029058036.jpg\" data-width=\"500.0\" data-height=\"284.0\" data-index=\"3\"/></p><p class=\"contentFont\">大约四年前SpaceX在发射猎鹰9号火箭时首次尝试回收助推器。第一次在海上的尝试(2015年1月)以一个壮观的火球结束，助推器猛烈撞击无人机船。2015年4月的第二次尝试计划获得成功，但最终发生爆炸。</p><p><img border=\"0\" src=\"http://imgm.gmw.cn/attachement/jpg/site215/20190412/5776067550701503578.jpg\" data-width=\"500.0\" data-height=\"283.0\" data-index=\"4\"/></p><p><img border=\"0\" src=\"http://imgm.gmw.cn/attachement/jpg/site215/20190412/8020667920392435338.jpg\" data-width=\"500.0\" data-height=\"281.0\" data-index=\"5\"/></p><p><img border=\"0\" src=\"http://imgm.gmw.cn/attachement/png/site215/20190412/7378693718558945925.png\" data-width=\"490.0\" data-height=\"272.0\" data-index=\"6\"/></p><p class=\"contentFont\">SpaceX于2015年12月在卡纳维拉尔角的原始混凝土着陆垫上回收了第一个火箭助推器。从那里开始，又进行了三次尝试，其中一次是海上登陆。SpaceX在去年完成了20次成功发射(只有一次回收失败)。自从去年首次发射“重型猎鹰”以来，该公司只有一次未能成功回收助推器。</p>\n" +
                "                       <p class=\"contentFont\">来源：环球网</p>\n" +
                "\t\t</article>\n" +
                "\t\t<div style=\"display:none\" id=\"yuanweninfo\">url:http://m.gmw.cn/2019-04/12/content_1300296315.htm,id:1300296315</div>\n" +
                "\t</div>\n" +
                "\t<!-- 我是分割线－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－ -->\n" +
                "\t<script type=\"text/javascript\">\n" +
                "\t\t// 此段js务必保留\n" +
                "\t\t(function() {\n" +
                "\t\t\tvar domHeight = document.body.clientHeight;\n" +
                "\t\t\tconsole.log('domHeight', domHeight);\n" +
                "\t\t\twindow.parent.postMessage({\n" +
                "\t\t\t\tevent : 'iFrameComplete',\n" +
                "\t\t\t\theight : domHeight,\n" +
                "\t\t\t\ttime : Date.now()\n" +
                "\t\t\t}, '*');\n" +
                "\t\t\tvar thirdJs = document.createElement(\"script\");\n" +
                "\t\t\tthirdJs.src = \"https://gss0.bdstatic.com/5bd1bjqh_Q23odCf/static/thirdparty/js/wrap/third\"\n" +
                "\t\t\t\t\t+ window.BAIDU_VERSION + \".js\";\n" +
                "\t\t\tdocument.body.appendChild(thirdJs);\n" +
                "\t\t\tthirdJs.onload = function() {\n" +
                "\t\t\t\twindow.parent.postMessage({\n" +
                "\t\t\t\t\tevent : 'thirdJsLoaded',\n" +
                "\t\t\t\t\ttime : Date.now()\n" +
                "\t\t\t\t}, '*');\n" +
                "\t\t\t};\n" +
                "\t\t}());\n" +
                "\t</script>\n" +
                "\t<script>\n" +
                "\t    // 此段js务必保留\n" +
                "\t    window.parent.postMessage({\n" +
                "\t        event: 'bodyComplete',\n" +
                "\t        time: Date.now()\n" +
                "\t    }, '*');\n" +
                "\t</script>\n" +
                "\t<!-- 天润统计 js ================ BEGIN -->\n" +
                "\t\t<script type=\"text/javascript\">document.write(unescape(\"%3Cscript src='https://terren.gmw.cn/webdig.js?z=7' type='text/javascript'%3E%3C/script%3E\"));</script>\n" +
                "\t\t<script type=\"text/javascript\">wd_paramtracker(\"_wdxid=000000000000000000000000000000000000000000\")</script>\n" +
                "\t<!-- 天润统计 ================ END -->\n" +
                "\t<script>\n" +
                "\t// 动态写入 听云 js\n" +
                "\tvar random = Math.random() * 10000;\n" +
                "\tconsole.log('ramdom = '+random);\n" +
                "\tif(random < 10 ){\n" +
                "\t\tdocument.write(unescape(\"%3Cscript src='http://m.gmw.cn/baijia/js/gmw-ty-b.js' type='text/javascript'%3E%3C/script%3E\"));                    \n" +
                "\t}\n" +
                "</script>\n" +
                "</body>\n" +
                "</html>\n";
    }
}

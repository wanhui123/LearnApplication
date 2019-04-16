package com.wh.learnapplication.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;

import com.socks.library.KLog;
import com.wh.learnapplication.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RxJava2Activity extends AppCompatActivity {

    private static final String TAG = RxJava2Activity.class.getSimpleName();
    @BindView(R.id.btn_1)
    AppCompatButton btn1;
    @BindView(R.id.btn_2)
    AppCompatButton btn2;
    @BindView(R.id.btn_3)
    AppCompatButton btn3;
    @BindView(R.id.btn_4)
    AppCompatButton btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java2);
        ButterKnife.bind(this);
    }


    @OnClick({R.id.btn_1, R.id.btn_2, R.id.btn_3, R.id.btn_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_1:
                test1();//基本使用
                break;
            case R.id.btn_2:
                test2();//切换线程
                break;
            case R.id.btn_3:
                test3();
                break;
            case R.id.btn_4:
                break;
        }
    }
    private Disposable mDisposable;
    private void test3() {
        mDisposable=Flowable.interval(1,TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "accept: doOnNext : "+aLong );
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.e(TAG, "accept: 设置文本 ："+aLong );
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDisposable.dispose();
    }

    @SuppressLint("CheckResult")
    private void test2() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                Log.e(TAG, "Observable thread is : " + Thread.currentThread().getName());
                emitter.onNext(1);
//                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())//发射事件的线程
                .subscribeOn(Schedulers.io())//多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
                .observeOn(AndroidSchedulers.mainThread())//订阅者接收事件的线程      但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "After observeOn(mainThread)，Current thread is " + Thread.currentThread().getName());
                    }
                })
                .observeOn(Schedulers.io())//订阅者接收事件的线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.e(TAG, "After observeOn(io)，Current thread is " + Thread.currentThread().getName());
                    }
                });
    }

    private void test1() {
//        Observable<Integer> aaa = Observable.create(new ObservableOnSubscribe<Integer>() {// 第一步：初始化Observable
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                KLog.d("Observable emit 1" + "\n");
//                emitter.onNext(1);
//                KLog.d("Observable emit 2" + "\n");
//                emitter.onNext(2);
//                KLog.d("Observable emit 3" + "\n");
//                emitter.onNext(3);
//                emitter.onComplete();
//                KLog.d("Observable emit 4" + "\n");
//                emitter.onNext(4);
//            }
//        });
//
//        Observer<Integer> bbb = new Observer<Integer>() {
//            // 第二步：初始化Observer
//            private Disposable disposable;
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                disposable = d;
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                KLog.d("onNext : integer : " + integer + "\n");
//                if (integer==2){
//                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
//                    disposable.dispose();
//                }
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                KLog.d("onError : value : " + e.getMessage() + "\n");
//            }
//
//            @Override
//            public void onComplete() {
//                KLog.d("onComplete" + "\n");
//            }
//        };
//
//        aaa.subscribe(bbb);

        Observable.create(new ObservableOnSubscribe<Integer>() {// 第一步：初始化Observable
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                KLog.d("Observable emit 1" + "\n");
                emitter.onNext(1);
                KLog.d("Observable emit 2" + "\n");
                emitter.onNext(2);
                KLog.d("Observable emit 3" + "\n");
                emitter.onNext(3);
                emitter.onComplete();
                KLog.d("Observable emit 4" + "\n");
                emitter.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            // 第二步：初始化Observer
            private Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                KLog.d("onNext : integer : " + integer + "\n");
//                if (integer==2){
//                    // 在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
//                    disposable.dispose();
//                }
            }

            @Override
            public void onError(Throwable e) {
                KLog.d("onError : value : " + e.getMessage() + "\n");
            }

            @Override
            public void onComplete() {
                KLog.d("onComplete" + "\n");
            }
        });
    }
}

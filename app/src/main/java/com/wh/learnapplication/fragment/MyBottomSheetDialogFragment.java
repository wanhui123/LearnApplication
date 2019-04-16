package com.wh.learnapplication.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wh.learnapplication.R;

import java.lang.reflect.Field;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_answer_analysis, container, false);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        TextView tv_content = view.findViewById(R.id.tv_content);
        TextView tv_reference = view.findViewById(R.id.tv_reference);
        tv_content.setText("测试新时期脱贫攻坚的目标，集中到一点，就是到2020年实现“两个确保”：确保农村贫困人口实现脱贫，确保贫困县全部脱贫摘帽。实现这一目标，意味着我国要比世界银行确定的在全球消除绝对贫困现象的时间提前十年。");
        tv_reference.setText("-----习近平总书记2015年11月27日在中央扶贫开发工作会议上的讲话测试");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MyDialog(getContext(), getTheme());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //去掉父布局的背景
        View view = getView();
        if (view != null) {
            View parent = (View) view.getParent();
            if (parent != null) {
                parent.setBackgroundColor(Color.TRANSPARENT);
            }
        }
    }

    private class MyDialog extends BottomSheetDialog {
        MyDialog(@NonNull Context context, int theme) {
            super(context, theme);
        }

        @Override
        public void cancel() {
            BottomSheetBehavior<?> behavior = getBehavior();
            if (behavior == null || behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
                super.cancel();
            } else {
                behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
            }
        }

        @Override
        protected void onStart() {
            setup();
            super.onStart();
            final BottomSheetBehavior<?> behavior = getBehavior();
            if (behavior != null) {
                // 延迟300ms，等动画结束在执行菜单弹出动画。
                getView().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    }
                }, 300);
            }
        }

        private void setup() {
            BottomSheetBehavior<?> behavior = getBehavior();
            if (behavior == null) {
                return;
            }
            behavior.setSkipCollapsed(true);
            behavior.setPeekHeight(0);
        }

        private BottomSheetBehavior<?> getBehavior() {
            try {
                Field field = BottomSheetDialog.class.getDeclaredField("mBehavior");
                if (field == null) {
                    return null;
                }
                field.setAccessible(true);
                return (BottomSheetBehavior<?>) field.get(this);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

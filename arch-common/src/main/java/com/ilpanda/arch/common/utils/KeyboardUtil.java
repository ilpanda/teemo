package com.ilpanda.arch.common.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;


import java.util.ArrayList;

public class KeyboardUtil {

    private static final String TAG = KeyboardUtil.class.getSimpleName();

    /**
     * 显示软键盘的延迟时间
     */
    public static final int SHOW_KEYBOARD_DELAY_TIME = 200;
    public final static int KEYBOARD_VISIBLE_THRESHOLD_DP = 100;


    public static void showKeyboard(final EditText editText, boolean delay) {
        showKeyboard(editText, delay ? SHOW_KEYBOARD_DELAY_TIME : 0);
    }


    /**
     * 针对给定的editText显示软键盘（editText会先获得焦点）. 可以和{@link #hideKeyboard(View)}
     * 搭配使用，进行键盘的显示隐藏控制。
     */

    public static void showKeyboard(final EditText editText, int delay) {
        if (null == editText)
            return;

        if (!editText.requestFocus()) {
            Log.w(TAG, "showSoftInput() can not get focus");
            return;
        }
        if (delay > 0) {
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) editText.getContext().getApplicationContext()
                            .getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                }
            }, delay);
        } else {
            InputMethodManager imm = (InputMethodManager) editText.getContext().getApplicationContext()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    /**
     * 隐藏软键盘 可以和{@link #showKeyboard(EditText, boolean)}搭配使用，进行键盘的显示隐藏控制。
     *
     * @param view 当前页面上任意一个可用的view
     */
    public static boolean hideKeyboard(final View view) {
        if (null == view)
            return false;

        InputMethodManager inputManager = (InputMethodManager) view.getContext().getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        // 即使当前焦点不在editText，也是可以隐藏的。
        return inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * Set keyboard visibility change event listener.
     *
     * @param activity Activity
     * @param listener KeyboardVisibilityEventListener
     */
    @SuppressWarnings("deprecation")
    public static void setVisibilityEventListener(final Activity activity,
                                                  final KeyboardVisibilityEventListener listener) {

        if (activity == null) {
            throw new NullPointerException("Parameter:activity must not be null");
        }

        if (listener == null) {
            throw new NullPointerException("Parameter:listener must not be null");
        }

        final View activityRoot = ((ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT)).getChildAt(0);

        final ViewTreeObserver.OnGlobalLayoutListener layoutListener =
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    private final Rect r = new Rect();

                    private final int visibleThreshold = Math.round(
                            ScreenUtil.dipToPx(activity, KEYBOARD_VISIBLE_THRESHOLD_DP));

                    private boolean wasOpened = false;

                    @Override
                    public void onGlobalLayout() {
                        activityRoot.getWindowVisibleDisplayFrame(r);

                        int heightDiff = activityRoot.getRootView().getHeight() - r.height();

                        boolean isOpen = heightDiff > visibleThreshold;

                        if (isOpen == wasOpened) {
                            // keyboard state has not changed
                            return;
                        }

                        wasOpened = isOpen;

                        boolean removeListener = listener.onVisibilityChanged(isOpen, heightDiff);
                        if (removeListener) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                activityRoot.getViewTreeObserver()
                                        .removeOnGlobalLayoutListener(this);
                            } else {
                                activityRoot.getViewTreeObserver()
                                        .removeGlobalOnLayoutListener(this);
                            }
                        }
                    }
                };
        activityRoot.getViewTreeObserver().addOnGlobalLayoutListener(layoutListener);
        activity.getApplication()
                .registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallback(activity) {
                    @Override
                    protected void onTargetActivityDestroyed() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                            activityRoot.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(layoutListener);
                        } else {
                            activityRoot.getViewTreeObserver()
                                    .removeGlobalOnLayoutListener(layoutListener);
                        }
                    }
                });
    }

    /**
     * Determine if keyboard is visible
     *
     * @param activity Activity
     * @return Whether keyboard is visible or not
     */
    public static boolean isKeyboardVisible(Activity activity) {
        Rect r = new Rect();

        View activityRoot = ((ViewGroup) activity.findViewById(Window.ID_ANDROID_CONTENT)).getChildAt(0);
        int visibleThreshold =
                Math.round(ScreenUtil.dipToPx(activity, KEYBOARD_VISIBLE_THRESHOLD_DP));

        activityRoot.getWindowVisibleDisplayFrame(r);

        int heightDiff = activityRoot.getRootView().getHeight() - r.height();

        return heightDiff > visibleThreshold;
    }


    public interface KeyboardVisibilityEventListener {

        /**
         * @return to remove global listener or not
         */
        boolean onVisibilityChanged(boolean isOpen, int heightDiff);
    }

    /**
     * 适用于 Activity 的 windowSoftMode 为 adjustNothing 的场景
     * 正常情况下,我们希望键盘在弹出时, EditText 正好在键盘上方.
     * 然而有时,我们希望键盘在弹出时, EditText 下方的 View 也在键盘上方, 例如 EditText 的登录按钮.
     *
     * @param activity            当前的 Activity 页面
     * @param view                你希望移动的布局,有时候我们不希望标题栏向上移动,我们可以传入与标题栏同级 View 的子 View,这样上移的时候,不会覆盖
     * @param paddingOffset       View 的偏移
     * @param keyboardHeight      键盘高度
     * @param navigationBarHeight 状态栏占据的高度
     */
    public static void moveUpToKeyboard(Activity activity, View view, int paddingOffset, int keyboardHeight, int navigationBarHeight) {

        FrameLayout content = (FrameLayout) activity.findViewById(android.R.id.content);
        View moveView = view == null ? content : view;
        if (keyboardHeight == 0) {
            moveView.animate().translationY(0)
                    .setDuration(200)
                    .setInterpolator(new OvershootInterpolator(0))
                    .start();
            return;
        }

//        final int movePadding = ScreenUtil.dipToPx(activity, 100);
        final int movePadding = paddingOffset;

        //找到所有输入框
        ArrayList<EditText> allEts = new ArrayList<>();
        findAllEditText(allEts, content);

        EditText focusEt = null;
        // 找到焦点 EditText
        for (EditText et : allEts) {
            if (et.isFocused()) {
                focusEt = et;
                break;
            }
        }

        if (focusEt == null) {
            return;
        }

        // dy是整个屏幕需要上移的高度    需求y1 = 软键盘高度+EditText在屏幕的高度+阈值(键盘距离目标EditText保持的位置)

        int dy = 0;

        int windowHeight = ScreenUtil.getScreenHeight(activity);
        int focusEtTop = 0;
        int focusBottom = 0;

        int[] locations = new int[2];
        // 找到焦点EditText的屏幕位置
        focusEt.getLocationOnScreen(locations);
        focusEtTop = locations[1];
        focusBottom = focusEtTop + focusEt.getMeasuredHeight();

        // 被遮挡的距离(应该向上移动的距离) = focusBottom的值 + keyboardHeight（监听键盘变化的值）- 屏幕的高度
        int overflowHeight = (focusBottom + keyboardHeight) - windowHeight - navigationBarHeight;
        dy = movePadding + overflowHeight;

        if (dy < 0) {
            return;
        }

        // 最后计算出来动画的偏移高度 这里需要加一个判断如果 EditText 特别的高,会因为我们的动画上移屏幕导致光标看不到了.
        // 解决方案就是把上移动画 高度最后再修正一下 根据 EditText 的 Top 来计算
//        int etMinHeight = focusEtTop - getNavBarHeight(activity);//上移的最低距离
//        dy = Math.min(etMinHeight, dy);

        moveView.animate().translationY(-dy)
                .setDuration(200)
                .setInterpolator(new OvershootInterpolator(0))
                .start();
    }

    public static void findAllEditText(ArrayList<EditText> list, ViewGroup group) {
        for (int i = 0; i < group.getChildCount(); i++) {
            View v = group.getChildAt(i);
            if (v instanceof EditText && v.getVisibility() == View.VISIBLE) {
                list.add((EditText) v);
            } else if (v instanceof ViewGroup) {
                findAllEditText(list, (ViewGroup) v);
            }
        }
    }


}

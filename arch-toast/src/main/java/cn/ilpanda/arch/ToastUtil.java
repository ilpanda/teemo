package cn.ilpanda.arch;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This library from https://github.com/GrenderG/Toasty
 */
public class ToastUtil {

    private ToastUtil() {

    }

    private static Toast mLastToast;

    static Drawable tint9PatchDrawableFrame(Context context, int tintColor) {
        final NinePatchDrawable toastDrawable = (NinePatchDrawable) getDrawable(context, R.drawable.toast_frame);
        toastDrawable.setColorFilter(new PorterDuffColorFilter(tintColor, PorterDuff.Mode.SRC_IN));
        return toastDrawable;
    }

    static void setBackground(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            view.setBackground(drawable);
        else
            view.setBackgroundDrawable(drawable);
    }

    static Drawable getDrawable(Context context, int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            return context.getDrawable(id);
        else
            return context.getResources().getDrawable(id);
    }


    private static final int DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF");

    private static final int ERROR_COLOR = Color.parseColor("#D50000");

    private static final int INFO_COLOR = Color.parseColor("#3F51B5");

    private static final int SUCCESS_COLOR = Color.parseColor("#388E3C");

    private static final int WARNING_COLOR = Color.parseColor("#FFA900");

    private static final String TOAST_TYPEFACE = "sans-serif-condensed";

    public static void showSuccess(Context context, CharSequence message) {
        normal(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showError(Context context, CharSequence message) {
        error(context, message, Toast.LENGTH_LONG).show();
    }

    public static void showWarning(Context context, CharSequence message) {
        warning(context, message, Toast.LENGTH_LONG).show();
    }

    private static Toast normal(Context context, CharSequence message) {
        return normal(context, message, Toast.LENGTH_SHORT, null, false);
    }

    private static Toast normal(Context context, CharSequence message, Drawable icon) {
        return normal(context, message, Toast.LENGTH_SHORT, icon, true);
    }

    private static Toast normal(Context context, CharSequence message, int duration) {
        return normal(context, message, duration, null, false);
    }

    private static Toast normal(Context context, CharSequence message, int duration,
                                Drawable icon) {
        return normal(context, message, duration, icon, true);
    }

    private static Toast normal(Context context, CharSequence message, int duration,
                                Drawable icon, boolean withIcon) {
        return custom(context, message, icon, DEFAULT_TEXT_COLOR, duration, withIcon);
    }

    private static Toast warning(Context context, CharSequence message) {
        return warning(context, message, Toast.LENGTH_SHORT, true);
    }

    private static Toast warning(Context context, CharSequence message, int duration) {
        return warning(context, message, duration, true);
    }

    private static Toast warning(Context context, CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(context, R.drawable.ic_error_outline_white_48dp),
                DEFAULT_TEXT_COLOR, WARNING_COLOR, duration, withIcon, true);
    }

    private static Toast info(Context context, CharSequence message) {
        return info(context, message, Toast.LENGTH_SHORT, true);
    }

    private static Toast info(Context context, CharSequence message, int duration) {
        return info(context, message, duration, true);
    }

    private static Toast info(Context context, CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(context, R.drawable.ic_info_outline_white_48dp),
                DEFAULT_TEXT_COLOR, INFO_COLOR, duration, withIcon, true);
    }

    private static Toast success(Context context, CharSequence message) {
        return success(context, message, Toast.LENGTH_SHORT, true);
    }

    private static Toast success(Context context, CharSequence message, int duration) {
        return success(context, message, duration, true);
    }

    private static Toast success(Context context, CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(context, R.drawable.ic_check_white_48dp),
                DEFAULT_TEXT_COLOR, SUCCESS_COLOR, duration, withIcon, true);
    }

    private static Toast error(Context context, CharSequence message) {
        return error(context, message, Toast.LENGTH_SHORT, true);
    }

    private static Toast error(Context context, CharSequence message, int duration) {
        return error(context, message, duration, true);
    }

    private static Toast error(Context context, CharSequence message, int duration, boolean withIcon) {
        return custom(context, message, getDrawable(context, R.drawable.ic_clear_white_48dp),
                DEFAULT_TEXT_COLOR, ERROR_COLOR, duration, withIcon, true);
    }

    private static Toast custom(Context context, CharSequence message, Drawable icon,
                                int textColor, int duration, boolean withIcon) {
        return custom(context, message, icon, textColor, -1, duration, withIcon, false);
    }

    private static Toast custom(Context context, CharSequence message, int iconRes, int textColor, int tintColor, int duration,
                                boolean withIcon, boolean shouldTint) {
        return custom(context, message, getDrawable(context, iconRes), textColor,
                tintColor, duration, withIcon, shouldTint);
    }

    private static Toast custom(Context context, CharSequence message, Drawable icon,
                                int textColor, int tintColor, int duration,
                                boolean withIcon, boolean shouldTint) {

        if (context == null) return null;

        context = context.getApplicationContext();

        Toast toast = new Toast(context.getApplicationContext());

        if (mLastToast != null) {
            mLastToast.cancel();
        }
        mLastToast = toast;

        final View toastLayout = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.toast_layout, null);
        final ImageView toastIcon = (ImageView) toastLayout.findViewById(R.id.toast_icon);
        final TextView toastTextView = (TextView) toastLayout.findViewById(R.id.toast_text);
        Drawable drawableFrame;

        if (shouldTint)
            drawableFrame = tint9PatchDrawableFrame(context, tintColor);
        else
            drawableFrame = getDrawable(context, R.drawable.toast_frame);
        setBackground(toastLayout, drawableFrame);

        if (withIcon && icon != null) {
            setBackground(toastIcon, icon);
        } else
            toastIcon.setVisibility(View.GONE);

        toast.setGravity(Gravity.CENTER, 0, 0);
        toastTextView.setTextColor(textColor);
        toastTextView.setText(TextUtils.isEmpty(message) ? "网络错误" : message);

        toast.setView(toastLayout);
        toast.setDuration(duration);
        return toast;
    }


}

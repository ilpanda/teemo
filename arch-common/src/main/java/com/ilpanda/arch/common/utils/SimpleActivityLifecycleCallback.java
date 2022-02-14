package com.ilpanda.arch.common.utils;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

public abstract class SimpleActivityLifecycleCallback implements Application.ActivityLifecycleCallbacks {

    private final Activity mTargetActivity;

    public SimpleActivityLifecycleCallback(Activity mTargetActivity) {
        this.mTargetActivity = mTargetActivity;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity == mTargetActivity) {
            mTargetActivity.getApplication().unregisterActivityLifecycleCallbacks(this);
            onTargetActivityDestroyed();
        }
    }

    protected abstract void onTargetActivityDestroyed();

}

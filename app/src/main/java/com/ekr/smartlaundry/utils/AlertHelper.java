package com.ekr.smartlaundry.utils;

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.ekr.smartlaundry.R;
import com.shashank.platform.fancyflashbarlib.Flashbar;
import com.shashank.platform.fancyflashbarlib.anim.FlashAnim;

public class AlertHelper {

    public static Flashbar alertSuccess(Activity activity){
        return new Flashbar.Builder(activity)
                .gravity(Flashbar.Gravity.BOTTOM)
                .title("Login Berhasil!")
                .message("Selamat Datang Di SmartLaundry.")
                .enableSwipeToDismiss()         //By default activity feature is disabled
                .icon(R.drawable.success)
                .showIcon()
                .positiveActionText("OK")
                .positiveActionTapListener(Flashbar::dismiss)
                .enterAnimation(FlashAnim.with(activity)
                        .animateBar()
                        .duration(750)
                        .alpha()
                        .overshoot())
                .exitAnimation(FlashAnim.with(activity)
                        .animateBar()
                        .duration(400)
                        .accelerateDecelerate())
                .build();
    }


}

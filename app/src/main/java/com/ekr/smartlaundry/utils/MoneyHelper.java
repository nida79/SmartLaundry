package com.ekr.smartlaundry.utils;

import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Locale;

public class MoneyHelper {
    public static void setRupiah(TextView textView,int nominal){
        Locale localeID = new Locale("in", "ID");
        NumberFormat formatRupiah = NumberFormat.getCurrencyInstance(localeID);
        formatRupiah.setMaximumFractionDigits(0);
        textView.setText(formatRupiah.format((double) nominal));
    }
}

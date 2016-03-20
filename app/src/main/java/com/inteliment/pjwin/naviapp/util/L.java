package com.inteliment.pjwin.naviapp.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by hans on 20-Mar-16.
 */
public class L {
    public static void toastShort(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public static void toastShort(Context context, int messageId) {
        Toast.makeText(context, messageId, Toast.LENGTH_SHORT).show();
    }
}

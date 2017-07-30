package com.example.king.gou.utils;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;

public class FingerPrintUtils {

    private CancellationSignal mCancellationSignal;
    private FingerprintManagerCompat mFingerprintManager;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public FingerPrintUtils(Activity activity) {
        mCancellationSignal = new CancellationSignal();
        mFingerprintManager = FingerprintManagerCompat.from(activity);
    }

    public void setFingerPrintListener(FingerprintManagerCompat.AuthenticationCallback callback) {
        mFingerprintManager.authenticate(null, 0, mCancellationSignal, callback, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void stopsFingerPrintListener() {
        mCancellationSignal.cancel();
        mCancellationSignal = null;
    }
}
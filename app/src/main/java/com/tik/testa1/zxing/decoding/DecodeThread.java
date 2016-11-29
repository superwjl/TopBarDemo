package com.tik.testa1.zxing.decoding;

import android.os.Handler;
import android.os.Looper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.tik.testa1.act.ScanningAct;

import java.util.Hashtable;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

public class DecodeThread extends Thread {

	public static final String BARCODE_BITMAP = "barcode_bitmap";
	
	private ScanningAct mActivity;
	private Hashtable<DecodeHintType, Object> hints;
	private Handler mHandler;
	private CountDownLatch handlerInitLatch;

	public DecodeThread(ScanningAct activity, Vector<BarcodeFormat> decodeFormats, String characterSet) {
		mActivity = activity;
		hints = new Hashtable<DecodeHintType, Object>();
		handlerInitLatch = new CountDownLatch(1);

		if (decodeFormats == null || decodeFormats.isEmpty()) {
			decodeFormats = new Vector<BarcodeFormat>();
			decodeFormats.addAll(DecodeFormatManager.ONE_D_FORMATS);
			decodeFormats.addAll(DecodeFormatManager.QR_CODE_FORMATS);
			decodeFormats.addAll(DecodeFormatManager.DATA_MATRIX_FORMATS);
		}
		hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
		if (characterSet != null) {
			hints.put(DecodeHintType.CHARACTER_SET, characterSet);
		}
	}

	public Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return mHandler;
	}

	@Override
	public void run() {
		Looper.prepare();
		mHandler = new DecodeHandler(mActivity, hints);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}

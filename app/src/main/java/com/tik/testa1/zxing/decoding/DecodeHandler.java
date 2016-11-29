package com.tik.testa1.zxing.decoding;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.tik.testa1.R;
import com.tik.testa1.act.ScanningAct;
import com.tik.testa1.zxing.camera.CameraManager;
import com.tik.testa1.zxing.camera.PlanarYUVLuminanceSource;

import java.util.Hashtable;

public class DecodeHandler extends Handler {

	private ScanningAct mActivity;
	private MultiFormatReader multiFormatReader;

	public DecodeHandler(ScanningAct activity, Hashtable<DecodeHintType, Object> hints) {
		mActivity = activity;
		multiFormatReader = new MultiFormatReader();
		multiFormatReader.setHints(hints);
	}

	@Override
	public void handleMessage(Message msg) {
		switch (msg.what) {
		case R.id.decode:
			decode((byte[]) msg.obj, msg.arg1, msg.arg2);
			break;
		case R.id.quit:
			Looper.myLooper().quit();
			break;
		}
	}

	private void decode(byte[] data, int width, int height) {
		Result rawResult = null;
		byte[] rotatedData = new byte[data.length];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++)
				rotatedData[x * height + height - y - 1] = data[x + y * width];
		}
		int tmp = width; // Here we are swapping, that's the difference to #11
		width = height;
		height = tmp;
		PlanarYUVLuminanceSource source = CameraManager.get().buildLuminanceSource(rotatedData, width, height);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
		try {
			rawResult = multiFormatReader.decodeWithState(bitmap);
		} catch (ReaderException re) {
			source = CameraManager.get().buildLuminanceSource(data, height, width);
			bitmap = new BinaryBitmap(new HybridBinarizer(source));
			try {
				rawResult = multiFormatReader.decodeWithState(bitmap);
			} catch (ReaderException readerException) {}
		} finally {
			multiFormatReader.reset();
		}

		if (rawResult != null) {
			Message message = Message.obtain(mActivity.getHandler(), R.id.decode_succeeded, rawResult.getText());
			message.sendToTarget();
		} else {
			Message message = Message.obtain(mActivity.getHandler(), R.id.decode_failed);
			message.sendToTarget();
		}
	}

}

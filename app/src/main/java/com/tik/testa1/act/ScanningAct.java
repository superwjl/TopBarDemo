package com.tik.testa1.act;

import android.content.DialogInterface;
import android.content.res.AssetFileDescriptor;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.tik.testa1.base.BaseActivity;
import com.tik.testa1.zxing.camera.CameraManager;
import com.tik.testa1.zxing.decoding.CaptureActivityHandler;
import com.tik.testa1.zxing.decoding.InactivityTimer;
import com.tik.testa1.R;

import java.io.IOException;
import java.util.Vector;

import butterknife.BindView;

public class ScanningAct extends BaseActivity implements Callback {

	/** 声音 */
	private static final float BEEP_VOLUME = 0.10f;
	/** 震动持续时间 */
	private static final long VIBRATE_DURATION = 200L;

	/** 扫描Handler */
	private CaptureActivityHandler handler;
	/** 是否有SurfaceView */
	private boolean hasSurface;
	/** 解析Fromat */
	private Vector<BarcodeFormat> decodeFormats;
	/** 文字编码 */
	private String characterSet;

	private InactivityTimer inactivityTimer;
	/** 音频播放器 */
	private MediaPlayer mediaPlayer;
	/** 是否播放 */
	private boolean playBeep;
	/** 震动 */
	private boolean vibrate;
	/** 截取的x坐标 */
	private int x = 0;
	/** 截取的y坐标 */
	private int y = 0;
	/** 截取的区域宽度 */
	private int cropWidth = 0;
	/** 截取的区域高度 */
	private int cropHeight = 0;

	@BindView(R.id.container)
	RelativeLayout captureContainter;

	/** 扫描框布局 */
	@BindView(R.id.scanning_rect)
	RelativeLayout captureCropLayout;

	@BindView(R.id.scanning_line)
	ImageView scanning_line;

	@BindView(R.id.surfaceView)
	SurfaceView surfaceView;

	@BindView(R.id.toobar)
	Toolbar mToolbar;

	@Override
	protected void beforeBindViews() {

	}

	@Override
	protected void afterBindViews() {
		initToolBar();
		initData();
		startAnimation();
	}

	private void initToolBar(){
		mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
		mToolbar.setTitle("扫一扫");
//		mToolbar.setLogo(R.mipmap.ic_launcher);
		mToolbar.setSubtitle("二维码/条形码扫描");
		mToolbar.setTitleTextColor(Color.WHITE);
		mToolbar.setSubtitleTextColor(Color.GREEN);
		setSupportActionBar(mToolbar);
		mToolbar.setNavigationIcon(R.drawable.back);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
			@Override
			public boolean	 onMenuItemClick(MenuItem item) {
				switch (item.getItemId()){
					case R.id.action_share:
						Toast.makeText(ScanningAct.this, "share", Toast.LENGTH_SHORT).show();
						break;
					case R.id.action_info:
						Toast.makeText(ScanningAct.this, "info", Toast.LENGTH_SHORT).show();
						break;
					case R.id.action_setting:
						Toast.makeText(ScanningAct.this, "setting", Toast.LENGTH_SHORT).show();
						break;
				}
				return true;
			}
		});
	}

	@Override
	protected int getLayoutId() {
		return R.layout.activity_scanning;
	}


	protected void initData() {
		// 初始化 CameraManager
		CameraManager.init(getApplicationContext());
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		Log.e("", "initData --> hasSurface = "+hasSurface);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		Log.e("", "onWindowFocusChanged --> hasSurface = "+hasSurface);
		super.onWindowFocusChanged(hasFocus);
		int[] location = new int[2];
		captureCropLayout.getLocationOnScreen(location);
		Rect rect = new Rect();
		rect.left = location[0];
		rect.right = rect.left + captureCropLayout.getWidth();
		rect.top = location[1];
		rect.bottom = rect.top + captureCropLayout.getHeight();
		CameraManager.get().setFramingRect(rect);
	}

	/**
	 * 设置扫描条动画
	 **/
	private void startAnimation() {
		TranslateAnimation mAnimation = new TranslateAnimation(TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.ABSOLUTE, 0f, TranslateAnimation.RELATIVE_TO_PARENT, 0f,
				TranslateAnimation.RELATIVE_TO_PARENT, 0.9f);
		mAnimation.setDuration(1500);
		mAnimation.setRepeatCount(-1);
		mAnimation.setRepeatMode(Animation.REVERSE);
		mAnimation.setInterpolator(new LinearInterpolator());
		scanning_line.setAnimation(mAnimation);
	}


	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			Log.e("", "onResume ---> hasSurface = true");
			initCamera(surfaceHolder);// 初始化SurfaceView时，直接打开Camera
		} else {
			surfaceHolder.addCallback(this);// 未初始化SurfaceView时，通过回调打开Camera
		}
		decodeFormats = null;
		characterSet = null;
		playBeep = true;// 播放声音
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {// 铃声类型不为标准时，不播放声音
			playBeep = false;
		}
		initBeepSound();// 初始化播放声音
		vibrate = true;// 震动
	}

	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);// 打开Camera
//			Point point = CameraManager.get().getCameraResolution();// 预览图
//
//			// 预览图的宽度，也即camera的分辨率宽度
//			int width = point.y;
//			// 预览图的高度，也即camera的分辨率高度
//			int height = point.x;
//
//			// 获取预览图中二维码图片的左上顶点x坐标
//			int x = captureCropLayout.getLeft() * width / captureContainter.getWidth();
//			// 预览图中二维码图片的左上顶点y坐标
//			int y = captureCropLayout.getTop() * height / captureContainter.getHeight();
//
//			// 获取预览图中二维码图片的宽度
//			int cropWidth = captureCropLayout.getWidth() * width / captureContainter.getWidth();
//			// 预览图中二维码图片的高度
//			int cropHeight = captureCropLayout.getHeight() * height / captureContainter.getHeight();
//
//			// 设置
//			setX(x);
//			setY(y);
//			setCropWidth(cropWidth);
//			setCropHeight(cropHeight);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			// 新建Handler
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	/**
	 * 初始化播放声音
	 */
	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);
			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				// 设置声音文件
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				// 左右声道声音大小
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				// 准备播放
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);// 复位
		}
	};

	/**
	 * 扫面完成后播放声音及震动
	 */
	private void playBeepSoundAndVibrate() {
		// 播放声音
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			// 震动手机
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	@Override
	protected void onPause() {
		// 画面暂停时
		super.onPause();
		// 取消Handler
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		// 关闭Camera
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		// 画面结束时停止InactivityTimer
		inactivityTimer.shutdown();
		super.onDestroy();
	}

	public Handler getHandler() {
		return handler;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCropWidth() {
		return cropWidth;
	}

	public void setCropWidth(int cropWidth) {
		this.cropWidth = cropWidth;
	}

	public int getCropHeight() {
		return cropHeight;
	}

	public void setCropHeight(int cropHeight) {
		this.cropHeight = cropHeight;
	}

	public void handleDecode(String result) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();// 播放声音及震动
		new AlertDialog.Builder(this).setMessage(result).setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				handler.restartPreviewAndDecode();
			}
		}).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_scanning, menu);
		return true;
	}
}

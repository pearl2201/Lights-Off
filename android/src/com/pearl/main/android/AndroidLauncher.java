package com.pearl.main.android;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.pearl.main.Pearl;
import com.pearl.main.game.IActivityRequestHandler;

public class AndroidLauncher extends AndroidApplication implements IActivityRequestHandler {

	private static final String AD_UNIT_ID = "ca-app-pub-5996733189368817/3367534883";
	private static final String GOOGLE_PLAY_URL = "http://play.google.com/store/apps/details?id=com.pearl.main.android";

	protected AdView adView;
	protected View gameView;
	protected Pearl game;
	private boolean isIntenetConnection;

	protected Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (isIntenetConnection) {
				switch (msg.what) {
				case SHOW_ADS_BANNER: {
					adView.setVisibility(View.VISIBLE);
					startAdvertising(adView);
					break;
				}
				case HIDE_ADS_BANNER: {
					adView.setVisibility(View.GONE);
					break;
				}
				}
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();

		cfg.useAccelerometer = false;
		cfg.useCompass = false;

		// Do the stuff that initialize() would do for you
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

		RelativeLayout layout = new RelativeLayout(this);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		layout.setLayoutParams(params);

		AdView admobView = createAdView();

		adView.setVisibility(View.GONE);
		View gameView = createGameView(cfg);
		
		layout.addView(gameView);

		layout.addView(admobView);

		isIntenetConnection = isOnline();
		setContentView(layout);
	}

	private boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	private AdView createAdView() {
		adView = new AdView(this);
		adView.setAdSize(AdSize.SMART_BANNER);
		adView.setAdUnitId(AD_UNIT_ID);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		adView.setLayoutParams(params);
		adView.setBackgroundColor(Color.BLACK);
		return adView;
	}

	private View createGameView(AndroidApplicationConfiguration cfg) {
		game = new Pearl(this);
		gameView = initializeForView(game, cfg);
		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
		params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
		// params.addRule(RelativeLayout.BELOW, adView.getId());
		gameView.setLayoutParams(params);
		return gameView;
	}

	private void startAdvertising(AdView adView) {

		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
	}

	@Override
	public void onResume() {
		super.onResume();
		if (adView != null)
			adView.resume();
	}

	@Override
	public void onPause() {
		if (adView != null)
			adView.pause();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		if (adView != null)
			adView.destroy();
		super.onDestroy();
	}

	@Override
	public void onBackPressed() {

		if (game.isLoadingAssetComplete()) {
			if (game != null)
				game.pause();
			final Dialog dialog = new Dialog(this);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

			LinearLayout ll = new LinearLayout(this);
			ll.setOrientation(LinearLayout.VERTICAL);

			Button b0 = new Button(this);
			b0.setText("Resume");
			b0.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					game.resume();
					dialog.dismiss();
				}
			});
			ll.addView(b0);

			Button b1 = new Button(this);
			b1.setText("Quit");
			b1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
			ll.addView(b1);

			Button b2 = new Button(this);
			b2.setText("Rate My Game");
			b2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_URL)));
					dialog.dismiss();
				}
			});
			ll.addView(b2);

			dialog.setContentView(ll);
			dialog.show();
		}
	}

	@Override
	public void showAds(boolean show) {
		// TODO Auto-generated method stub
		if (show) {
			handler.sendEmptyMessage(SHOW_ADS_BANNER);
		} else {
			handler.sendEmptyMessage(HIDE_ADS_BANNER);
		}
	}

	@Override
	public void showRateApp() {
		// TODO Auto-generated method stub
		startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(GOOGLE_PLAY_URL)));
	}
}
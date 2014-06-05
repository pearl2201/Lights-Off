package com.pearl.main.game;

public interface IActivityRequestHandler {
	public final int SHOW_ADS_BANNER = 1;
	public final int HIDE_ADS_BANNER = 0;
	
	public void showAds(boolean show);
	
	public void showRateApp();
	

}

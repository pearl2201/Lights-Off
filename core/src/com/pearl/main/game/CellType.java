package com.pearl.main.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum CellType {
	SMALL(Assets.instance.light7, Assets.instance.dark7, Assets.instance.solve7, 6), MEDIUM(Assets.instance.light5, Assets.instance.dark5,
			Assets.instance.solve5,100 ),
			LARGE(Assets.instance.light4, Assets.instance.dark4, Assets.instance.solve4, 100);

	private int size;
	private TextureRegion light;
	private TextureRegion dark;
	private TextureRegion solve;
	private int maxSolutionLength;

	CellType(TextureRegion light, TextureRegion dark, TextureRegion solve, int maxSolutionLength) {
		this.light = light;
		this.dark = dark;
		this.size = light.getRegionHeight();
		this.solve = solve;
		this.maxSolutionLength = maxSolutionLength;
	}

	public int getMaxSolutionLength() {
		return maxSolutionLength;
	}

	public TextureRegion getSolve() {
		return solve;
	}

	public int getSize()

	{
		return size;
	}

	public TextureRegion getLightTextureRegion() {
		return light;
	}

	public TextureRegion getDarkTextureRegion() {
		return dark;
	}

	
}

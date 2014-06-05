package com.pearl.main.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.pearl.main.game.CellType;

public class ScreenBoardCoorConvert {

	public static Vector2 convertScreenToBoardCoor(int screenX, int screenY, CellType type) {
		Vector2 board = new Vector2();
		board.x = Math.floorDiv((630 - screenY), type.getSize()) - 1;
		board.y = Math.floorDiv((screenX - 17), type.getSize());
		System.out.println("board " + board.x + " " + board.y);
		return board;

	}

	public static Vector2 convertBoardToScreen(int x, int y, CellType type) {
		Vector2 screen = new Vector2();

		screen.x = 17 + y * type.getSize();
		screen.y = 630 - (x + 1) * type.getSize();
		return screen;
	}

	public static Vector2 convertBoardAToBoardCoor(int screenX, int screenY, CellType type, int n) {
		System.out.println("screenX " + screenX + " screenY " + screenY);
		Vector2 board = new Vector2();
		board.x = MathUtils.floor((n * type.getSize() - screenY) / type.getSize());
		board.y = MathUtils.floor((screenX) / type.getSize());
		return board;

	}

}

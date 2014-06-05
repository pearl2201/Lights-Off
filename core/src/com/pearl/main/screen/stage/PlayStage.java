package com.pearl.main.screen.stage;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.pearl.main.Pearl;
import com.pearl.main.game.Assets;
import com.pearl.main.game.Cell;
import com.pearl.main.game.CellType;
import com.pearl.main.game.LevelGeneration;
import com.pearl.main.screen.MenuScreen;
import com.pearl.main.screen.stage.world.WinWorld;
import com.pearl.main.screen.stage.world.helper.SimpleAbstractActor;
import com.pearl.main.utils.Constants;
import com.pearl.main.utils.Prefs;
import com.pearl.main.utils.ScreenBoardCoorConvert;

public class PlayStage extends Stage {

	public enum GameState {
		Playing, Solving, Wining
	}

	private boolean[][] lightBoard;
	private boolean[][] solutionBoard;
	private boolean[] stepBoard;
	private int stepCount;
	private Cell[][] cellBoard;
	private Pearl game;
	private LevelGeneration levelGeneration;
	private int n;
	private CellType type;
	private SpriteBatch batch;
	private Actor boardA;
	private GameState gameState;
	private int level;
	private WinWorld winWorld;
	private Vector2 lastSolve;

	public PlayStage(Pearl game, int n) {
		// TODO Auto-generated constructor stub

		super(new StretchViewport(Constants.VIEWPORT_WIDTH, Constants.VIEWPORT_HEIGHT));
		batch = (SpriteBatch) getSpriteBatch();
		this.game = game;
		this.n = n;
		if (n == 4)
			type = CellType.LARGE;
		else if (n == 5)
			type = CellType.MEDIUM;
		else
			type = CellType.SMALL;
		level = Prefs.instance.levels.get(n);
		lightBoard = new boolean[n][n];
		cellBoard = new Cell[n][n];
		lastSolve = new Vector2(0, 0);
		levelGeneration = new LevelGeneration();
		levelGeneration.init(n);
		stepBoard = new boolean[n * n];
		stepCount = 0;
		Gdx.input.setInputProcessor(this);
		gameState = GameState.Playing;

		createStageBoard();
		loadLevel(level);
		addStageActor();

		addEventListenerForBoard();

	}

	private void addStageActor() {
		// TODO Auto-generated method stub
		SimpleAbstractActor reset = new SimpleAbstractActor(Assets.instance.reset, 280, 90);
		addActor(reset);
		reset.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				super.clicked(event, x, y);
				Assets.instance.buttonS.play();
				if (gameState != GameState.Wining) {
					resetBoard();
				}
			}
		});

		SimpleAbstractActor solve = new SimpleAbstractActor(Assets.instance.solve, 280, 20);
		addActor(solve);
		solve.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				if (gameState == GameState.Playing) {
					super.clicked(event, x, y);
					Assets.instance.buttonS.play();
					gameState = GameState.Solving;
					doSolve();
				}
			}
		});

		SimpleAbstractActor arrowR = new SimpleAbstractActor(Assets.instance.arrowRight, 208, 50);
		addActor(arrowR);
		arrowR.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				if (gameState != GameState.Wining) {
					Assets.instance.buttonS.play();
					nextLevel();
				}
				super.clicked(event, x, y);
			}
		});

		SimpleAbstractActor arrowL = new SimpleAbstractActor(Assets.instance.arrowLeft, 13, 50);
		addActor(arrowL);
		arrowL.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				if (gameState != GameState.Wining) {
					Assets.instance.buttonS.play();
					if (level > 1)
						level -= 1;
					else
						level = 1;
					loadLevel(level);
				}

				super.clicked(event, x, y);
			}
		});

		SimpleAbstractActor menu = new SimpleAbstractActor(Assets.instance.menuB, 207, 650);
		addActor(menu);
		menu.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				if (gameState != GameState.Wining){
					Assets.instance.buttonS.play();
					game.setScreen(new MenuScreen(game));}
			}
		});

		SimpleAbstractActor exit = new SimpleAbstractActor(Assets.instance.exitL, 347, 650);
		addActor(exit);
		exit.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				Assets.instance.buttonS.play();
				Gdx.app.exit();
			}
		});

	}

	public void doSolve() {
		// TODO Auto-generated method stub
		if (gameState == GameState.Solving) {

			boolean found = false;
			for (int i = 0; i < n && !found; i++) {
				for (int j = 0; j < n && !found; j++) {
					if (stepBoard[i * n + j]) {
						found = true;
						lastSolve.set(i, j);
						cellBoard[i][j].setSolve(true);

					}
				}

			}
			for (int k = 0; k < n * n; k++)
				System.out.print(" " + stepBoard[k]);
		}

	}

	private void addEventListenerForBoard() {
		// TODO Auto-generated method stub
		boardA = new Actor();
		boardA.setBounds(17, 630 - n * type.getSize(), n * type.getSize(), n * type.getSize());
		addActor(boardA);

		boardA.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// TODO Auto-generated method stub
				if (!(gameState == GameState.Wining)) {
					Assets.instance.cellS.play();
					Vector2 pos = ScreenBoardCoorConvert.convertBoardAToBoardCoor((int) x, (int) y, type, n);
					int i = (int) pos.x;
					int j = (int) pos.y;
					stepCount++;
					stepBoard[i * n + j] = stepBoard[i * n + j] ? false : true;
					toggleButton(i, j);
					if (gameState == GameState.Solving) {
						cellBoard[(int) lastSolve.x][(int) lastSolve.y].setSolve(false);
						gameState = GameState.Playing;
					}
					checkWin();

					super.clicked(event, x, y);
				}
			}
		});
	}

	private void checkWin() {
		boolean win = true;
		for (int i = 0; i < n && win; i++)
			for (int j = 0; j < n && win; j++) {
				if (lightBoard[i][j])
					win = false;
			}
		if (win) {
			// call win game
			Assets.instance.winS.play();
			System.out.println("add Win world");
			winWorld = new WinWorld(this);
			addActor(winWorld);
			gameState = GameState.Wining;
			Prefs.instance.save(n, level);
		}
	}

	public void loadLevel(int level) {
		// TODO Auto-generated method stub

		int minimal_solution_length = (int) MathUtils.floor((float) (2 * Math.log(level) + 1));
		if (type == CellType.MEDIUM)
			minimal_solution_length = (int) MathUtils.floor((float) (3 * Math.log(level) + 1));
		else if (type == CellType.SMALL)
			minimal_solution_length = (int) MathUtils.floor((float) (10 * Math.log(level) + 1));
		System.out.println("minimal" + minimal_solution_length);

		solutionBoard = levelGeneration.createSolution(minimal_solution_length);
		System.out.println("==========================================");
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(solutionBoard[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println("==========================================");
		resetBoard();

	}

	private void createStageBoard() {
		// TODO Auto-generated method stub
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				cellBoard[i][j] = new Cell(i, j, false, type);
				addActor(cellBoard[i][j]);
			}
	}

	public void toggleLightFromSolution() {

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				if (solutionBoard[i][j]) {
					toggleButton(i, j);
				}
			}

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub
		batch.begin();
		batch.draw(Assets.instance.header, 0, 705);
		batch.draw(Assets.instance.playBg, 0, 0);
		Assets.instance.font.setColor(Color.WHITE);
		Assets.instance.font.draw(batch, Integer.toString(level), 120 - Assets.instance.font.getBounds(Integer.toString(level)).width / 2, 85
				+ Assets.instance.font.getBounds(Integer.toString(level)).height + Assets.instance.font.getLineHeight() / 2);
		batch.end();
		super.draw();

	}

	public void toggleButton(int i, int j) {
		lightBoard[i][j] = (lightBoard[i][j] == false) ? true : false;
		cellBoard[i][j].isToggle();
		if ((i + 1) < n) {
			lightBoard[i + 1][j] = (lightBoard[i + 1][j] == false) ? true : false;
			cellBoard[i + 1][j].isToggle();
		}

		if (i - 1 >= 0) {
			lightBoard[i - 1][j] = (lightBoard[i - 1][j] == false) ? true : false;
			cellBoard[i - 1][j].isToggle();
		}

		if (j + 1 < n) {
			lightBoard[i][j + 1] = (lightBoard[i][j + 1] == false) ? true : false;
			cellBoard[i][j + 1].isToggle();
		}

		if (j - 1 >= 0) {
			lightBoard[i][j - 1] = (lightBoard[i][j - 1] == false) ? true : false;
			cellBoard[i][j - 1].isToggle();
		}
	}

	public void resetBoard() {
		// TODO Auto-generated method stub
		stepCount = 0;
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++) {
				cellBoard[i][j].setLight(false);
				lightBoard[i][j] = false;
			}

		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				stepBoard[i * n + j] = solutionBoard[i][j];

		gameState = GameState.Playing;
		toggleLightFromSolution();
		// //////////////////////////////

	}

	public int getLevel() {
		return level;

	}

	public void nextLevel() {
		if (gameState == GameState.Wining)
			getRoot().removeActor(winWorld);
		level++;
		if (type == CellType.LARGE) {
			if (level >= 6)
				level = 6;
		} else {
			if (level > 100)
				level = 100;
		}

		loadLevel(level);
		Prefs.instance.save(n, level);

	}

	public int getStepCount() {
		return stepCount;
	}
}

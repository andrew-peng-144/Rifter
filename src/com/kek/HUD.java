package com.kek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class HUD implements Disposable {

	// Scene2D.ui Stage and its own Viewport for HUD
	Stage stage;
	private Viewport viewport;

	// Mario score/time Tracking Variables
	private Integer worldTimer;
	private boolean timeUp; // true when the world timer reaches 0
	private float timeCount;
	private static Integer score;

	// Scene2D widgets
	private Label countdownLabel;
	private static Label scoreLabel;
	private Label timeLabel;
	private Label levelLabel;
	private Label worldLabel;
	private Label marioLabel;

	Rifter game;

	public HUD(Rifter game) {
		this.game = game;
		// define our tracking variables
		worldTimer = 300;
		timeCount = 0;
		score = 0;

		// setup the HUD viewport using a new camera seperate from our gamecam
		// define our stage using that viewport and our games spritebatch
		viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.batch);

		// define a table used to organize our hud's labels
		Table table = new Table();
		// Top-Align table
		table.top();
		// make the table fill the entire stage
		table.setFillParent(true);

		// define our labels using the String, and a Label style consisting of a font
		// and color
		countdownLabel = new Label(String.format("%03d", worldTimer),
				new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		levelLabel = new Label("1-1", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		worldLabel = new Label("WORLD", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		marioLabel = new Label("MARIO", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
		table.debug();
		// add our labels to our table, padding the top, and giving them all equal width
		// with expandX
		table.add(marioLabel).expandX().padTop(10);
		table.add(worldLabel).expandX().padTop(10);
		table.add(timeLabel).expandX().padTop(10);
		// add a second row to our table
		table.row();
		table.add(scoreLabel).expandX();
		table.add(levelLabel).expandX();
		table.add(countdownLabel).expandX();

		text = new Label("", new Label.LabelStyle(new BitmapFont(), Color.CHARTREUSE));
		Pixmap pixmap = new Pixmap(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), Format.RGBA8888);
		pixmap.setColor(new Color(.7f,.7f,.7f,.2f));
		pixmap.fill();
		grayBox = new Texture(pixmap);

		// add our table to the stage
		stage.addActor(table);

		Table botTable = new Table();
		botTable.bottom();
		botTable.debug();
		botTable.setFillParent(true);
		botTable.add(text).expandX().align(Align.center);
		stage.addActor(botTable);
	}

	Label text;
	Texture grayBox;

	public void update(float dt) {

		timeCount += dt;
		if (timeCount >= 1) {
			if (worldTimer > 0) {
				worldTimer--;
			} else {
				timeUp = true;
			}
			countdownLabel.setText(String.format("%03d", worldTimer));
			timeCount = 0;
		}
		// text stuff testing
		if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
			isDrawingTextDialogThingy = true;
			Timer timer = new Timer();
			timer.scheduleTask(new Task() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					isDrawingTextDialogThingy=false;
				}
			}, 1f);
		}
		if (isDrawingTextDialogThingy) {
			// TODO setup text delaying later
			text.setText("YES! + " + Math.random());
			
			
		} else {
			text.setText("");
		}

	}

	boolean isDrawingTextDialogThingy;

	public void render() {

		stage.draw();

		if (isDrawingTextDialogThingy) {
			game.batch.begin();
			// draw dat rectangle
			game.batch.draw(grayBox, 0,0,400,100);
			game.batch.end();
		}
	}

	@Override
	public void dispose() {

		stage.dispose();
	}

}

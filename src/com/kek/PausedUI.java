package com.kek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PausedUI implements Disposable {
	public Stage stage;
	private Viewport viewport;

	// Scene2D widgets
	private Button quitButton;
	Label paused;
	Texture gray;
	Rifter game;
	public PausedUI(final Rifter game) {

		this.game = game;
		Pixmap pixmap = new Pixmap( Gdx.graphics.getWidth(),Gdx.graphics.getHeight(), Format.RGBA8888 );
		pixmap.setColor( .8f,.8f,.8f,.75f );
		pixmap.fill();
		gray = new Texture( pixmap );
		pixmap.dispose();

		// setup the HUD viewport using a new camera seperate from our gamecam
		// define our stage using that viewport and our games spritebatch
		viewport = new FitViewport(Constants.V_WIDTH, Constants.V_HEIGHT, new OrthographicCamera());
		stage = new Stage(viewport, game.batch);
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"),(TextureAtlas) game.assets.get("uiskin.atlas"));
		quitButton = new Button(skin);
		
		paused = new Label("PAUSED",skin);
		
		quitButton.addListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				game.setScreen(new MenuScreen(game));
				
			}
			
		});
		
		
		// define a table used to organize our hud's labels
		Table table = new Table();
		table.debug();


		// make the table fill the entire stage
		table.setFillParent(true);
		table.add(paused);
		table.row();
		
		table.add(quitButton);

		// add our table to the stage
		stage.addActor(table);
		
		Gdx.input.setInputProcessor(stage);
	}

	public void update(float dt) {

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

	public void render() {
		game.batch.begin();
		game.batch.draw(gray, 0, 0);
		game.batch.end();
		stage.draw();
	}
}

package com.kek;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.actions.RotateByAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuScreen implements Screen {

	Stage stage;
	Table table;
	
	Game game;

	public MenuScreen(Game game) {
		this.game = game;

	}
	
	TextButton play;

	@Override
	public void show() {
		// TODO Auto-generated method stub
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);

		table = new Table();
		table.setFillParent(true);
		stage.addActor(table);

		table.setDebug(true); // This is optional, but enables debug lines for tables.

		TextureAtlas atlas;
		
		Skin skin = new Skin(Gdx.files.internal("uiskin.json"),new TextureAtlas("ui/uiskin.atlas"));
		Color color = skin.getColor("green");
		Button kekyes = new Button(skin.get("toggle", Button.ButtonStyle.class));
		play = new TextButton("kYES!YES!ek", skin);
		play.addListener(new ChangeListener() {
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + play.isChecked());
				play.setText("Good job!");
				game.setScreen(new PlayScreen(game));
			}
		});
		MoveToAction mta = new MoveToAction();
		mta.setPosition(200,200);
		mta.setDuration(1);
		play.addAction(mta);
		
		Label nameLabel = new Label("Name:", skin);
	    TextField nameText = new TextField("keqwasdfasdfk", skin);
	    
	    final CheckBox YES_ = new CheckBox("NEGUS", skin);
	    YES_.addListener(new ChangeListener() {
	    	@Override
	    	public void changed(ChangeEvent event, Actor actor) {
	    		// TODO Auto-generated method stub
	    		YES_.setText("NEGUS123455");
	    	}
	    });
	    
	    Label addressLabel = new Label("Address:", skin);
	    TextField addressText = new TextField("", skin);
	    
	    table.add(play);
	    table.add(nameText).width(100).padBottom(20);
	    table.row();
	    table.add(addressLabel);
	    table.add(addressText).width(100);
	    
	    table.add(YES_);
	    table.row();
	    table.add(kekyes);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(delta);
		play.setPosition(play.getX()+1, play.getY());
		stage.draw();

	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		stage.dispose();
	}

}

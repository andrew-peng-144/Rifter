package com.kek;

import static com.kek.Constants.PPM;
import static com.kek.Constants.V_HEIGHT;
import static com.kek.Constants.V_WIDTH;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import entities.MovingBlock;
import entities.Player;
import entities.Projectile;
import entities.Spawner;
import tools.AContactFilter;
import tools.AContactListener;
import tools.WorldCreator;

public class PlayScreen implements Screen {
	private Rifter game;

	public Rifter getGame() {
		return game;
	}

	private TextureAtlas atlas;

	// basic playscreen variables
	public OrthographicCamera gamecam;
	public Viewport gamePort;

	// Tiled map variables
	private TmxMapLoader maploader;
	public TiledMap map;
	private OrthogonalTiledMapRenderer renderer;

	// Box2d variables
	public World world;
	private Box2DDebugRenderer b2dr;
	private WorldCreator creator;

	// sprites
	private Player player;

	// stuff
	public Array<Projectile> projectiles = new Array<Projectile>();

	public Player getPlayer() {
		return player;
	}

	private Music music;

	public PlayScreen(Game game) {
		// atlas = new TextureAtlas("Mario_and_Enemies.pack");
		
		this.game = (Rifter) game;
		// create cam used to follow mario through cam world
		gamecam = new OrthographicCamera();

		// create a FitViewport to maintain virtual aspect ratio despite screen size
		gamePort = new FitViewport(V_WIDTH / PPM, V_HEIGHT / PPM, gamecam);

		// Load our map and setup our map renderer
		maploader = new TmxMapLoader();
		map = maploader.load("map.tmx");
		renderer = new OrthogonalTiledMapRenderer(map, 1 / PPM);

		// initially set our gamcam to be centered correctly at the start of of map
		gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);
		// gamecam.update();
		// create our Box2D world, setting no gravity in X, -10 gravity in Y, and allow
		// bodies to sleep
		world = new World(new Vector2(0, -10), true);
		// allows for debug lines of our box2d world.
		b2dr = new Box2DDebugRenderer();

		creator = new WorldCreator(this);

		// create player in our game world
		player = new Player(this);

		world.setContactListener(new AContactListener());
		world.setContactFilter(new AContactFilter());
		// music = MarioBros.manager.get("audio/music/mario_music.ogg", Music.class);
		// music.setLooping(true);
		// music.setVolume(0.3f);
		// music.play();
		hud = new HUD(this.game);
		pausedUI = new PausedUI(this.game);
		turnOnSoundNegus = new BitmapFont();

	}

	BitmapFont turnOnSoundNegus;

	public void update(float dt) {

		InputHandler.update(this, dt);

		// takes 1 step in the physics simulation(60 times per second)
		world.step(1 / 60f, 6, 2);

		removeDestroyedEntities();

		player.update(dt);
		for (Projectile p : projectiles)
			p.update(dt);
		for (MovingBlock m : movingBlocks)
			m.update(dt);
		updateASDFSTUFF();

		for (Spawner s : spawners)
			s.update(dt);

		hud.update(dt);
		
		// attach our gamecam to our players coordinate
		// gamecam.position.set(player.getBody().getPosition(), 0);
		// gamecam.position.x = player.getBody().posi

		// update our gamecam with correct coordinates after changes
		gamecam.update();
		// tell our renderer to draw only what our camera can see in our game world.
		renderer.setView(gamecam);

	}

	private void updateASDFSTUFF() {
		// if (creator.movingBlock != null) {
		// Body block = creator.movingBlock;
		//
		// if (Math.abs(block.getPosition().x - creator.movingBlockSpawnPoint.x) >= 1){
		// System.out.println("asdf");
		// block.setLinearVelocity(-block.getLinearVelocity().x, 0);
		// //player.getBody().setLinearVelocity(0, 0);
		// }
		//
		// if(player.onMovingPlatform) {
		// //player.getBody().setLinearVelocity(creator.movingBlock.getLinearVelocity());
		// }
		// }

		// useless

		// if(creator.pj.getJointTranslation() >= creator.pj.getUpperLimit()){
		// creator.pj.setMotorSpeed(-3);
		// } else if(creator.pj.getJointTranslation() <= creator.pj.getLowerLimit()){
		// creator.pj.setMotorSpeed(3);
		// }
	}

	public Array<MovingBlock> movingBlocks = new Array<MovingBlock>();

	private void removeDestroyedEntities() {
		for (int i = 0; i < projectiles.size; i++) {
			if (projectiles.get(i).isDestroyed())
				projectiles.removeIndex(i);
		}

	}

	private void handleEscapeKey() {
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			if (isPaused)
				resume();
			else
				pause();
		}

	}

	public Array<Spawner> spawners = new Array<Spawner>();

	@Override
	public void render(float delta) {
		handleEscapeKey();
		// separate our update logic from render
		if (!isPaused)
			update(delta);

		// Clear the game screen with Black
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// render our game TILED map
		renderer.render();

		// render our Box2DDebugLines
		b2dr.render(world, gamecam.combined);
		SpriteBatch batch = game.batch;
		batch.setProjectionMatrix(gamecam.combined);

		batch.begin();

		player.render(batch);
		for (Projectile p : projectiles)
			p.render(batch);
		for (MovingBlock m : movingBlocks)
			m.render(batch);
		// TODO get a list of all entities, and just render that entire list.

		// Sprite s = new Sprite(new Texture("badlogic.jpg"));
		// s.setPosition(0,2.5f/PPM);
		// s.setBounds(0, 0, 50/PPM, 50/PPM);
		// s.draw(batch);
		turnOnSoundNegus.draw(game.batch, "YES", 2, 2, .05f, 0, false);

		batch.end();
		// draw pause stage if paused
		if (isPaused) {
			batch.setProjectionMatrix(pausedUI.stage.getCamera().combined);
			pausedUI.render();

		}

		batch.setProjectionMatrix(hud.stage.getCamera().combined);
		hud.render();

//		if (gameOver()) {
//			game.setScreen(new GameOverScreen(game));
//			dispose();
//		}
	}

	HUD hud;
	PausedUI pausedUI;

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		gamePort.update(width, height);

	}

	public boolean isPaused;

	@Override
	public void pause() {
		System.out.println("PAUSED");
		isPaused = true;
	}

	@Override
	public void resume() {
		System.out.println("RESUME");
		isPaused = false;

	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		map.dispose();
		renderer.dispose();
		world.dispose();
		b2dr.dispose();

	}
}

package entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.kek.PlayScreen;

public abstract class Spawner {
	/**
	 * the location of the spawner AND where things will spawn.
	 */
	public Vector2 spawnPos;
	//public Array<Object> spawned;
	
	public float delay;
	protected PlayScreen screen;
	public Spawner(Vector2 spawnPos, float delay, PlayScreen screen) {
		this.spawnPos=spawnPos;
		this.delay=delay;
		this.screen=screen;
	}
	private float tc;
	public void update(float dt) {
		tc+=dt;
		if(tc >= delay) {
			tc = 0;
			spawn();
		}
	}
	/**
	 * **can be used anonymously.
	 * <br>
	 * any code put here will be run every time delay goes off.
	 */
	protected abstract void spawn();

	
}

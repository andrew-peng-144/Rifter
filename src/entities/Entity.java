package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.kek.Constants;
import com.kek.PlayScreen;

/**
 * 
 * something with a physics body (usually moving and has fixtures for collision),
 * <br> and a visible sprite
 * "attached" to it
 *
 */
public abstract class Entity {

	protected Body body;

	public float PPM = Constants.PPM;
	protected Sprite sprite;

	protected PlayScreen screen;

	private boolean destroyed;

	public boolean isDestroyed() {
		return destroyed;
	}

	protected float timeAlive;

	public boolean markedToDestroy;

	public Entity(Body body, Sprite sprite, PlayScreen screen) {
		this.body = body;
		this.sprite = sprite;
		this.screen = screen;

	}

	/**
	 * BODY AND SPRITE ARE NOT SET!
	 * 
	 * @param screen
	 */
	public Entity(PlayScreen screen) {
		this.screen = screen;
	}

	/**
	 * pls super-call this to link sprite and body
	 * 
	 * @param dt
	 */
	public void update(float dt) {
		if (markedToDestroy && !destroyed) {
			screen.world.destroyBody(body);
			destroyed = true;
		} else if (!destroyed) {
			sprite.setX(body.getPosition().x - sprite.getWidth() / 2);
			sprite.setY(body.getPosition().y - sprite.getHeight() / 2);
			timeAlive += dt;
		}

	}

	/**
	 * pls super-call this to draw
	 * 
	 * @param batch
	 */
	public void render(SpriteBatch batch) {
		if (!destroyed) {
			if (sprite != null) {
				sprite.draw(batch);
			}
		}
	}

	public void destroy() {
		markedToDestroy = true;
	}

	@Override
	public String toString() {
		return "Entity [body=" + body + ", PPM=" + PPM + ", sprite=" + sprite;
	}

	public Body getBody() {
		return body;
	}

	protected void setBody(Body body) {
		this.body = body;
	}

	public Sprite getSprite() {
		return sprite;
	}

	protected void setSprite(Sprite sprite) {
		this.sprite = sprite;
	}

}

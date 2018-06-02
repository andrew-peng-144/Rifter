package entities;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;
import com.kek.Constants;
import com.kek.InputHandler;
import com.kek.PlayScreen;

import tools.AFactory;
//TODO make this extend Entity. just dont make a sprite and it's all good kek
public class Weapon {

	Projectile proj;
	/**
	 * the delay, in seconds, between shots
	 */
	float delay;
	/**
	 * is this is 0, the weapon has infinite ammo. All melee should have these to 0.
	 */
	float reloadTime;

	int clipLeft;
	int clipSize;

	Sprite sprite;

	private PlayScreen screen;

	ProjectileDef pdef;

	Array<Projectile> projsOut = new Array<Projectile>();
	private String name;
	FiringMode firingMode;

	public static enum FiringMode {
		AUTO, MANUAL
	}

	public Weapon(PlayScreen screen) {
		this("default",
				new ProjectileDef()
						.bodyDef(AFactory.newBodyDef(BodyType.KinematicBody, new Vector2(), new Vector2(1, 0)))
						.damage(1f).spriteFile("Tone.png")
						.fixtureDefs(AFactory.newFixtureDef(1, Constants.PLAYER_BIT, (short) 0, 0, true, 0,
								AFactory.newCircleShape(6 / Constants.PPM))),

				.3f, 1.5f, 8, FiringMode.MANUAL, AFactory.newSprite("badlogic.jpg", 1 / Constants.PPM), screen);

	}

	public Weapon(String name, ProjectileDef pdef, float delay, float reloadTime, int clipSize, FiringMode firingMode,
			Sprite weaponSprite, PlayScreen screen) {

		this.firingMode = firingMode;
		this.name = name;
		this.pdef = pdef;
		this.delay = delay;
		this.reloadTime = reloadTime;
		this.clipSize = clipSize;
		this.screen = screen;
		this.sprite = weaponSprite;
		clipLeft = clipSize;
		projXspeed = pdef.bodyDef.linearVelocity.x;
	}
	
	public final float projXspeed;

	private float tc4delay;
	private float tc4reload;

	public void update(float dt) {
		// TODO: weapon mechanics.
		// press x to shoot once.
		// hold x to spray. timer starts when x is first held.
		// spamming x should not be able to exceed the firerate
		// when no more clip, reload. reload time should be counting as long as weapon
		// is out.
		// if weapon is switched, reload time should reset.
		//
		
		switch (firingMode) {
		case AUTO:
			// if (Gdx.input.isKeyPressed(Input.Keys.X))
			// tc4delay += dt;
			// else
			// tc4delay = -.001f;
			//
			// if (clipLeft == 0) {
			// tc4reload+=dt;
			// if (tc4reload > reloadTime) {
			// tc4reload = -.001f;
			// clipLeft = clipSize; // reload
			// }
			// } else if (tc4delay >= delay || tc4delay == 0) {
			// tc4delay = 0;
			// shoot();
			// }
		
			break;
		case MANUAL:
			if (InputHandler.x_just) {
				shoot();
				//((Sound) screen.getGame().assets.get("ML CSP.mp3")).play();
			}
			break;
		}
	}

	public void render(SpriteBatch sb) {
		sprite.draw(sb);
	}

	private void setProjectileOrigin(Vector2 o) {
		pdef.bodyDef.position.set(o);
	}
	

	private void shoot() {
		clipLeft--;
		setProjectileOrigin(screen.getPlayer().body.getPosition());
		
		pdef.bodyDef.linearVelocity.set((screen.getPlayer().sprite.isFlipX() ? -1 : 1) * projXspeed, pdef.bodyDef.linearVelocity.y);
		
		screen.projectiles.add(new Projectile(pdef, screen));
	}

}

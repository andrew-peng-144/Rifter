package entities;

import static com.kek.Constants.*;
import static com.kek.InputHandler.*;
import static tools.AUserData.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kek.PlayScreen;

import tools.AUserData;

public class Player extends Entity {
	public boolean canJump;
	Weapon weapon;

	public Vector2 spawnPoint;

	public Player(PlayScreen screen) {
		super(screen);
		spawnPoint = new Vector2(5 * 16 / PPM, 5 * 16 / PPM);

		BodyDef bdef = new BodyDef();
		bdef.position.set(spawnPoint);
		bdef.type = BodyType.DynamicBody;
		bdef.linearDamping = P_DAMP;
		//bdef.fixedRotation = true;
		
		FixtureDef fdef = new FixtureDef();
//		CircleShape circle = new CircleShape();
//		circle.setRadius(6 / PPM);
		PolygonShape box = new PolygonShape();
		box.setAsBox(P_WIDTH/2,P_HEIGHT/2);
		fdef.shape = box;
		fdef.filter.categoryBits = PLAYER_BIT;
		fdef.friction = P_FRICTION;
		fdef.density = 0;
		
		
		Body body = screen.world.createBody(bdef);
		body.setUserData(this);
		body.createFixture(fdef).setUserData(CORE); //index 0
		
		float a=3/PPM,b=7/PPM; // dist parallel to desired extension, dist orthogonal to desired extension.
		
		EdgeShape edge = new EdgeShape(); //bottom extention, for sensing if block below.
		edge.set(-a,-b,a,b);
		fdef.shape = edge;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(BOTTOM_EXT); //index 1
		
		edge.set(-b,-a,-b,a);
		fdef.shape = edge;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(LEFT_EXT); //index 2
		
		edge.set(b,-a,b,a);
		fdef.shape = edge;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(RIGHT_EXT); //index 3
		
		edge.set(-a,b,a,b);
		fdef.shape = edge;
		fdef.isSensor = true;
		body.createFixture(fdef).setUserData(TOP_EXT); //index 4
		

		setBody(body);
		Sprite sprite = new Sprite(new Texture("debug.png"));
		sprite.setBounds(body.getPosition().x, body.getPosition().y, 12 / PPM, 12 / PPM);
		setSprite(sprite);

		weapon = new Weapon(screen);
	}

	boolean inOtherWorld;

	@Override
	public void update(float dt) {
		super.update(dt);

		if (right && body.getLinearVelocity().x <= P_MAX_X_VEL)
			body.applyLinearImpulse(new Vector2(P_X_IMP, 0), body.getWorldCenter(), true);
		if (left && body.getLinearVelocity().x >= -P_MAX_X_VEL)
			body.applyLinearImpulse(new Vector2(-P_X_IMP, 0), body.getWorldCenter(), true);
		if (up_just && canJump) {
			//body.applyLinearImpulse(new Vector2(0, P_JUMP_IMP),body.getWorldCenter(),true);
			body.setLinearVelocity(body.getLinearVelocity().x,P_JUMP_IMP);
			//canJump = false;
		}
		//System.out.println(body.getWorldCenter());
		if (left && getSprite().isFlipX() == false) {
			getSprite().setFlip(true, false);
		} else if (right && getSprite().isFlipX() == true) {
			getSprite().setFlip(false, false);
		}

		if (r_just)
			toRespawn = true;

		if (toRespawn) { // respawn
			respawn();
		} else if (c_just) {
			if (inOtherWorld) {
				teleportRelative(0, -DY);
				inOtherWorld = false;
			} else {
				teleportRelative(0, DY);
				inOtherWorld = true;
			}
		}

		weapon.update(dt);
		if (rip) {
			tc_showRip += dt;
			if (tc_showRip > 1) {
				rip = false;
				tc_showRip = 0;
			}	
		}
	}

	boolean toRespawn;
	boolean rip;
	double tc_showRip;
	
	//public boolean onMovingPlatform;

	public void prepareToRespawn() {
		toRespawn = true;
		rip = true;
	}

	/**
	 * Precondition: toRespawn==true
	 */
	private void respawn() {
		toRespawn = false;
		inOtherWorld = false;
		teleportTo(spawnPoint);
	}

	private void teleportRelative(float dx, float dy) {
		teleportTo(new Vector2(body.getPosition().x + dx, body.getPosition().y + dy));
	}

	private void teleportTo(Vector2 dest) {
		body.setTransform(dest, 0);
		getBody().applyLinearImpulse(new Vector2(0, -0.01f), getBody().getWorldCenter(), true);
	}

	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		// weapon.render(sb);
		
		if (win) {
			Texture t = screen.getGame().assets.get("win.png",Texture.class);
			sb.draw(t, body.getPosition().x, body.getPosition().y, t.getWidth()/PPM, t.getHeight()/PPM);
		} else if (rip) {
			Texture t = screen.getGame().assets.get("rip.png",Texture.class);
			sb.draw(t, body.getPosition().x, body.getPosition().y, t.getWidth()/PPM, t.getHeight()/PPM);

		}
	}

	boolean win;

	public void win() {
		win = true;

	}

}

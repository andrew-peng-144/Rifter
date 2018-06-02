package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.kek.PlayScreen;

import tools.AFactory;
import tools.AUserData;

public class MovingBlockSpawner extends Spawner{
	
//	/**
//	 * THIS IS UNNESSCARY
//	 * @author A
//	 *
//	 */
//	private class MovingBlockData{
//		public MovingBlockData(BodyDef bdef, FixtureDef fdef, Vector2 vel) {
//			super();
//		
//			this.bdef = bdef;
//			this.fdef = fdef;
//			this.vel = vel;
//		}
//		BodyDef bdef;
//		FixtureDef fdef;
//		Vector2 vel;
//	//Sprite sprite;
//	}
//
//	/**
//	 * THIS IS ULTIMATELY UNNECCESARY I WAS JUST TOO LAZY
//	 * 
//	 * @author A
//	 *
//	 */
//	private class MovingBlock extends Entity {
//
//		public MovingBlock(Body body, Sprite sprite, PlayScreen screen) {
//			super(body, sprite, screen);
//
//		}
//
//		@Override
//		public void update(float dt) {
//			super.update(dt);
//
//		}
//
//		public void draw(SpriteBatch batch) {
//			sprite.draw(batch);
//		}
//	}
	
	BodyDef bdef;
	FixtureDef fdef;
	Vector2 vel;
	boolean platforms;
	public MovingBlockSpawner(Vector2 spawnPos, float delay, PlayScreen screen, Vector2 vel, float width, float height, boolean platforms) {
		super(spawnPos, delay, screen);
		BodyDef bdef = new BodyDef();
		bdef.type = BodyDef.BodyType.KinematicBody;
		bdef.position.set(spawnPos);
		FixtureDef fdef = new FixtureDef();

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(width/2, height/2);
		fdef.shape = shape;
		fdef.friction = 0.8f;
		
		this.platforms=platforms;
		this.bdef=bdef;
		this.fdef=fdef;
		this.vel=vel;
	}

	public void update(float dt) {
		super.update(dt);

	}
	

	@Override
	protected void spawn() {
		Body body = ((PlayScreen)screen).world.createBody(bdef);
		Fixture fixture = body.createFixture(fdef);
		body.setLinearVelocity(vel);
		if(platforms)
			fixture.setUserData(AUserData.PLATFORM);
		
		Sprite sprite = AFactory.newSprite("icon.png", 1/100f);
		sprite.setPosition(bdef.position.x*100,bdef.position.y*100);
		screen.movingBlocks.add(new MovingBlock(body, sprite, screen, 50));
		
	}

//	@Override //HANDLED IN PLAYSCREEN.
//	public void drawChildren(SpriteBatch batch) {
//		for(MovingBlock e: movingBlocks)
//			e.sprite.draw(batch);
//		
//	}

//	protected void despawn(Object object) {
//		//spawned.removeValue(object,true);
//	}

}

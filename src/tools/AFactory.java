package tools;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
/**
 * create bodies and fixture easier
 * @author A
 *
 */
public class AFactory {
	/**
	 * creates body w/ given parameters

	 * @return the bodydef
	 */
	public static BodyDef newBodyDef(BodyType type, Vector2 position, Vector2 linVel, float angVel, float linDamp,
			boolean fixedRotation, float gravityScale) {
		BodyDef bdef = new BodyDef();
		bdef.type = type;
		bdef.position.set(position);
		bdef.linearVelocity.set(linVel);
		bdef.angularVelocity = angVel;
		bdef.fixedRotation = fixedRotation;
		bdef.gravityScale = gravityScale;
		bdef.linearDamping = linDamp;
		return bdef;
	}
	
	public static BodyDef newBodyDef(BodyType type, Vector2 position, Vector2 linVel) {
		return newBodyDef(type,position,linVel,0,0,true,1);
	}
	
	/**
	 * ADDS fixture to body, given fixture parameters
	 *
	 * @return the fixture added
	 */
	public static FixtureDef newFixtureDef(float density, short categoryBits,short maskBits,
			float friction, boolean isSensor, float restitution, Shape shape) {
		FixtureDef fdef = new FixtureDef();
		fdef.density=density;
		fdef.filter.categoryBits=categoryBits;
		fdef.filter.maskBits = maskBits;
		
		fdef.friction=friction;
		fdef.isSensor=isSensor;
		fdef.restitution=restitution;
		fdef.shape=shape;

		return fdef;
	}

	public static Sprite newSprite(String file, float width,float height) {
		Sprite sprite = new Sprite(new Texture(file));
		sprite.setSize(width, height);
		return sprite;
	}
	public static Sprite newSprite(String file, float scale) {
		Sprite sprite = new Sprite(new Texture(file));
		sprite.setScale(scale);
		return sprite;
	}
	
	public static CircleShape newCircleShape(float radius) {
		CircleShape cs = new CircleShape();
		cs.setRadius(radius);
		return cs;
	}
	
}

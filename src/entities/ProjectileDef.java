package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
/**
 * damage, sprite, body definitions and fixtures definitions
 * @author A
 *
 */
public class ProjectileDef {
	float damage;
	String spriteFile;
	FixtureDef[] fixtureDefs;
	
	BodyDef bodyDef;
	
	public ProjectileDef damage(float damage) {
		this.damage = damage;return this;
	}

	public ProjectileDef spriteFile(String spriteFile) {
		this.spriteFile = spriteFile;return this;
	}

	public ProjectileDef fixtureDefs(FixtureDef...fixtureDefs) {
		this.fixtureDefs=fixtureDefs; return this;
	}
	/**
	 * 
	 * @param bodyDef the position can be 0,0 because projectiles have separate parameter for position
	 * @return
	 */
	public ProjectileDef bodyDef(BodyDef bodyDef) {
		this.bodyDef=bodyDef; return this;
	}

	
	
}

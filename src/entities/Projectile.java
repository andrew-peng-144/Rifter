package entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.kek.Constants;
import com.kek.PlayScreen;

import tools.AFactory;

/**
 * moving entity that does specific stuff on contact
 * and has certain parameters like damage.
 * @author A
 *
 */
public class Projectile extends Entity{
	float damage;
	float lifeTime;
	
	public Projectile(ProjectileDef pdef, PlayScreen screen) {
		super(screen.world.createBody(pdef.bodyDef),
				AFactory.newSprite(pdef.spriteFile, 1/Constants.PPM),screen);
		
		this.damage = pdef.damage;
		lifeTime = 4;
		for(int i = 0; i < pdef.fixtureDefs.length;i++)
			this.body.createFixture(pdef.fixtureDefs[i]);

	
	}
	
	private static BodyDef asdf(BodyDef bdef, Vector2 origin) {
		bdef.position.set(origin);
		return bdef;
		
	}
	@Override
	public void update(float dt) {
		super.update(dt);
		if(timeAlive > lifeTime)
			destroy();
	}
	
}

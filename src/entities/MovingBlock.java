package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.kek.Constants;
import com.kek.PlayScreen;

public class MovingBlock extends Entity {

	float maxDistInTiles;
	Vector2 spawnPoint;
	
	public MovingBlock(Body body, Sprite sprite, PlayScreen screen, float maxDistInTiles) {
		super(body, sprite, screen);
		spawnPoint = body.getWorldCenter();
		this.maxDistInTiles=maxDistInTiles;
		
	}
	@Override
	public void update(float dt) {
		super.update(dt);
		
		if(spawnPoint.dst(body.getPosition()) >= maxDistInTiles/Constants.TPM) {
			destroy();
		}
	}

}

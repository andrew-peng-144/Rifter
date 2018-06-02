package entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.kek.PlayScreen;

public class Enemy extends Entity{

	public Enemy(Body body, Sprite sprite, PlayScreen screen) {
		super(body, sprite, screen);
		
	}

}

package tools;

import static com.kek.Constants.BLOCK_BIT;
import static com.kek.Constants.PLAYER_BIT;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.ContactFilter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Timer;
import com.kek.Constants;

import entities.Player;

public class AContactFilter implements ContactFilter {

	@Override
	public boolean shouldCollide(Fixture fixA, Fixture fixB) {

		short fixAbits = fixA.getFilterData().categoryBits;
		short fixBbits = fixB.getFilterData().categoryBits;

		Fixture playerFixture = fixAbits == PLAYER_BIT ? fixA : fixBbits == PLAYER_BIT ? fixB : null;
		Fixture blockFixture = fixAbits == BLOCK_BIT ? fixA : fixBbits == BLOCK_BIT ? fixB : null;

		Player player = null;
		String playerFixtureUD = null;

		if (playerFixture != null && blockFixture != null && blockFixture.getUserData() != null) {
			if (blockFixture.getUserData().equals(AUserData.PLATFORM)) {
//				player = (Player) playerFixture.getBody().getUserData();
//				float playerBottom = player.getBody().getPosition().y - Constants.P_HEIGHT/2;
//				float platTop = blockFixture.getBody().getPosition().y 
//						+ .08f/2;
//				System.out.println("a"+playerBottom);
//				System.out.println("b"+platTop);
				//TODO: player still collides when entering platform from side.

				// position-based above, velocity-based below
				return playerFixture.getBody().getLinearVelocity().y < 0
						//&& playerBottom > platTop
						;
			}
		}

		return true;
	}

}

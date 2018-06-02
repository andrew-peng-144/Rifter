package tools;

import static com.kek.Constants.BLOCK_BIT;
import static com.kek.Constants.ENEMY_BIT;
import static com.kek.Constants.PLAYER_BIT;
import static com.kek.Constants.ZONE_BIT;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import entities.Player;

public class AContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		short fixAbits = fixA.getFilterData().categoryBits;
		short fixBbits = fixB.getFilterData().categoryBits;

		Fixture playerFixture = fixAbits == PLAYER_BIT ? fixA : fixBbits == PLAYER_BIT ? fixB : null;

		Player player = null;
		AUserData playerFixtureUD = null;
		// handling collision between PLAYER and other stuff.
		if (playerFixture != null) {
			player = (Player) playerFixture.getBody().getUserData();
			playerFixtureUD = (AUserData) playerFixture.getUserData();

			int cDef = fixAbits | fixBbits;
			switch (cDef) {
			case PLAYER_BIT | ENEMY_BIT:

				break;
			case PLAYER_BIT | BLOCK_BIT:
				Fixture blockFixture = fixA == playerFixture ? fixB : fixA;
				AUserData blockFixtureUD = (AUserData) blockFixture.getUserData();
				if (playerFixtureUD != null)
					switch ((AUserData) playerFixtureUD) {

					case CORE:
						break;

					case BOTTOM_EXT:
						player.canJump = true;
//						if (blockFixtureUD != null && blockFixtureUD.equals("moving"))
//							player.onMovingPlatform = true;

						break;
					case LEFT_EXT:

						break;
					case RIGHT_EXT:

						break;
					case TOP_EXT:
						break;
					default:
						break;
					}
				break;
			case PLAYER_BIT | ZONE_BIT:
				Fixture zone = playerFixture == fixA ? fixB : fixA;
				AUserData zoneUD = (AUserData) zone.getUserData();
				if (zoneUD != null)
					if (zoneUD.equals(AUserData.DEATH_ZONE))
						player.prepareToRespawn();
					else if (zoneUD.equals(AUserData.YOUWINZONE))
						player.win();
				break;
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixA = contact.getFixtureA();
		Fixture fixB = contact.getFixtureB();

		short fixAbits = fixA.getFilterData().categoryBits;
		short fixBbits = fixB.getFilterData().categoryBits;

		Fixture playerFixture = fixAbits == PLAYER_BIT ? fixA : fixBbits == PLAYER_BIT ? fixB : null;

		Player player = null;
		AUserData playerFixtureUD = null;
		if (playerFixture != null) {
			player = (Player) playerFixture.getBody().getUserData();
			playerFixtureUD = (AUserData) playerFixture.getUserData();

			int cDef = fixAbits | fixBbits;
			switch (cDef) {
			case PLAYER_BIT | ENEMY_BIT:

				break;
			case PLAYER_BIT | BLOCK_BIT:
				Fixture blockFixture = fixA == playerFixture ? fixB : fixA;
				AUserData blockFixtureUD = (AUserData) blockFixture.getUserData();
				if (playerFixtureUD != null)
				{}
				break;
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}

}

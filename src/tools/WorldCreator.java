package tools;

import static com.kek.Constants.BLOCK_BIT;
import static com.kek.Constants.PPM;
import static com.kek.Constants.ZONE_BIT;

import java.util.Iterator;

import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJoint;
import com.badlogic.gdx.physics.box2d.joints.PrismaticJointDef;
import com.kek.Constants;
import com.kek.PlayScreen;

import entities.MovingBlockSpawner;

/**
 * TODO lots of repeated code here, cleanup pls
 * 
 * @author A
 *
 */
public class WorldCreator {

	public WorldCreator(PlayScreen screen) {
		World world = screen.world;
		TiledMap map = screen.map;
		// create body and fixture variables
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Body body;
		for (int layer = 0; layer < map.getLayers().getCount(); layer++)
			switch (layer) {
			case 3: // solid
				for (RectangleMapObject object : map.getLayers().get(layer).getObjects()
						.getByType(RectangleMapObject.class)) {

					Rectangle rect = object.getRectangle();
					fdef.filter.categoryBits = BLOCK_BIT;
					MapProperties mp = object.getProperties();

					if (mp.get("type") != null) {
						if (mp.get("type").equals("platform")) {
							bdef.type = BodyDef.BodyType.StaticBody;
							bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM,
									(rect.getY() + rect.getHeight() * 0.75f) / PPM);
							body = world.createBody(bdef);

							shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 4 / PPM);
							fdef.shape = shape;
							fdef.friction = 0.4f;
							body.createFixture(fdef).setUserData(AUserData.PLATFORM);
						} else if (mp.get("type").equals("rotatingBlock")) {
							bdef.type = BodyType.KinematicBody;
							bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM,
									(rect.getY() + rect.getHeight() * 0.75f) / PPM);
							body = world.createBody(bdef);

							shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
							fdef.shape = shape;
							fdef.friction = 0.6f;
							body.createFixture(fdef).setUserData(AUserData.ROTATING_BLOCK);
							body.setAngularVelocity(mp.get("angVel", Float.class));

						}
					} else {
						bdef.type = BodyDef.BodyType.StaticBody;
						bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM,
								(rect.getY() + rect.getHeight() / 2) / PPM);

						body = world.createBody(bdef);

						shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
						fdef.shape = shape;
						fdef.friction = 0.4f;
						body.createFixture(fdef);
					}
				}

				break;
			case 4: // spawners
				for (RectangleMapObject object : map.getLayers().get(layer).getObjects()
						.getByType(RectangleMapObject.class)) {
					Rectangle rect = object.getRectangle();
					Vector2 center = rect.getCenter(new Vector2()).scl(1 / PPM); // CONVERT PIXELS TO METERS

					MapProperties mp = object.getProperties();
					Iterator<?> i = mp.getKeys();
					while (true) { // checking what properties it has
						System.out.println(i.next());
						if (!i.hasNext())
							break;
					}

					if (mp.get("type").equals("movingBlockSpawner")) {

							screen.spawners.add(new MovingBlockSpawner(center, mp.get("delay", Float.class), screen,
									new Vector2(mp.get("velX", Float.class), mp.get("velY", Float.class)),
									mp.get("width", Float.class), mp.get("height", Float.class),
									mp.get("platforms")==null ? false : true));
						// screen.spawners.add(new MovingBlockSpawner(center, (Float)mp.get("delay"),
						// screen,
						// new Vector2((Float)mp.get("velX"), (Float) mp.get("velY")), //gahh this is
						// inefficient
						// (Float)mp.get("width"),(Float)mp.get("height")));
					}
				}
				break;
			case 5: // zones
				for (RectangleMapObject object : map.getLayers().get(layer).getObjects()
						.getByType(RectangleMapObject.class)) {

					Rectangle rect = object.getRectangle();
					fdef.filter.categoryBits = ZONE_BIT;
					MapProperties mp = object.getProperties();

					bdef.type = BodyDef.BodyType.StaticBody;
					bdef.position.set((rect.getX() + rect.getWidth() / 2) / PPM,
							(rect.getY() + rect.getHeight() / 2) / PPM);

					body = world.createBody(bdef);

					shape.setAsBox(rect.getWidth() / 2 / PPM, rect.getHeight() / 2 / PPM);
					fdef.shape = shape;
					fdef.isSensor = true;
					if (mp.get("type") != null) {
						body.createFixture(fdef).setUserData(strToUserData((String)mp.get("type")));

					}
				}
				break;

			}
		// createMovingBlock(world);
		// createRotatingBlock(world);
	}

	private AUserData strToUserData(String str) {
		if(str.equals("DeathZone"))
			return AUserData.DEATH_ZONE;
		else if(str.equals("YouWinZone"))
			return AUserData.YOUWINZONE;
		return null;
	}

	public PrismaticJoint pj;

	public Vector2 movingBlockSpawnPoint;
	public Body movingBlock;

	public Vector2 rotatingBlockSpawnPoint;
	public Body rotatingBlock;

	// private void createMovingBlock(World world) {
	//
	// BodyDef bdef = new BodyDef();
	// FixtureDef fdef = new FixtureDef();
	//
	// movingBlockSpawnPoint = new Vector2(3f, .5f);
	// bdef.position.set(movingBlockSpawnPoint);
	// bdef.type = BodyType.KinematicBody;
	// movingBlock = world.createBody(bdef);
	//
	// PolygonShape square = new PolygonShape();
	// square.setAsBox(.1f, .1f);
	// fdef.shape = square;
	// fdef.filter.categoryBits = Constants.BLOCK_BIT;
	// fdef.friction = 0f;
	// movingBlock.createFixture(fdef).setUserData("moving platform");
	//
	// movingBlock.setLinearVelocity(-1, 0);
	//
	// }
	//
	// private void createRotatingBlock(World world) {
	// BodyDef bdef = new BodyDef();
	// FixtureDef fdef = new FixtureDef();
	//
	// rotatingBlockSpawnPoint = new Vector2(1.12f, 1.12f);
	// bdef.position.set(rotatingBlockSpawnPoint);
	// bdef.type = BodyType.KinematicBody;
	// rotatingBlock = world.createBody(bdef);
	//
	// PolygonShape rect = new PolygonShape();
	// rect.setAsBox(.5f, .1f);
	// fdef.shape = rect;
	// fdef.filter.categoryBits = Constants.BLOCK_BIT;
	// fdef.friction = .5f;
	// rotatingBlock.createFixture(fdef);
	//
	// rotatingBlock.setAngularVelocity(2f);
	// }

	/**
	 * i hate this so the motor on this only works for dynamic bodies meaning that
	 * if it comes in contact with player the player's impulse will GREATLY affect
	 * the motor's functionality this is only useful for... actual physics
	 * simulations...
	 * 
	 * @param world
	 * @deprecated
	 */
	private void createPrismaticJointsKek(World world) {
		Body bodyA, bodyB;

		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();

		bdef.position.set(3f, .5f);
		bdef.type = BodyType.KinematicBody;
		bodyA = world.createBody(bdef);

		bdef.position.set(4f, .5f);
		bdef.type = BodyType.StaticBody;
		bodyB = world.createBody(bdef);

		PolygonShape square = new PolygonShape();
		square.setAsBox(.1f, .1f);
		fdef.shape = square;
		fdef.filter.categoryBits = Constants.BLOCK_BIT;
		fdef.friction = .8f;
		bodyA.createFixture(fdef);
		bodyB.createFixture(fdef);

		PrismaticJointDef pjd = new PrismaticJointDef();

		pjd.enableMotor = true;
		pjd.bodyA = bodyA;
		pjd.bodyB = bodyB;
		// pjd.localAnchorA.set(-1,0);
		// pjd.localAnchorB.set(1,0);
		pjd.motorSpeed = 1f;
		pjd.localAxisA.set(1, 0).nor();
		// pjd.referenceAngle = (float) Math.toRadians(5);
		pjd.collideConnected = false;
		pjd.maxMotorForce = 20f;
		pjd.enableLimit = true;
		// IMPORTANT TODO: false because the lower and upper translations are just
		// indicator values.
		// i will manually handle the limits via direction changing
		pjd.lowerTranslation = 0;
		pjd.upperTranslation = 1f;

		pj = (PrismaticJoint) world.createJoint(pjd);
	}

}
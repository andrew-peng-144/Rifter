package com.kek;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.FitViewport;

import entities.Player;
import entities.Projectile;
import tools.AFactory;
/**
 * whats the point of this lol
 * @author A
 *
 */
public class InputHandler {

	public static boolean x_just;
	public static boolean z_just;
	public static boolean up_just;
	public static boolean left;
	public static boolean right;
	public static boolean r_just;
	public static boolean c_just;
	
	public static void update(PlayScreen screen, float dt) {

		right = Gdx.input.isKeyPressed(Input.Keys.RIGHT);
		left = Gdx.input.isKeyPressed(Input.Keys.LEFT);
		up_just = Gdx.input.isKeyJustPressed(Input.Keys.UP);
		z_just = Gdx.input.isKeyJustPressed(Input.Keys.Z);
		x_just = Gdx.input.isKeyJustPressed(Input.Keys.X);
		r_just = Gdx.input.isKeyJustPressed(Input.Keys.R);
		c_just = Gdx.input.isKeyJustPressed(Input.Keys.C);
		// if(z_just) {
		// screen.projectiles.add(new Projectile(1,
		// AFactory.newBody(BodyType.KinematicBody, player.getBody().getWorldCenter(),
		// new Vector2(player.getSprite().isFlipX() ? -1 : 1,0), screen.world),
		// AFactory.newSprite("Tone.png", 1/Constants.PPM), screen));}

		
		
		if(Gdx.input.isKeyPressed(Input.Keys.Q)) {
			OrthographicCamera oc = screen.gamecam;
			oc.zoom+=0.03f;
			oc.update();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E)) {
			OrthographicCamera oc = screen.gamecam;
			oc.zoom-=0.03f;
			oc.update();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W)) {
			OrthographicCamera oc = screen.gamecam;
			oc.translate(0,0.01f);
			oc.update();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			OrthographicCamera oc = screen.gamecam;
			oc.translate(-.01f,0);
			oc.update();
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S)) {
			OrthographicCamera oc = screen.gamecam;
			oc.translate(0,-0.01f);
			oc.update();
		}
	
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			OrthographicCamera oc = screen.gamecam;
			oc.translate(0.01f,0);
			oc.update();
		}
		
		
	}
}

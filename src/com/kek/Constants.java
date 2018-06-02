package com.kek;

public class Constants {
	public static final float V_WIDTH = 400;
	public static final float V_HEIGHT = 250;
	
	//conversions
	public static final float PPM = 100; //pixels per meter
	public static final float PPT = 16; //pixels per tile
	public static final float TPM = PPM / PPT; //tiles per meter
	
	public static final short DEFAULT_BIT = 0;
	public static final short BLOCK_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short ENEMY_BIT = 4;
	public static final short ZONE_BIT = 8;
	public static final short BIT4 = 16;
	
	//player constants

	public static final float P_DAMP = 1.5f;
	public static final float P_JUMP_IMP = 5f;
	public static final float P_X_IMP= 0.15f; // N * s
	public static final float P_MAX_X_VEL = 3;
	public static final float P_MIN_Y_VEL = -15;
	public static final float P_FRICTION = .4f;
	public static final float P_WIDTH = .12f;
	public static final float P_HEIGHT = .12f;
	
	public static final float DY = 54 / TPM;
	}

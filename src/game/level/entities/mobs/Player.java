/*******************************************************************************
 * Copyright (c) 2013 Glynn Taylor.
 * All rights reserved. This program and the accompanying materials, 
 * (excluding imported libraries, such as LWJGL and Slick2D)
 * are made available under the terms of the GNU Public License
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     Glynn Taylor - initial API and implementation
 ******************************************************************************/
/*
 * Contains information about the player such as statistics, 
 current world number, level info, coordinates, does not 
 handle player movement.
 */
package game.level.entities.mobs;

import game.level.entities.mobs.player.Inventory;
import game.level.map.TileMap;
import game.resources.GraphicsHandler.MobArt;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Player extends Mob {
	private static final long serialVersionUID = -3150745899627903515L;
	private static final float PLAYER_HEIGHT = 2f;
	private int Level = 1;
	private int LevelUpPoints = 6;
	public int Experience = 30;
	public int WorldNumber;
	private int X, Y;
	private final Inventory inventory = new Inventory();

	public int getPlayerExperience() {
		return Experience;
	}

	public Player(int x, int y) {
		super();
		Statistics = new Stats(10, 10, 10, 10, 10, 10);
		IsPlayer = true;
		setX(x);
		setY(y);
	}

	@Override
	public void Render(int x, int y) {

		GL11.glRotatef(Facing * 90f, 0f, 0f, 1f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Color.white.bind();
		MobArt.Player.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(-0.3f, -0.3f, 0);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(0.3f, -0.3f, 0);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(0.3f, 0.3f, 0);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(-0.3f, 0.3f, 0);

		// Topwards side
		// Top right
		GL11.glTexCoord2f(64f / 64f, 18f / 64f);
		GL11.glVertex3f(-0.3f, -0.3f, 0);
		// Bottom left+Right
		GL11.glTexCoord2f(64f / 64f, 36f / 64f);
		GL11.glVertex3f(0.3f, -0.3f, 0);
		// Bottom left
		GL11.glTexCoord2f(18 / 64f, 36f / 64f);
		GL11.glVertex3f(0.3f, -0.3f, -PLAYER_HEIGHT);

		// Top left
		GL11.glTexCoord2f(18f / 64f, 18f / 64f);
		GL11.glVertex3f(-0.3f, -0.3f, -PLAYER_HEIGHT);

		// Rightwards side
		// Top right
		GL11.glTexCoord2f(64f / 64f, 0f / 64f);
		GL11.glVertex3f(0.3f, -0.3f, 0);
		// Bottom Right
		GL11.glTexCoord2f(64f / 64f, 18f / 64f);
		GL11.glVertex3f(0.3f, 0.3f, 0);
		// Bottom left
		GL11.glTexCoord2f(19 / 64f, 18f / 64f);
		GL11.glVertex3f(0.3f, 0.3f, -PLAYER_HEIGHT);
		// Top left
		GL11.glTexCoord2f(19f / 64f, 0f / 64f);
		GL11.glVertex3f(0.3f, -0.3f, -PLAYER_HEIGHT);

		// Front
		// Bottom left
		GL11.glTexCoord2f(0, 46f / 64f);
		GL11.glVertex3f(0.3f, 0.3f, 0);
		// Bottom left+Right
		GL11.glTexCoord2f(18f / 64f, 46f / 64f);
		GL11.glVertex3f(-0.3f, 0.3f, 0);

		// Top right
		GL11.glTexCoord2f(18f / 64f, 0);
		GL11.glVertex3f(-0.3f, 0.3f, -PLAYER_HEIGHT);
		// Top left
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(0.3f, 0.3f, -PLAYER_HEIGHT);

		// Leftwards Side
		// Top right
		GL11.glTexCoord2f(64f / 64f, 37f / 64f);
		GL11.glVertex3f(-0.3f, 0.3f, 0);
		// Bottom left+Right
		GL11.glTexCoord2f(64f / 64f, 54f / 64f);
		GL11.glVertex3f(-0.3f, -0.3f, 0);
		// Bottom left
		GL11.glTexCoord2f(19 / 64f, 54f / 64f);
		GL11.glVertex3f(-0.3f, -0.3f, -PLAYER_HEIGHT);
		// Top left
		GL11.glTexCoord2f(19f / 64f, 37f / 64f);
		GL11.glVertex3f(-0.3f, 0.3f, -PLAYER_HEIGHT);

		// TOP
		// Top right
		GL11.glTexCoord2f(18f / 64f, 46f / 64f);
		GL11.glVertex3f(-0.3f, -0.3f, -PLAYER_HEIGHT);
		// Top left
		GL11.glTexCoord2f(0, 46f / 64f);
		GL11.glVertex3f(0.3f, -0.3f, -PLAYER_HEIGHT);
		// Bottom left
		GL11.glTexCoord2f(0, 64f / 64f);
		GL11.glVertex3f(0.3f, 0.3f, -PLAYER_HEIGHT);
		// Bottom left+Right
		GL11.glTexCoord2f(18f / 64f, 64f / 64f);
		GL11.glVertex3f(-0.3f, 0.3f, -PLAYER_HEIGHT);
		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);
		// HEAD
		GL11.glColor3f(255f / 255f, 187f / 255f, 255f / 255f);
		GL11.glVertex3f(-0.2f, -0.2f, -PLAYER_HEIGHT - 0.5f);
		GL11.glVertex3f(0.2f, -0.2f, -PLAYER_HEIGHT - 0.5f);
		GL11.glVertex3f(0.2f, 0.2f, -PLAYER_HEIGHT - 0.5f);
		GL11.glVertex3f(-0.2f, 0.2f, -PLAYER_HEIGHT - 0.5f);

		GL11.glVertex3f(-0.2f, -0.2f, -PLAYER_HEIGHT);
		GL11.glVertex3f(0.2f, -0.2f, -PLAYER_HEIGHT);
		GL11.glVertex3f(0.2f, -0.2f, -PLAYER_HEIGHT - 0.5f);
		GL11.glVertex3f(-0.2f, -0.2f, -PLAYER_HEIGHT - 0.5f);

		GL11.glVertex3f(0.2f, -0.2f, -PLAYER_HEIGHT);
		GL11.glVertex3f(0.2f, 0.2f, -PLAYER_HEIGHT);
		GL11.glVertex3f(0.2f, 0.2f, -PLAYER_HEIGHT - 0.5f);
		GL11.glVertex3f(0.2f, -0.2f, -PLAYER_HEIGHT - 0.5f);

		GL11.glVertex3f(0.2f, 0.2f, -PLAYER_HEIGHT);
		GL11.glVertex3f(-0.2f, 0.2f, -PLAYER_HEIGHT);
		GL11.glVertex3f(-0.2f, 0.2f, -PLAYER_HEIGHT - 0.5f);
		GL11.glVertex3f(0.2f, 0.2f, -PLAYER_HEIGHT - 0.5f);

		GL11.glVertex3f(-0.2f, 0.2f, -PLAYER_HEIGHT);
		GL11.glVertex3f(-0.2f, -0.2f, -PLAYER_HEIGHT);
		GL11.glVertex3f(-0.2f, -0.2f, -PLAYER_HEIGHT - 0.5f);
		GL11.glVertex3f(-0.2f, 0.2f, -PLAYER_HEIGHT - 0.5f);
		RenderHealthBar();
		GL11.glEnd();
		GL11.glRotatef(Facing * -90f, 0f, 0f, 1f);

	}

	public void GetExperience(int amount) {
		Experience += amount;
		while (Experience >= GetExpNeedToLevel()) {
			Experience -= GetExpNeedToLevel();
			setLevel(getLevel() + 1);
			System.out.println("User levelled up");
			LevelUpPoints += 6;
		}
	}

	public int GetExpNeedToLevel() {
		return getLevel() * 100;
	}

	public Stats GetStats() {
		return Statistics;
	}

	/**
	 * @return the level
	 */
	public int getLevel() {
		return Level;
	}

	/**
	 * @param set
	 *            the player's level
	 */
	public void setLevel(int level) {
		Level = level;
	}

	/**
	 * @return the levelUpPoints
	 */
	public int getLevelUpPoints() {
		return LevelUpPoints;
	}

	/**
	 * @param levelUpPoints
	 *            the levelUpPoints to set
	 */
	public void setLevelUpPoints(int levelUpPoints) {
		LevelUpPoints = levelUpPoints;
	}

	/**
	 * @return the inventory
	 */
	public Inventory getInventory() {
		return inventory;
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return X;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(int x) {
		X = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return Y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(int y) {
		Y = y;
	}

	public TileMap GetLevel(TileMap[] t) {
		return t[WorldNumber];
	}

}

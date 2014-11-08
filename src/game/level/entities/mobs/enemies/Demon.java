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
 * Contains data, stats and texture reference, for the mob
 */
package game.level.entities.mobs.enemies;

import game.level.entities.mobs.LootTable;
import game.level.entities.mobs.Mob;
import game.level.entities.mobs.Stats;
import game.resources.GraphicsHandler.MobArt;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Demon extends Mob {
	private static final long serialVersionUID = 1711886230531813619L;
	private static final float MOB_HEIGHT = 3f;

	public Demon() {
		super();
		Statistics = new Stats(17, 50, 15, 13, 4, 12);
		Loot = LootTable.getLoot(5);

	}

	@Override
	public void Render(int x, int y) {
		GL11.glRotatef(Facing * 90f, 0f, 0f, 1f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Color.white.bind();
		MobArt.Demon.bind();
		GL11.glBegin(GL11.GL_QUADS);

		// Topwards side
		// Top right
		GL11.glTexCoord2f(64f / 64f, 18f / 64f);
		GL11.glVertex3f(-0.3f, -0.3f, 0);
		// Bottom left+Right
		GL11.glTexCoord2f(64f / 64f, 36f / 64f);
		GL11.glVertex3f(0.3f, -0.3f, 0);
		// Bottom left
		GL11.glTexCoord2f(18 / 64f, 36f / 64f);
		GL11.glVertex3f(0.3f, -0.3f, -MOB_HEIGHT);

		// Top left
		GL11.glTexCoord2f(18f / 64f, 18f / 64f);
		GL11.glVertex3f(-0.3f, -0.3f, -MOB_HEIGHT);

		// Rightwards side
		// Top right
		GL11.glTexCoord2f(64f / 64f, 0f / 64f);
		GL11.glVertex3f(0.3f, -0.3f, 0);
		// Bottom Right
		GL11.glTexCoord2f(64f / 64f, 18f / 64f);
		GL11.glVertex3f(0.3f, 0.3f, 0);
		// Bottom left
		GL11.glTexCoord2f(19 / 64f, 18f / 64f);
		GL11.glVertex3f(0.3f, 0.3f, -MOB_HEIGHT);
		// Top left
		GL11.glTexCoord2f(19f / 64f, 0f / 64f);
		GL11.glVertex3f(0.3f, -0.3f, -MOB_HEIGHT);

		// Front
		// Bottom left
		GL11.glTexCoord2f(0, 46f / 64f);
		GL11.glVertex3f(0.3f, 0.3f, 0);
		// Bottom left+Right
		GL11.glTexCoord2f(18f / 64f, 46f / 64f);
		GL11.glVertex3f(-0.3f, 0.3f, 0);

		// Top right
		GL11.glTexCoord2f(18f / 64f, 0);
		GL11.glVertex3f(-0.3f, 0.3f, -MOB_HEIGHT);
		// Top left
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(0.3f, 0.3f, -MOB_HEIGHT);

		// Leftwards Side
		// Top right
		GL11.glTexCoord2f(64f / 64f, 37f / 64f);
		GL11.glVertex3f(-0.3f, 0.3f, 0);
		// Bottom left+Right
		GL11.glTexCoord2f(64f / 64f, 54f / 64f);
		GL11.glVertex3f(-0.3f, -0.3f, 0);
		// Bottom left
		GL11.glTexCoord2f(19 / 64f, 54f / 64f);
		GL11.glVertex3f(-0.3f, -0.3f, -MOB_HEIGHT);
		// Top left
		GL11.glTexCoord2f(19f / 64f, 37f / 64f);
		GL11.glVertex3f(-0.3f, 0.3f, -MOB_HEIGHT);

		// TOP
		// Top right
		GL11.glTexCoord2f(18f / 64f, 46f / 64f);
		GL11.glVertex3f(-0.3f, -0.3f, -MOB_HEIGHT);
		// Top left
		GL11.glTexCoord2f(0, 46f / 64f);
		GL11.glVertex3f(0.3f, -0.3f, -MOB_HEIGHT);
		// Bottom left
		GL11.glTexCoord2f(0, 64f / 64f);
		GL11.glVertex3f(0.3f, 0.3f, -MOB_HEIGHT);
		// Bottom left+Right
		GL11.glTexCoord2f(18f / 64f, 64f / 64f);
		GL11.glVertex3f(-0.3f, 0.3f, -MOB_HEIGHT);

		GL11.glEnd();
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);
		RenderHealthBar();
		GL11.glEnd();
		GL11.glRotatef(Facing * -90f, 0f, 0f, 1f);

	}
}

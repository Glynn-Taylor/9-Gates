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
 * Contains special rendering data, stats and texture 
 reference, for the “Lich” mob; as well as a game victory
 flag.
 */
package game.level.entities.mobs.enemies;

import game.level.entities.mobs.LootTable;
import game.level.entities.mobs.Mob;
import game.level.entities.mobs.Stats;
import game.resources.GraphicsHandler.MobArt;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

public class Lich extends Mob {
	private static final long serialVersionUID = -8104770569935956176L;
	private static final float MOB_HEIGHT = 4f;
	private float YawRotate = 6;

	public Lich() {
		super();
		Statistics = new Stats(8, 30, 5, 7, 2, 7);
		Statistics.setIsLich(true);
		Loot = LootTable.getLoot(6);

	}

	@Override
	public void Render(int x, int y) {
		GL11.glRotatef(Facing * 90f, 0f, 0f, 1f);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		Color.white.bind();
		MobArt.Lich.bind();
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
		RenderCrystal(1, 1);
		RenderCrystal(1, 80);
		RenderCrystal(1, 170);

	}

	private void RenderCrystal(int i, int j) {
		GL11.glTranslated(Math.cos((YawRotate + j) / 180 * Math.PI) * i,
				Math.sin((YawRotate + j) / 180f * Math.PI) * i, 0);
		GL11.glRotatef(YawRotate, 0, 0, 1);
		GL11.glBegin(GL11.GL_TRIANGLES);

		GL11.glColor3f(0, 0, 1);
		GL11.glVertex3f(+0.1f, -0.1f, -0.1f);
		GL11.glVertex3f(-0.1f, -0.1f, -0.1f);
		GL11.glColor3f(1, 1, 1);
		GL11.glVertex3f(0, 0, -1f);

		GL11.glColor3f(0, 0, 1);
		GL11.glVertex3f(-0.1f, -0.1f, -0.1f);
		GL11.glVertex3f(0, +0.1f, -0.1f);
		GL11.glColor3f(1, 1, 1);
		GL11.glVertex3f(0, 0, -1f);

		GL11.glColor3f(0, 0, 1);
		GL11.glVertex3f(0, +0.1f, -0.1f);
		GL11.glVertex3f(+0.1f, -0.1f, -0.1f);
		GL11.glColor3f(1, 1, 1);
		GL11.glVertex3f(0, 0, -1f);

		GL11.glEnd();

		GL11.glRotatef(-YawRotate, 0, 0, 1);
		GL11.glTranslated(-Math.cos((YawRotate + j) / 180 * Math.PI) * i,
				-Math.sin((YawRotate + j) / 180f * Math.PI) * i, 0);
		YawRotate += 8;

	}
}

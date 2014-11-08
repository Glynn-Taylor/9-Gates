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
 * Handles transitioning between levels
 */
package game.level.entities.furniture;

import game.level.entities.mobs.Mob;
import game.level.entities.mobs.Player;

import org.lwjgl.opengl.GL11;

public class Portal extends Furniture {

	private static final long serialVersionUID = 3083672002165744126L;
	private final int Direction;
	private float YawRotation = 0;

	public Portal(int direction) {
		IsMob = false;
		Direction = direction;
	}

	@Override
	public void Interact(Mob m) {
		if (m.IsPlayer) {
			Player p = (Player) m;
			p.WorldNumber += Direction;
		}

	}

	@Override
	public void Render(int x, int y) {
		GL11.glRotatef(YawRotation, 0, 0, 1);
		GL11.glBegin(GL11.GL_LINE_LOOP);

		if (Direction > 0) {
			GL11.glColor3f(139f / 256f, 34f / 256f, 82f / 256f);
		} else {
			GL11.glColor3f(224f / 256f, 102f / 256f, 255f / 256f);
		}
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);

		GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		GL11.glVertex3f(0.5f, 0.5f, -0.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -0.5f);

		GL11.glVertex3f(-0.5f, -0.5f, 0f);
		GL11.glVertex3f(0.5f, -0.5f, 0f);

		GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);

		GL11.glVertex3f(0.5f, -0.5f, 0f);
		GL11.glVertex3f(0.5f, 0.5f, 0f);

		GL11.glVertex3f(0.5f, 0.5f, -0.5f);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);

		GL11.glVertex3f(0.5f, 0.5f, 0f);
		GL11.glVertex3f(-0.5f, 0.5f, 0f);

		GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
		GL11.glVertex3f(0.5f, 0.5f, -0.5f);

		GL11.glVertex3f(-0.5f, 0.5f, 0f);
		GL11.glVertex3f(-0.5f, -0.5f, 0f);

		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -0.5f);

		GL11.glVertex3f(-0.3f, -0.3f, 0f);
		GL11.glVertex3f(0.3f, -0.3f, 0f);

		GL11.glVertex3f(0.3f, -0.3f, -4.5f);
		GL11.glVertex3f(-0.3f, -0.3f, -4.5f);

		GL11.glVertex3f(0.3f, -0.3f, 0f);
		GL11.glVertex3f(0.3f, 0.3f, 0f);

		GL11.glVertex3f(0.3f, 0.3f, -4.5f);
		GL11.glVertex3f(0.3f, -0.3f, -4.5f);

		GL11.glVertex3f(0.3f, 0.3f, 0f);
		GL11.glVertex3f(-0.3f, 0.3f, 0f);

		GL11.glVertex3f(-0.3f, 0.3f, -4.5f);
		GL11.glVertex3f(0.3f, 0.3f, -4.5f);

		GL11.glVertex3f(-0.3f, 0.3f, 0f);
		GL11.glVertex3f(-0.3f, -0.3f, 0f);

		GL11.glVertex3f(-0.3f, -0.3f, -4.5f);
		GL11.glVertex3f(-0.3f, 0.3f, -4.5f);

		GL11.glVertex3f(-0.3f, -0.3f, -4.5f);
		GL11.glVertex3f(0.3f, -0.3f, -4.5f);
		GL11.glVertex3f(0.3f, 0.3f, -4.5f);
		GL11.glVertex3f(-0.3f, 0.3f, -4.5f);

		// TOP OF PILLAR

		GL11.glVertex3f(-0.5f, -0.5f, -4.5f);

		GL11.glVertex3f(0.5f, -0.5f, -4.5f);
		GL11.glVertex3f(0.5f, 0.5f, -4.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -4.5f);

		GL11.glVertex3f(-0.5f, -0.5f, -4f);
		GL11.glVertex3f(0.5f, -0.5f, -4f);

		GL11.glVertex3f(0.5f, -0.5f, -4.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -4.5f);

		GL11.glVertex3f(0.5f, -0.5f, -4f);
		GL11.glVertex3f(0.5f, 0.5f, -4f);

		GL11.glVertex3f(0.5f, 0.5f, -4.5f);
		GL11.glVertex3f(0.5f, -0.5f, -4.5f);

		GL11.glVertex3f(0.5f, 0.5f, -4f);
		GL11.glVertex3f(-0.5f, 0.5f, -4f);

		GL11.glVertex3f(-0.5f, 0.5f, -4.5f);
		GL11.glVertex3f(0.5f, 0.5f, -4.5f);

		GL11.glVertex3f(-0.5f, 0.5f, -4f);
		GL11.glVertex3f(-0.5f, -0.5f, -4f);

		GL11.glVertex3f(-0.5f, -0.5f, -4.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -4.5f);

		GL11.glEnd();
		GL11.glRotatef(-YawRotation, 0, 0, 1);
		YawRotation += 12;
	}

}

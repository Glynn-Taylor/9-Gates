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
 * Static piece of scenery
 */
package game.level.entities.furniture;

import game.level.entities.mobs.Mob;

import org.lwjgl.opengl.GL11;

public class Pillar extends Furniture {

	private static final long serialVersionUID = -1308503601402431644L;

	public Pillar() {
		IsMob = false;
	}

	@Override
	public void Render(int x, int y) {
		GL11.glBegin(GL11.GL_QUADS);

		// BOTTOM OF PILLAR
		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		GL11.glVertex3f(0.5f, 0.5f, -0.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -0.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(-0.5f, -0.5f, 0f);
		GL11.glVertex3f(0.5f, -0.5f, 0f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(0.5f, -0.5f, 0f);
		GL11.glVertex3f(0.5f, 0.5f, 0f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(0.5f, 0.5f, -0.5f);
		GL11.glVertex3f(0.5f, -0.5f, -0.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(0.5f, 0.5f, 0f);
		GL11.glVertex3f(-0.5f, 0.5f, 0f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -0.5f);
		GL11.glVertex3f(0.5f, 0.5f, -0.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(-0.5f, 0.5f, 0f);
		GL11.glVertex3f(-0.5f, -0.5f, 0f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -0.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(-0.3f, -0.3f, 0f);
		GL11.glVertex3f(0.3f, -0.3f, 0f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(0.3f, -0.3f, -4.5f);
		GL11.glVertex3f(-0.3f, -0.3f, -4.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(0.3f, -0.3f, 0f);
		GL11.glVertex3f(0.3f, 0.3f, 0f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(0.3f, 0.3f, -4.5f);
		GL11.glVertex3f(0.3f, -0.3f, -4.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(0.3f, 0.3f, 0f);
		GL11.glVertex3f(-0.3f, 0.3f, 0f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(-0.3f, 0.3f, -4.5f);
		GL11.glVertex3f(0.3f, 0.3f, -4.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(-0.3f, 0.3f, 0f);
		GL11.glVertex3f(-0.3f, -0.3f, 0f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(-0.3f, -0.3f, -4.5f);
		GL11.glVertex3f(-0.3f, 0.3f, -4.5f);

		GL11.glColor3f(0.4f, 0.4f, 0.4f);
		GL11.glVertex3f(-0.3f, -0.3f, -4.5f);
		GL11.glVertex3f(0.3f, -0.3f, -4.5f);
		GL11.glVertex3f(0.3f, 0.3f, -4.5f);
		GL11.glVertex3f(-0.3f, 0.3f, -4.5f);

		// TOP OF PILLAR

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(-0.5f, -0.5f, -4.5f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(0.5f, -0.5f, -4.5f);
		GL11.glVertex3f(0.5f, 0.5f, -4.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -4.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(-0.5f, -0.5f, -4f);
		GL11.glVertex3f(0.5f, -0.5f, -4f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(0.5f, -0.5f, -4.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -4.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(0.5f, -0.5f, -4f);
		GL11.glVertex3f(0.5f, 0.5f, -4f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(0.5f, 0.5f, -4.5f);
		GL11.glVertex3f(0.5f, -0.5f, -4.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(0.5f, 0.5f, -4f);
		GL11.glVertex3f(-0.5f, 0.5f, -4f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -4.5f);
		GL11.glVertex3f(0.5f, 0.5f, -4.5f);

		GL11.glColor3f(0.8f, 0.8f, 0.8f);
		GL11.glVertex3f(-0.5f, 0.5f, -4f);
		GL11.glVertex3f(-0.5f, -0.5f, -4f);
		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -4.5f);
		GL11.glVertex3f(-0.5f, 0.5f, -4.5f);

		GL11.glEnd();

	}

	@Override
	public void Interact(Mob m) {
		System.out.println("Hit pillar");

	}

}

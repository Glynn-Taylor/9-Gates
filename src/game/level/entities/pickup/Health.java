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
 * Pickup that creates a health potion in the player's 
 inventory when picked up.
 */
package game.level.entities.pickup;

import game.level.entities.mobs.Mob;
import game.level.entities.mobs.Player;
import game.level.entities.mobs.player.items.standard.HealthPotion;

import org.lwjgl.opengl.GL11;

public class Health extends Pickup {
	private static final long serialVersionUID = 4428490012375659020L;
	private float YawRotate = 6;

	@Override
	public void Render(int x, int y) {

		GL11.glRotatef(YawRotate, 0, 0, 1);
		GL11.glBegin(GL11.GL_TRIANGLES);

		GL11.glColor3f(1, 0, 0);
		GL11.glVertex3f(+0.5f, -0.5f, -0.5f);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glColor3f(1, 1, 1);
		GL11.glVertex3f(0, 0, -1f);

		GL11.glColor3f(1, 0, 0);
		GL11.glVertex3f(-0.5f, -0.5f, -0.5f);
		GL11.glVertex3f(0, +0.5f, -0.5f);
		GL11.glColor3f(1, 1, 1);
		GL11.glVertex3f(0, 0, -1f);

		GL11.glColor3f(1, 0, 0);
		GL11.glVertex3f(0, +0.5f, -0.5f);
		GL11.glVertex3f(+0.5f, -0.5f, -0.5f);
		GL11.glColor3f(1, 1, 1);
		GL11.glVertex3f(0, 0, -1f);

		GL11.glEnd();

		GL11.glRotatef(-YawRotate, 0, 0, 1);

		YawRotate++;
	}

	@Override
	public void Get(Mob m) {
		if (m.IsPlayer) {
			Player p = (Player) m;
			p.getInventory().AddItem(new HealthPotion(10));
		}

	}

}

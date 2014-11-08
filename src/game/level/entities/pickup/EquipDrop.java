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
 * A dropped form (entity) of an equipable pickup
 */
package game.level.entities.pickup;

import game.level.entities.mobs.Mob;
import game.level.entities.mobs.Player;
import game.level.entities.mobs.player.items.EquipableItem;

import org.lwjgl.opengl.GL11;

public class EquipDrop extends EquipablePickup {
	private static final long serialVersionUID = -8987697108026251707L;
	private float YawRotate = 6;
	private String Name;
	private final int Damage = 0;
	private final EquipableItem DroppedItem;

	public EquipDrop(EquipableItem Item) {
		DroppedItem = Item;
	}

	@Override
	public void Render(int x, int y) {

		GL11.glRotatef(YawRotate, 0, 0, 1);
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glColor3f(0.5f, 0.5f, 0.5f);
		GL11.glVertex3f(+0.3f, -0.3f, -0.5f);
		GL11.glVertex3f(-0.3f, -0.3f, -0.5f);
		GL11.glVertex3f(-0.3f, 0.3f, -0.5f);
		GL11.glVertex3f(0.3f, 0.3f, -0.5f);

		GL11.glColor3f(1, 1, 1);
		GL11.glVertex3f(+0.2f, -0.2f, -1f);
		GL11.glVertex3f(-0.2f, -0.2f, -1f);
		GL11.glVertex3f(-0.2f, 0.2f, -1f);
		GL11.glVertex3f(0.2f, 0.2f, -1f);

		GL11.glEnd();

		GL11.glRotatef(-YawRotate, 0, 0, 1);

		YawRotate++;
	}

	@Override
	public void Get(Mob m) {
		if (m.IsPlayer) {
			Player p = (Player) m;
			p.getInventory().AddItem(DroppedItem);

		}

	}

}

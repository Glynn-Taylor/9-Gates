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
 * A lookup for what enemies drop on death
 */
package game.level.entities.mobs;

import game.level.entities.mobs.player.items.equipable.Boots;
import game.level.entities.mobs.player.items.equipable.Chest;
import game.level.entities.mobs.player.items.equipable.Head;
import game.level.entities.mobs.player.items.equipable.Legs;
import game.level.entities.mobs.player.items.equipable.Neck;
import game.level.entities.mobs.player.items.equipable.Ring;
import game.level.entities.mobs.player.items.equipable.Shield;
import game.level.entities.mobs.player.items.equipable.Sword;
import game.level.entities.pickup.EquipDrop;
import game.level.entities.pickup.Experience;
import game.level.entities.pickup.Pickup;

import java.util.Random;

public final class LootTable {

	private static Random rGen = new Random();

	public static Pickup getLoot(int level) {
		if (rGen.nextInt(100) < 95) {
			return new Experience(rGen.nextInt((level + 1) * 10) + 10);
		} else {
			int Type = rGen.nextInt(8);
			switch (Type) {
			case 0:
				return new EquipDrop(new Sword(level));
			case 1:
				return new EquipDrop(new Shield(level));
			case 2:
				return new EquipDrop(new Ring(level));
			case 3:
				return new EquipDrop(new Legs(level));
			case 4:
				return new EquipDrop(new Head(level));
			case 5:
				return new EquipDrop(new Chest(level));
			case 6:
				return new EquipDrop(new Boots(level));
			case 7:
				return new EquipDrop(new Neck(level));
			}
			return new Experience(rGen.nextInt((level + 1) * 10) + 10);
		}

	}
}

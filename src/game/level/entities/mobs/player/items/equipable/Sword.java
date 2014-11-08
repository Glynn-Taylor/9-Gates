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
 * An equipable item, handles initial creation, what stats it 
 effects and adds random changes to the magnitude of 
 the effects. Also takes into account the slot which the 
 item will fit into.
 */
package game.level.entities.mobs.player.items.equipable;

import game.level.entities.mobs.player.items.EquipableItem;
import game.level.entities.pickup.EquipDrop;
import game.level.entities.pickup.Pickup;

import java.util.Random;

public class Sword extends EquipableItem {
	private static final long serialVersionUID = -4315644729889252704L;
	private static String[][] names = {
			{ "Rubber sword", "Novelty sword" },
			{ "Pointless sword", "Broken shortsword" },
			{ "Rusty sword", "Decrepit longsword" },
			{ "Pointy sword", "Training sword", "Worn blade" },
			{ "Sword of benign malevolence", "Poisoned blade",
					"Well crafted blade" },
			{ "Sword of moderate doom", "Sword of malignent evil",
					"Demonic blade" },
			{ "Crystal sword", "The shining destroyer" }

	};
	private static int[] damageLimits = { 1, 1, 1, 2, 3, 2, 3, 4, 10, 5, 15, 5,
			35, 60 };

	public Sword() {
		Random r = new Random();
		int Choice = r.nextInt(names.length);
		Name = names[Choice][r.nextInt(names[Choice].length)];
		Slot = 4;
		XTextureRef = 1;
		YTextureRef = Choice;
		SecondaryModifiers[8] = r.nextInt(damageLimits[Choice * 2 + 1] + 1)
				+ damageLimits[Choice * 2];

	}

	public Sword(int Choice) {
		Random r = new Random();
		Name = names[Choice][r.nextInt(names[Choice].length)];
		Slot = 4;
		XTextureRef = 1;
		YTextureRef = Choice;
		SecondaryModifiers[8] = r.nextInt(damageLimits[Choice * 2 + 1] + 1)
				+ damageLimits[Choice * 2];

	}

	public Sword(String name, int damage) {
		Name = name;
		Slot = 4;
		XTextureRef = 1;
		YTextureRef = FindIndexOf(name);
		SecondaryModifiers[8] = damage;

	}

	private int FindIndexOf(String name) {
		for (int i = 0; i < names.length; i++) {
			for (int i2 = 0; i2 < names.length; i2++) {
				if (names[i][i2].equals(name))
					return i;
			}
		}
		return 0;
	}

	@Override
	protected Pickup DropSpecificItem() {
		return new EquipDrop(this);

	}

	@Override
	protected void SetModifiers() {

	}

}

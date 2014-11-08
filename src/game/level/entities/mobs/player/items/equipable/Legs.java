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

public class Legs extends EquipableItem {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2951711699248311519L;
	private static String[][] names = { { "Leggings" }, { "Torn trousers" },
			{ "Chainmail leggings" },
			{ "Conjurer's trousers", "Stylish leggings" },
			{ "Leaves..", "Umm... trousers?" },
			{ "Cardboard box", "Box of ultimate defense" },
			{ "Trousers of ultimate DOOM" }

	};
	private static int[] PRLimits = { 2, 3, 10, 5, 3, 4, 3, 2, 1, 2, 1, 1,

	35, 60 };

	public Legs() {
		Random r = new Random();
		// 07 MG
		// 10 Dodge chance
		// 02 PR
		int Choice = r.nextInt(names.length);
		Name = names[Choice][r.nextInt(names[Choice].length)];
		Slot = 6;
		XTextureRef = 3;
		YTextureRef = Choice;
		SecondaryModifiers[2] = r.nextInt(PRLimits[Choice * 2 + 1] + 1)
				+ PRLimits[Choice * 2];
		SecondaryModifiers[7] = (r.nextInt(PRLimits[Choice * 2 + 1] + 1) + PRLimits[Choice * 2]) / 2;
		SecondaryModifiers[10] = (r.nextInt(PRLimits[Choice * 2 + 1] + 1)) / -2;

	}

	public Legs(int Choice) {
		Random r = new Random();
		Name = names[Choice][r.nextInt(names[Choice].length)];
		Slot = 6;
		XTextureRef = 3;
		YTextureRef = Choice;
		SecondaryModifiers[2] = r.nextInt(PRLimits[Choice * 2 + 1] + 1)
				+ PRLimits[Choice * 2];
		SecondaryModifiers[7] = (r.nextInt(PRLimits[Choice * 2 + 1] + 1) + PRLimits[Choice * 2]) / 2;
		SecondaryModifiers[10] = (r.nextInt(PRLimits[Choice * 2 + 1] + 1)) / -2;

	}

	public Legs(String name, int pr, int mr, int dc) {
		Name = name;
		Slot = 6;
		XTextureRef = 3;
		YTextureRef = FindIndexOf(name);
		SecondaryModifiers[2] = pr;
		SecondaryModifiers[7] = mr;
		SecondaryModifiers[10] = dc;

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

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

public class Shield extends EquipableItem {
	private static final long serialVersionUID = 6201763301358136190L;
	private static String[][] names = {
			{ "Battered wooden shield", "Deku shield" },
			{ "Trash can lid", "Metal buckler" },
			{ "Ancient shield", "Worn round shield" },
			{ "Wooden scutum shield", "Carved wooden shield", "Practice shield" },
			{ "Iron scutum shield", "Iron square shield",
					"Legionnaire's shield" },
			{ "Golden buckler", "Shiny shield", "The reflector" },
			{ "Crystal shield", "Holy defender" }

	};
	private static int[] PRLimits = { 15, 5, 10, 5, 3, 4, 3, 2, 1, 2, 1, 1,

	35, 60 };

	public Shield() {
		Random r = new Random();
		// 07 MG
		// 10 Dodge chance
		// 02 PR
		int Choice = r.nextInt(names.length);
		Name = names[Choice][r.nextInt(names[Choice].length)];
		Slot = 5;
		XTextureRef = 7;
		YTextureRef = Choice;
		SecondaryModifiers[2] = r.nextInt(PRLimits[Choice * 2 + 1] + 1)
				+ PRLimits[Choice * 2];
		SecondaryModifiers[7] = (r.nextInt(PRLimits[Choice * 2 + 1] + 1) + PRLimits[Choice * 2]) / 2;
		SecondaryModifiers[10] = (r.nextInt(PRLimits[Choice * 2 + 1] + 1)) / -2;

	}

	public Shield(int Choice) {
		Random r = new Random();
		// 07 MG
		// 10 Dodge chance
		// 02 PR
		Name = names[Choice][r.nextInt(names[Choice].length)];
		Slot = 5;
		XTextureRef = 7;
		YTextureRef = Choice;
		SecondaryModifiers[2] = r.nextInt(PRLimits[Choice * 2 + 1] + 1)
				+ PRLimits[Choice * 2];
		SecondaryModifiers[7] = (r.nextInt(PRLimits[Choice * 2 + 1] + 1) + PRLimits[Choice * 2]) / 2;
		SecondaryModifiers[10] = (r.nextInt(PRLimits[Choice * 2 + 1] + 1)) / -2;

	}

	public Shield(String name, int pr, int mr, int dc) {
		Name = name;
		Slot = 5;
		XTextureRef = 7;
		YTextureRef = FindIndexOf(name);
		SecondaryModifiers[2] = pr;
		SecondaryModifiers[7] = mr;
		SecondaryModifiers[10] = dc;

	}

	private int FindIndexOf(String name) {
		for (int i = 0; i < names.length; i++) {

			if (names[i].equals(name))
				return i;

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

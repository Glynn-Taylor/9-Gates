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
 * An inventory item for gold – currently has no physical 
 use in the game as there are no shops.
 */
package game.level.entities.mobs.player.items.standard;

import game.level.entities.mobs.Player;
import game.level.entities.mobs.player.Inventory;
import game.level.entities.mobs.player.items.StandardItem;
import game.level.entities.pickup.Health;
import game.level.entities.pickup.Pickup;

public class Gold extends StandardItem {
	private static final long serialVersionUID = -269398497830174894L;
	private final int Value;

	public Gold(int value) {
		XTextureRef = 3;
		Value = value;
	}

	@Override
	public boolean UseItem(Player p, Inventory i) {
		return false;

	}

	@Override
	protected Pickup DropItem() {
		return new Health();

	}

	@Override
	public String getName() {
		return "Gold(" + Integer.toString(Value) + ")";

	}

}

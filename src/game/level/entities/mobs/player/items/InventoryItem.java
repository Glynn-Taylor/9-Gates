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
 * Abstract base class for an item that can be in the 
 inventory (parent of equipable item and standard item)
 */
package game.level.entities.mobs.player.items;

import game.level.entities.mobs.Player;
import game.level.entities.mobs.player.Inventory;
import game.level.entities.pickup.Pickup;

import java.io.Serializable;

public abstract class InventoryItem implements Serializable {
	private static final long serialVersionUID = 9193421547929989106L;

	protected static final int IconWidth = 50;

	protected int XTextureRef = 0, YTextureRef = 0;

	public abstract void Render(int ScreenX, int ScreenY);

	protected boolean Equipable;

	public InventoryItem() {

	}

	public abstract boolean UseItem(Player p, Inventory i);

	protected abstract Pickup DropItem();

	public int getWidth() {
		return IconWidth;
	}

	public abstract String getName();

	/**
	 * @return the isEquippable
	 */
	public boolean isEquipable() {
		return Equipable;
	}

}

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
 * Holds an inventory item and keeps track of the amount 
 of each of them.
 */
package game.level.entities.mobs.player.items;

import game.level.entities.pickup.Pickup;

import java.io.Serializable;

public class InventorySlot implements Serializable {
	private static final long serialVersionUID = -576229832310710145L;
	private int Amount = 0;
	private InventoryItem Item = null;

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return Amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(int amount) {
		Amount = amount;
	}

	/**
	 * @return the item
	 */
	public InventoryItem getItem() {
		return Item;
	}

	public Pickup DropItem() {
		Pickup returnItem = Item.DropItem();
		Amount--;
		CheckAmount();
		return returnItem;
	}

	public void ItemDestroyed() {
		Amount--;
		CheckAmount();
	}

	private void CheckAmount() {
		if (Amount <= 0) {
			Amount = 0;
			Item = null;
		}
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(InventoryItem item) {
		this.Item = item;
	}

	public void addNewItem(InventoryItem item) {
		this.Item = item;
		Amount = 1;
	}
}

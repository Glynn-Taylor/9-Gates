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
 * Abstract base class for pickups that give the player 
 equipable items.
 */
package game.level.entities.pickup;

public abstract class EquipablePickup extends Pickup {
	private static final long serialVersionUID = 6257811150029958901L;
	protected int Level = -1;

	public void setLevel(int l) {
		Level = l;
	}
}

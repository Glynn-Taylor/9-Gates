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
 * Abstract base class for pickups
 */
package game.level.entities.pickup;

import game.level.entities.Entity;
import game.level.entities.mobs.Mob;

public abstract class Pickup extends Entity {
	private static final long serialVersionUID = 6010318952821152331L;

	public abstract void Get(Mob m);

}

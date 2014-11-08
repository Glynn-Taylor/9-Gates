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
 * Abstract base class for scenery/useable objects
 */
package game.level.entities.furniture;

import game.level.entities.Entity;
import game.level.entities.mobs.Mob;

public abstract class Furniture extends Entity {

	private static final long serialVersionUID = 7914980190800422662L;
	public Entity HolderEntity;
	public boolean WalkAble;
	public boolean IsDead;

	public abstract void Interact(Mob m);

}

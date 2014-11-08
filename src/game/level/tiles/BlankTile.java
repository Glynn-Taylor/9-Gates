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
 * Placeholder tile, prevents rendering
 */
package game.level.tiles;

public class BlankTile extends MapTile {

	private static final long serialVersionUID = -7637854799277780388L;

	public BlankTile() {
		IsFloor = false;
	}

	@Override
	public void Render(int x, int y) {

	}

}

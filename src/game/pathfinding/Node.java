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
 * The equivalent of a tile, for use in pathfinding.
 */
package game.pathfinding;

import java.io.Serializable;

public class Node implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7808083027569765230L;
	public boolean Active;
	// F=Total estimated cost
	public int F = 0;
	public Node ParentNode;
	// G=Distance from starting node
	// H=Distance left to B
	private int X, Y;
	private int G = 0;

	public Node(boolean isActive) {
		Active = isActive;
	}

	public Node AddParent(Node parentNode) {
		ParentNode = parentNode;
		return this;
	}

	public void SetParent(Node parentNode) {
		ParentNode = parentNode;

	}

	public void SetG(int additive) {
		G = ParentNode.GetG() + additive;
	}

	public int GetG() {
		return G;
	}

	public void SetF(int H) {
		F = G + H;

	}

	public Point GetPoint() {
		return new Point(X, Y);
	}

	public void SetPoint(int x, int y) {
		X = x;
		Y = y;
	}
}

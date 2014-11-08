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
 * Handles pathfinding logic, involving manipulation of nodes, implements A* algorithm.
 */
package game.pathfinding;

import game.level.tiles.MapTile;

import java.io.Serializable;
import java.util.ArrayList;

public class NodeMap implements Serializable {
	private static final long serialVersionUID = 8070866163959095149L;
	private final ArrayList<Node> OpenList = new ArrayList<Node>();
	private final ArrayList<Node> ClosedList = new ArrayList<Node>();
	private Node[][] nodeMap;
	private int MapWidth;
	private int MapHeight;
	private int lowestFX, lowestFY;

	public void GenerateNodeMap(MapTile[][] map) {
		MapHeight = map.length;
		MapWidth = map[0].length;
		nodeMap = new Node[MapHeight][MapWidth];
		for (int y = 0; y < MapHeight; y++) {
			for (int x = 0; x < MapWidth; x++) {
				nodeMap[y][x] = new Node(map[y][x].IsFloor);
			}
		}
	}

	public Point FindPath2(int aX, int aY, int bX, int bY) {
		OpenList.clear();
		ClosedList.clear();
		// Removed at end of AddSurrounding
		OpenList.add(nodeMap[aY][aX]);

		ArrayList<Point> points = new ArrayList<Point>();
		int lowestF = 0;
		Node lowestN = null;
		while (!OpenList.contains(nodeMap[bY][bX]) && OpenList.size() > 0
				&& !ClosedList.contains(nodeMap[bY][bX])) {
			lowestF = 0;
			for (int i = 0; i < OpenList.size(); i++) {
				if (OpenList.get(i).F < lowestF || lowestF == 0) {
					lowestN = OpenList.get(i);
					lowestF = lowestN.F;
					for (int y = 0; y < MapHeight; y++) {
						for (int x = 0; x < MapWidth; x++) {
							if (nodeMap[y][x] == lowestN) {
								lowestFX = x;
								lowestFY = y;
							}
						}
					}
				}
			}

			OpenList.remove(lowestN);
			ClosedList.add(lowestN);
			Node currentNode;
			for (int y = -1; y < 2; y++) {
				for (int x = -1; x < 2; x++) {
					if (x * x != y * y) {
						currentNode = nodeMap[lowestFY + y][lowestFX + x];
						if (!currentNode.Active) {
						} else if (ClosedList.contains(currentNode)) {

						} else {
							if (OpenList.contains(currentNode)) {
								int GCost = 0;
								if (y != 0 && x != 0) {
									GCost = 14;
								} else {
									GCost = 10;
								}
								if (currentNode.GetG() > GCost + lowestN.GetG()) {
									currentNode.SetParent(lowestN);
									currentNode.SetG(GCost);
									int cost = 10 * (Math.abs((lowestFX + x)
											- bX) + Math.abs((lowestFY + y)
											- bY));
									currentNode.SetF(cost);
								}
							} else {
								OpenList.add(currentNode);
								currentNode
										.SetPoint(lowestFX + x, lowestFY + y);
								currentNode.SetParent(lowestN);
								if (y != 0 && x != 0) {
									currentNode.SetG(14);
								} else {
									currentNode.SetG(10);
								}
								int cost = 10 * (Math.abs((lowestFX + x) - bX) + Math
										.abs((lowestFY + y) - bY));
								currentNode.SetF(cost);

							}
						}
					}
				}
			}
		}
		Node traceNode = nodeMap[bY][bX];
		while (traceNode != nodeMap[aY][aX]) {

			if (traceNode.ParentNode != null
					&& traceNode.ParentNode != traceNode) {
				points.add(traceNode.GetPoint());
				traceNode = traceNode.ParentNode;
			} else {
				System.out.println("null node");
				return null;
			}

		}
		return points.get(points.size() - 1);
	}
}

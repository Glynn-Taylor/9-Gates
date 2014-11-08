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
 * Handles GUI elements, allows them to be added and 
 taken away easily using methods, bulk processes input 
 and handles mouse click events
 */
package game.graphics;

import game.level.entities.mobs.Player;
import game.level.map.TileMap;

import java.util.ArrayList;

import org.newdawn.slick.opengl.Texture;

public class GUI_Layer {
	private final ArrayList<GUI_Button> Buttons = new ArrayList<GUI_Button>();
	private boolean[] ButtonClicked;
	private static long LastClickTime;
	private final long RegisterClickDelay = 300;
	private boolean Enabled = true;
	private GUI_Console Console;
	private final ArrayList<GUI_Object> objects = new ArrayList<GUI_Object>();

	public void Render() {
		if (Enabled) {
			for (int i = 0; i < Buttons.size(); i++) {
				Buttons.get(i).Render();
			}
			if (Console != null)
				Console.Render();
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i).Enabled)
					objects.get(i).Render();
			}
		}

	}

	public void toggleObjectEnabled(int i) {
		objects.get(i).toggleEnabled();
	}

	public void setObjectEnabled(int i, boolean b) {
		objects.get(i).SetEnabled(b);
	}

	public void ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (Enabled) {
			for (int i = 0; i < Buttons.size(); i++) {
				if (Buttons.get(i).InsideButton(mouseX, mouseY)
						&& mouseDown
						&& System.currentTimeMillis() - LastClickTime > RegisterClickDelay) {
					ButtonClicked[i] = true;
					LastClickTime = System.currentTimeMillis();
				} else {
					ButtonClicked[i] = false;
				}
			}
			if (Console != null) {
				Console.ProcessInput();
			}
			for (int i = 0; i < objects.size(); i++) {
				if (objects.get(i).Enabled)
					objects.get(i).ProcessInput(mouseX, mouseY, mouseDown);
			}
		}
	}

	public void AddButton(int startX, int startY, int width, int height,
			Texture t) {
		Buttons.add(new GUI_Button(startX, startY, width, height, t));
		ButtonClicked = new boolean[Buttons.size()];
	}

	public void AddConsole(int startX, int startY, int width, int height,
			String fontName, TileMap t, Player p) {
		Console = new GUI_Console(startX, startY, startX + width, startY
				+ height, fontName, t, p);
	}

	public void AddStatDisplay(int startX, int startY, int width, int height,
			String fontName, Player p) {
		objects.add(new GUI_StatDisplay(startX, startY, width, height,
				fontName, p));
	}

	public void AddInvDisplay(int startX, int startY, int width, int height,
			String fontName, Player p, TileMap[] t) {
		objects.add(new GUI_InvDisplay(startX, startY, width, height, fontName,
				p, t));
	}

	public void SetEnabled(boolean i) {
		Enabled = i;
	}

	public boolean getConsoleEnabled() {
		if (Console != null)
			return Console.getEnabled();
		return false;
	}

	public void setConsoleEnabled(boolean b) {
		if (Console != null)
			Console.SetEnabled(b);

	}

	public void toggleConsole() {
		if (Console != null)
			Console.toggleEnabled();
	}

	public void toggleEnabled() {
		Enabled = !Enabled;
	}

	public boolean isButtonDown(int i) {
		if (Enabled) {
			if (ButtonClicked[i] == true) {
				ButtonClicked[i] = false;
				return true;
			}
		}
		return false;
	}
}

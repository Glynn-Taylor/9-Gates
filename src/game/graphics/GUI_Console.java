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
 * Creates a console for debugging, very specialized input 
 handling/output
 */
package game.graphics;

import game.level.entities.mobs.Player;
import game.level.map.TileMap;

import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GUI_Console {

	int ScreenX, ScreenY, ScreenButtonWidth, ScreenButtonHeight;
	Texture texture;
	UnicodeFont font;
	private String Log = ">WELCOME GUEST";
	private String Prompt = "";
	private boolean Enabled = false;
	private final int CharacterWidth;
	private final int LineHeight;
	private final TileMap map;
	private final Player player;
	private boolean DevMode = true;

	public void Render() {
		if (Enabled) {
			texture.bind();
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(ScreenX, ScreenY, 0);

			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(ScreenX, ScreenY + ScreenButtonHeight, 0);

			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(ScreenX + ScreenButtonWidth, ScreenY
					+ ScreenButtonHeight, 0);

			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(ScreenX + ScreenButtonWidth, ScreenY, 0);

			GL11.glEnd();
			font.drawString(ScreenX + 10, ScreenY + 10, Log, Color.green);
			font.drawString(ScreenX + 10, ScreenY + ScreenButtonHeight - 30,
					"> " + Prompt, Color.green);
		}

	}

	public void ProcessInput() {
		if (Enabled) {
			while (Keyboard.next()) {

				if (Keyboard.getEventKeyState()) {
					if (Keyboard.isKeyDown(Keyboard.KEY_BACK)) {
						Prompt = Prompt.substring(0, Prompt.length() - 1);
					} else if (Keyboard.getEventKey() == Keyboard.KEY_RETURN) {
						Log += '\n' + Prompt;
						if (Prompt.charAt(0) == '/') {
							if (DevMode) {
								getAdminCommand(Prompt.substring(1));
							} else {
								getCommand(Prompt.substring(1));
							}
						}
						while (CountLines() > LineHeight) {
							DeleteLine();
						}
						Prompt = "";
					} else {
						if (Prompt.length() <= CharacterWidth)
							Prompt += Keyboard.getEventCharacter();
					}

				}
			}
		}
	}

	public boolean getEnabled() {
		return Enabled;
	}

	public GUI_Console(int startX, int startY, int width, int height,
			String fontName, TileMap t, Player p) {

		ScreenX = startX;
		ScreenY = startY;
		ScreenButtonWidth = width;
		ScreenButtonHeight = height;

		player = p;
		map = t;
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("res/Materials/GUI/UI/Console.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		font = LoadFont(fontName);
		CharacterWidth = width / 15;
		LineHeight = height / 19;
	}

	public void SetEnabled(boolean i) {
		Enabled = i;
	}

	public void toggleEnabled() {
		// Clears keyboard key buffer
		while (Keyboard.next()) {

		}
		Prompt = "";
		Enabled = !Enabled;
	}

	public UnicodeFont LoadFont(String name) {
		UnicodeFont font1 = null;
		String fontPath = "res/fonts/" + name + ".ttf";
		try {
			font1 = new UnicodeFont(fontPath, 15, true, false);
		} catch (SlickException e1) {
			e1.printStackTrace();
		}
		font1.addAsciiGlyphs();
		font1.addGlyphs(400, 600);
		font1.getEffects().add(new ColorEffect(java.awt.Color.WHITE));
		try {
			font1.loadGlyphs();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		return font1;
	}

	public void getCommand(String s) {
		switch (s.toLowerCase()) {
		
		case "login enigma":
			Log += '\n' + ">WELCOME BACK DAEDULUS";
			DevMode = true;
			break;
		default:
			Log += '\n' + ">INVALID COMMAND";
			break;
		}
	}

	public void getAdminCommand(String s) {
		String str = s.toLowerCase();
		if (str.contains("addexp")) {
			try {
				int amount = Integer.parseInt(str.substring(7));
				player.GetExperience(amount);
				Log += '\n' + ">ADDED PLAYER EXP: " + str.substring(7);
			} catch (Exception e) {
				Log += '\n' + ">FAILED TO ADD EXP";
			}
		} else {

			switch (str) {

			case "wireframe":
				Log += '\n' + ">WIREFRAME MODE TOGGLED";
				map.DebugMode = !map.DebugMode;
				break;
			case "login enigma":
				Log += '\n' + ">WELCOME BACK DEVELOPER";
				DevMode = true;
				break;
			case "fight for asuna":
				Log += '\n' + ">I WILL SURVIVE IN THIS WORLD!";
				player.GetStats().AddToPrimaryStat(1, 500);
				player.GetStats().AddToPrimaryStat(4, 500);
				player.GetStats().CheckAddHealth(9999999);
				break;
			default:
				Log += '\n' + ">INVALID COMMAND";
				break;
			}
		}
	}

	public void DeleteLine() {
		int i;
		for (i = 0; i < Log.length(); i++) {
			if (Log.charAt(i) == '\n') {
				Log = Log.substring(i + 1);
				break;
			}
		}

	}

	public int CountLines() {
		int count = 0;
		for (int i = 0; i < Log.length(); i++) {
			if (Log.charAt(i) == '\n') {
				count++;
			}
		}
		return count;
	}
}

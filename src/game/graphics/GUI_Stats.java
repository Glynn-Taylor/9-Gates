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
 * Displays player statistics such as exp/health/mana/level 
 onto the game screen and prints amounts
 */
package game.graphics;

import game.level.entities.mobs.Player;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class GUI_Stats {

	private final Player player;
	private Texture ExpTexture;
	private Texture HealthTexture;
	private Texture ManaTexture;
	private UnicodeFont font1;

	public GUI_Stats(Player p) {
		player = p;
		try {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			ExpTexture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("res/Materials/GUI/UI/ExpBar.png"));
			HealthTexture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("res/Materials/GUI/UI/HealthBar.png"));
			ManaTexture = TextureLoader.getTexture("PNG", ResourceLoader
					.getResourceAsStream("res/Materials/GUI/UI/ManaBar.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		String fontPath = "res/fonts/ABEAKRG.ttf";
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

	}

	public void DrawBars() {
		if (player != null) {
			Color.white.bind();
			DrawExperienceBar();
			Color.white.bind();
			DrawHealthBar();
			Color.white.bind();
			DrawManaBar();
			font1.drawString(10, 10,
					"Player level: " + Integer.toString(player.getLevel()),
					Color.white);
			font1.drawString(
					10,
					25,
					"Level: " + Integer.toString(player.WorldNumber + 1) + "/9",
					Color.white);
		}

	}

	private void DrawExperienceBar() {
		if (ExpTexture != null) {
			ExpTexture.bind();
			GL11.glBegin(GL11.GL_QUADS);

			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(225, 704, 0);

			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(225, 763, 0);

			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(
					225 + 843f * player.Experience / player.GetExpNeedToLevel(),
					763, 0);

			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(
					225 + 843f * player.Experience / player.GetExpNeedToLevel(),
					704, 0);
			GL11.glEnd();
			String drawString = Integer.toString(player.Experience);
			font1.drawString(645.0F - (float) drawString.length() / 2 * 10,
					705.0F, drawString, Color.black);
		}
	}

	private void DrawHealthBar() {
		if (HealthTexture != null) {
			HealthTexture.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(226, 701 - 54 * player.GetStats()
					.GetHealthPercent() / 100, 0);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(226, 701, 0);

			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(318, 701, 0);

			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(318, 701 - 54 * player.GetStats()
					.GetHealthPercent() / 100, 0);
			GL11.glEnd();
			String drawString = Integer.toString(player.GetStats().GetHealth());
			font1.drawString(271 - (float) drawString.length() / 2 * 10,
					668.0F, drawString, Color.black);

		}
	}

	private void DrawManaBar() {
		if (ManaTexture != null) {
			ManaTexture.bind();
			GL11.glBegin(GL11.GL_QUADS);
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex3f(978,
					701 - 54 * player.GetStats().GetManaPercent() / 100, 0);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex3f(978, 701, 0);

			GL11.glTexCoord2f(1, 1);
			GL11.glVertex3f(1070, 701, 0);

			GL11.glTexCoord2f(1, 0);
			GL11.glVertex3f(1070,
					701 - 54 * player.GetStats().GetManaPercent() / 100, 0);
			GL11.glEnd();
			String drawString = Integer.toString(player.GetStats().GetMana());
			font1.drawString(1020 - (float) drawString.length() / 2 * 10,
					668.0F, drawString, Color.black);
		}
	}
}

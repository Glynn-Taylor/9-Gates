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
 * Stores a reference to all of the mob textures and item art
 */
package game.resources;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public final class GraphicsHandler {
	public enum MobArt {
		Bandit("res/Materials/Mobs/Bandit.png"), Demon(
				"res/Materials/Mobs/Demon.png"), Dwarf(
				"res/Materials/Mobs/Dwarf.png"), Ghost(
				"res/Materials/Mobs/Ghost.png"), Orc(
				"res/Materials/Mobs/Orc.png"), Skeleton(
				"res/Materials/Mobs/Skeleton.png"), Vampire(
				"res/Materials/Mobs/Vampire.png"), Werewolf(
				"res/Materials/Mobs/Werewolf.png"), Lich(
				"res/Materials/Mobs/Lich.png"), Zombie(
				"res/Materials/Mobs/Zombie.png"), Player(
				"res/Materials/Mobs/Player.png");

		private Texture tex;

		MobArt(String path) {
			try {
				tex = TextureLoader.getTexture("PNG",
						ResourceLoader.getResourceAsStream(path), false,
						GL11.GL_NEAREST);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void bind() {
			tex.bind();
		}
	}

	public enum ItemArt {
		Equipables("res/Materials/GUI/Items/EquipableItems.png"), SelectedIcon(
				"res/Materials/GUI/Items/Selected.png"), Items(
				"res/Materials/GUI/Items/Items.png");

		private Texture tex;

		ItemArt(String path) {
			try {
				tex = TextureLoader.getTexture("PNG",
						ResourceLoader.getResourceAsStream(path),
						GL11.GL_NEAREST);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void bind() {
			tex.bind();
		}

		public float getTextureWidth() {
			return tex.getTextureWidth();
		}

		public float getTextureHeight() {
			return tex.getTextureHeight();
		}
	}
}

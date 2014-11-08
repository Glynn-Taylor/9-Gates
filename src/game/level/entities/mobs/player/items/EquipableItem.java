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
 * Abstract base class for any item that can be equipped.
 */
package game.level.entities.mobs.player.items;

import game.level.entities.mobs.Player;
import game.level.entities.mobs.Stats;
import game.level.entities.mobs.player.Inventory;
import game.level.entities.pickup.Pickup;
import game.resources.GraphicsHandler.ItemArt;

import org.lwjgl.opengl.GL11;

public abstract class EquipableItem extends InventoryItem {
	private static final long serialVersionUID = -4502848954432625618L;
	protected String Name = "Equipable";
	protected int Slot = -1;
	protected int[] SecondaryModifiers = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	protected static float TEXTURE_QUAD_XLENGTH, TEXTURE_QUAD_YLENGTH;
	protected boolean Equipped;
	protected Stats statRef;

	public EquipableItem() {

		TEXTURE_QUAD_XLENGTH = 16f / ItemArt.Equipables.getTextureWidth();
		TEXTURE_QUAD_YLENGTH = 16f / ItemArt.Equipables.getTextureHeight();

		Equipable = true;
		SetModifiers();
	}

	@Override
	public void Render(int ScreenX, int ScreenY) {

		ItemArt.Equipables.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(TEXTURE_QUAD_XLENGTH * XTextureRef,
				TEXTURE_QUAD_YLENGTH * YTextureRef);
		GL11.glVertex3f(ScreenX, ScreenY, 0);

		GL11.glTexCoord2f(TEXTURE_QUAD_XLENGTH * XTextureRef,
				TEXTURE_QUAD_YLENGTH * (YTextureRef + 1));
		GL11.glVertex3f(ScreenX, ScreenY + IconWidth, 0);

		GL11.glTexCoord2f(TEXTURE_QUAD_XLENGTH * (XTextureRef + 1),
				TEXTURE_QUAD_YLENGTH * (YTextureRef + 1));
		GL11.glVertex3f(ScreenX + IconWidth, ScreenY + IconWidth, 0);

		GL11.glTexCoord2f(TEXTURE_QUAD_XLENGTH * (XTextureRef + 1),
				TEXTURE_QUAD_YLENGTH * YTextureRef);
		GL11.glVertex3f(ScreenX + IconWidth, ScreenY, 0);

		GL11.glEnd();
	}

	@Override
	public boolean UseItem(Player p, Inventory i) {
		if (getSlot() >= 0) {
			i.EquipItem(getSlot(), this);

			Equip(p.GetStats());
		} else {
			System.out.println("ERROR: SLOT NOT SET ON THIS ITEM!");
		}
		return false;
	}

	protected void Equip(Stats s) {
		if (!isEquipped()) {

			s.AddSecondaryModifier(SecondaryModifiers, 1);
			statRef = s;
			setEquipped(true);
		}

	}

	public void UnEquip() {
		if (isEquipped()) {

			statRef.AddSecondaryModifier(SecondaryModifiers, -1);
			statRef = null;
			setEquipped(false);
		}
	}

	@Override
	protected Pickup DropItem() {
		if (isEquipped())
			UnEquip();
		return DropSpecificItem();
	}

	protected abstract Pickup DropSpecificItem();

	protected abstract void SetModifiers();

	@Override
	public String getName() {
		return Name;

	}

	/**
	 * @return the slot
	 */
	public int getSlot() {
		return Slot;
	}

	/**
	 * @return the equipped
	 */
	public boolean isEquipped() {
		return Equipped;
	}

	/**
	 * @param equipped
	 *            the equipped to set
	 */
	protected void setEquipped(boolean equipped) {
		Equipped = equipped;
	}

}

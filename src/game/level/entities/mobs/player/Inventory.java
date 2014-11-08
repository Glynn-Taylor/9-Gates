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
 * Handles the manipulation and display of inventory 
 items, e.g. drop and use buttons, as well as the equipped 
 items
 */
package game.level.entities.mobs.player;

import game.level.entities.mobs.Player;
import game.level.entities.mobs.player.items.EquipableItem;
import game.level.entities.mobs.player.items.InventoryItem;
import game.level.entities.mobs.player.items.InventorySlot;
import game.level.entities.mobs.player.items.equipable.Sword;
import game.level.entities.mobs.player.items.standard.HealthPotion;
import game.level.entities.pickup.Pickup;
import game.resources.GraphicsHandler.ItemArt;

import java.io.Serializable;
import java.util.Random;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.UnicodeFont;

public class Inventory implements Serializable {

	private static final long serialVersionUID = -7476792429567728640L;

	private final int Collums = 4, Rows = 5, Spacing = 60;

	private final InventorySlot[] Slots = new InventorySlot[20];
	private int XRef = 0, YRef = 0, StartRow = 0;
	private final int WidthRef;
	private int SelectedIcon = -1;

	private final EquipableItem[] EquippedItems = new EquipableItem[8];
	/*
	 * [0] = Head [1] = Necklace [2] = Ring [3] = Body [4] = RH [5] = LH [6] =
	 * Greaves [7] = Boots
	 */
	private final int[] InvDisplayOffsets = {
			// [0] = Head
			(int) (43f / 256f * 500f), (int) (20f / 256f * 300f),
			// [1] = Necklace
			(int) (82f / 256f * 500f), (int) (25f / 256f * 300f),
			// [2] = Ring
			(int) (7f / 256f * 500f), (int) (25f / 256f * 300f),
			// [3] = Body
			(int) (45f / 256f * 500f), (int) (81f / 256f * 300f),
			// [4] = RH
			(int) (83f / 256f * 500f), (int) (81f / 256f * 300),
			// [5] = LH
			(int) (6f / 256f * 500f), (int) (83f / 256f * 300),
			// [6] = Greaves
			(int) (45f / 256f * 500f), (int) (145f / 256f * 300f),
			// [7] = Boots
			(int) (45f / 256f * 500f), (int) (208f / 256f * 300f)

	};

	public void EquipItem(int slot, EquipableItem item) {
		if (EquippedItems[slot] != null)
			EquippedItems[slot].UnEquip();
		EquippedItems[slot] = item;
	}

	public Inventory() {
		Random r = new Random();
		for (int i = 0; i < Slots.length; i++) {
			Slots[i] = new InventorySlot();

		}
		Slots[0].addNewItem(new HealthPotion(50));
		Slots[1].addNewItem(new Sword(0));
		WidthRef = Slots[0].getItem().getWidth();

	}

	public void Render(int startX, int startY, UnicodeFont font) {
		for (int x = 0; x < Collums; x++) {
			for (int y = 0; y < Rows; y++) {
				if ((y + StartRow) * Collums + x < Slots.length) {
					InventoryItem i = Slots[(y + StartRow) * Collums + x]
							.getItem();
					if (i != null) {
						i.Render(startX + x * Spacing, startY + y * Spacing);
						font.drawString(
								startX + x * Spacing + WidthRef,
								startY + y * Spacing + WidthRef,
								Integer.toString(Slots[(y + StartRow) * Collums
										+ x].getAmount()), Color.black);
						Color.white.bind();
					}
					if ((y + StartRow) * Collums + x == SelectedIcon)
						RenderSelectedTexture(startX + x * Spacing, startY + y
								* Spacing);
				}
			}
		}
		for (int i = 0; i < EquippedItems.length; i++) {
			if (EquippedItems[i] != null) {
				EquippedItems[i].Render(
						startX - 230 + InvDisplayOffsets[i * 2], startY - 30
								+ InvDisplayOffsets[i * 2 + 1]);
			}
		}
		if (SelectedIcon >= 0)
			if (Slots[SelectedIcon].getItem() != null)
				font.drawString(startX, startY + 180, Slots[SelectedIcon]
						.getItem().getName(), Color.black);
		XRef = startX;
		YRef = startY;
	}

	private void RenderSelectedTexture(int x, int y) {
		ItemArt.SelectedIcon.bind();
		GL11.glBegin(GL11.GL_QUADS);

		GL11.glTexCoord2f(0, 0);
		GL11.glVertex3f(x, y, 0);

		GL11.glTexCoord2f(0, 1);
		GL11.glVertex3f(x, y + WidthRef, 0);

		GL11.glTexCoord2f(1, 1);
		GL11.glVertex3f(x + WidthRef, y + WidthRef, 0);

		GL11.glTexCoord2f(1, 0);
		GL11.glVertex3f(x + WidthRef, y, 0);

		GL11.glEnd();

	}

	public void IncrementStartRow() {
		StartRow++;
		if (StartRow > Slots.length / Collums)
			StartRow--;
	}

	public void DecrementStartRow() {
		StartRow--;
		if (StartRow < 0)
			StartRow = 0;
	}

	public boolean AddItem(InventoryItem item) {

		for (int i = 0; i < Slots.length; i++) {

			if (Slots[i].getItem() != null
					&& Slots[i].getItem().getName().equals(item.getName())) {
				Slots[i].setAmount(Slots[i].getAmount() + 1);
				return true;
			}
		}
		for (int i = 0; i < Slots.length; i++) {
			if (Slots[i].getItem() == null) {
				Slots[i].addNewItem(item);
				return true;
			}
		}
		return false;
	}

	public boolean ValidSelection() {
		if (SelectedIcon >= 0 && Slots[SelectedIcon].getItem() != null)
			return true;
		return false;
	}

	public Pickup ItemDrop() {
		if (Slots[SelectedIcon].getItem().isEquipable()) {
			CheckIfEquipped((EquipableItem) Slots[SelectedIcon].getItem());
		}
		return Slots[SelectedIcon].DropItem();
	}

	private void CheckIfEquipped(EquipableItem item) {
		if (item.isEquipped()) {
			item.UnEquip();
			EquippedItems[item.getSlot()] = null;
		}

	}

	public void UseItem(Player p) {
		if (Slots[SelectedIcon].getItem().UseItem(p, this)) {
			Slots[SelectedIcon].ItemDestroyed();
		}
	}

	public void ProcessInput(int mouseX, int mouseY, boolean mouseDown) {
		if (mouseDown) {
			for (int x = 0; x < Collums; x++) {
				for (int y = 0; y < Rows; y++) {
					if ((y + StartRow) * Collums + x < Slots.length
							&& Slots[(y + StartRow) * Collums + x].getItem() != null) {
						if (mouseX > XRef + x * Spacing
								&& mouseX < XRef + x * Spacing + WidthRef
								&& mouseY > YRef + y * Spacing
								&& mouseY < YRef + y * Spacing + WidthRef) {
							SelectedIcon = (y + StartRow) * Collums + x;
						}
					}
				}
			}
		}
	}

}

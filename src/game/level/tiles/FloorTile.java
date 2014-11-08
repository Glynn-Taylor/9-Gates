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
 * Abstract base class for map tiles (wall and floor/null )
 */
package game.level.tiles;

import game.level.entities.Entity;
import game.level.entities.furniture.Furniture;
import game.level.entities.mobs.Mob;
import game.level.entities.mobs.Player;
import game.level.entities.pickup.Pickup;
import game.states.Game.GameState;

import java.util.ArrayList;

public class FloorTile extends MapTile {
	private static final long serialVersionUID = 2779419600180823187L;
	private Entity ActiveEntity;
	private final ArrayList<Pickup> Pickups = new ArrayList<Pickup>();

	public FloorTile() {
		IsFloor = true;
	}

	public void RemoveActiveEntity() {

		ActiveEntity = null;

	}

	public boolean AddActiveEntity(Entity m) {
		if (ActiveEntity == null) {
			ActiveEntity = m;
			return true;
		} else if (!ActiveEntity.IsMob) {
			return false;
		} else {
			Mob mob = (Mob) m;
			Mob activeMob = (Mob) ActiveEntity;
			if (EnemyOnTile(mob.IsPlayer))
				mob.Attack(activeMob);
			if (activeMob.IsDead())
				KillEntity();
			return false;
		}
	}

	private void KillEntity() {
		if (ActiveEntity != null) {
			if (ActiveEntity.IsMob) {
				Mob m = (Mob) ActiveEntity;
				if (m.HasLoot())
					Pickups.add(m.DropLoot());
				if (m.IsPlayer)
					GameState.SwitchToState(GameState.State_SinglePlayer,
							GameState.State_DeathScreen);
			}
		}

		ActiveEntity = null;

	}

	public boolean SetActiveEntity(Entity m) {
		ActiveEntity = m;
		return true;

	}

	public boolean isObstacle() {
		if (ActiveEntity == null) {
			return false;
		} else {
			if (!ActiveEntity.IsMob)
				return true;
		}
		return false;
	}

	public void SetActiveEntity(Mob m) {
		ActiveEntity = m;
	}

	@Override
	public void Render(int x, int y) {
		if (ActiveEntity != null) {

			ActiveEntity.MapRender(x, y);
			if (!ActiveEntity.IsMob) {
				Furniture f = (Furniture) ActiveEntity;
				if (f.HolderEntity != null) {
					f.HolderEntity.MapRender(x, y);
				}
			}
		}
		for (int i = 0; i < Pickups.size(); i++) {
			Pickups.get(i).MapRender(x, y);
		}

	}

	public void Update() {

	}

	public Mob GetActiveMob() {
		Mob m = (Mob) ActiveEntity;
		return m;

	}

	public Entity GetActiveEntity() {

		return ActiveEntity;

	}

	public boolean EnemyOnTile(boolean isPlayer) {
		Mob m = (Mob) ActiveEntity;
		return m.IsPlayer != isPlayer;
	}

	public void AddPickup(Pickup p) {
		Pickups.add(p);
	}

	public boolean Pickup(Player p) {
		boolean itemsPickedUp = false;
		for (int i = 0; i < Pickups.size(); i++) {
			Pickups.get(i).Get(p);
			Pickups.remove(i);
			itemsPickedUp = true;
		}
		return itemsPickedUp;
	}

	public void Interact(Entity entity) {
		Mob m = (Mob) entity;
		Furniture f = (Furniture) ActiveEntity;
		f.Interact(m);

	}
}

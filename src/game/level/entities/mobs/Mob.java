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
 * Abstract base class for entities that move/have 
 intelligence (includes player)
 */
package game.level.entities.mobs;

import game.level.entities.Entity;
import game.level.entities.pickup.Pickup;

import org.lwjgl.opengl.GL11;

public abstract class Mob extends Entity {
	private static final long serialVersionUID = 8470324894575886543L;
	protected Stats Statistics;
	protected Pickup Loot;
	public boolean IsPlayer;
	public boolean HasMoved;
	protected int Facing = 1;

	public Mob() {
		IsMob = true;
	}

	public void Attack(Mob enemy) {

		enemy.TakeDamage(Statistics.GetDamage());
	}

	public void TakeDamage(float damage) {
		Statistics.AddHealth(-damage);
	}

	public void AddHealth(float amount) {
		Statistics.AddHealth(amount);
	}

	public boolean IsDead() {
		return Statistics.IsDead;

	}

	public void RenderHealthBar() {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		float HealthPercent = Statistics.GetHealthPercent() / 100;
		GL11.glColor3f(1, 0, 0);
		GL11.glVertex3f(-0.3f * HealthPercent, -0.1f, -3.1f);
		GL11.glVertex3f(0.3f * HealthPercent, -0.1f, -3.1f);
		GL11.glVertex3f(0.3f * HealthPercent, 0.1f, -3.1f);
		GL11.glVertex3f(-0.3f * HealthPercent, 0.1f, -3.1f);
	}

	public Pickup DropLoot() {
		return Loot;

	}

	public boolean HasLoot() {
		return Loot != null;

	}

	/**
	 * @param facing
	 *            the facing to set
	 */
	public void setFacing(int facing) {
		Facing = facing;
	}
}

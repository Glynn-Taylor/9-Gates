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
 * Contains player statistics, such as health, damage etc.
 */
package game.level.entities.mobs;

import game.states.Game.GameState;

import java.io.Serializable;

public class Stats implements Serializable {

	private static final long serialVersionUID = 5988845818144794738L;
	private final int[] PrimaryStats;
	/*
	 * stat-list Intelligence LifeForce Aim Spirit Brawn Nimbleness
	 */
	private final String[] PrimaryNames = { "Intelligence", "LifeForce   ",
			"Aim         ", "Spirit      ", "Brawn       ", "Nimbleness  " };

	private final float[] SecondaryStats = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	/*
	 * stat-list [00]Mana [01]Magic power [02]Physical resist [03]Health [04]Bow
	 * damage [05]Thrown damage [06]Regen [07]Magic resist [08]Melee damage
	 * [09]Critical damage [10]Dodge chance [11]Critical chance
	 */
	private final String[] SecondaryNames = { "Mana           ",
			"Magic power    ", "Physical resist", "Health			", "Bow damage		",
			"Thrown damage	", "Regen			", "Magic resist	", "Melee damage	",
			"Critical damage", "Dodge chance	", "Critical chance" };
	private float CurrentHealth, CurrentMana;
	public boolean IsDead = false;
	private boolean IsLich = false;

	public Stats(int Intelligence, int LifeForce, int Aim, int Spirit,
			int Brawn, int Nimbleness) {
		PrimaryStats = new int[] { Intelligence, LifeForce, Aim, Spirit, Brawn,
				Nimbleness };
		CalculateSecondaries(Intelligence, LifeForce, Aim, Spirit, Brawn,
				Nimbleness);
	}

	private void CalculateSecondaries(int intelligence, int lifeForce, int aim,
			int spirit, int brawn, int nimbleness) {
		CalculateSecondary(intelligence, 0, 0.5f, 0.6f);
		CalculateSecondary(lifeForce, 2, 0.5f, 0.6f);
		CalculateSecondary(aim, 4, 0.5f, 0.6f);
		CalculateSecondary(spirit, 6, 0.5f, 0.6f);
		CalculateSecondary(brawn, 8, 0.5f, 0.6f);
		CalculateSecondary(nimbleness, 10, 0.5f, 0.6f);
		CurrentHealth = SecondaryStats[3];
		CurrentMana = SecondaryStats[0];
	}

	private void CalculateSecondary(int baseValue, int startIndex,
			float multiplierFirst, float multiplierSecond) {
		float bValue = baseValue;
		SecondaryStats[startIndex] = bValue * multiplierFirst;
		SecondaryStats[startIndex + 1] = bValue * multiplierSecond;
	}

	public void Update() {
		if (!IsDead) {
			CurrentMana = CurrentMana + SecondaryStats[6] >= SecondaryStats[0] ? SecondaryStats[0]
					: CurrentMana + SecondaryStats[6];
			CurrentHealth = CurrentHealth + SecondaryStats[6] >= SecondaryStats[3] ? SecondaryStats[3]
					: CurrentMana + SecondaryStats[6];
		}
	}

	public boolean CanCast() {
		return false;

	}

	public void AddSecondaryModifier(int[] mod, int mult) {
		for (int i = 0; i < SecondaryStats.length; i++) {
			SecondaryStats[i] += mod[i] * mult;
		}
	}

	public String getPrimaryString() {
		String str = "";
		for (int i = 0; i < PrimaryNames.length; i++) {
			str += PrimaryNames[i] + '\n';
		}
		return str;
	}

	public String getPrimaryStringValues() {
		String str = "";
		for (int i = 0; i < PrimaryNames.length; i++) {
			str += Integer.toString(PrimaryStats[i]) + '\n';
		}
		return str;
	}

	public String getSecondaryString() {
		String str = "";
		for (int i = 0; i < SecondaryNames.length; i++) {
			String s = Float.toString(SecondaryStats[i]);
			str += SecondaryNames[i] + '\n';
		}
		return str;
	}

	public String getSecondaryStringValues() {
		String str = "";
		for (int i = 0; i < SecondaryNames.length; i++) {
			String s = Float.toString(SecondaryStats[i]);
			str += s.substring(0, s.indexOf('.') + 2) + '\n';
		}
		return str;
	}

	public void AddHealth(float health) {
		CurrentHealth += health;
		System.out.println("New health:" + Float.toString(CurrentHealth));
		if (CurrentHealth <= 0) {
			IsDead = true;
			if (IsLich)
				GameState.SwitchToState(GameState.State_SinglePlayer,
						GameState.State_VictoryScreen);
		}
		if (CurrentHealth > SecondaryStats[3])
			CurrentHealth = SecondaryStats[3];
	}

	public boolean CheckAddHealth(float health) {
		if (CurrentHealth == SecondaryStats[3])
			return false;
		CurrentHealth += health / 10;
		System.out.println("New health:" + Float.toString(CurrentHealth));
		if (CurrentHealth <= 0) {
			IsDead = true;
		}
		if (CurrentHealth > SecondaryStats[3]) {
			CurrentHealth = SecondaryStats[3];
		}
		return true;

	}

	public float GetDamage() {
		return SecondaryStats[8];

	}

	public float GetHealthPercent() {
		return (CurrentHealth / SecondaryStats[3]) * 100;

	}

	public int GetHealth() {
		return (int) (CurrentHealth * 10);

	}

	public int GetMana() {
		return (int) (CurrentMana * 10);

	}

	public float GetManaPercent() {
		return (CurrentMana / SecondaryStats[0]) * 100;

	}

	public void AddToPrimaryStat(int i, int amount) {
		float[] CurrentSecondary = SecondaryStats.clone();
		RecalculateSecondaries();
		float[] OldSecondaries = SecondaryStats.clone();
		PrimaryStats[i] += amount;
		RecalculateSecondaries();
		for (int i2 = 0; i2 < SecondaryStats.length; i2++) {
			SecondaryStats[i2] += (CurrentSecondary[i2] - OldSecondaries[i2]);
			System.out.println(SecondaryStats[i2]);
		}
	}

	public void AddToSecondaryStat(int i, int amount) {
		SecondaryStats[i] += amount;
	}

	public void RecalculateSecondaries() {
		CalculateSecondary(PrimaryStats[0], 0, 0.5f, 0.6f);
		CalculateSecondary(PrimaryStats[1], 2, 0.5f, 0.6f);
		CalculateSecondary(PrimaryStats[2], 4, 0.5f, 0.6f);
		CalculateSecondary(PrimaryStats[3], 6, 0.5f, 0.6f);
		CalculateSecondary(PrimaryStats[4], 8, 0.5f, 0.6f);
		CalculateSecondary(PrimaryStats[5], 10, 0.5f, 0.6f);
	}

	/**
	 * @param isLich
	 *            the isLich to set
	 */
	public void setIsLich(boolean isLich) {
		IsLich = isLich;
	}

}

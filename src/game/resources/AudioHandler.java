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
 * Stores a reference to all sound clips
 */
package game.resources;

import java.io.IOException;

import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

public final class AudioHandler {

	public enum AudioPiece {
		PickupEffect("res/sound/Pickup2.wav"), PlayerWalkEffect(
				"res/sound/Move_Short.wav"), EnemyHitEffect(
				"res/sound/Hit_Hurt_Quiet.wav"), PlayerHitEffect(
				"res/sound/Player_Hit_Hurt.wav");

		private Audio SoundEffect;

		AudioPiece(String path) {
			try {
				SoundEffect = AudioLoader.getAudio("WAV",
						ResourceLoader.getResourceAsStream(path));
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void play() {
			SoundEffect.playAsSoundEffect(1f, 0.5f, false);
		}
	}
}

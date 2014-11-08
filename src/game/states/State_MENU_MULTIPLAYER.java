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
 * A state that handles how the multiplayer menu would function, should there be any multiplayer modes implemented
 */
package game.states;

import game.graphics.GUI_Layer;
import game.states.Game.GameState;

import java.io.IOException;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class State_MENU_MULTIPLAYER extends State {
	private final GUI_Layer GUI = new GUI_Layer();

	@Override
	protected void Init() {
		int ButtonStartX = 930;
		int ButtonStartY = 224;
		int Spacing = 90;
		try {
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			Texture button1 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/ConnectButton.png"),
							false, GL11.GL_NEAREST);
			GUI.AddButton(ButtonStartX, ButtonStartY + Spacing * 0,
					button1.getImageWidth(), button1.getImageHeight(), button1);
			Texture button2 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/HostButton.png"),
							false, GL11.GL_NEAREST);
			GUI.AddButton(ButtonStartX, ButtonStartY + Spacing * 1,
					button2.getImageWidth(), button2.getImageHeight(), button2);
			Texture button3 = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Buttons/BackButton.png"),
							false, GL11.GL_NEAREST);
			GUI.AddButton(ButtonStartX, ButtonStartY + Spacing * 2,
					button3.getImageWidth(), button3.getImageHeight(), button3);
			BackGroundImage = TextureLoader
					.getTexture(
							"PNG",
							ResourceLoader
									.getResourceAsStream("res/Materials/GUI/Backgrounds/MenuBackground2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void Update() {
		System.out.println("Running MENU");

	}

	@Override
	protected void ProcessInput() {
		GUI.ProcessInput(MouseLastX, MouseLastY, Mouse.isButtonDown(0));
		if (GUI.isButtonDown(0)) {
		}
		if (GUI.isButtonDown(1)) {
		}
		if (GUI.isButtonDown(2)) {
			GameState.SwitchToState(GameState.State_Menu_Multiplayer,
					GameState.State_Menu);
		}

	}

	@Override
	protected void Render() {

		OrthoMode();
		ModelMode();
		DrawBackground();
		GUI.Render();
		ProjectionMode();
		ModelMode();

	}

	@Override
	public void Unload() {

	}

}

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
 * Creates a file browser that looks for gamesaves in the 
 default game save folder
 */
package game.util;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileFilter;

public class GameBrowser extends JFrame {

	private static final long serialVersionUID = 4382570231691080960L;

	public String GetOpenPath() {
		JFileChooser chooser = new JFileChooser();
		File file = new File(chooser.getFileSystemView().getDefaultDirectory()
				.toString()
				+ "\\9GatesData");
		if (!file.exists())
			file.mkdirs();
		chooser.setCurrentDirectory(file);
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setFileFilter(new ImageFileFilter());

		int retVal = chooser.showOpenDialog(this);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			File myFile = chooser.getSelectedFile();
			return myFile.getAbsolutePath();
		}
		return null;

	}

	public String GetSavePath() {
		JFileChooser chooser = new JFileChooser();
		File file = new File(chooser.getFileSystemView().getDefaultDirectory()
				.toString()
				+ "\\9GatesData");
		if (!file.exists())
			file.mkdirs();

		chooser.setCurrentDirectory(file);
		chooser.setAcceptAllFileFilterUsed(true);
		chooser.setFileFilter(new ImageFileFilter());

		int retVal = chooser.showSaveDialog(this);
		if (retVal == JFileChooser.APPROVE_OPTION) {
			File myFile = chooser.getSelectedFile();
			return myFile.getAbsolutePath();
		}
		return null;

	}

	public void DestroyMe() {
		this.dispose();
	}

	class ImageFileFilter extends FileFilter {

		@Override
		public boolean accept(File pathname) {
			if (pathname.isDirectory())
				return true;
			if (pathname.getName().endsWith(".dat"))
				return true;

			return false;
		}

		@Override
		public String getDescription() {
			return ".dat";

		}
	}
}

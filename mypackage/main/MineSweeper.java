package mypackage.main;

import javax.swing.*;

public class MineSweeper {
	public static void start() {
		GUI frame = new GUI("MineSweeper");
		frame.pack();
	    frame.setVisible(true);
	}
 	
	public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) { }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                start();
            }
        });
	}
}

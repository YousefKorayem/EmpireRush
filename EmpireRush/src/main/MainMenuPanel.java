package main;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class MainMenuPanel extends JPanel implements Runnable{
		static final int MENU_WIDTH = 1280;
		static final int MENU_HEIGHT = 720;
		static final Dimension SCREEN_SIZE = new Dimension(MENU_WIDTH, MENU_HEIGHT);
		
		Thread menuThread;
		Image image;
		Graphics graphics;
		Random random;
}

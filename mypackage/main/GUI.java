package mypackage.main;
import java.util.Timer;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class GUI extends JFrame implements Static{
	private static final int SIZE = 30;
	private static final Color[] MCOLOR = {
		Color.white, Color.blue, Color.green, Color.red, 
		new Color(35, 59, 108), Color.orange, Color.cyan, 
		Color.black, Color.gray, Color.yellow};
	private int score = 0, time = 0, x, y, n; 
	private JPanel mainPane;
	private JLayeredPane minePane;
	private JButton[][] btn;
	private JLabel[][] mineNum;
	private JTextField scoreField, timerField;
	private MineButtonMouseAction[][] mbListener;
	private TimerTask task;
	private Timer timer;
	private Setting set = new Setting();
	
	public GUI(String title){
		x = 9; y = 9; n = 10;
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainPane = new JPanel();
		minePane = new JLayeredPane();
		
		initSet(x, y, n);
		
		mainPane.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
		mainPane.setLayout(new BoxLayout(mainPane, BoxLayout.Y_AXIS));		
		JButton levelButton = new JButton("Level");
		levelButton.setFocusPainted(false);
		LevelButtonAction levelButtonListener = new LevelButtonAction();
		levelButton.addActionListener(levelButtonListener);
		JButton startButton = new JButton("Start");
		startButton.setFocusPainted(false);
        StartButtonAction startButtonListener = new StartButtonAction();
        startButton.addActionListener(startButtonListener);
		timerField = new JTextField(Integer.toString(time));
		timerField.setEditable(false);
		timerField.setHorizontalAlignment(JTextField.RIGHT);
		scoreField = new JTextField(Integer.toString(score));
		scoreField.setEditable(false);
		scoreField.setHorizontalAlignment(JTextField.RIGHT);
        JPanel subPane1 = new JPanel();
        subPane1.setLayout(new GridLayout(1, 0));
        subPane1.add(timerField);
        subPane1.add(Box.createRigidArea(new Dimension(30, 10)));
        subPane1.add(scoreField);
        JPanel subPane2 = new JPanel();
        subPane2.setLayout(new GridLayout(1, 0));
        subPane2.add(levelButton);
        subPane2.add(Box.createRigidArea(new Dimension(30, 10)));
        subPane2.add(startButton);
		
        JButton quitButton = new JButton("Quit");
        quitButton.setFocusPainted(false);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        QuitButtonAction quitButtonListener = new QuitButtonAction();
        quitButton.addActionListener(quitButtonListener);
		
		mainPane.add(subPane2);
		mainPane.add(Box.createRigidArea(new Dimension(10, 10)));
		mainPane.add(subPane1);
		mainPane.add(Box.createRigidArea(new Dimension(10, 20)));
		mainPane.add(minePane);
		mainPane.add(Box.createRigidArea(new Dimension(10, 10)));
		mainPane.add(quitButton);
		
		Container contentPane = getContentPane();
		contentPane.add(mainPane, BorderLayout.CENTER);
	}
	
	public Component setMineCellLower(int x, int y, int n) {
		JPanel pane = new JPanel();
		pane.setBounds(0, 0, SIZE * x, SIZE * y);
		mineNum = new JLabel[x][y];
		pane.setLayout(new GridLayout(x, y));
		set.setMine(x, y, n);
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				int num = set.getCell(i, j).getNum();
				if (num == 0) {
					mineNum[i][j] = new JLabel("");
					mineNum[i][j].setForeground(MCOLOR[0]);
				} else if (num != BOMB) {
					mineNum[i][j] = new JLabel("<html><b>"+Integer.toString(num)+"</b></html>");
					mineNum[i][j].setForeground(MCOLOR[num]);
				} else {
					mineNum[i][j] = new JLabel("B");
					mineNum[i][j].setForeground(MCOLOR[BOMB]);
				}
				mineNum[i][j].setSize(SIZE, SIZE);
				mineNum[i][j].setHorizontalAlignment(JLabel.CENTER);
				mineNum[i][j].setVerticalAlignment(JLabel.CENTER);
				mineNum[i][j].setBorder(new LineBorder(Color.gray));
				pane.add(mineNum[i][j]);
			}
		}		
		return pane;
	}
	
	public Component setMineCellUpper(int x, int y) {
		JPanel pane = new JPanel();
		pane.setBounds(0, 0, SIZE * x, SIZE * y);
		pane.setOpaque(false);
		btn = new JButton[x][y];
		mbListener = new MineButtonMouseAction[x][y];
		pane.setLayout(new GridLayout(btn.length, btn[0].length));
		for (int i = 0; i < x; i++) {
			for (int j = 0; j < y; j++) {
				btn[i][j] = new JButton();
				btn[i][j].setSize(SIZE, SIZE);
				btn[i][j].setHorizontalAlignment(JButton.CENTER);
				btn[i][j].setFocusPainted(false);
		        mbListener[i][j] = new MineButtonMouseAction(i, j);
		        btn[i][j].addMouseListener( mbListener[i][j] );
				pane.add(btn[i][j]);
			}
		}		
		return pane;
	}
	
	public void initSet(int x, int y, int n) {
		minePane.removeAll();
		minePane.setPreferredSize(new Dimension(SIZE * x, SIZE * y));
		Component cellsUpper = setMineCellUpper(x, y);
		Component cellsLower = setMineCellLower(x, y, n);		
		minePane.add(cellsUpper, new Integer(2));
		minePane.add(cellsLower, new Integer(1));
		timer = new Timer();
		task = null;
		pack();
	}
	
	public void showEndMessage(int type) {
	    try{
	    	Thread.sleep(300);
	    }catch(InterruptedException err){}
	    timer.cancel();
		for (int i = 0; i < x; i++)
			for (int j = 0; j < y; j++) {
				if (type == 1) {
	        	    set.getCell(i, j).changeState();
	        	    btn[i][j].setText(set.getCell(x, y).getMark());
				}
				btn[i][j].removeMouseListener(mbListener[i][j]);
			}
		switch (type) {
		case 1:
			JOptionPane.showMessageDialog(minePane, "Game Over", "End", JOptionPane.INFORMATION_MESSAGE);
			break;
		case 2:
			JOptionPane.showMessageDialog(minePane, "Game Clear!", "Clear", JOptionPane.INFORMATION_MESSAGE);
			break;
		}
	}
	
	class MineButtonAction implements ActionListener {
		private int x, y;
		private boolean isEnd;
		public MineButtonAction(int i, int j) {
			super();
			x = i;
			y = j;
		}
        public void actionPerformed(ActionEvent e) {
        	if (task == null) {
        		task = new countTimer();
        		timer.schedule(task, 0, 1000);
        	}
        	if (mineNum[x][y].getText().equals("B")) {
        		showEndMessage(1);
        		return;
        	}
        	isEnd = set.click(x, y);
        	for (int i = 0; i < set.getX(); i++)
        		for (int j = 0; j < set.getY(); j++)
        			btn[i][j].setVisible(!set.getCell(i, j).getPressed());
        	score += 100;
        	scoreField.setText(Integer.toString(score));
        	if (isEnd) {
        		showEndMessage(2);
        	}
        }
	}
	
	class MineButtonMouseAction implements MouseListener {
		private int x, y;
		private boolean isEnd;
		public MineButtonMouseAction(int i, int j) {
			super();
			x = i;
			y = j;
		}
        public void mouseClicked(MouseEvent e) {
        	if(SwingUtilities.isRightMouseButton(e)){
        	    set.getCell(x, y).changeState();
        	    btn[x][y].setText(set.getCell(x, y).getMark());
        	} else if(SwingUtilities.isLeftMouseButton(e)){
        		if (task == null) {
            		task = new countTimer();
            		timer.schedule(task, 0, 1000);
            	}
            	if (mineNum[x][y].getText().equals("B")) {
            		showEndMessage(1);
            		return;
            	}
            	isEnd = set.click(x, y);
            	for (int i = 0; i < set.getX(); i++)
            		for (int j = 0; j < set.getY(); j++)
            			btn[i][j].setVisible(!set.getCell(i, j).getPressed());
            	score += 100;
            	scoreField.setText(Integer.toString(score));
            	if (isEnd) {
            		showEndMessage(2);
            	}
        	}
        }
        public void mouseExited(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mousePressed(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
	}
	
	class LevelButtonAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			Config con = new Config(GUI.this, "Config", true, x, y, n);
			con.addWindowListener(new WindowAdapter() {
	            public void windowClosed(WindowEvent e) {
	            }
	        });
			if (con.isOK) {
				x = con.numX; y = con.numY; n = con.numN;
			}
		}
	}
	
    class StartButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	initSet(x, y, n);
        	time = 0;
    		timerField.setText(Integer.toString(time));
    		task = null;
        }
    }
    
    class QuitButtonAction implements ActionListener {
        public void actionPerformed(ActionEvent e) {
        	System.exit(0);
        }
    }
    
    class countTimer extends TimerTask {
		public void run() {
			time++;
			timerField.setText(Integer.toString(time));
		}
    }    
}

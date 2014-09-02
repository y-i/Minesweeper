package mypackage.main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * 盤面サイズの設定
 * 数値は右寄せ
 */
public class Config extends JDialog{
	public boolean isOK = false;
	public int numX, numY, numN;
	private JTextField x, y, n;
	private JPanel main;
	public Config(JFrame owner, String title, boolean modal, int x, int y, int n) {
		super(owner, title, modal);
		main = new JPanel();
		JLabel l1 = new JLabel("Height:");
		JLabel l2 = new JLabel("Width:");
		JLabel l3 = new JLabel("Mine:");
		l1.setPreferredSize(new Dimension(45, l1.getPreferredSize().height));
		l2.setPreferredSize(new Dimension(45, l1.getPreferredSize().height));
		l3.setPreferredSize(new Dimension(45, l1.getPreferredSize().height));
		this.x = new JTextField(Integer.toString(x));
		this.y = new JTextField(Integer.toString(y));
		this.n = new JTextField(Integer.toString(n));
		this.x.setColumns(2);
		this.y.setColumns(2);
		this.n.setColumns(2);
		this.x.setHorizontalAlignment(JTextField.RIGHT);
		this.y.setHorizontalAlignment(JTextField.RIGHT);
		this.n.setHorizontalAlignment(JTextField.RIGHT);
		JButton ok = new JButton("OK");
		ok.setSize(60, 30);
		ok.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){
				 try {
					 /*
					  * 盤面は9~40のサイズのみ
					  */
					 numX = Integer.parseInt(Config.this.x.getText());
					 numY = Integer.parseInt(Config.this.y.getText());
					 numN = Integer.parseInt(Config.this.n.getText());
					 if (numX < 9) {numX = 9;}
					 else if (numX > 40) {numX = 40;}
					 if (numY < 9) {numY = 9;}
					 else if (numY > 40) {numY = 40;}
					 int max = (numX-1)*(numY-1);
					 if (numN < 10) {numN = 10;}
					 else if (numN > max) {numN = max;}
					 isOK = true;
				 } catch (NumberFormatException err) {}
				 dispose();
			 }
		});
		JButton cancel = new JButton("Cancel");
		cancel.setSize(40, 30);
		cancel.addActionListener(new ActionListener(){
			 public void actionPerformed(ActionEvent e){
				 dispose();
			 }
		});
		JRadioButton easyButton = new JRadioButton("簡単", false);
		JRadioButton mediumButton = new JRadioButton("普通", false);
		JRadioButton hardButton = new JRadioButton("難しい", false);
		JRadioButton customButton = new JRadioButton("カスタム", false);
		ButtonGroup bg = new ButtonGroup();
		bg.add(easyButton);
		bg.add(mediumButton);
		bg.add(hardButton);
		bg.add(customButton);
		customButton.setSelected(true);
		
		JPanel subPane1 = new JPanel();
		JPanel subPane2 = new JPanel();
		JPanel subPane3 = new JPanel();
		JPanel buttonPane = new JPanel();
		JPanel choosePane = new JPanel();
		buttonPane.setLayout(new GridLayout(1, 0));
		choosePane.setLayout(new GridLayout(2, 0));
		subPane1.add(l1);
		subPane1.add(this.x);
		subPane2.add(l2);
		subPane2.add(this.y);
		subPane3.add(l3);
		subPane3.add(this.n);
		buttonPane.add(ok);
		buttonPane.add(cancel);
		choosePane.add(easyButton);
		choosePane.add(mediumButton);
		choosePane.add(hardButton);
		choosePane.add(customButton);
		
		main.add(choosePane);
		main.add(subPane1);
		main.add(subPane2);
		main.add(subPane3);
		main.add(buttonPane);
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		getContentPane().add(main);
		
		pack();
		setVisible(true);
	}
}

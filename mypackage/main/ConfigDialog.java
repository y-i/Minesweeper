package mypackage.main;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/*
 * 盤面サイズの設定
 * 数値は右寄せ
 */
public class ConfigDialog extends JDialog{
	public boolean isOK = false;
	public int numX, numY, numN;
	private JTextField x, y, n;
	private JPanel main;

	private void setInput(JLabel hLabel, JLabel wLabel, JLabel mLabel, int x, int y, int n) {
		hLabel.setPreferredSize(new Dimension(45, hLabel.getPreferredSize().height));
		wLabel.setPreferredSize(new Dimension(45, wLabel.getPreferredSize().height));
		mLabel.setPreferredSize(new Dimension(45, mLabel.getPreferredSize().height));
		this.x = new JTextField(Integer.toString(x));
		this.y = new JTextField(Integer.toString(y));
		this.n = new JTextField(Integer.toString(n));
		this.x.setColumns(2);
		this.y.setColumns(2);
		this.n.setColumns(2);
		this.x.setHorizontalAlignment(JTextField.RIGHT);
		this.y.setHorizontalAlignment(JTextField.RIGHT);
		this.n.setHorizontalAlignment(JTextField.RIGHT);
	}
	private void setOkButton(JButton ok) {
		ok.setSize(60, 30);
		ok.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					/*
					* 盤面は9~40のサイズのみ
					*/
					numX = Integer.parseInt(ConfigDialog.this.x.getText());
					numY = Integer.parseInt(ConfigDialog.this.y.getText());
					numN = Integer.parseInt(ConfigDialog.this.n.getText());

					numX = Math.min(40, Math.max(9, numX));
					numY = Math.min(40, Math.max(9, numY));
					int maxMine = (numX-1)*(numY-1);
					numN = Math.min(maxMine, Math.max(10, numN));

					isOK = true;
				} catch (NumberFormatException err) {}
				dispose();
			}
		});
	}
	private void setCancelButton(JButton cancel) {
		cancel.setSize(40, 30);
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				dispose();
			}
		});
	}
	private void setDifficultyButton(JRadioButton easyButton, JRadioButton mediumButton, JRadioButton hardButton, JRadioButton customButton, ButtonGroup bg) {
		bg.add(easyButton);
		bg.add(mediumButton);
		bg.add(hardButton);
		bg.add(customButton);
		customButton.setSelected(true);
	}
	private void setDialog(int x, int y, int n) {
		main = new JPanel();
		JLabel hLabel = new JLabel("Height:");
		JLabel wLabel = new JLabel("Width:"); 
		JLabel mLabel = new JLabel("Mine:");
		setInput(hLabel, wLabel, mLabel, x, y, n);

		JButton ok = new JButton("OK");
		setOkButton(ok);
		
		JButton cancel = new JButton("Cancel");
		setCancelButton(cancel);
		
		JRadioButton easyButton = new JRadioButton("簡単", false);
		JRadioButton mediumButton = new JRadioButton("普通", false);
		JRadioButton hardButton = new JRadioButton("難しい", false);
		JRadioButton customButton = new JRadioButton("カスタム", false);
		ButtonGroup bg = new ButtonGroup();
		setDifficultyButton(easyButton, mediumButton, hardButton, customButton, bg);
		
		
		JPanel subPane1 = new JPanel();
		JPanel subPane2 = new JPanel();
		JPanel subPane3 = new JPanel();
		JPanel buttonPane = new JPanel();
		JPanel choosePane = new JPanel();
		buttonPane.setLayout(new GridLayout(1, 0));
		choosePane.setLayout(new GridLayout(2, 0));
		subPane1.add(hLabel);
		subPane1.add(this.x);
		subPane2.add(wLabel);
		subPane2.add(this.y);
		subPane3.add(mLabel);
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
	}
	public ConfigDialog(JFrame owner, String title, boolean modal, int x, int y, int n) {
		super(owner, title, modal);

		setDialog(x,y,n);
		
		pack();
		setVisible(true);
	}
}

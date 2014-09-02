package mypackage.main;

public class Setting implements Static {
	private int x, y, num, unOpen, score;
	private Cells[][] c;
	public void setMine(int i, int j, int n) {
		x = i; y = j; num = n; unOpen = x * y;
		c = new Cells[x+2][y+2];
		for (i = 0; i < x+2; i++)
			for (j = 0; j < y+2; j++) {
				c[i][j] = new Cells();
				if (i != 0 && i != x+2 && y != 0 && y != y+2)
					c[i][j].setPressed(false);
			}
		while (n > 0) {
			int tmpX = (int)(Math.random()*x+1);	//1~J
			int tmpY = (int)(Math.random()*y+1);
			if (c[tmpX][tmpY].getNum() != BOMB) {
				c[tmpX][tmpY].setNum(BOMB);
				n--;
			}
		}
		setNumAll();
	}
	public void setNumAll() {
		int i, j;
		for (i = 1; i <= x; i++) {
			for (j = 1; j <= y; j++) {
				if (c[i][j].getNum() != BOMB) {
					setNum(i, j);
				}
			}
		}
	}
	public void setNum(int i, int j) {
		int k, l;
		int sum = 0;
		for (k = i - 1; k <= i + 1; k++) {
			for (l = j - 1; l <= j + 1; l++) {
				if (c[k][l].getNum() == BOMB) {
					sum++;
				}	
			}
		}
		c[i][j].setNum(sum);
	}
	public boolean click(int x, int y) {
		openCell(x, y);
		if (unOpen > num)
			return false;
		else
			return true;
	}
	public void openCell(int x, int y) {
		getCell(x, y).setPressed(true);
		unOpen--;
		if (getCell(x, y).getNum() == 0)
			for (int i = x-1; i <= x+1; i++)
				for (int j = y-1; j <= y+1; j++)
					if (!getCell(i, j).getPressed())
						switch (getCell(i, j).getNum()) {
						case 0:
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
						case 6:
						case 7:
						case 8:
							openCell(i, j);
							break;
						}
	}
	public void avoidFirstCell (int x, int y) {
		do {
			int tmpX = (int)(Math.random()*x+1);	//1~J
			int tmpY = (int)(Math.random()*y+1);
			if (c[tmpX][tmpY].getNum() != BOMB) {
				c[tmpX][tmpY].setNum(BOMB);
				setNum(x+1, y+1);
			}
		} while (getCell(x, y).getNum() == BOMB);
	}
	public int getX() {
		return x;
	}
	public int getY() {
		return y;
	}
	public int getScore() {
		return score;
	}
	
	public Cells getCell(int i, int j) {
		return c[i+1][j+1];
	}
	public class Cells {
		private String[] mark = {"", "!", "œ"};
		private int num, state;
		private boolean isPressed;
		public Cells() {
			num = -2;
			state = 0;
			isPressed = true;
		}
		public void changeState() {
			if (isPressed == true && num == BOMB) 
				state = 2;
			else 
				state = 1 - state; 
		}
		public String getMark() {
			return mark[state];
		}
		public int getNum() {
			return num;
		}
		public void setNum(int num) {
			this.num = num;
		}
		public boolean getPressed() {
			return isPressed;
		}
		public void setPressed(boolean isPressed) {
			this.isPressed = isPressed;
		}	
	}
}

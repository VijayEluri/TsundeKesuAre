package jp.snowink.tka;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import jp.snowink.tka.mino.Mino;


public class VsPanel extends JPanel {

	public int block_size = 10;
	public int small_block_size = 8;
	
	public VsPanel() {
		
		
	}
	
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		
		Field my_field = DataPool.vs_field_1;
		Field your_field = DataPool.vs_field_2;
		
		// 基点
		Point cp_1 = new Point(70, 60);
		Point cp_2 = new Point(280, 60);
		
		// 枠
		g.setColor(Color.BLACK);
		g.drawRect(cp_1.x - 1, cp_1.y - 1, my_field.getBlocks().length * block_size + 1, my_field.getViewLine() * block_size + 1);
		g.drawRect(cp_2.x - 1, cp_2.y - 1, your_field.getBlocks().length * block_size + 1, your_field.getViewLine() * block_size + 1);
		
//		g.fillRect(cp_1.x - 1, cp_1.y - 1, my_field.getBlocks().length * block_size + 2, my_field.getViewLine() * block_size + 2);
//		g.fillRect(cp_2.x - 1, cp_2.y - 1, your_field.getBlocks().length * block_size + 2, your_field.getViewLine() * block_size + 2);
		
		// フィールド
		drawField(g, my_field, block_size, cp_1);				
		drawField(g, your_field, block_size, cp_2);		
		
		// 影
		drawShadow(g, my_field, block_size, cp_1);				
		drawShadow(g, your_field, block_size, cp_2);		
		
		// なうみの	
		drawMino(g, my_field, block_size, cp_1);				
		drawMino(g, your_field, block_size, cp_2);		
		
		// HOLD
		drawHold(g, my_field, small_block_size, new Point(cp_1.x - 40, cp_1.y));
		drawHold(g, your_field, small_block_size, new Point(cp_2.x - 40, cp_2.y));
		
		// NEXT
		drawNext(g, my_field, small_block_size, new Point(cp_1.x + 28, cp_1.y - 40), small_block_size);
		drawNext(g, your_field, small_block_size, new Point(cp_2.x + 28, cp_2.y - 40), small_block_size);
		
	}
	
	private void drawBlock(Graphics g, int size, Point point, Color color, Color color_dark, Color color_light) {
		g.setColor(color);
		g.fillRect(point.x, point.y, size, size);
		g.setColor(color_dark);
		g.drawLine(point.x + 1, point.y, point.x + size - 2, point.y);
		g.drawLine(point.x + 2, point.y + 1, point.x + size - 2, point.y + 1);
		g.drawLine(point.x, point.y + 1, point.x, point.y + size - 2);
		g.drawLine(point.x + 1, point.y + 1, point.x + 1, point.y + size - 2);
		g.setColor(color_light);
		g.drawLine(point.x, point.y + size - 1, point.x + size - 2, point.y + size - 1);
		g.drawLine(point.x + size - 1, point.y, point.x + size - 1, point.y + size - 2);
	}
	
	private void drawField(Graphics g, Field field, int size, Point point) {
		Block[][] blocks = field.getBlocks();
		for (int x = 0; x < blocks.length; x++) {
			for (int y = 0; y < field.getViewLine(); y++) {
				if (blocks[x][y].isBlock()) {
					drawBlock(g, size, new Point(point.x + x * size, point.y + field.getViewLine() * size - (y + 1) * size), blocks[x][y].getColor(), blocks[x][y].getColorDark(), blocks[x][y].getColorLight());
				}
			}
		}
	}
	
	private void drawHold(Graphics g, Field field, int size, Point point) {
		int tmp = 4; // 要修正
		g.setColor(Color.BLACK);
		g.drawRect(point.x - 1, point.y - 1, size * tmp + 1, size * tmp + 1);
		
		Mino mino;
		if ((mino = field.getHoldMino()) != null) {
			for (int x = 0; x < mino.getMinoSize(); x++) {
				for (int y = 0; y < mino.getMinoSize(); y++) {
					if (mino.getPiece()[x][y].isBlock()) {
						drawBlock(g, size, new Point(point.x + size * x, point.y + size * mino.getMinoSize() - size * (y + 1)), mino.color, mino.color_dark, mino.color_light);
					}
				}
			}
		}
	}
	
	private void drawMino(Graphics g, Field field, int size, Point point) {
		Mino mino = field.getNowMino();
		for (int x = 0; x < mino.getMinoSize(); x++) {
			for (int y = 0; y < mino.getMinoSize(); y++) {
				if (mino.getPiece()[x][y].isBlock()) {
					drawBlock(g, size, new Point(point.x + (mino.getPosition().x + x) * size, point.y + field.getViewLine() * size - (mino.getPosition().y + y + 1) * size), mino.color, mino.color_dark, mino.color_light);
				}
			}
		}
	}
	
	private void drawNext(Graphics g, Field field, int size, Point point, int interval) {
		for (int h = 0; h < field.getNextMinoVolume(); h++) {
			Mino mino = field.getNextMinos().get(h);
			
			int next_x;
			int next_y;
			
			switch (h) {
			case 0:
				next_x = point.x;
				next_y = point.y;
				break;
			case 1:
				next_x = point.x + size * mino.getMinoSize() + interval;
				next_y = point.y;
				break;
			default: 
				next_x = point.x + (size * mino.getMinoSize() + interval) * 2;
				next_y = point.y + (size * mino.getMinoSize() + interval) * (h - 2);
				break;
			}
			
			g.setColor(Color.BLACK);
			g.drawRect(next_x - 1, next_y - 1, size * mino.getMinoSize() + 1, size * mino.getMinoSize() + 1);
			Block[][] ga3 = mino.getPiece();
			for (int i = 0; i < mino.getMinoSize(); i++) {
				for (int j = 0; j < mino.getMinoSize(); j++) {
					if (ga3[i][j].isBlock()) {
						drawBlock(g, size, new Point(next_x + size * i, next_y + size * mino.getMinoSize() - size * (j + 1)), mino.color, mino.color_dark, mino.color_light);
					}
				}
			}
		}
	}
	
	private void drawShadow(Graphics g, Field field, int size, Point point) {
		Mino mino = field.getNowMino();
		for (int x = 0; x < mino.getMinoSize(); x++) {
			for (int y = 0; y < mino.getMinoSize(); y++) {
				if (mino.getPiece()[x][y].isBlock()) {
					drawBlock(g, size, new Point(point.x + (mino.getDropPoint().x + x) * size, point.y + field.getViewLine() * size - (mino.getDropPoint().y + y + 1) * size), mino.shadow_color, mino.shadow_color_dark, mino.shadow_color_light);
				}
			}
		}
	}
	
	
}

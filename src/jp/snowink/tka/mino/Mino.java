package jp.snowink.tka.mino;
import java.awt.Color;
import java.awt.Point;

import jp.snowink.tka.Block;


public abstract class Mino {
	
	private static final int ROTATE_PATTERN = 4;
	
	private int mino_size;
	protected Point start_position;
	private int start_rotate;
	
	protected Point position = new Point();	
	protected int rotate;
	
	protected Block[][][] piece;
	protected Block[][] blocks;
	
	
	// Blockの色設定よりもMinoの色設定が優先される……ようにする
	
	// ミノの色設定
	public Color color = Color.BLACK;
	public Color color_dark = Color.BLACK;
	public Color color_light = Color.BLACK;
	
	// 影の色設定
	public Color shadow_color = Color.BLACK;
	public Color shadow_color_dark = Color.BLACK;
	public Color shadow_color_light = Color.BLACK;
	
	// 初期化
	public Mino(int mino_size, Block[][] blocks, Point start_position, int start_rotate) {
		
		this.mino_size = mino_size;
		piece = new Block[ROTATE_PATTERN][mino_size][mino_size];
		
		this.blocks = blocks;
		for (int r = 0; r < ROTATE_PATTERN; r++) {
			for (int x = 0; x < mino_size; x++) {
				for (int y = 0; y < mino_size; y++) {
					piece[r][x][y] = new Block();
				}
			}
		}
		
		this.start_position = start_position;
		this.start_rotate = start_rotate;
		initMino();
		
		
		
	}
	
	public boolean check(Block[][] piece, int position_x, int position_y, Block[][] blocks) {
		for (int x = 0; x < mino_size; x++) {
			for (int y = 0; y < mino_size; y++) {
				if (piece[x][y].isBlock()) {
					// 場外判定
					if (position_x + x < 0 || position_x + x > blocks.length - 1 || position_y + y < 0 ) {
						return false;
					}
					// かぶり判定
					if (blocks[position_x + x][position_y + y].isBlock()) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	public Point getDropPoint() {
		for (int i = position.y; i >= -mino_size + 1; i--) {
			if(!check(getPiece(), position.x, i - 1, blocks)) {
				return new Point(position.x, i);
			}
		}
		return new Point(position.x, -mino_size + 1);
	}
	
	public int getMinoSize() {
		return mino_size;
	}

	public Block[][] getPiece() {
		return piece[rotate];
	}
		
	public Block[][] getPiece(int rotate) {
		if (rotate >= 0 && rotate < ROTATE_PATTERN) {
			return piece[rotate];
		}
		else {
			return getPiece();
		}
	}
	
	public Point getPosition() {
		return position;
	}
	
	public void hardDrop() {
		Point point = getDropPoint();
		position.y = point.y;
	}
	
	public void initMino() {
		position.x = start_position.x;
		position.y = start_position.y;
		rotate = start_rotate;
	}
	
	public boolean moveBottom() {
		if (check(getPiece(), position.x, position.y - 1, blocks)) {
			position.y--;
			return true;
		}
		return false;
	}
	
	public boolean moveLeft() {
		if (check(getPiece(), position.x - 1, position.y, blocks)) {
			position.x--;
			return true;
		}
		return false;
	}
	
	public boolean moveRight() {
		if (check(getPiece(), position.x + 1, position.y, blocks)) {
			position.x++;
			return true;
		}
		return false;
	}
		
	public abstract boolean rotateLeft();
	
	public abstract boolean rotateRight();
	
}
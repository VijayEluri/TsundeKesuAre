package org.snowink.tka;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import org.snowink.tka.mino.*;



public class Field implements Cloneable {

	// cloneメソッドに記述する必要のあるフィールド
	private Block[][] ban = new Block[10][24];
	private Mino now_mino = null;
	private Mino hold_mino = null;
	private ArrayList<Mino> next_minos = new ArrayList<Mino>();
	public ArrayList<Mino> minos = new ArrayList<Mino>();
	
	private Field your_field = null;
	
	// cloneメソッドに記述する必要のないフィールド
	private int max_mino_size = 4;
	private int view_line = 20;
	private boolean can_hold = true;
	private int next_mino_volume = 6;
	private int next_rize = 0;
	private int ake = new Random().nextInt(ban.length);
	private boolean btb = false;
	private String message = "";
	private int kakuritsu = 80;
	private boolean not_move = false;
	public boolean ochihajime = true;
	private boolean gameover = false;
	public int total_line = 0;
	public int total_attack = 0;
	boolean clone = false;
	
	public Field() {
		now_mino = getMino();
		for (int i = 0; i < next_mino_volume; i++) {
			next_minos.add(getMino());
		}
		
		for (int x = 0; x < ban.length; x++) {
			for (int y = 0; y < ban[0].length; y++) {
				ban[x][y] = new Block();
			}
		}
	}
	
	public void addTotalAttack(int rize) {
		total_attack += rize;
	}
	
	public void addTotalLines(int line) {
		total_line += line;
	}
	
	public boolean canHold() {
		return can_hold;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		Field field = (Field) super.clone();
		field.now_mino = (Mino) now_mino.clone();
		if (hold_mino != null) {
			field.hold_mino = (Mino) hold_mino.clone();
		}
		field.ban = new Block[ban.length][ban[0].length];
		for (int x = 0; x < field.ban.length; x++) {
			for (int y = 0; y < field.ban[0].length; y++) {
				field.ban[x][y] = (Block) ban[x][y].clone();
			}
		}
		field.minos = (ArrayList<Mino>) minos.clone();
		field.next_minos = (ArrayList<Mino>) next_minos.clone();
		
		field.your_field = null;
		
		field.clone = true;
		
		return field;
	}
	
	public void gameOver() {
		gameover = true;
	}
	
//	public void gameOver() {
//		gameover = true;
//		if (!clone) {
//			timer.gameOver();
//		}
//		DataPool.joutai = 2;
//	}
	
	public int getAke() {
		return ake;
	}
	
	public int getMaxMinoSize() {
		return max_mino_size;
	}

	public void setMaxMinoSize(int max_mino_size) {
		this.max_mino_size = max_mino_size;
	}
	
	public int getNextRize() {
		return next_rize;
	}
	
	public Mino getMino() {
		if (minos.isEmpty()) {
			minos.add(new MinoI(this, ban));
			minos.add(new MinoJ(this, ban));
			minos.add(new MinoL(this, ban));
			minos.add(new MinoO(this, ban));
			minos.add(new MinoS(this, ban));
			minos.add(new MinoT(this, ban));
			minos.add(new MinoZ(this, ban));
		}
		int i = new Random().nextInt(minos.size());
		Mino m = minos.get(i);
		minos.remove(i);
		return m;
	}
	
	public int getKakuritsu() {
		return kakuritsu;
	}
	
	public Mino getNowMino() {
		return now_mino;
	}

	public ArrayList<Mino> getNextMinos() {
		return next_minos;
	}

	public Mino getHoldMino() {
		return hold_mino;
	}

	public Block[][] getBan() {
		return ban;
	}
	
	public int getViewLine() {
		return view_line;
	}
	
	public String getMessage() {
		return message;
	}
	
	public int getNextMinoVolume() {
		return next_mino_volume;
	}
	
	public Field getYourFiled() {
		return your_field;
	}
	
//	public void hardDrop() {
//		now_mino.hardDrop(ban);
//		setti();
//		if (!clone) {
//			timer.reset();
//		}
//	}
//	
//	public void hold() {
//		if (hold_mino == null) {
//			hold_mino = now_mino;
//			now_mino = next_minos.get(0);
//			next_minos.remove(0);
//			next_minos.add(getMino());
//			can_hold = false;
//		}
//		else if (can_hold) {
//			Mino tmp = now_mino;
//			now_mino = hold_mino;
//			hold_mino = tmp;
//			can_hold = false;
//		}
//		hold_mino.initMino();
//	}
	
	public boolean isBTB() {
		return btb;
	}
	
	public boolean isCanHold() {
		return can_hold;
	}
	
	public boolean isGameOver() {
		return gameover;
	}
	
	public boolean isNotMove() {
		return not_move;
	}
	
//	public boolean moveLeft() {
//		if (now_mino.moveLeft(ban)) {
//			not_move = false;
//			if (!clone) {
//				timer.reset();
//			}
//			return true;
//		}
//		return false;
//	}
//	
//	public boolean moveRight() {
//		if (now_mino.moveRight(ban)) {
//			not_move = false;
//			if (!clone) {
//				timer.reset();
//			}
//			return true;
//		}
//		return false;
//	}
//	
//	public boolean moveBottom() {
//		if (now_mino.moveBottom(ban)) {
//			not_move = false;
//			return true;
//		}
//		return false;
//	}
	
//	public void rize() {
//		if (next_rize > 0) {
//			for (int y = ban[0].length - 1; y >= next_rize; y--) {
//				for (int x = 0; x < ban.length; x++) {
//					try {
//						ban[x][y] = (Block) ban[x][y - next_rize].clone();
//					} catch (CloneNotSupportedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			int rnd;
//			for (int y = next_rize - 1; y >= 0; y--) {
//				// 一定確率で穴の位置を変更
//				if (new Random().nextInt(100) + 1 > kakuritsu ) {
//					// 再抽選時に同じ値を防ぐ
//					while (ake == (rnd = new Random().nextInt(ban.length))) {}
//					ake = rnd;
//				}
//				for (int x = 0; x < ban.length; x++) {
//					ban[x][y] = new Block();
//					if (x != ake) {
//						ban[x][y].createBlock();
//						ban[x][y].setColors(new Color(128, 128, 128), new Color(255, 255, 255), new Color(64, 64, 64));
//						ban[x][y].setShadowColors(new Color(255, 255, 0), new Color(255, 255, 0), new Color(255, 255, 0));
//					}
//				}
//			}
//			next_rize = 0;
//		}
//	}
	
//	public void rotateLeft() {
//		if (now_mino.rotateLeft(ban)) {
//			not_move = true;
//			if (!clone) {
//				timer.reset();
//			}
//		}
//	}
//
//	public void rotateRight() {
//		if (now_mino.rotateRight(ban)) {
//			not_move = true;
//			if (!clone) {
//				timer.reset();
//			}
//		}
//	}

//	public void lineCheck() {
//		boolean tspin = false;
//		int line = 0;
//		int rize = 0;
////		message = "";
//		
//		if (now_mino instanceof MinoT && not_move) {
//			int count = 0;
//			Point[] kado = {new Point(0, 1), new Point(0, 3), new Point(2, 1), new Point(2, 3)};
//			for (Point i : kado) {
//				if (now_mino.getPosition().x + i.x < 0 || now_mino.getPosition().x + i.x >= ban.length || now_mino.getPosition().y + i.y < 0 || ban[now_mino.getPosition().x + i.x][now_mino.getPosition().y + i.y].isBlock()) {
//					count++;
//				}
//			}
//			if (count >= 3) {
//				tspin = true;
//			}
//		}
//		
//		for (int i = 0; i < ban[0].length; i++) {
//			boolean line_clear_flag = true;
//			
//			for (int k = 0; k < ban.length; k++) {
//				if (!ban[k][i].isBlock()) {
//					line_clear_flag = false;
//				}
//			}
//			
//			
//			if (line_clear_flag) {
//				line++;
//			}
//			else if (line >= 1) {
//				for (int j = 0; j < ban.length; j++) {
//					try {
//						ban[j][i - line] = (Block) ban[j][i].clone();
//					} catch (CloneNotSupportedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//			
//		}
//		if (line >= 1) {
//			for (int i = ban[0].length; i >= ban[0].length - line - 1; i--) {
//				for (int j = 0; j < ban.length; j++) {
//					ban[j][ban[0].length - 1] = new Block();
//				}
//			}
//		}
//		
//		// 相手に送るライン数
//		if (tspin) {
//			switch (line) {
//			case 1:
//				rize = 2;
//				break;
//			case 2:
//				rize = 4;
//				break;
//			case 3:
//				rize = 6;
//				break;
//			}
//			if (btb) {
//				rize += 1;
//			}
//		}
//		else {
//			switch (line) {
//			case 2:
//				rize = 1;
//				break;
//			case 3:
//				rize = 2;
//				break;
//			case 4:
//				rize = 4;
//				if (btb) {
//					rize += 1;
//				}
//				break;
//			}
//		}
//
//		if (next_rize > 0) {
//				
//			// 相殺しきれてない
//			if (next_rize >= rize) {
//				next_rize -= rize;
//				rize = 0;
//			}
//			// 相殺できてさらに送れる
//			else {
//				rize -= next_rize;
//				next_rize = 0;
//			}
//		}
//		
//		total_line += line;
//		total_attack += rize;
//			
//		if (your_field != null) {
//			your_field.setNextRize(your_field.getNextRize() + rize);
//		}
//		
//		// BTB, メッセージ
//		if (tspin) {
//			switch (line) {
//			case 0:
//				message = "T-SPIN";
//				break;
//			case 1:
//				message = "T-SPIN SINGLE";
//				break;
//			case 2:
//				message = "T-SPIN DOUBLE";
//				break;
//			case 3:
//				message = "T-SPIN TRIPLE";
//				break;
//			}
//			if (line >= 1) {
//				if (btb) {
//					message = "BACK TO BACK " +  message;
//				}
//				btb = true;
//			}
//		}
//		else {
//			switch (line) {
//			case 1: case 2: case 3:
//				message = "";
//				btb = false;
//				break;
//			case 4:
//				message = "4-LINES";
//				if (btb) {
//					message = "BACK TO BACK " +  message;
//				}
//				btb = true;
//				break;
//			}
//		}
//			
////		System.out.println(tspin + " " + line);
//			
//	}
//	
//	
//	public void setti() {
//		
//		for (int x = 0; x < now_mino.getMinoSize(); x++) {
//			for (int y = 0; y < now_mino.getMinoSize(); y++) {
//				if (now_mino.getPiece()[x][y].isBlock()) {
//					try {
//						ban[now_mino.getPosition().x + x][now_mino.getPosition().y + y] = (Block) now_mino.getPiece()[x][y].clone();
//					} catch (CloneNotSupportedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//		
//		lineCheck();
//		rize();
//		
//		// 新しいミノ出現
//		now_mino = next_minos.get(0);
//		next_minos.remove(0);
//		next_minos.add(getMino());
//		if(!now_mino.check(now_mino.getPosition(), ban)) {
//			message = "GAME OVER";
//			gameOver();
//		}
//		else {
//			can_hold = true;
//			if (!clone) {
//				timer.init();
//			}
//			ochihajime = true;
//		}
//	}
	
	public void setAke(int ake) {
		this.ake = ake;
	}
	
	public void setBan(Block[][] blocks) {
		this.ban = blocks;
	}
	
	public void setBTB(boolean btb) {
		this.btb = btb;
	}
	
	public void setCanHold(boolean can_hold) {
		this.can_hold = can_hold;
	}
	
	public void setHoldMino(Mino hold_mino) {
		this.hold_mino = hold_mino;
	}

	public void setKakuritsu(int kakuritsu) {
		this.kakuritsu = kakuritsu;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public void setNextMinoVolume(int next_mino_volume) {
		this.next_mino_volume = next_mino_volume;
	}

	public void setNextRize(int next_rize) {
		this.next_rize = next_rize;
	}
	
	public void setNotMove(boolean not_move) {
		this.not_move = not_move;
	}
	
	public void setNowMino(Mino now_mino) {
		this.now_mino = now_mino;
	}
	
	public void setOchihajime(boolean ochihajime) {
		this.ochihajime = ochihajime;
	}
	
	public void setYourField(Field field) {
		this.your_field = field;
	}

}

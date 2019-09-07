/*
	 Game.java (Java)
	 
	 Objetivo: Estabelecer a inicialização e renderização do Jogo Brick Breaker.
	 
	 Site: http://www.dirackslounge.online
	 
	 Versão 1.0
	 
	 Programador: Rodolfo Dirack 05/09/2019
	 
	 Email: rodolfo_profissional@hotmail.com
	 
	 Licença: GPL-3.0 <https://www.gnu.org/licenses/gpl-3.0.txt>.
*/

package BrickBreaker;
import BrickBreaker.Player;
import BrickBreaker.Screen;
import BrickBreaker.Ball;
import BrickBreaker.Bricks;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.awt.Rectangle;

public class Game implements Runnable, KeyListener{

	public boolean isRunning=false;
	private boolean pausedGame = false;
	private int frame=0;
	private String TITLE;
	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	private int SCALE=1;
	private Screen screen;
	private Player player;
	private Ball ball;
	private ArrayList<Bricks> brick = new ArrayList<Bricks>();

	public Game(String TITLE, int WIDTH, int HEIGHT, int SCALE){
		this.TITLE = TITLE;
		this.SCREEN_WIDTH = WIDTH;
		this.SCREEN_HEIGHT = HEIGHT;
		this.SCALE = SCALE;
		this.screen = new Screen(TITLE, WIDTH, HEIGHT, SCALE);
		this.player = new Player(20*SCALE,5*SCALE,this.SCREEN_WIDTH/2,this.SCREEN_HEIGHT-5);
		this.ball = new Ball(5*SCALE,5*SCALE,this.SCREEN_WIDTH/2,this.SCREEN_HEIGHT/2);
		this.buildBricks();
		this.screen.setBackgroungColor(Color.BLACK);
		this.screen.canvas.addKeyListener(this);
	}

	public void buildBricks(){
		
		int positionInRow;
		int row;
		int i;
		int totalBricksPerRow = (int)(this.SCREEN_WIDTH)/(10);

		for(row=0;row<2;row++){
			for(positionInRow=0;positionInRow<totalBricksPerRow;positionInRow++){
				i = positionInRow + row*(totalBricksPerRow);
				this.brick.add(new Bricks(10*this.SCALE,10*this.SCALE,(positionInRow*10*this.SCALE),(row*10*this.SCALE)));
				this.brick.get(i).bounds = new Rectangle((int)(this.brick.get(i).x),(int)(this.brick.get(i).y),this.brick.get(i).width,this.brick.get(i).height);
			}
		}
	}

	public int getActualFrameNumber(){
		return this.frame;
	}

	private void restart(){
		this.ball.setPosition(this.SCREEN_WIDTH/2,this.SCREEN_HEIGHT/2);
		this.ball.giveBallAnStartingAngle();
		this.ball.speed = this.ball.v0;
	}

	public void updateGame(){
		this.frame++;
		if(this.ball.y >= this.SCREEN_HEIGHT){
			this.restart();
		}else if(!(pausedGame)){
			this.ball.update(this.player.getBoundsRectangle(),this.SCREEN_WIDTH,this.brick);
			this.player.update(this.SCREEN_WIDTH);
		}
	}

	public void renderizeGame(){
		this.screen.drawFrame(this.player,this.ball,this.brick);
	}

	public synchronized void startGame(){
		this.isRunning = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run(){

		while(isRunning){
			updateGame();
			renderizeGame();
			try{
				Thread.sleep(1000/60);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}

	}

	public void keyPressed(KeyEvent e){
		if(!(this.pausedGame) && e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.moveRight=true;
		}else if(!(this.pausedGame) && e.getKeyCode()==KeyEvent.VK_LEFT){
			player.moveLeft=true;
		}else if(e.getKeyCode() == KeyEvent.VK_DOWN){
			if(!(this.pausedGame)){
				this.pausedGame=true;
			}else{
				this.pausedGame=false;
			}
		}
	}

	public void keyReleased(KeyEvent e){
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			player.moveRight=false;
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
			player.moveLeft=false;
		}
	}

	public void keyTyped(KeyEvent e){}

}

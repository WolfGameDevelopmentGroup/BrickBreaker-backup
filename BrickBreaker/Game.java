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
import java.awt.Color;

public class Game implements Runnable{

	public boolean isRunning=false;
	private int frame=0;
	private String TITLE;
	private int SCREEN_WIDTH;
	private int SCREEN_HEIGHT;
	private int SCALE=1;
	private Screen screen;
	private Player player;
	private Ball ball;

	public Game(String TITLE, int WIDTH, int HEIGHT, int SCALE){
		this.TITLE = TITLE;
		this.SCREEN_WIDTH = WIDTH;
		this.SCREEN_HEIGHT = HEIGHT;
		this.SCALE = SCALE;
		this.screen = new Screen(TITLE, WIDTH, HEIGHT, SCALE);
		this.player = new Player(20,5,this.SCREEN_WIDTH/2,this.SCREEN_HEIGHT-5);
		this.ball = new Ball(5,5,this.SCREEN_WIDTH/2,this.SCREEN_HEIGHT/2);
		this.screen.setBackgroungColor(Color.BLACK);
	}

	public int getActualFrameNumber(){
		return this.frame;
	}

	public void updateGame(){
		this.frame++;
		this.ball.update(this.player.getBoundsRectangle(),this.SCREEN_WIDTH);
	}

	public void renderizeGame(){
		this.screen.drawFrame(this.player,this.ball);
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

}

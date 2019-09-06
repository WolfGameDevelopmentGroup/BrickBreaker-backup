/*
	 Ball.java (Java)
	 
	 Objetivo: Define a bola do Jogo Brick Breaker.
	 
	 Site: http://www.dirackslounge.online
	 
	 Versão 1.0
	 
	 Programador: Rodolfo Dirack 06/09/2019
	 
	 Email: rodolfo_profissional@hotmail.com
	 
	 Licença: GPL-3.0 <https://www.gnu.org/licenses/gpl-3.0.txt>.
*/

package BrickBreaker;

import java.util.Random;
import java.awt.Rectangle;

public class Ball extends BrickBreakerObject{

	public double v0=2;
	private double dx;
	private double dy;

	public Ball(int WIDTH, int HEIGHT, int x, int y){

		this.speed=this.v0;
		this.giveBallAnStartingAngle();
		this.setSize(WIDTH,HEIGHT);
		this.setPosition(x,y);
	}

	public void giveBallAnStartingAngle(){

		int numAleatorio = new Random().nextInt(11);
		int angle = 30;

		if(numAleatorio <= 5){
			angle = 210;
		}

		int dangle = new Random().nextInt(120);
		angle = angle+dangle;

		numAleatorio = new Random().nextInt(11);

		this.dx = Math.cos(Math.toRadians((double)angle));
		this.dy = Math.sin(Math.toRadians((double)angle));

	}

	public int getBallXPosition(){
		return (int)this.x;
	}

	public int getBallYPosition(){
		return (int)this.y;
	}

	private void giveBallRandomColisionAngle(){			
			this.dx = Math.cos(Math.toRadians(new Random().nextInt(80)));
			this.dy = Math.sin(Math.toRadians(new Random().nextInt(80)));
	}

	public void update(Rectangle boundsPlayer, int SCREEN_WIDTH){

		this.verifyBallColisionWithWall(SCREEN_WIDTH);
		this.verifyBallColisionWithPlayer(boundsPlayer);
		this.x += this.dx * this.speed;
		this.y += this.dy * this.speed;
	}

	private void verifyBallColisionWithPlayer(Rectangle boundsPlayer){

		this.bounds = new Rectangle((int)(this.x),(int)(this.y),this.width,this.height);

		if(this.bounds.intersects(boundsPlayer)){
			this.speed += 0.1;
			this.giveBallRandomColisionAngle();
			if(dy > 0)		
				this.dy *= -1;
		}


	}

	private void verifyBallColisionWithWall(int SCREEN_WIDTH){

		if(this.x+this.width > SCREEN_WIDTH){
			this.dx *= -1.0;
		}else if(this.x <= 0){
			this.dx *= -1.0;
		}

	}

}

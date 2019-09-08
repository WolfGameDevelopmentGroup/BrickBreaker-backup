/*
	 Screen.java (Java)
	 
	 Objetivo: Gerar a tela do jogo.
	 
	 Site: http://www.dirackslounge.online
	 
	 Versão 1.0
	 
	 Programador: Rodolfo Dirack 06/09/2019
	 
	 Email: rodolfo_profissional@hotmail.com
	 
	 Licença: GPL-3.0 <https://www.gnu.org/licenses/gpl-3.0.txt>.
*/

package BrickBreaker;

import java.awt.Canvas;
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.*;
import java.awt.Font;

public class Screen extends Canvas{

	public static JFrame jframe;
	public static Canvas canvas;
	private String jframeTitle;
	private int WIDTH;
	private int HEIGHT;
	private int SCALE;
	private BufferStrategy bs;
	private BufferedImage layer;
	private Color bgColor = Color.WHITE;
	private Graphics g;

	public Screen(String jframeTitle, int WIDTH, int HEIGHT, int SCALE){
		this.WIDTH = WIDTH;
		this.HEIGHT = HEIGHT;
		this.SCALE = SCALE;

		this.canvas = new Canvas();
		this.canvas.setPreferredSize(new Dimension(this.WIDTH*this.SCALE, this.HEIGHT*this.SCALE));	

		this.jframeTitle = jframeTitle;
		this.jframe = new JFrame(this.jframeTitle);
		this.jframe.setPreferredSize(new Dimension(this.WIDTH*this.SCALE, this.HEIGHT*this.SCALE));

		this.jframe.add(this.canvas);
		this.jframe.setResizable(false);
		this.jframe.pack();
		this.jframe.setLocationRelativeTo(null);
		this.jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	

		this.canvas.createBufferStrategy(3);
		this.bs = this.canvas.getBufferStrategy();
		this.layer = new BufferedImage(this.WIDTH*this.SCALE,this.HEIGHT*this.SCALE,BufferedImage.TYPE_INT_RGB);
		this.g = this.layer.getGraphics();
		this.g.setColor(this.bgColor);
		this.g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
		this.showScreen();
		this.canvas.requestFocus();
	}

	public void showScreen(){
		this.jframe.setVisible(true);
	}

	private void drawBackground(){
		this.g.setColor(this.bgColor);
		this.g.fillRect(0,0,this.WIDTH*this.SCALE, this.HEIGHT*this.SCALE);
	}

	public void drawPlayer(Player player){
		this.g.setColor(Color.white);
		this.g.fill3DRect(player.x,player.y,player.width, player.height,true);
		this.g.setColor(Color.RED);
		this.g.fill3DRect(player.x,player.y,5*this.SCALE,player.height,true);
		this.g.fill3DRect(player.x+player.width-(5*this.SCALE),player.y,5*this.SCALE,player.height,true);
	}
	public void drawBall(Ball ball){
		this.g.setColor(Color.white);
		this.g.fillOval(ball.getBallXPosition(),ball.getBallYPosition(),ball.width, ball.height);
	}

	public void drawPlayerLife(Player player){
		this.g.setColor(Color.white);
		int x = (int)(this.SCALE*(this.WIDTH/2));
		int y = (int)(this.SCALE*(this.HEIGHT/2)+50);
		if(player.getPlayerLife() <= 0){
			this.g.drawString("Game Over",x-40,y);
			this.g.drawString("Press Key Down",x-50,y+40);
			this.g.drawString("to start a new game",x-60,y+80);
			this.g.setColor(Color.RED);
			this.g.fill3DRect((this.WIDTH-30)*this.SCALE,10,30*this.SCALE,5*this.SCALE,false);
		}else{
			this.g.setColor(Color.RED);
			this.g.fill3DRect((this.WIDTH-30)*this.SCALE,10,30*this.SCALE,5*this.SCALE,false);
			this.g.setColor(Color.GREEN);
			this.g.fill3DRect((this.WIDTH-30)*this.SCALE,10,(10*player.getPlayerLife())*this.SCALE,5*this.SCALE,true);

		}
	}

	private void drawPlayerScore(Player player){
		this.g.setColor(Color.WHITE);
		String str = "Score: "+player.score;
		this.g.drawString(str,10,20);
	}

	public void drawBrick(ArrayList<Bricks> brick){
		int n = brick.size();
		int i;
		for(i=0;i<n;i++){
			if(!(brick.get(i).itWasRemoved)){
				this.g.setColor(brick.get(i).getBrickColor());
				this.g.fill3DRect(brick.get(i).x,brick.get(i).y,brick.get(i).width,brick.get(i).height,true);
			}
		}
	}

	public void drawFrame(Player player, Ball ball, ArrayList<Bricks> brick){
		this.g = this.layer.getGraphics();
		this.drawBackground();
		this.drawPlayer(player);
		this.drawBall(ball);
		this.drawBrick(brick);
		this.drawPlayerLife(player);
		this.drawPlayerScore(player);
		this.g = this.bs.getDrawGraphics();
		this.g.drawImage(this.layer, 0, 0, this.WIDTH*this.SCALE,this.HEIGHT*this.SCALE,null);
		this.bs.show();
	}

	public void setBackgroungColor(Color bgColor){
		this.bgColor = bgColor;
		this.g.setColor(this.bgColor);
	}

}

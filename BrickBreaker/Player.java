/*
	 Player.java (Java)
	 
	 Objetivo: Jogador no jogo Brick Breaker.
	 
	 Site: http://www.dirackslounge.online
	 
	 Versão 1.0
	 
	 Programador: Rodolfo Dirack 06/09/2019
	 
	 Email: rodolfo_profissional@hotmail.com
	 
	 Licença: GPL-3.0 <https://www.gnu.org/licenses/gpl-3.0.txt>.
*/

package BrickBreaker;

public class Player extends BrickBreakerObject{

	public Player(int WIDTH, int HEIGHT, int x, int y){

		this.setSize(WIDTH,HEIGHT);
		this.setPosition(x,y);
	}

}
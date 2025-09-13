package spriteframework;

import javax.swing.*;

public abstract class MainFrame extends JFrame  {

	// cria o tabuleiro do jogo
	protected abstract AbstractBoard createBoard();

	public MainFrame(String t) {

		// adiciona o tabuleiro ao JFrame
		add(createBoard());

		// configurações da janela principal (título, tamanho e comportamento)
		setTitle(t);
		setSize(Commons.BOARD_WIDTH, Commons.BOARD_HEIGHT);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}

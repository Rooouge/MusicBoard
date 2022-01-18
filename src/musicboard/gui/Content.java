package musicboard.gui;

import java.awt.BorderLayout;

import javax.swing.BorderFactory;

import jutils.gui.Colors;
import musicboard.gui.board.BoardPanel;
import musicboard.gui.operations.OperationsPanel;

@SuppressWarnings("serial")
public class Content extends jutils.gui.Content {

	public Content() throws Exception {
		this.setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.GRAY_64));
		
		this.add(new OperationsPanel(), BorderLayout.WEST);
		this.add(new BoardPanel(), BorderLayout.CENTER);
	}
	
	
}

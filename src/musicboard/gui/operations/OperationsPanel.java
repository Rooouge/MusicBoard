package musicboard.gui.operations;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import jutils.config.Images;
import jutils.gui.Colors;
import jutils.gui.EmptyBorder;
import jutils.gui.IconOnlyButton;
import jutils.gui.TransparentPanel;
import jutils.random.Utils;
import musicboard.Global;
import musicboard.data.Operations;
import musicboard.data.xml.XML;

@SuppressWarnings("serial")
public class OperationsPanel extends JPanel {

	public OperationsPanel() {
		this.setBackground(Colors.CYAN);
		this.setLayout(new BorderLayout());
		this.setBorder(new EmptyBorder(15));
		
		this.add(menuBar(),	BorderLayout.NORTH);
	}
	
	
	private JPanel menuBar() {
		IconOnlyButton add = new IconOnlyButton(Images.getImageIcon("add"));
		add.addActionListener(e -> {
			new AddDialog().setVisible(true);
			try {
				XML.write();
				Global.refreshGui();
				Utils.beep();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		IconOnlyButton delete = new IconOnlyButton(Images.getImageIcon("delete"));
		delete.addActionListener(e -> {
			int resp = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete all selected files?", "Delete", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, Images.getImageIcon("delete"));
			
			if(resp == JOptionPane.YES_OPTION) {
				try {
					Operations.delete();
					XML.write();
					Global.refreshGui();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		TransparentPanel panel = new TransparentPanel(new GridLayout(5, 1, 0, 15));
		panel.add(add);
		panel.add(delete);
		
		return panel;
	}
	
}

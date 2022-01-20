package musicboard.gui.operations;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jutils.config.Images;
import jutils.gui.Colors;
import jutils.gui.EmptyBorder;
import musicboard.Global;
import musicboard.data.Board;
import musicboard.data.Operations;
import musicboard.data.State;

@SuppressWarnings("serial")
public class AddDialog extends JDialog {

	private JTextField nameField;
	private JComboBox<String> stateBox;
	
	
	public AddDialog() {
		this.setTitle("Add new item");
		this.setIconImage(Images.getImageIcon("add").getImage());
		this.setContentPane(content());
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
	}
	
	
	private JPanel content() {
		JPanel panel = new JPanel(new BorderLayout(0, 15));
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Colors.GRAY_64), new EmptyBorder(15)));
		panel.add(description(), BorderLayout.NORTH);
		panel.add(values(), BorderLayout.CENTER);
		panel.add(create(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel description() {
		JLabel description = new JLabel("<html>Insert the name of the new item and then choose the initial state</html>");
		
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.black), new EmptyBorder(5)));
		panel.add(description, BorderLayout.CENTER);
		
		return panel;
	}
	
	private JPanel values() {
		JLabel nameLabel = new JLabel("Name :");
		nameField = new JTextField(25);
		
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		namePanel.add(nameLabel);
		namePanel.add(nameField);
		
		
		JLabel stateLabel = new JLabel("State :");
		stateBox = new JComboBox<>(Global.get("board", Board.class).getStates().stream().map(State::getName).collect(Collectors.toList()).toArray(new String[0]));
		
		JPanel statePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
		statePanel.add(stateLabel);
		statePanel.add(stateBox);
		
		
		JPanel panel = new JPanel(new GridLayout(2, 1));
		panel.add(namePanel);
		panel.add(statePanel);
		
		return panel;
	}
	
	private JPanel create() {
		JButton button = new JButton("Create");
		button.addActionListener(e -> {			
			Operations.add(nameField, stateBox.getSelectedItem());
			setVisible(false);
		});
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.add(button);
		
		return panel;
	}
}

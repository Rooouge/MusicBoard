package musicboard.gui.operations;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jutils.asserts.Assert;
import jutils.config.Config;
import jutils.config.Images;
import jutils.gui.Colors;
import jutils.gui.EmptyBorder;
import jutils.random.Utils;
import musicboard.Global;
import musicboard.data.Item;
import musicboard.data.Operations;
import musicboard.data.xml.XML;

@SuppressWarnings("serial")
public class EditDialog extends JDialog {

	private Item item;
	private JTextField valueField;
	private JTextField creationDateField;
	private SimpleDateFormat sdf;
	private ActionListener listener;
	
	
	public EditDialog(Item item) {
		this.item = item;
		sdf = new SimpleDateFormat(Config.getValue("creation-date-format"));
		listener = e -> {
			try {
				String newValue = valueField.getText().trim();
				Assert.isNotEmpty(newValue);
				
				String newCreationDateString = creationDateField.getText().trim();
				Date newCreationDate = sdf.parse(newCreationDateString);
				
				Operations.edit(item, newValue, newCreationDate);
				XML.write();
				Global.refreshGui();
				this.setVisible(false);
				Utils.beep();
			} catch(Exception e1) {
				e1.printStackTrace();
			}
		};
		
		setTitle("Edit");
		setIconImage(Images.getImageIcon("edit").getImage());
		this.setContentPane(content());
		
		this.pack();
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setModal(true);
	}
	
	
	private JPanel content() {
		JPanel panel = new JPanel(new BorderLayout(0, 15));
		panel.setBorder(new EmptyBorder(10));
		
		panel.add(editValues(), BorderLayout.CENTER);
		panel.add(confirm(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel editValues() {
		JLabel valueLabel = new JLabel("Value:");
		valueLabel.setForeground(Colors.GRAY_64);
		valueField = new JTextField(item.getValue(), 30);
		valueField.addActionListener(listener);
		JPanel valuePanel = new JPanel(new BorderLayout());
		valuePanel.add(valueLabel, BorderLayout.WEST);
		valuePanel.add(valueField, BorderLayout.SOUTH);
		
		JLabel creationDateLabel = new JLabel("Creation date:");
		creationDateLabel.setForeground(Colors.GRAY_64);
		creationDateField = new JTextField(sdf.format(item.getCreationDate()));
		creationDateField.addActionListener(listener);
		JPanel creationDatePanel = new JPanel(new BorderLayout());
		creationDatePanel.add(creationDateLabel, BorderLayout.WEST);
		creationDatePanel.add(creationDateField, BorderLayout.SOUTH);
		
		JPanel panel = new JPanel(new BorderLayout());		
		panel.add(valuePanel, BorderLayout.NORTH);
		panel.add(creationDatePanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	private JPanel confirm() {		
		JButton confirmButton = new JButton("Confirm");
		confirmButton.addActionListener(listener);
		
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.add(confirmButton);
		
		return panel;
	}
}

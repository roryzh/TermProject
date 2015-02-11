import java.awt.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.ArrayList;

/**
 * 
 * @author tandoni
 *
 */
@SuppressWarnings("serial")
public class MapFrame extends JFrame {
	static ArrayList<String> places = null;
	private static final int WIDTH = 1000;
	private static final int HEIGHT = 1000;
	public Panel panel = new Panel();
	public static Console console;// = new Console();

	public MapFrame() {
		super("MAPS");
		this.setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.add(new ControlPanel(), BorderLayout.NORTH);
		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLoweredBevelBorder(), "Display");
		border.setTitleJustification(TitledBorder.LEFT);
		this.panel.setBorder(border);
		content.add(this.panel, BorderLayout.CENTER);
		content.add(this.console, BorderLayout.EAST);
		setVisible(true);
	}

	public static void main(String[] args) {
		places = new ArrayList<String>();
		// JButton button = new JButton();
		// ArrayList<JButton> buttons = new ArrayList<JButton>();
		// JPanel panel = new JPanel();
		// JFrame buttonFrame = new JFrame();
		// buttonFrame.setAlwaysOnTop(true);
		// JTextField whereToStart = new JTextField();
		// buttonFrame.add(whereToStart);
		//
		// buttonFrame.add(panel);
		// buttonFrame.setSize(WIDTH, HEIGHT);
		// buttonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// buttonFrame.setVisible(true);
		// panel.setVisible(true);

		// places.add("asdasdas");
		try {
			BufferedReader read = new BufferedReader(new FileReader("input.in"));
			String line = read.readLine().toLowerCase();
			
			
			
			
		} catch (IOException error) {
			MapFrame.console.setText("File not Found");
		}
		// places.add("Indianapolis");
		// places.add("Terre Haute");
		// places.add("Bloomington");
		// places.add("NYC");
		Collections.sort(places);

		new MapFrame();
	}

	/**
	 * 
	 * @author tandoni
	 *
	 */
	class ControlPanel extends JPanel {
		InputField fromWhere = new InputField();
		DDList dropDown = new DDList();
		InputField toWhere = new InputField();
		// InputField howMuchDistance
		CurrentLocationButton currButton = new CurrentLocationButton();
		DestinationLocationButton destButton = new DestinationLocationButton();

		public ControlPanel() {
			TitledBorder border = BorderFactory.createTitledBorder(
					BorderFactory.createLoweredBevelBorder(), "Control Panel");
			border.setTitleJustification(TitledBorder.LEFT);

			this.setBorder(border);
			this.setLayout(new FlowLayout());
			this.setPreferredSize(new Dimension(100, 100));
			this.add(this.fromWhere);
			this.add(this.currButton);
			this.add(this.toWhere);
			this.add(this.destButton);
			this.add(this.dropDown);
		}

		/**
		 * 
		 * @author tandoni
		 *
		 */
		// TODO: Implement this!
		class CurrentLocationButton extends JButton {
			public CurrentLocationButton() {
				super("Where are you?");
				this.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						String curr = ControlPanel.this.fromWhere.getText()
								.toLowerCase();
						for (int i = 0; i < places.size(); i++)
							places.set(i, places.get(i).toLowerCase());
						if (places.contains(curr))
							System.out.println(curr); // TODO: Stuff here.
						else
							MapFrame.this.console.setText(String.format(
									"%s doesn't\nexist", curr));
					}
				});
			}
		}

		/**
		 * 
		 * @author tandoni
		 *
		 */
		class DestinationLocationButton extends JButton {
			public DestinationLocationButton() {
				super("What's your destination??");
				this.addMouseListener(new MouseAdapter() {
					@SuppressWarnings("unused")
					public void MousePressed(MouseEvent e) {
						String dest = ControlPanel.this.toWhere.getText()
								.toLowerCase();
						for (int i = 0; i < places.size(); i++)
							places.set(i, places.get(i).toLowerCase());
						if (places.contains(dest)) // TODO: Set dest and path
													// finding etc.
							System.out.println("Destination is: " + dest);
						else
							MapFrame.this.console.setText(String.format(
									"%s doesn't\nexist", dest));

					}
				});
			}
		}
	}

	/**
	 * 
	 * @author tandoni
	 *
	 */
	class Panel extends JPanel {

	}

	/**
	 * 
	 * @author tandoni
	 *
	 */
	@SuppressWarnings({ "rawtypes" })
	class DDList extends JComboBox {
		@SuppressWarnings("unchecked")
		public DDList() {
			super();
			for (int i = 0; i < places.size(); i++)
				this.addItem(places.get(i));
			this.setRenderer(new MyComboBoxRenderer("Select a destination"));
			this.setPreferredSize(new Dimension(135, 20));
			this.setSelectedIndex(-1);
		}
	}

	/**
	 * just for header.
	 * 
	 * @author tandoni
	 *
	 */
	class MyComboBoxRenderer extends JLabel implements ListCellRenderer {
		private String _title;

		public MyComboBoxRenderer(String title) {
			_title = title;
		}

		@Override
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean hasFocus) {
			if (index == -1 && value == null)
				setText(_title);
			else
				setText(value.toString());
			return this;
		}
	}

	/**
	 * 
	 * @author tandoni
	 *
	 */
	class InputField extends JTextField {
		public InputField() {
			this.setColumns(8);
			this.selectAll();
		}
	}

	/**
	 * 
	 * @author tandoni
	 *
	 */
	class Console extends JTextArea {
		public Console() {
			this.setEditable(false);
			TitledBorder border = BorderFactory.createTitledBorder(
					BorderFactory.createLoweredBevelBorder(), "Console");
			border.setTitleJustification(TitledBorder.LEFT);
			this.setBorder(border);
			this.setPreferredSize(new Dimension(145, 5));
			this.append("MAPS Helper:\n " + " Type your current\n location\n");
		}
	}

}

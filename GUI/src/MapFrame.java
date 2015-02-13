import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;

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
	public Panel panel;
	public Console console= new Console();
	static HashMap<String, String> cities = new HashMap<String, String>();

	public MapFrame() {
		super("MAPS");
		panel = new Panel(cities);
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
		try {
			BufferedReader read = new BufferedReader(new FileReader("input.in"));
			int numberOfCities = Integer.parseInt(read.readLine().toString());
			for (int i = 0; i < numberOfCities; i++) {
				cities.put(read.readLine().toString(), read.readLine()
						.toString());
			}

		} catch (IOException error) {
		//	MapFrame.this.console.setText("File not Found");
		}
//		 places.add("Indianapolis");
//		 places.add("Terre Haute");
//		 places.add("Bloomington");
//		 places.add("NYC");
	//	places.add()
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
		ArrayList<Node> nodes = new ArrayList<Node>();
		public Panel(HashMap<String, String> cities) {
			HashSet<String> keys = new HashSet<String>();
			keys.addAll(cities.keySet());
			for(String key : keys){
				String[] str = cities.get(key).split(" ");
				int x = Integer.parseInt(str[0]);
				int y = Integer.parseInt(str[1]);
				nodes.add(new Node(x,y,key));
			}
		}

		public void paintComponent(Graphics comp) {
			super.paintComponent(comp);
			Graphics2D comp2D = (Graphics2D) comp;
			for(int i = 0 ; i<nodes.size();i++){
				nodes.get(i).drawOn(comp2D);
			}
		}
		
		class Node{// extends JComponent {
			private double CenterX, CenterY;
			private double radius;
			private Color color;
			private String value;

			public Node(double x, double y, String v) {
			//	this.setBorder(BorderFactory.createLoweredBevelBorder());
				this.CenterX = x;
				this.CenterY = y;
				this.radius = 25;
				this.color = new Color(255, 255, 0);
				this.value = v;
			}

			public void drawOn(Graphics2D g2) {
				Ellipse2D.Double cir = new Ellipse2D.Double(this.CenterX,
						this.CenterY, this.radius, this.radius);
				g2.draw(cir);
				g2.setColor(this.color);
				g2.fill(cir);
				g2.setColor(Color.BLACK);
				BasicStroke temp = new BasicStroke();
				Shape outline = temp.createStrokedShape(cir);
				g2.draw(outline);
				g2.drawString("" + this.value, (float) (this.CenterX + 9.5),
						(float) (this.CenterY + 16.5));
			}

			public void translate(double x, double y, String v) {
				this.CenterX = x;
				this.CenterY = y;
				this.value = v;
			}
		}
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

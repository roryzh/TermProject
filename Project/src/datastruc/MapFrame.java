package datastruc;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.border.TitledBorder;

import datastruc.AStar.AStarNode;
import datastruc.AdjList.Place;

/**
 * 
 * @author tandoni
 *
 */
@SuppressWarnings("serial")
public class MapFrame extends JFrame {
	private static final int WIDTH = 1750;
	private static final int HEIGHT = 1080;
	public Panel panel;
	public Response response;
	AStar myGPS;

	static AdjList b;

	String[] str;

	public MapFrame(AdjList x) {
		super.setTitle("Null Pointers");
		b = x;
		str = new String[b.adj.size()];
		panel = new Panel();
		response = new Response();
		myGPS = new AStar("", "");
		int i = 0;
		for (Map.Entry<String, Place> entry : b.adj.entrySet()) {
			str[i] = entry.getKey();
			i++;
		}
		Arrays.sort(str);
		this.setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container content = getContentPane();
		content.setLayout(new BorderLayout());
		content.add(new ControlPanel(), BorderLayout.EAST);
		TitledBorder border = BorderFactory.createTitledBorder(
				BorderFactory.createLoweredBevelBorder(), "Route");
		border.setTitleJustification(TitledBorder.LEFT);
		this.panel.setBorder(border);
		content.add(this.panel, BorderLayout.CENTER);
		content.add(response, BorderLayout.WEST);
		setVisible(true);

	}

	/**
	 * 
	 * @author tandoni
	 *
	 */
	class ControlPanel extends JPanel {
		DDList fromList = new DDList(str, "Select Start Point");
		DDList toList = new DDList(str, "Select your Destination");
		CurrentLocationButton doStuff = new CurrentLocationButton("GO! GO! GO!");

		JRadioButton timeButton = new JRadioButton("I'm a busy person");
		JRadioButton distanceButton = new JRadioButton("I'm low on gas");
		JRadioButton moneyButton = new JRadioButton("I'm Poor!");

		ButtonGroup group = new ButtonGroup();

		public ControlPanel() {
			fromList.setBackground(new Color(135, 206, 250));
			toList.setBackground(new Color(135, 206, 250));
			fromList.setToolTipText("Select your Starting Point");
			toList.setToolTipText("Select your Destination bro");

			distanceButton.setBackground(new Color(238, 100, 100));
			timeButton.setBackground(new Color(75, 255, 75));
			moneyButton.setBackground(new Color(75, 255, 255));

			TitledBorder border = BorderFactory.createTitledBorder(
					BorderFactory.createLoweredBevelBorder(), "Dashboard");
			border.setTitleJustification(TitledBorder.LEFT);
			distanceButton.setSelected(true);

			group.add(timeButton);
			group.add(distanceButton);
			group.add(moneyButton);

			distanceButton.setActionCommand("distance");
			timeButton.setActionCommand("time");
			moneyButton.setActionCommand("money");

			FlowLayout dd = new FlowLayout();

			dd.setVgap(50);
			this.setBorder(border);
			this.setLayout(dd);
			this.setPreferredSize(new Dimension(400, 0));
			this.add(this.fromList);
			this.add(this.toList);
			this.add(this.distanceButton);
			this.add(this.timeButton);
			this.add(this.moneyButton);
			this.add(this.doStuff);
		}

		/**
		 * 
		 * @author tandoni
		 *
		 */
		@SuppressWarnings("rawtypes")
		class DDList extends JComboBox {
			@SuppressWarnings("unchecked")
			public DDList(String[] s, String title) {
				super(s);
				this.setRenderer(new MyComboBoxRenderer(title));
				this.setPreferredSize(new Dimension(170, 20));
				this.setSelectedIndex(-1);
			}
		}

		/**
		 * 
		 * @author tandoni
		 *
		 */
		class CurrentLocationButton extends JButton {
			public CurrentLocationButton(String s) {
				super(s);
				this.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {

						if (group.getSelection().getActionCommand()
								.equals("distance")) {
							// System.out.println("goes in distance");
							String from = (String) ControlPanel.this.fromList
									.getSelectedItem();
							if (!b.adj.containsKey(from)) {
								response.setText(String.format(
										"%s doesn't\nexist", from));
							} else {
								String to = (String) ControlPanel.this.toList
										.getSelectedItem();
								myGPS = new AStar(from, to);
								myGPS.findPath();
								String path = "Start from ";
								double distanceTravelled = myGPS
										.getTotalDistance();
								double time = myGPS.getTotalTime();
								double money = myGPS.getTotalSpend();
								panel.repaint();
								for (int i = 0; i < myGPS.camefrom.size(); i++) {
									path += (myGPS.camefrom.get(i).name);
									if (i + 1 < myGPS.camefrom.size())
										path += ",\nthen go to ";
								}
								response.setText("Least Distance path is:\n"
										+ path
										+ ".\nYou have Reached!"
										+ "\n\nTotal distance travelled:\n"
										+ String.format("%.2f",
												distanceTravelled) + " miles"
										+ "\n\nTotal time taken:\n"
										+ String.format("%.2f", time)
										+ " hours"
										+ "\n\nTotal Money Spent:\n$"
										+ String.format("%.2f", money));
							}
						}// done distance

						else if (group.getSelection().getActionCommand()
								.equals("time")) {
							// System.out.println("goes in time");
							String from = (String) ControlPanel.this.fromList
									.getSelectedItem();
							if (!b.adj.containsKey(from)) {
								response.setText(String.format(
										"%s doesn't\nexist", from));
							} else {
								String to = (String) ControlPanel.this.toList
										.getSelectedItem();
								myGPS = new AStar(from, to);
								myGPS.shortestTimePath();
								String path = "Start from ";
								double distanceTravelled = myGPS
										.getTotalDistance();
								double time = myGPS.getTotalTime();
								double money = myGPS.getTotalSpend();
								panel.repaint();
								for (int i = 0; i < myGPS.camefrom.size(); i++) {
									path += (myGPS.camefrom.get(i).name);
									if (i + 1 < myGPS.camefrom.size())
										path += ",\nthen go to ";
								}
								response.setText("Least Distance path is:\n"
										+ path
										+ ".\nYou have Reached!"
										+ "\n\nTotal distance travelled:\n"
										+ String.format("%.2f",
												distanceTravelled) + " miles"
										+ "\n\nTotal time taken:\n"
										+ String.format("%.2f", time)
										+ " hours"
										+ "\n\nTotal Money Spent:\n$"
										+ String.format("%.2f", money));
							}
						} // done time

						else if (group.getSelection().getActionCommand()
								.equals("money")) {
							// System.out.println("goes in money");
							String from = (String) ControlPanel.this.fromList
									.getSelectedItem();
							if (!b.adj.containsKey(from)) {
								response.setText(String.format(
										"%s doesn't\nexist", from));
							} else {
								String to = (String) ControlPanel.this.toList
										.getSelectedItem();
								myGPS = new AStar(from, to);
								myGPS.cheapestPath();
								String path = "Start from ";
								double distanceTravelled = myGPS
										.getTotalDistance();
								double time = myGPS.getTotalTime();
								double money = myGPS.getTotalSpend();
								panel.repaint();
								for (int i = 0; i < myGPS.camefrom.size(); i++) {
									path += (myGPS.camefrom.get(i).name);
									if (i + 1 < myGPS.camefrom.size())
										path += ",\nthen go to ";
								}
								response.setText("Least Distance path is:\n"
										+ path
										+ ".\nYou have Reached!"
										+ "\n\nTotal distance travelled:\n"
										+ String.format("%.2f",
												distanceTravelled) + " miles"
										+ "\n\nTotal time taken:\n"
										+ String.format("%.2f", time)
										+ " hours"
										+ "\n\nTotal Money Spent:\n$"
										+ String.format("%.2f", money));
							}
						}// done with money... YAY!

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

		public Panel() {
			Set<String> keys = b.adj.keySet();
			for (String key : keys) {
				int x = b.adj.get(key).getX();
				int y = b.adj.get(key).getY();
				nodes.add(new Node(x, y, key));
			}

		}

		public void paintComponent(Graphics comp) {
			super.paintComponent(comp);
			Graphics2D comp2D = (Graphics2D) comp;
			for (int i = 0; i < nodes.size(); i++) {
				nodes.get(i).drawOn(comp2D);
			}
			if (MapFrame.this.myGPS.camefrom != null) {
				comp2D.setStroke(new BasicStroke(5));
				comp2D.setColor(Color.PINK);
				this.edgeMaker(comp2D);
			}
		}

		public void edgeMaker(Graphics2D comp2D) {
			ArrayList<AStarNode> locations = MapFrame.this.myGPS.camefrom;
			for (int i = 1; i < locations.size(); i++) {
				double x1 = b.adj.get(locations.get(i).name).getX() + 12.5;
				double y1 = b.adj.get(locations.get(i).name).getY() + 12.5;
				double x2 = b.adj.get(locations.get(i - 1).name).getX() + 12.5;
				double y2 = b.adj.get(locations.get(i - 1).name).getY() + 12.5;
				Line2D l1 = new Line2D.Double(x1, y1, x2, y2);
				comp2D.draw(l1);
			}
		}

		/**
		 * 
		 * @author tandoni
		 *
		 */
		class Node {// extends JComponent {
			private double CenterX, CenterY;
			private double radius;
			private Color color;
			private String value;

			public Node(double x, double y, String v) {
				this.CenterX = x;
				this.CenterY = y;
				this.radius = 25;
				this.color = new Color(0, 191, 255);
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
						(float) (this.CenterY + 40));
			}

			public void translate(double x, double y, String v) {
				this.CenterX = x;
				this.CenterY = y;
				this.value = v;
			}
		}
	}

	/**
	 * just for header.
	 * 
	 * @author tandoni
	 *
	 */
	@SuppressWarnings("rawtypes")
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
	class Response extends JTextArea {
		public Response() {
			this.setEditable(false);
			TitledBorder border = BorderFactory.createTitledBorder(
					BorderFactory.createLoweredBevelBorder(), "Response");
			border.setTitleJustification(TitledBorder.LEFT);
			this.setBorder(border);
			this.setFont(new Font("Helvetica", Font.BOLD, 16));
			this.setPreferredSize(new Dimension(250, 5));
			this.append("MAPS Helper:\n " + " Type your current\n location\n");
		}
	}

}

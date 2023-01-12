package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.swing.JPanel;

/**
 * Represents a single Histogram. It contains a Map of values and how frequently they appear,
 * and draws a bar graph based on that on a JPanel.
 */
public class Histogram extends JPanel {
  private static double SCALING_FACTOR;
  private final Map<Integer, Integer> valueAndFrequencyTable;
  private final Color histogramColor;

  /**
   * Constructor that takes in a model and a type and initializes the valueAndFrequencyTable field
   * and the color of the histogram with the method in the model class.
   *
   * @param imageModel the image that will be represented with in the histogram
   * @param type       the type of color that the histogram will be
   */
  public Histogram(ImageModel imageModel, String type) {
    super();
    this.valueAndFrequencyTable = imageModel.returnColorFrequencies(type);
    switch (type) {
      case "red":
        this.histogramColor = Color.RED;
        break;
      case "green":
        this.histogramColor = new Color(24, 173, 24);
        break;
      case "blue":
        this.histogramColor = Color.BLUE;
        break;
      case "intensity":
        this.histogramColor = Color.LIGHT_GRAY;
        break;
      default:
        throw new IllegalArgumentException("Invalid color type");
    }

  }

  // Helper method that sets the scaling factor based on the max value of the map
  private void graphDimensions() {
    Collection<Integer> frequency = this.valueAndFrequencyTable.values();
    int maxValue = Collections.max(frequency);
    SCALING_FACTOR = (this.getHeight() * 0.3) / maxValue;
  }

  /**
   * Draws the graph by going through the map and drawing lines that start from the bottom
   * and go as tall as the frequency (which is multiplied by the scaling factor so that the
   * histogram never goes off-screen).
   *
   * @param g the <code>Graphics</code> object to protect
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;
    this.graphDimensions();
    AffineTransform originalTransform = g2d.getTransform();
    g2d.translate(0, this.getPreferredSize().getHeight());
    g2d.scale(1, -1);

    for (Integer value : this.valueAndFrequencyTable.keySet()) {
      int barHeight = this.valueAndFrequencyTable.get(value);
      g2d.setColor(this.histogramColor);
      g2d.drawLine(value, 0, value, (int) (barHeight * SCALING_FACTOR));
    }
    g2d.setTransform(originalTransform);
  }


}
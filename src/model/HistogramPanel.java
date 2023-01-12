package model;

import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * The panel where all the histograms are stored. This class takes in an array of histograms
 * (so in context of this assignment the red, green, blue, and intensity histograms) and adds
 * each one to this panel, which is set in a Grid layout that way we get a nice square that sits
 * right next to the image the user is editing. The reason we chose an array is so we can support
 * an arbitrary number of Histograms (i.e. let's say in the future we needed to add in a max value
 * histogram, all we would need to do is add in a case in our method that returns histograms and
 * in the histogram class, but this remains unchanged).
 */
public class HistogramPanel extends JPanel {

  /**
   * Constructor that takes in an array of histograms and sets the panel accordingly.
   *
   * @param histograms an array of histograms to be added
   */
  public HistogramPanel(Histogram[] histograms) {
    for (Histogram h : histograms) {
      this.add(h);
    }
    this.setLayout(new GridLayout(2, 2));
  }


}

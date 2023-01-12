package view;

import java.awt.FlowLayout;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import controller.ImageControllerImpl;
import model.Histogram;
import model.HistogramPanel;
import model.ImageModel;
import model.ImageModelImpl;
import model.Matrix;

/**
 * This class is our GUI. It extends JFrame and contains all the functionality required to
 * make a functional image editor with Java Swing.
 */
public class GUIManager extends JFrame implements ActionListener {

  private final JPanel imagePanel;
  private final JButton loadButton;
  private final JButton saveButton;
  private final JButton resetButton;
  private final JComboBox operations;
  private final JLabel imageOnScreen;
  private final ImageControllerImpl imageController;
  private Histogram[] histograms;
  private ImageModel originalImage;
  private ImageModel imageToBeMutated;

  /**
   * This constructor initializes all of our class variables and adds all JPanels
   * to the main panel.
   */
  public GUIManager() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new GridBagLayout());
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    JScrollPane mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);
    JPanel operationsPanel = new JPanel();
    operationsPanel.setLayout(new FlowLayout());
    mainPanel.add(operationsPanel);
    this.imagePanel = new JPanel();
    imagePanel.setLayout(new FlowLayout());
    imageOnScreen = new JLabel();
    JScrollPane imageScrollPane = new JScrollPane(imageOnScreen);
    imageScrollPane.setPreferredSize(new Dimension(600, 600));
    imagePanel.add(imageScrollPane);
    histograms = new Histogram[]{};
    mainPanel.add(imagePanel);
    this.loadButton = new JButton("Load image");
    this.saveButton = new JButton("Save image");
    this.resetButton = new JButton("Reset image");
    this.loadButton.addActionListener(this);
    this.saveButton.addActionListener(this);
    this.resetButton.addActionListener(this);
    String[] operationsThatTheUserCanPerform = {"sepia", "greyscale", "horizontal flip",
        "vertical flip", "sharpen", "blur", "brighten", "darken",
        "greyscale-red-component", "greyscale-green-component", "greyscale-blue-component",
        "greyscale-value-component", "greyscale-intensity-component",
        "greyscale-luma-component"};
    operations = new JComboBox(operationsThatTheUserCanPerform);
    operations.addActionListener(this);
    operationsPanel.add(operations);
    this.imageController = new ImageControllerImpl();
    operationsPanel.add(loadButton);
    operationsPanel.add(saveButton);
    operationsPanel.add(resetButton);
    this.pack();
    this.setVisible(true);
    this.setResizable(false);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == loadButton) {
      this.loadImage();
    } else if (e.getSource() == saveButton) {
      this.saveImage();
    } else if (e.getSource() == operations) {
      if (imageToBeMutated != null && originalImage != null) {
        String s = Arrays.toString(operations.getSelectedObjects());
        this.performGUIMethods(s);
      }
    } else if (e.getSource() == resetButton) {
      if (imageToBeMutated != null && originalImage != null) {
        imageOnScreen.setIcon(new ImageIcon(originalImage.returnAsBufferedImage()));
        imageToBeMutated = originalImage;
        this.setHistogram();
      }
    }
  }

  // This method sets the histogram to be displayed based on the current images RGB values.
  private void setHistogram() {
    Histogram redHistogram = new Histogram(imageToBeMutated, "red");
    redHistogram.setPreferredSize(new Dimension(300, 300));
    Histogram greenHistogram = new Histogram(imageToBeMutated, "green");
    greenHistogram.setPreferredSize(new Dimension(300, 300));
    Histogram blueHistogram = new Histogram(imageToBeMutated, "blue");
    blueHistogram.setPreferredSize(new Dimension(300, 300));
    Histogram intensityHistogram = new Histogram(imageToBeMutated, "intensity");
    intensityHistogram.setPreferredSize(new Dimension(300, 300));
    histograms = new Histogram[]{redHistogram, greenHistogram, blueHistogram, intensityHistogram};
    HistogramPanel histogramPanel = new HistogramPanel(histograms);
    imagePanel.add(histogramPanel);
    if (imagePanel.getComponentCount() > 2) {
      imagePanel.remove(1);
    }
    this.pack();
  }

  /**
   * Takes in an operation and performs the methods accordingly.
   *
   * @param s The type of operation the user is performing
   */
  private void performGUIMethods(String s) {
    switch (s) {
      case "[sepia]":
        imageToBeMutated = imageToBeMutated.colorTransformation(Matrix.SEPIA);
        break;
      case "[greyscale]":
        imageToBeMutated = imageToBeMutated.colorTransformation(Matrix.LUMA);
        break;
      case "[horizontal flip]":
        imageToBeMutated = imageToBeMutated.horizontalFlip();
        break;
      case "[vertical flip]":
        imageToBeMutated = imageToBeMutated.verticalFlip();
        break;
      case "[sharpen]":
        imageToBeMutated = imageToBeMutated.filter(Matrix.SHARPEN);
        break;
      case "[blur]":
        imageToBeMutated = imageToBeMutated.filter(Matrix.GAUSSIAN_BLUR);
        break;
      case "[brighten]":
        this.enhanceColor("brighten");
        break;
      case "[darken]":
        this.enhanceColor("darken");
        break;
      case "[greyscale-red-component]":
        imageToBeMutated = imageToBeMutated.greyScale("red");
        break;
      case "[greyscale-green-component]":
        imageToBeMutated = imageToBeMutated.greyScale("green");
        break;
      case "[greyscale-blue-component]":
        imageToBeMutated = imageToBeMutated.greyScale("blue");
        break;
      case "[greyscale-value-component]":
        imageToBeMutated = imageToBeMutated.greyScale("value");
        break;
      case "[greyscale-intensity-component]":
        imageToBeMutated = imageToBeMutated.greyScale("intensity");
        break;
      case "[greyscale-luma-component]":
        imageToBeMutated = imageToBeMutated.greyScale("luma");
        break;
      default:
        JOptionPane.showMessageDialog(GUIManager.this,
                "We can't do this, please try again",
                "Dropdown error",
                JOptionPane.INFORMATION_MESSAGE);
    }
    this.setHistogram();
    imageOnScreen.setIcon(new ImageIcon(imageToBeMutated.returnAsBufferedImage()));
  }

  // Helper that performs the brighten and darken operations
  private void enhanceColor(String enhanceType) {
    String resp = "Please enter a positive integer to specify " +
            "how much you want to enhance your image by";
    String input = JOptionPane.showInputDialog(this, resp);
    if (input != null) {
      int colorToAdjust = -1;
      try {
        colorToAdjust = Integer.parseInt(input);
      } catch (NumberFormatException e) {
        // Do nothing, since we initialized the value to be -1 it will pass the below if statement
        // and show an error message.
      }
      if (colorToAdjust < 0) {
        JOptionPane.showMessageDialog(GUIManager.this,
                "Please enter a positive integer",
                "Didn't enter a positive integer!", JOptionPane.ERROR_MESSAGE);
        return;
      }
      imageToBeMutated = imageToBeMutated.changeExposure(enhanceType, colorToAdjust);
      imageOnScreen.setIcon(new ImageIcon(imageToBeMutated.returnAsBufferedImage()));
    }

  }

  // Helper method that saves the image to whatever directory the user specifies.
  private void saveImage() {
    if (imageToBeMutated != null && originalImage != null) {
      ImageView view = new ImageViewImpl(imageToBeMutated);
      final JFileChooser fileChooser = new JFileChooser(".");
      int returnValue = fileChooser.showSaveDialog(GUIManager.this);
      if (returnValue == JFileChooser.APPROVE_OPTION) {
        String fileName = fileChooser.getSelectedFile().toString();
        String[] fileTypeOfImageSave = fileName.split("\\.");
        String strToCheckSave = fileTypeOfImageSave[fileTypeOfImageSave.length - 1];
        if (ImageControllerImpl.checkIfNotPPM(strToCheckSave)) {
          try {
            view.saveBufferedImage(strToCheckSave, fileName);
          } catch (IOException ex) {
            this.showIOError();
          }
        } else if (strToCheckSave.equals("ppm")) {
          BufferedWriter writer = null;
          try {
            writer = new BufferedWriter(new FileWriter(fileName));
          } catch (IOException ex) {
            this.showIOError();
          }
          try {
            view.saveFile(writer);
          } catch (IOException ex) {
            this.showIOError();
          }
        } else {
          JOptionPane.showMessageDialog(GUIManager.this,
                  "Please make sure the file type you want to save is valid. " +
                          "See README and USEME for full details on what files we support. ",
                  "Unable to save file",
                  JOptionPane.INFORMATION_MESSAGE);
        }
      }
    }
  }

  // Abstracted helper to show an IO error so that we didn't have to type an error message 4
  // different times
  private void showIOError() {
    JOptionPane.showMessageDialog(GUIManager.this,
            "IO error occurred. Please try again",
            "Input/Output error",
            JOptionPane.INFORMATION_MESSAGE);
  }

  // Helper for loading in any type of image (ppm, jpg, png, bmp, etc.)
  private void loadImage() {
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    int response = fileChooser.showOpenDialog(this);
    if (response == JFileChooser.APPROVE_OPTION) {
      File file = fileChooser.getSelectedFile();
      String[] fileTypeOfImageSave = file.getAbsolutePath().split("\\.");
      String fileType = fileTypeOfImageSave[fileTypeOfImageSave.length - 1];
      StringBuilder stringBuilder = new StringBuilder();
      Scanner sc = null;
      try {
        if (ImageControllerImpl.checkIfNotPPM(fileType)) {
          stringBuilder =
                  this.imageController.getImageAsStringBuilder(fileChooser.
                          getSelectedFile().getAbsolutePath());
          this.setImage(sc, stringBuilder);
        } else if (fileType.equals("ppm")) {

          try {
            sc = new Scanner(new FileInputStream(file));
          } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(GUIManager.this,
                    ("File " + file + " not found!"),
                    "File not found!", JOptionPane.ERROR_MESSAGE);
            return;
          }

          while (sc.hasNextLine()) {
            String s = sc.nextLine();
            if (s.charAt(0) != '#') {
              stringBuilder.append(s + System.lineSeparator());
            }
          }
          this.setImage(sc, stringBuilder);
        } else {
          JOptionPane.showMessageDialog(GUIManager.this,
                  "The file you submitted is not supported by us, please see the USEME " +
                          "for more details",
                  "Not a valid file", JOptionPane.ERROR_MESSAGE);
          return;
        }

      } catch (IOException ex) {
        this.showIOError();
      }
      this.setHistogram();
      this.pack();
    }
  }

  // Takes in a scanner and a string builder and sets the image on screen
  private void setImage(Scanner sc, StringBuilder stringBuilder) {
    sc = new Scanner(stringBuilder.toString());
    Scanner sc2 = new Scanner(stringBuilder.toString());
    imageToBeMutated = new ImageModelImpl(sc);
    originalImage = new ImageModelImpl(sc2);
    imageOnScreen.setIcon(new ImageIcon(imageToBeMutated.returnAsBufferedImage()));
  }


}

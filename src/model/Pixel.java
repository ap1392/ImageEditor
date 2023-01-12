package model;


import java.awt.Color;

/**
 * Represents a single pixel that is used to make an image. Every pixel has a row and column to
 * represent where it is in the image and an RGB value to represent the color it shows.
 */
public class Pixel {
  private final int pixelRow;
  private final int pixelCol;
  private int redColorValue;
  private int greenColorValue;
  private int blueColorValue;
  private Color color;

  /**
   * Constructor that takes in a pixel row, column, and the rgb values.
   *
   * @param pixelRow        represents the row of the pixel
   * @param pixelCol        represents the column of the pixel
   * @param redColorValue   represents the red color value
   * @param greenColorValue represents the green color value
   * @param blueColorValue  represents the blue color value
   * @throws IllegalArgumentException if the rgb values are negative
   */
  public Pixel(int pixelRow, int pixelCol, int redColorValue,
               int greenColorValue, int blueColorValue) throws IllegalArgumentException {
    if (pixelRow < 0 || pixelCol < 0 || redColorValue < 0 ||
            greenColorValue < 0 || blueColorValue < 0) {
      throw new IllegalArgumentException("No negative values allowed");
    }
    this.pixelRow = pixelRow;
    this.pixelCol = pixelCol;
    this.redColorValue = redColorValue;
    this.greenColorValue = greenColorValue;
    this.blueColorValue = blueColorValue;
    this.color = new Color(redColorValue, greenColorValue, blueColorValue);
  }

  /**
   * Sets a new color.
   *
   * @param redColorValue   represents the red value of the new color
   * @param greenColorValue represents the green value of the new color
   * @param blueColorValue  represents the blue value of the new color
   */
  public void setColor(int redColorValue, int greenColorValue, int blueColorValue)
          throws IllegalArgumentException {
    if (redColorValue < 0 || greenColorValue < 0 || blueColorValue < 0) {
      throw new IllegalArgumentException("Color values must be positive");
    }
    this.redColorValue = redColorValue;
    this.greenColorValue = greenColorValue;
    this.blueColorValue = blueColorValue;
    this.color = new Color(redColorValue, greenColorValue, blueColorValue);
  }

  /**
   * Gets the red color value of the image.
   *
   * @return the red color value
   */
  public int getRedColorValue() {
    return this.redColorValue;
  }

  /**
   * Gets the green color value of the image.
   *
   * @return the green color value
   */
  public int getGreenColorValue() {
    return this.greenColorValue;
  }

  /**
   * Gets the blue color value of the image.
   *
   * @return the blue color value
   */
  public int getBlueColorValue() {
    return this.blueColorValue;
  }

  /**
   * Gets the row of the pixel.
   *
   * @return the row of the pixel
   */
  public int getPixelRow() {
    return this.pixelRow;
  }

  /**
   * Gets the column of the pixel.
   *
   * @return the column of the pixel
   */
  public int getPixelCol() {
    return this.pixelCol;
  }

  public Color getColor() {
    return this.color;
  }
}
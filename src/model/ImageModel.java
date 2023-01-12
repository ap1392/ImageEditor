package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

/**
 * Represents an image. Here, we deal with how an image can be adjusted. For example,
 * exposure changes, greyscale changes, vertical
 * and horizontal flips can be made.
 */
public interface ImageModel {
  ArrayList<ArrayList<Pixel>> loadImage(Scanner sc) throws IllegalArgumentException;

  /**
   * Changes the exposure of an image (brightens/darkens it).
   *
   * @param enhanceType   determines whether the image is getting brighter or duller
   * @param colorToAdjust specifies which value we are adjusting by
   * @return the updated image
   * @throws IllegalArgumentException if the enhancement type is null or the
   *                                  value to adjust by is less than 0
   */
  ImageModel changeExposure(String enhanceType, int colorToAdjust)
          throws IllegalArgumentException;

  /**
   * Enables users to change the grey scale of our image.
   *
   * @param greyScaleType represents the type of enhancement (luma, red,
   *                      green, blue, value, intensity)
   * @return the new image
   */
  ImageModel greyScale(String greyScaleType);

  /**
   * Applies a filter to an image.
   *
   * @param matrix the filter matrix that the user wants to call
   * @return a new Image that has the filter applied to it
   * @throws IllegalArgumentException if the matrix has an even number of rows and/or columns
   */
  ImageModel filter(double[][] matrix) throws IllegalArgumentException;

  /**
   * Applies a color transformation to an image.
   *
   * @param matrix A 3x3 matrix used to apply a color transformation
   * @return a new Image that has a color transformation applied to it
   * @throws IllegalArgumentException if a matrix that does not have 3x3 dimensions is supplied to
   *                                  the method.
   */
  ImageModel colorTransformation(double[][] matrix) throws IllegalArgumentException;

  /**
   * If the type of image the user wants to save is not a ppm, this method converts the current
   * image into a buffered image so the ImageIO class can export files to a local directory.
   *
   * @return this image as a buffered image
   */
  BufferedImage returnAsBufferedImage();

  /**
   * Flips an image vertically.
   *
   * @return the new image
   */
  ImageModel verticalFlip();

  /**
   * Flips an image horizontally.
   *
   * @return the new image
   */
  ImageModel horizontalFlip();

  /**
   * Converts to a string builder that is used to store the contents of the image and
   * make it easier to export.
   *
   * @return the model as a string builder
   */
  StringBuilder toStringBuilder();

  /**
   * This method creates a Map that represents how often a color value between 0 and 255 appears
   * for a certain image.
   *
   * @param type The type of Map that should be created (either red, green, blue, and intensity).
   * @return a Map with the key representing the possible integer color values (0 - 255) and
   *         the value representing how often that color value appears in an image.
   */
  Map<Integer, Integer> returnColorFrequencies(String type);
}
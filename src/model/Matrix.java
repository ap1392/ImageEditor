package model;

/**
 * This class represents our filter matrices. They allow us to apply filters to images by
 * creating a matrix over the pixels and applying these values to the image's rgb values.
 */
public final class Matrix {


  /**
   * Matrix for gaussian blur filter.
   */
  public static final double[][] GAUSSIAN_BLUR = {{0.0625, 0.125, 0.0625},
      {0.125, 0.25, 0.125}, {0.0625, 0.125, 0.0625}};

  /**
   * Matrix for sharpen filter.
   */
  public static final double[][] SHARPEN = {{-0.125, -0.125, -0.125, -0.125, -0.125},
      {-0.125, 0.25, 0.25, 0.25, -0.125}, {-0.125, 0.25, 1, 0.25, -0.125},
      {-0.125, 0.25, 0.25, 0.25, -0.125}, {-0.125, -0.125, -0.125, -0.125, -0.125}};

  /**
   * Matrix for luma grayscale filter.
   */
  public static final double[][] LUMA = {{0.2126, 0.7152, 0.0722},
      {0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722}};

  /**
   * Matrix for sepia filter.
   */
  public static final double[][] SEPIA = {{0.393, 0.769, 0.189},
      {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}};

}
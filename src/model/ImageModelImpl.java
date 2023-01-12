package model;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Class that initializes an image using a 2d array of pixels.
 */
public class ImageModelImpl implements ImageModel {
  private ArrayList<ArrayList<Pixel>> image = new ArrayList<>();
  private int height = 0;
  private int width = 0;
  private int maxValue = 0;

  /**
   * Constructor that takes in a scanner to initialize the image.
   *
   * @param scanner represents the scanner for the image
   * @throws IllegalArgumentException if the scanner is null
   */
  public ImageModelImpl(Scanner scanner) throws IllegalArgumentException {
    if (scanner == null) {
      throw new IllegalArgumentException("Scanner can't be null");
    }
    this.image = this.loadImage(scanner);
  }

  /**
   * Loads the image so it can be modified.
   *
   * @param sc represents the scanner for the image
   * @return an array list of an array list of pixels
   * @throws IllegalArgumentException if the ppm is invalid
   */
  @Override
  public ArrayList<ArrayList<Pixel>> loadImage(Scanner sc) throws IllegalArgumentException {
    String token;
    token = sc.next();
    if (!token.equals("P3")) {
      throw new IllegalArgumentException("Invalid PPM file: plain RAW file should begin with P3");
    }
    this.width = sc.nextInt();
    this.height = sc.nextInt();
    this.maxValue = sc.nextInt();

    ArrayList<ArrayList<Pixel>> imageList = new ArrayList<>();
    for (int i = 0; i < this.height; i++) {
      ArrayList<Pixel> pixelArrayList = new ArrayList<>();
      for (int j = 0; j < this.width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel pixel = new Pixel(i, j, r, g, b);
        pixel.setColor(r, g, b);
        pixelArrayList.add(pixel);
      }
      imageList.add(pixelArrayList);
    }
    return imageList;
  }

  @Override
  public ImageModel changeExposure(String enhanceType, int colorToAdjust)
          throws IllegalArgumentException {
    if (enhanceType == null) {
      throw new IllegalArgumentException("Please specify if you want to brighten or "
              + "darken the image by entering 'brighten' or 'darken'");
    }
    if (colorToAdjust < 0) {
      throw new IllegalArgumentException("You must input a positive integer");
    }
    String stringToCompare = enhanceType.toLowerCase();
    if (stringToCompare.equals("brighten")) {
      Scanner enhancedImageScanner = new Scanner(this.enhanceImage(colorToAdjust).toString());
      return new ImageModelImpl(enhancedImageScanner);
    }
    if (stringToCompare.equals("darken")) {
      colorToAdjust = colorToAdjust * -1;
      Scanner enhancedImageScanner = new Scanner(this.enhanceImage(colorToAdjust).toString());
      return new ImageModelImpl(enhancedImageScanner);
    } else {
      throw new IllegalArgumentException("Please specify if you want to brighten or "
              + "darken the image by entering 'brighten' or 'darken'");
    }

  }

  /**
   * Allows us to enhance our image by adjusting its exposure to whatever the given adjustment is.
   *
   * @param colorAdjust specifies the value we are adjusting by
   * @return the enhanced image in the form of a string builder
   */
  private StringBuilder enhanceImage(int colorAdjust) {
    StringBuilder enhanceImageStringBuilder = new StringBuilder();
    enhanceImageStringBuilder.append("P3\n");
    enhanceImageStringBuilder.append(this.width).append("\n");
    enhanceImageStringBuilder.append(this.height).append("\n");
    enhanceImageStringBuilder.append(this.maxValue).append("\n");
    for (ArrayList<Pixel> rowList : this.image) {
      for (Pixel pixel : rowList) {
        int redColor = pixel.getRedColorValue();
        int greenColor = pixel.getGreenColorValue();
        int blueColor = pixel.getBlueColorValue();
        int enhancedRed = redColor + colorAdjust;
        int enhancedGreen = greenColor + colorAdjust;
        int enhancedBlue = blueColor + colorAdjust;
        if (enhancedRed > 255) {
          enhancedRed = 255;
        } else if (enhancedRed < 0) {
          enhancedRed = 0;
        }
        if (enhancedGreen > 255) {
          enhancedGreen = 255;
        } else if (enhancedGreen < 0) {
          enhancedGreen = 0;
        }
        if (enhancedBlue > 255) {
          enhancedBlue = 255;
        } else if (enhancedBlue < 0) {
          enhancedBlue = 0;
        }
        enhanceImageStringBuilder.append(enhancedRed).append("\n");
        enhanceImageStringBuilder.append(enhancedGreen).append("\n");
        enhanceImageStringBuilder.append(enhancedBlue).append("\n");
      }
    }
    return enhanceImageStringBuilder;
  }

  @Override
  public ImageModel greyScale(String greyScaleType) {
    StringBuilder greyImageStringBuilder = new StringBuilder();
    greyImageStringBuilder.append("P3").append(System.lineSeparator());
    greyImageStringBuilder.append(this.width).append(" ");
    greyImageStringBuilder.append(this.height).append(System.lineSeparator());
    greyImageStringBuilder.append(this.maxValue).append(System.lineSeparator());
    int redColor = -1;
    int greenColor = -1;
    int blueColor = -1;
    for (ArrayList<Pixel> rowList : this.image) {
      for (Pixel pixel : rowList) {
        int pixelRedColorValue = pixel.getRedColorValue();
        int pixelGreenColorValue = pixel.getGreenColorValue();
        int pixelBlueColorValue = pixel.getBlueColorValue();
        switch (greyScaleType) {
          case "red":
            redColor = pixelRedColorValue;
            greenColor = pixelRedColorValue;
            blueColor = pixelRedColorValue;
            break;
          case "green":
            redColor = pixelGreenColorValue;
            greenColor = pixelGreenColorValue;
            blueColor = pixelGreenColorValue;
            break;
          case "blue":
            redColor = pixelBlueColorValue;
            greenColor = pixelBlueColorValue;
            blueColor = pixelBlueColorValue;
            break;
          case "value":
            int colorToSetValue = Math.max(Math.max(pixelRedColorValue, pixelGreenColorValue),
                    pixelBlueColorValue);
            redColor = colorToSetValue;
            greenColor = colorToSetValue;
            blueColor = colorToSetValue;
            break;
          case "intensity":
            int colorToSetIntensity = (pixelRedColorValue + pixelGreenColorValue +
                    pixelBlueColorValue) / 3;
            redColor = colorToSetIntensity;
            greenColor = colorToSetIntensity;
            blueColor = colorToSetIntensity;
            break;
          case "luma":
            int colorToSetLuma = (int) Math.round(pixelRedColorValue * 0.2126) +
                    (int) (Math.round(0.7152 * pixelGreenColorValue)) +
                    (int) (Math.round(0.0722 * pixelBlueColorValue));
            redColor = colorToSetLuma;
            greenColor = colorToSetLuma;
            blueColor = colorToSetLuma;
            break;
          default:
            // do nothing
        }
        greyImageStringBuilder.append(redColor);
        greyImageStringBuilder.append(System.lineSeparator());
        greyImageStringBuilder.append(greenColor);
        greyImageStringBuilder.append(System.lineSeparator());
        greyImageStringBuilder.append(blueColor);
        greyImageStringBuilder.append(System.lineSeparator());
      }
    }
    Scanner greyImageScanner = new Scanner(greyImageStringBuilder.toString());
    ImageModel imageToReturn = new ImageModelImpl(greyImageScanner);
    return imageToReturn;
  }

  @Override
  public ImageModel filter(double[][] matrix) throws IllegalArgumentException {
    if (matrix.length % 2 == 0 || matrix[1].length % 2 == 0) {
      throw new IllegalArgumentException("Can't have an even amount of rows and/or columns!");
    }
    StringBuilder blurStringBuilder = new StringBuilder();
    blurStringBuilder.append("P3\n");
    blurStringBuilder.append(this.width).append("\n");
    blurStringBuilder.append(this.height).append("\n");
    blurStringBuilder.append(this.maxValue).append("\n");
    for (int i = 0; i < this.height; i++) {
      ArrayList<Pixel> rowList = this.image.get(i);
      for (int j = 0; j < this.width; j++) {
        Pixel pixelToStartAt = rowList.get(j);
        ArrayList<ArrayList<Pixel>> pixelList = this.getPixelList(pixelToStartAt, matrix);
        int[] updatedPixelColors = this.getNewPixelColors(pixelList, matrix);
        int newRedColor = updatedPixelColors[0];
        if (newRedColor > 255) {
          newRedColor = 255;
        }
        if (newRedColor < 0) {
          newRedColor = 0;
        }
        int newGreenColor = updatedPixelColors[1];
        if (newGreenColor > 255) {
          newGreenColor = 255;
        }
        if (newGreenColor < 0) {
          newGreenColor = 0;
        }
        int newBlueColor = updatedPixelColors[2];
        if (newBlueColor > 255) {
          newBlueColor = 255;
        }
        if (newBlueColor < 0) {
          newBlueColor = 0;
        }
        blurStringBuilder.append(newRedColor).append("\n");
        blurStringBuilder.append(newGreenColor).append("\n");
        blurStringBuilder.append(newBlueColor).append("\n");

      }
    }
    Scanner scanner = new Scanner(blurStringBuilder.toString());
    return new ImageModelImpl(scanner);
  }

  /**
   * This method applies a filter to one pixel. It handles all operations and returns an array
   * containing the final red, green, and blue color values for a pixel.
   *
   * @param pixelList a pixel list the same size of the filter to perform operations on
   * @param matrix    a filter that will be used to adjust the color values of the pixel
   * @return an array containing the new red, green, and blue color values for a pixel.
   */
  private int[] getNewPixelColors(ArrayList<ArrayList<Pixel>> pixelList,
                                  double[][] matrix) {
    double redColorToReturn = 0;
    double greenColorToReturn = 0;
    double blueColorToReturn = 0;
    for (int i = 0; i < pixelList.size(); i++) {
      ArrayList<Pixel> rowList = pixelList.get(i);
      for (int j = 0; j < rowList.size(); j++) {
        int redColorToAdjust = 0;
        int greenColorToAdjust = 0;
        int blueColorToAdjust = 0;
        Pixel pixel = null;
        try {
          pixel = rowList.get(j);
          redColorToAdjust = pixel.getRedColorValue();
          greenColorToAdjust = pixel.getGreenColorValue();
          blueColorToAdjust = pixel.getBlueColorValue();
        } catch (NullPointerException e) {
          // Do nothing, null pixels have no colors so their values will be ignored
        }
        double filterColor = matrix[i][j];
        redColorToReturn += filterColor * redColorToAdjust;
        greenColorToReturn += filterColor * greenColorToAdjust;
        blueColorToReturn += filterColor * blueColorToAdjust;
      }

    }
    return new int[]{(int) redColorToReturn, (int) greenColorToReturn, (int) blueColorToReturn};
  }

  /**
   * The way we decided to approach this method is to make a brand-new mini arraylist that has the
   * exact same dimensions as the filter. All the values in the pixel will correspond to the exact
   * same location as the number in the matrix (i.e. the center pixel, or the one taken in as a
   * parameter, corresponds 1/4 in guasian blur, the pixel top left to the inputted one corresponds
   * with 1/16, and so on).
   *
   * @param pixel  the pixel that whose values need to be changed
   * @param matrix the filter that will edit the picture
   * @return a 2d array list the same size as the filter
   */
  private ArrayList<ArrayList<Pixel>> getPixelList(Pixel pixel, double[][] matrix) {
    ArrayList<ArrayList<Pixel>> listToReturn = new ArrayList<>();
    ArrayList<Pixel> listToAdd = new ArrayList<>();
    for (int i = (pixel.getPixelRow() - (matrix.length / 2));
         i < (pixel.getPixelRow() + (matrix.length / 2) + 1); i++) {
      ArrayList<Pixel> rowList;
      try {
        rowList = this.image.get(i);
      } catch (IndexOutOfBoundsException e) {
        rowList = null;
      }
      for (int j = (pixel.getPixelCol() - (matrix[0].length / 2));
           j < (pixel.getPixelCol() + (matrix[0].length / 2) + 1); j++) {
        Pixel pixelToAdd = null;
        try {
          pixelToAdd = rowList.get(j);
        } catch (NullPointerException | IndexOutOfBoundsException e) {
          // do nothing
        }
        listToAdd.add(pixelToAdd);
      }
      listToReturn.add(listToAdd);
      listToAdd = new ArrayList<>();
    }
    return listToReturn;
  }

  @Override
  public ImageModel colorTransformation(double[][] matrix) throws IllegalArgumentException {
    if (matrix.length != 3 && matrix[1].length != 3) {
      throw new IllegalArgumentException("Color transformation matrix must have 3x3 dimensions");
    }
    StringBuilder colorTransFormationBuilder = new StringBuilder();
    colorTransFormationBuilder.append("P3\n");
    colorTransFormationBuilder.append(this.width).append("\n");
    colorTransFormationBuilder.append(this.height).append("\n");
    colorTransFormationBuilder.append(this.maxValue).append("\n");
    for (int i = 0; i < this.height; i++) {
      ArrayList<Pixel> rowList = this.image.get(i);
      for (int j = 0; j < this.width; j++) {
        Pixel pixel = rowList.get(j);
        double[] matrixToMultiplyRedBy = matrix[0];
        double[] matrixToMultiplyGreenBy = matrix[1];
        double[] matrixToMultiplyBlueBy = matrix[2];
        int redColor = pixel.getRedColorValue();
        int greenColor = pixel.getGreenColorValue();
        int blueColor = pixel.getBlueColorValue();
        int finalRedColor = (int) Math.round((redColor * matrixToMultiplyRedBy[0]) +
                (greenColor * matrixToMultiplyRedBy[1]) + (blueColor * matrixToMultiplyRedBy[2]));
        int finalBlueColor = (int) Math.round((redColor * matrixToMultiplyGreenBy[0]) +
                (greenColor * matrixToMultiplyGreenBy[1]) +
                (blueColor * matrixToMultiplyGreenBy[2]));
        int finalGreenColor = (int) Math.round((redColor * matrixToMultiplyBlueBy[0]) +
                (greenColor * matrixToMultiplyBlueBy[1]) + (blueColor * matrixToMultiplyBlueBy[2]));
        if (finalRedColor > 255) {
          finalRedColor = 255;
        }
        if (finalGreenColor > 255) {
          finalGreenColor = 255;
        }
        if (finalBlueColor > 255) {
          finalBlueColor = 255;
        }
        colorTransFormationBuilder.append(finalRedColor).append("\n");
        colorTransFormationBuilder.append(finalBlueColor).append("\n");
        colorTransFormationBuilder.append(finalGreenColor).append("\n");
      }
    }
    Scanner scanner = new Scanner(colorTransFormationBuilder.toString());
    return new ImageModelImpl(scanner);
  }

  @Override
  public BufferedImage returnAsBufferedImage() {
    BufferedImage imageToReturn = new BufferedImage(this.width, this.height,
            BufferedImage.TYPE_INT_RGB);
    for (int i = 0; i < this.height; i++) {
      ArrayList<Pixel> rowList = this.image.get(i);
      for (int j = 0; j < this.width; j++) {
        Pixel pixel = rowList.get(j);
        Color pixelColor = pixel.getColor();
        int rgb = pixelColor.getRGB();
        imageToReturn.setRGB(j, i, rgb);
      }
    }
    return imageToReturn;
  }

  @Override
  public ImageModel verticalFlip() {
    StringBuilder verticalStringBuilder = new StringBuilder();
    verticalStringBuilder.append("P3\n");
    verticalStringBuilder.append(this.width).append("\n");
    verticalStringBuilder.append(this.height).append("\n");
    verticalStringBuilder.append(this.maxValue).append("\n");
    for (int i = this.height - 1; i >= 0; i--) {
      ArrayList<Pixel> rowList = this.image.get(i);
      for (int j = 0; j < this.width; j++) {
        Pixel pixel = rowList.get(j);
        int redPixelColor = pixel.getRedColorValue();
        int greenPixelColor = pixel.getGreenColorValue();
        int bluePixelColor = pixel.getBlueColorValue();
        verticalStringBuilder.append(redPixelColor).append("\n");
        verticalStringBuilder.append(greenPixelColor).append("\n");
        verticalStringBuilder.append(bluePixelColor).append("\n");
      }
    }
    Scanner scanner = new Scanner(verticalStringBuilder.toString());
    return new ImageModelImpl(scanner);
  }

  @Override
  public ImageModel horizontalFlip() {
    StringBuilder horizontalStringBuilder = new StringBuilder();
    horizontalStringBuilder.append("P3\n");
    horizontalStringBuilder.append(this.width).append("\n");
    horizontalStringBuilder.append(this.height).append("\n");
    horizontalStringBuilder.append(this.maxValue).append("\n");
    for (int i = 0; i < this.height; i++) {
      ArrayList<Pixel> rowList = this.image.get(i);
      for (int j = this.width - 1; j >= 0; j--) {
        Pixel pixel = rowList.get(j);
        int redPixelColor = pixel.getRedColorValue();
        int greenPixelColor = pixel.getGreenColorValue();
        int bluePixelColor = pixel.getBlueColorValue();
        horizontalStringBuilder.append(redPixelColor).append("\n");
        horizontalStringBuilder.append(greenPixelColor).append("\n");
        horizontalStringBuilder.append(bluePixelColor).append("\n");
      }
    }
    Scanner scanner = new Scanner(horizontalStringBuilder.toString());
    return new ImageModelImpl(scanner);
  }

  public ArrayList<ArrayList<Pixel>> getImage() {
    return this.image;
  }

  @Override
  public StringBuilder toStringBuilder() {
    StringBuilder stringBufferToReturn = new StringBuilder();
    String stringToReturn = "" + this.image.get(0).size() + " ";
    stringToReturn += this.image.size() + "\n";
    stringToReturn += this.maxValue + "\n";
    stringBufferToReturn.append(stringToReturn);
    stringToReturn = "";
    for (int i = 0; i < this.height; i++) {
      ArrayList<Pixel> rowList = this.image.get(i);
      for (int j = 0; j < this.width; j++) {
        Pixel pixelToAdd = rowList.get(j);
        //        System.out.println("Saving Model.Pixel: " + pixelToAdd.getPixelRow() + " " +
        //                pixelToAdd.getPixelCol());
        int redColor = pixelToAdd.getRedColorValue();
        stringToReturn += redColor + "\n";
        int greenColor = pixelToAdd.getGreenColorValue();
        stringToReturn += greenColor + "\n";
        int blueColor = pixelToAdd.getBlueColorValue();
        stringToReturn += blueColor + "\n";
        stringBufferToReturn.append(stringToReturn);
        stringToReturn = "";
      }
    }
    return stringBufferToReturn;
  }

  @Override
  public Map<Integer, Integer> returnColorFrequencies(String type) {
    Map<Integer, Integer> mapToReturn = this.makeMap();
    for (int i = 0; i < this.height; i++) {
      ArrayList<Pixel> rowList = this.image.get(i);
      for (int j = 0; j < this.width; j++) {
        Pixel pixel = rowList.get(j);
        int keyToGet = -1;
        switch (type) {
          case "red":
            keyToGet = pixel.getRedColorValue();
            break;
          case "green":
            keyToGet = pixel.getGreenColorValue();
            break;
          case "blue":
            keyToGet = pixel.getBlueColorValue();
            break;
          case "intensity":
            keyToGet = (pixel.getRedColorValue() +
                    pixel.getGreenColorValue() + pixel.getBlueColorValue()) / 3;
            break;
          default:
            throw new IllegalArgumentException("We can't support that type of histogram");
        }
        int valueToIncrement = mapToReturn.get(keyToGet);
        valueToIncrement += 1;
        mapToReturn.put(keyToGet, valueToIncrement);
      }
    }

    return mapToReturn;
  }

  // Helper that creates a hashmap with values between 0 and 255.
  private Map<Integer, Integer> makeMap() {
    Map<Integer, Integer> mapToReturn = new HashMap<>();
    for (int i = 0; i < this.maxValue + 1; i++) {
      mapToReturn.put(i, 0);
    }
    return mapToReturn;
  }
}

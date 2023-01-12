package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ImageModel;
import model.ImageModelImpl;
import model.Matrix;
import view.ImageView;
import view.ImageViewImpl;

/**
 * Represents the image controller implementation class. This controller allows users to interact
 * with our code and edit an image.The operations that can be performed include:
 * grey scaling, horizontal flip, vertical flip, changing the exposure,
 * loading and saving the image.
 */
public class ImageControllerImpl implements ImageController {
  private final Map<String, ImageModel> images = new HashMap<>();

  /**
   * Empty constructor, view is initialized to null and is set in a later method.
   */
  public ImageControllerImpl() {
    // Creates a Controller.
  }

  /**
   * Checks to see if an image is not a ppm.
   *
   * @param originalImageName type of the file
   * @return true if an image is not a  ppm
   */
  public static boolean checkIfNotPPM(String originalImageName) {
    boolean booleanToReturn = false;
    for (String s : ImageIO.getWriterFormatNames()) {
      booleanToReturn = booleanToReturn || s.equals(originalImageName);
    }
    return booleanToReturn;
  }

  @Override
  public boolean performOperation(Operation operation) throws IOException {
    String operationName = operation.getOperationType().toLowerCase();
    int enhanceType = operation.getEnhanceType();
    String originalImageName = operation.getOriginalImageName();
    String newImage = operation.getNewImageName();
    StringBuilder stringBuilderToAdd = new StringBuilder();
    switch (operationName) {
      case "load":
        String[] fileTypeOfImage = originalImageName.split("\\.");
        String strToCheck = fileTypeOfImage[fileTypeOfImage.length - 1];
        if (!strToCheck.equals("ppm")) {
          stringBuilderToAdd = this.getImageAsStringBuilder(originalImageName);
          Scanner scToAdd = new Scanner(stringBuilderToAdd.toString());
          ImageModel image = this.loadImage(scToAdd);
          this.images.put(newImage, image);
          break;
        }
        Scanner sc;
        try {
          sc = new Scanner(new FileInputStream(originalImageName));
        } catch (FileNotFoundException e) {
          System.out.println("File " + originalImageName + " not found!");
          break;
        }
        StringBuilder builder = new StringBuilder();
        while (sc.hasNextLine()) {
          String s = sc.nextLine();
          if (s.charAt(0) != '#') {
            builder.append(s + System.lineSeparator());
          }
        }
        Scanner scNew = new Scanner(builder.toString());
        ImageModel image = this.loadImage(scNew);
        this.images.put(newImage, image);
        break;
      case "save":
        ImageModel imageToSave = this.images.get(newImage);
        BufferedWriter writer = new BufferedWriter(new FileWriter(originalImageName));
        String[] fileTypeOfImageSave = originalImageName.split("\\.");
        String strToCheckSave = fileTypeOfImageSave[fileTypeOfImageSave.length - 1];
        if (!this.images.containsKey(newImage)) {
          System.out.println("File you're saving needs to have been previously loaded.");
          break;
        }
        ImageView view = new ImageViewImpl(imageToSave);
        if (checkIfNotPPM(strToCheckSave)) {
          view.saveBufferedImage(strToCheckSave, originalImageName);
          break;
        }
        view.saveFile(writer);
        break;
      case "brighten":
        ImageModel imageToBrighten = this.images.get(originalImageName);
        ImageModel brighterImage = null;
        try {
          brighterImage =
                  imageToBrighten.changeExposure("brighten", enhanceType);
        } catch (IllegalArgumentException | NoSuchElementException e) {
          System.out.println("You must input a positive integer");
          break;
        } catch (NullPointerException e) {
          System.out.println("Please specify by how much you want to brighten or darken an image");
          break;
        }
        this.images.put(newImage, brighterImage);
        break;
      case "darken":
        ImageModel imageToDarken = this.images.get(originalImageName);
        ImageModel darkerImage = null;
        try {
          darkerImage =
                  imageToDarken.changeExposure("darken", enhanceType);
        } catch (IllegalArgumentException e) {
          System.out.println("You must input a positive integer");
          break;
        }
        this.images.put(newImage, darkerImage);
        break;
      case "horizontal-flip":
        ImageModel imageToFlipH = this.images.get(originalImageName);
        ImageModel hFlippedImage = imageToFlipH.horizontalFlip();
        this.images.put(newImage, hFlippedImage);
        break;
      case "vertical-flip":
        ImageModel imageToFlipV = this.images.get(originalImageName);
        ImageModel vFlippedImage = imageToFlipV.verticalFlip();
        this.images.put(newImage, vFlippedImage);
        break;
      case "blur":
        ImageModel imageToBlur = this.images.get(originalImageName);
        ImageModel blurredImage = imageToBlur.filter(Matrix.GAUSSIAN_BLUR);
        this.images.put(newImage, blurredImage);
        break;
      case "sharpen":
        ImageModel imageToSharpen = this.images.get(originalImageName);
        ImageModel sharpenedImage = imageToSharpen.filter(Matrix.SHARPEN);
        this.images.put(newImage, sharpenedImage);
        break;
      case "sepia":
        ImageModel sepiaImage = this.images.get(originalImageName);
        ImageModel sepiaImageToAdd = sepiaImage.colorTransformation(Matrix.SEPIA);
        this.images.put(newImage, sepiaImageToAdd);
        break;
      case "quit":
        return true;
      default:
        performGreyscale(operation);
    }
    return false;
  }

  /**
   * Helper method to perform a GreyScale operation.
   *
   * @param operation operation the user wants to perform
   */
  private void performGreyscale(Operation operation) {
    String operationName = operation.getOperationType().toLowerCase();
    String originalImageName = operation.getOriginalImageName();
    String newImage = operation.getNewImageName();
    switch (operationName) {
      case "greyscale-red":
        ImageModel imageGSR = this.images.get(originalImageName);
        ImageModel greyScaleRedImage = imageGSR.greyScale("red");
        this.images.put(newImage, greyScaleRedImage);
        break;
      case "greyscale-green":
        ImageModel imageGSG = this.images.get(originalImageName);
        ImageModel greyScaleGreenImage = imageGSG.greyScale("green");
        this.images.put(newImage, greyScaleGreenImage);
        break;
      case "greyscale-blue":
        ImageModel imageGSB = this.images.get(originalImageName);
        ImageModel greyScaleBlueImage = imageGSB.greyScale("blue");
        this.images.put(newImage, greyScaleBlueImage);
        break;
      case "greyscale-value":
        ImageModel imageGSV = this.images.get(originalImageName);
        ImageModel greyScaleValueImage = imageGSV.greyScale("value");
        this.images.put(newImage, greyScaleValueImage);
        break;
      case "greyscale-intensity":
        ImageModel imageGSI = this.images.get(originalImageName);
        ImageModel greyScaleIntensityImage = imageGSI.greyScale("intensity");
        this.images.put(newImage, greyScaleIntensityImage);
        break;
      case "greyscale-luma":
        ImageModel imageGSL = this.images.get(originalImageName);
        ImageModel greyScaleLumaImage = imageGSL.greyScale("luma");
        this.images.put(newImage, greyScaleLumaImage);
        break;
      case "greyscale":
        ImageModel imageGSLCT = this.images.get(originalImageName);
        ImageModel imageGSLCTtoReturn = imageGSLCT.colorTransformation(Matrix.LUMA);
        this.images.put(newImage, imageGSLCTtoReturn);
        break;
      default:
        // do nothing
    }
  }

  /**
   * Takes in a user input and supplies our code the type of operation the user wants to do.
   *
   * @param lineToParse the line of instructions the user has entered.
   * @return the operation that the user wants to do.
   */
  @Override
  public Operation parseInputs(String lineToParse) {
    String operationType = "";
    int enhanceType = 0;
    String fileToOperateOn = "";
    String fileName = "";

    Scanner sc = new Scanner(lineToParse);

    while (sc.hasNext()) {
      operationType = sc.next();
      if (operationType.equals("load")) {
        String[] splitString = lineToParse.split(" ");
        fileName = splitString[splitString.length - 1];
        StringBuilder stringToAdd = new StringBuilder();
        for (int i = 1; i < splitString.length - 1; i++) {
          stringToAdd.append(splitString[i]).append(" ");
        }
        fileToOperateOn = stringToAdd.substring(0, stringToAdd.toString().length() - 1);
        return new Operation(operationType, enhanceType, fileToOperateOn, fileName);
      }
      if (operationType.equalsIgnoreCase("quit")) {
        return new Operation(operationType.toLowerCase(), 0, "", "");
      }
      try {
        enhanceType = sc.nextInt();
      } catch (InputMismatchException e) {
        enhanceType = -1;
      } catch (NoSuchElementException e) {
        System.out.println("Please specify what type of file you want to perform the operation " +
                "on. If you're brightening or darkening an image, you need to specify how much" +
                "you want to brighten/darken an image with a positive integer.");
        break;
      }
      try {
        fileToOperateOn = sc.next();
      } catch (NoSuchElementException e) {
        System.out.println("You need to specify what file you want to perform the operation on");
        break;
      }
      try {
        fileName = sc.next();
      } catch (NoSuchElementException e) {
        System.out.println("Please give the file you're performing the operation on a new name." +
                " If you want to override an operation on an image that was previously saved," +
                "use the old name you used to save that image. Otherwise, enter a new name.");
        break;
      }
    }
    return new Operation(operationType, enhanceType, fileToOperateOn, fileName);
  }


  @Override
  public StringBuilder getImageAsStringBuilder(String originalImageName) throws IOException {
    StringBuilder stringBuilderToReturn = new StringBuilder();
    File file = new File(originalImageName);
    BufferedImage img = ImageIO.read(file);
    stringBuilderToReturn.append("P3" + "\n");
    stringBuilderToReturn.append(img.getWidth() + " ");
    stringBuilderToReturn.append(img.getHeight() + "\n");
    stringBuilderToReturn.append(255 + "\n");
    for (int y = 0; y < img.getHeight(); y++) {
      for (int x = 0; x < img.getWidth(); x++) {
        //Retrieving contents of a pixel
        int pixel = img.getRGB(x, y);
        //Creating a Color object from pixel value
        Color color = new Color(pixel, true);
        //Retrieving the R G B values
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        stringBuilderToReturn.append(red + "\n");
        stringBuilderToReturn.append(green + "\n");
        stringBuilderToReturn.append(blue + "\n");
      }
    }
    return stringBuilderToReturn;
  }


  @Override
  public ImageModel loadImage(Scanner sc) {
    return new ImageModelImpl(sc);
  }

}



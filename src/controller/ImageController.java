package controller;

import java.io.IOException;
import java.util.Scanner;

import model.ImageModel;

/**
 * This interface represents the controller used for manipulating and enhancing images.
 * The image can be made black and white, flipped horizontally and vertically, the exposure
 * can be increased or decreased, and the user can save their new image.
 */
public interface ImageController {

  /**
   * This is our "go" method. It takes in an operation from a user and deals with it accordingly.
   *
   * @param operation The operation the user wishes to perform.
   * @throws IOException if an IO error occurs
   */
  boolean performOperation(Operation operation) throws IOException;

  /**
   * Takes in a line of commands the user enters and parses it so our controller can perform
   * the operations the user wants.
   *
   * @param lineToParse the line the user entered
   * @return an operation that we can send to our perform operations method so it can perform
   *         what the user is asking
   */
  Operation parseInputs(String lineToParse);


  /**
   * This method takes in the image and saves it to a string builder. This is so any image,
   * regardless of the file type, can be worked on as a ppm (the image can also be exported as
   * any type the user wants, this method doesn't change that).
   *
   * @param originalImageName file path
   * @return String builder representing the image
   * @throws IOException if an IO error occurs
   */
  StringBuilder getImageAsStringBuilder(String originalImageName) throws IOException;

  /**
   * Loads in an image.
   *
   * @param sc Scanner containing the contents of a ppm image
   * @return an image for us to work on
   */
  ImageModel loadImage(Scanner sc);

}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

import controller.ImageController;
import controller.ImageControllerImpl;
import controller.Operation;
import view.GUIManager;


/**
 * The main method of our program. This class calls the controller to perform the user's inputs.
 */
public class ImageUtil {
  static ImageController controller = new ImageControllerImpl();

  /**
   * Main method used to run our image editor. See README for full details on how to use it.
   *
   * @param args does nothing, all input is through System.in
   * @throws IOException if an input/output error occurs
   */
  public static void main(String[] args) throws IOException {
    ArrayList<String> listOfOperations = new ArrayList<>();
    listOfOperations.add("load");
    listOfOperations.add("greyscale-red");
    listOfOperations.add("greyscale-green");
    listOfOperations.add("greyscale-blue");
    listOfOperations.add("greyscale-value");
    listOfOperations.add("greyscale-intensity");
    listOfOperations.add("greyscale-luma");
    listOfOperations.add("brighten");
    listOfOperations.add("darken");
    listOfOperations.add("horizontal-flip");
    listOfOperations.add("vertical-flip");
    listOfOperations.add("blur");
    listOfOperations.add("sharpen");
    listOfOperations.add("sepia");
    listOfOperations.add("greyscale");
    listOfOperations.add("save");
    listOfOperations.add("quit");
    if (args.length > 2) {
      throw new IllegalArgumentException("Sorry, we can't support more than one script file :(");
    }
    if (args.length == 2) {
      if (!args[0].equals("-file")) {
        System.out.println("You need to have -file as the first command before u run a JAR file");
        return;
      }
      String fileName = args[1];
      File file = new File(fileName);
      Scanner sc;
      try {
        sc = new Scanner(file);
      } catch (FileNotFoundException e) {
        System.out.println("File: " + fileName + " not found!");
        return;
      }
      while (sc.hasNextLine()) {
        String lineToParse = sc.nextLine();
        Operation operation = controller.parseInputs(lineToParse);
        if (listOfOperations.contains(operation.getOperationType().toLowerCase())) {
          controller.performOperation(operation);
        }
      }
      System.out.println("Script file has been executed");
      return;
    }
    if (args.length == 1) {
      if (!args[0].equals("-text")) {
        System.out.println("You need to have -text in order to run our text UI");
        return;
      }
      Readable reader = new InputStreamReader(System.in);
      Scanner lineScanner = new Scanner(reader);
      while (lineScanner.hasNext()) {
        String lineToParse = lineScanner.nextLine();
        Operation operation = controller.parseInputs(lineToParse);
        if (listOfOperations.contains(operation.getOperationType().toLowerCase())) {
          try {
            if (controller.performOperation(operation)) {
              return;
            }
          } catch (IOException e) {
            System.out.println("Couldn't find the file");
          }
        }
        else {
          System.out.println("Please enter a valid command line. You must enter an operation " +
                  "type, the file you're operating on, and the name of the file you're going to " +
                  "save. See the README files for more details");
        }
      }
    } else {
      new GUIManager();
    }
  }

}

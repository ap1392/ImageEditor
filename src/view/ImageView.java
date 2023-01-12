package view;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * This view takes whatever functionality the model has implemented
 * and sends it to an output destination.
 */
public interface ImageView {

  /**
   * Saves a ppm file to the directory this program is operating on in the user's computer.
   *
   * @param writer A buffered writer used to send files to a directory
   * @throws IOException if an IO error occurs
   */
  void saveFile(BufferedWriter writer) throws IOException;

  /**
   * Saves a buffered image to the directory this program is operating on in the user's computer.
   *
   * @param fileType type of file (jpg, png, etc)
   * @param fileName name the user wants to call the file
   * @return true if the code was able to send a file with proper contents to the directory,
   *         false if the code was able to send a file to the directory but no contents were added.
   * @throws IOException if an IO exception occurs
   */
  boolean saveBufferedImage(String fileType, String fileName) throws IOException;
}

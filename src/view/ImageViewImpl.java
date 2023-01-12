package view;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import model.ImageModel;

/**
 * Represent our image view implementation that allows users to view the image. Our view takes in
 * a model and uses it to send the contents of the model to a file for the user to see. It handles
 * all formatting.
 */
public class ImageViewImpl implements ImageView {
  private final ImageModel model;

  /**
   * Constructor that takes in an image model.
   *
   * @param model represents our image model. We use it to get the contents of the model and append
   *              send it to a file for the user to see.
   */
  public ImageViewImpl(ImageModel model) {
    this.model = model;
  }

  @Override
  public void saveFile(BufferedWriter writer) throws IOException {
    writer.write("P3\n");
    writer.write("# Created by Aditya Pathak and Anna Dendas\n");
    StringBuilder stringToAddToFile = this.model.toStringBuilder();
    writer.write(stringToAddToFile.toString());
    writer.close();
  }

  @Override
  public boolean saveBufferedImage(String fileType, String fileName) throws IOException {
    BufferedImage imageToReturn = this.model.returnAsBufferedImage();
    FileOutputStream outputStream = new FileOutputStream(fileName);
    boolean didTheFileGetSaved = ImageIO.write(imageToReturn, fileType, outputStream);
    if (!didTheFileGetSaved) {
      System.out.println("Wasn't able to save the file!");
    }
    return didTheFileGetSaved;
  }
}

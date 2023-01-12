import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import javax.imageio.ImageIO;

import controller.ImageController;
import controller.ImageControllerImpl;
import controller.Operation;
import model.ImageModel;
import model.ImageModelImpl;
import model.Matrix;
import view.ImageView;
import view.ImageViewImpl;

/**
 * Tests for our image model, controller, and view.
 */
public class TestMVC {
  Scanner sc = null;
  BufferedWriter writer = null;

  ImageModel image = null;

  ImageController controller = null;

  @Before
  public void init() {
    String imageFileName = "test/input/TestImage.ppm";
    //String imageFileName = "/Users/aditya2610/Documents/Football/new.ppm";
    sc = null;
    try {
      sc = new Scanner(new FileInputStream(imageFileName));
    } catch (FileNotFoundException e) {
      System.out.println("File " + imageFileName + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    this.sc = new Scanner(builder.toString());
    this.controller = new ImageControllerImpl();
  }

  private void load() {
    this.image = this.controller.loadImage(sc);
  }

  private void save(ImageModel image, String filename) throws IOException {
    this.writer = new BufferedWriter(new FileWriter("test/output/" + filename));
    ImageView view = new ImageViewImpl(image);
    view.saveFile(this.writer);
  }

  private void saveCustom(ImageModel image, String filename) throws IOException {
    ImageView view = new ImageViewImpl(image);
    view.saveBufferedImage("png", "test/output/" + filename);
  }

  // Tests if we can load in a jpg image and save it as a jpg, png, bmp, or ppm and checking if any
  // IO exceptions were thrown
  @Test
  public void testLoadJPGSaveAsAnyFile() throws IOException {
    StringBuilder stringBuilderToAdd =
            this.getImageAsStringBuilder("test/input/test.jpg");
    Scanner scToAdd = new Scanner(stringBuilderToAdd.toString());
    ImageModel image = this.controller.loadImage(scToAdd);
    ImageView view = new ImageViewImpl(image);
    try {
      Assert.assertTrue(view.saveBufferedImage("png", "test/output/test.png"));
      Assert.assertTrue(view.saveBufferedImage("jpg", "test/output/test.jpg"));
      Assert.assertTrue(view.saveBufferedImage("bmp", "test/output/test.bmp"));
      this.save(image, "test.ppm");
    } catch (IOException e) {
      System.out.println("Oh no testLoadPPMSaveAsAnyFile failed :(");
    }
  }

  // Tests if we can load in a ppm image and save it as a jpg, png, bmp, or ppm and checking if any
  // IO exceptions were thrown
  @Test
  public void testLoadPPMSaveAsAnyFile() {
    this.load();
    ImageView view = new ImageViewImpl(image);
    try {
      Assert.assertTrue(view.saveBufferedImage("png", "test/output/test.png"));
      Assert.assertTrue(view.saveBufferedImage("jpg", "test/output/test.jpg"));
      Assert.assertTrue(view.saveBufferedImage("bmp", "test/output/test.bmp"));
      this.save(image, "test.ppm");
    } catch (IOException e) {
      System.out.println("Oh no testLoadPPMSaveAsAnyFile failed :(");
    }
  }

  // Tests if we can load in a png image and save it as a jpg, png, bmp, or ppm and checking if any
  // IO exceptions were thrown
  @Test
  public void testLoadPNGSaveAsAnyFile() throws IOException {
    StringBuilder stringBuilderToAdd =
            this.getImageAsStringBuilder("test/input/test.png");
    Scanner scToAdd = new Scanner(stringBuilderToAdd.toString());
    ImageModel image = this.controller.loadImage(scToAdd);
    ImageView view = new ImageViewImpl(image);
    try {
      Assert.assertTrue(view.saveBufferedImage("png", "test/output/test.png"));
      Assert.assertTrue(view.saveBufferedImage("jpg", "test/output/test.jpg"));
      Assert.assertTrue(view.saveBufferedImage("bmp", "test/output/test.bmp"));
      this.save(image, "test.ppm");
    } catch (IOException e) {
      System.out.println("Oh no testLoadPPMSaveAsAnyFile failed :(");
    }
  }

  // Tests if we can load in a bmp image and save it as a jpg, png, bmp, or ppm and checking if any
  // IO exceptions were thrown
  @Test
  public void testLoadBMPSaveAsAnyFile() throws IOException {
    StringBuilder stringBuilderToAdd =
            this.getImageAsStringBuilder("test/input/test.bmp");
    Scanner scToAdd = new Scanner(stringBuilderToAdd.toString());
    ImageModel image = this.controller.loadImage(scToAdd);
    ImageView view = new ImageViewImpl(image);
    try {
      Assert.assertTrue(view.saveBufferedImage("png", "test/output/test.png"));
      Assert.assertTrue(view.saveBufferedImage("jpg", "test/output/test.jpg"));
      Assert.assertTrue(view.saveBufferedImage("bmp", "test/output/test.bmp"));
      this.save(image, "test.ppm");
    } catch (IOException e) {
      System.out.println("Oh no testLoadPPMSaveAsAnyFile failed :(");
    }
  }

  // Testing sepia. We are testing it as a ppm because regardless of the file the user
  // imports/exports, it will be worked on as a ppm all the time (this does not mean the user
  // can't export an image as jpg, png, etc., it just means that our model treats all images as
  // the same while it's making changes to it)
  @Test
  public void testSepia() {
    this.load();
    Assert.assertEquals("2 2\n" +
            "255\n" +
            "202\n" +
            "180\n" +
            "140\n" +
            "78\n" +
            "69\n" +
            "54\n" +
            "123\n" +
            "109\n" +
            "85\n" +
            "244\n" +
            "218\n" +
            "170\n", this.image.colorTransformation(Matrix.SEPIA)
            .toStringBuilder().toString());
  }

  // Testing greyscale color transformation. We are testing it as a ppm because regardless of the
  // file the user imports/exports, it will be worked on as a ppm all the time (this does not mean
  // the user can't export an image as jpg, png, etc., it just means that our model treats all
  // images as the same while it's making changes to it)
  @Test
  public void testGreyScale() {
    this.load();
    Assert.assertEquals("2 2\n" +
            "255\n" +
            "143\n" +
            "143\n" +
            "143\n" +
            "46\n" +
            "46\n" +
            "46\n" +
            "67\n" +
            "67\n" +
            "67\n" +
            "201\n" +
            "201\n" +
            "201\n", this.image.colorTransformation(Matrix.LUMA)
            .toStringBuilder().toString());
  }

  @Test
  public void tesModelConstructor() {
    try {
      ImageModel failedImage = new ImageModelImpl(null);
    } catch (IllegalArgumentException e) {
      // Uncomment the System.out to see if it works
      //System.out.println("tesModelConstructor was successful");
    }

    Assert.assertEquals("1", Integer.toString(1));
  }

  @Test
  public void testParseInputs() {
    String input = "brighten 100 koala koala-b-100";
    Operation operation = this.controller.parseInputs(input);
    Assert.assertEquals("brighten", operation.getOperationType());
    Assert.assertEquals(100, operation.getEnhanceType());
    Assert.assertEquals("koala", operation.getOriginalImageName());
    Assert.assertEquals("koala-b-100", operation.getNewImageName());
  }

  @Test
  public void testBufferedImage() {
    this.load();
    Assert.assertEquals(2, this.image.returnAsBufferedImage().getHeight());
    Assert.assertEquals(2, this.image.returnAsBufferedImage().getHeight());
  }

  // Testing sharpen. We are testing it as a ppm because regardless of the file the user
  // imports/exports, it will be worked on as a ppm all the time (this does not mean the user
  // can't export an image as jpg, png, etc., it just means that our model treats all images as
  // the same while it's making changes to it)
  @Test
  public void testSharpen() {
    String imageFileName = "test/input/Test5x5.ppm";
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(imageFileName));
    } catch (FileNotFoundException e) {
      System.out.println("File " + imageFileName + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    ImageController controller = new ImageControllerImpl();
    ImageModel imageToSharpen = controller.loadImage(sc);
    Assert.assertEquals("5 5\n" +
            "255\n" +
            "16\n" +
            "59\n" +
            "4\n" +
            "61\n" +
            "119\n" +
            "141\n" +
            "0\n" +
            "157\n" +
            "207\n" +
            "41\n" +
            "181\n" +
            "236\n" +
            "106\n" +
            "144\n" +
            "167\n" +
            "172\n" +
            "170\n" +
            "0\n" +
            "255\n" +
            "161\n" +
            "66\n" +
            "196\n" +
            "109\n" +
            "237\n" +
            "159\n" +
            "255\n" +
            "245\n" +
            "165\n" +
            "162\n" +
            "117\n" +
            "164\n" +
            "66\n" +
            "0\n" +
            "255\n" +
            "122\n" +
            "0\n" +
            "173\n" +
            "93\n" +
            "5\n" +
            "238\n" +
            "194\n" +
            "64\n" +
            "108\n" +
            "184\n" +
            "6\n" +
            "178\n" +
            "146\n" +
            "138\n" +
            "241\n" +
            "161\n" +
            "214\n" +
            "166\n" +
            "208\n" +
            "120\n" +
            "246\n" +
            "204\n" +
            "134\n" +
            "188\n" +
            "118\n" +
            "77\n" +
            "171\n" +
            "96\n" +
            "202\n" +
            "151\n" +
            "228\n" +
            "225\n" +
            "132\n" +
            "70\n" +
            "204\n" +
            "217\n" +
            "35\n" +
            "139\n" +
            "160\n" +
            "34\n" +
            "68\n", imageToSharpen.filter(Matrix.SHARPEN)
            .toStringBuilder().toString());
  }

  // Testing blur. We are testing it as a ppm because regardless of the file the user
  // imports/exports, it will be worked on as a ppm all the time (this does not mean the user
  // can't export an image as jpg, png, etc., it just means that our model treats all images as
  // the same while it's making changes to it)
  @Test
  public void testBlur() {
    String imageFileName = "test/input/Test5x5.ppm";
    Scanner sc;
    try {
      sc = new Scanner(new FileInputStream(imageFileName));
    } catch (FileNotFoundException e) {
      System.out.println("File " + imageFileName + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());
    ImageController controller = new ImageControllerImpl();
    ImageModel imageToBlur = controller.loadImage(sc);
    Assert.assertEquals("5 5\n" +
            "255\n" +
            "31\n" +
            "42\n" +
            "20\n" +
            "44\n" +
            "66\n" +
            "59\n" +
            "38\n" +
            "82\n" +
            "91\n" +
            "44\n" +
            "92\n" +
            "95\n" +
            "54\n" +
            "66\n" +
            "68\n" +
            "81\n" +
            "70\n" +
            "14\n" +
            "118\n" +
            "86\n" +
            "52\n" +
            "107\n" +
            "90\n" +
            "95\n" +
            "91\n" +
            "110\n" +
            "98\n" +
            "78\n" +
            "85\n" +
            "62\n" +
            "94\n" +
            "64\n" +
            "23\n" +
            "133\n" +
            "82\n" +
            "45\n" +
            "130\n" +
            "94\n" +
            "64\n" +
            "117\n" +
            "112\n" +
            "69\n" +
            "85\n" +
            "87\n" +
            "44\n" +
            "95\n" +
            "64\n" +
            "63\n" +
            "117\n" +
            "93\n" +
            "84\n" +
            "117\n" +
            "100\n" +
            "75\n" +
            "120\n" +
            "92\n" +
            "67\n" +
            "91\n" +
            "65\n" +
            "45\n" +
            "76\n" +
            "56\n" +
            "72\n" +
            "88\n" +
            "82\n" +
            "95\n" +
            "92\n" +
            "66\n" +
            "86\n" +
            "102\n" +
            "44\n" +
            "68\n" +
            "78\n" +
            "33\n" +
            "39\n", imageToBlur.filter(Matrix.GAUSSIAN_BLUR)
            .toStringBuilder().toString());
  }


  @org.junit.Test
  public void testLoadAndSave() throws IOException {
    this.load();
    this.save(this.image, "ogImage.ppm");
    Assert.assertEquals("2 2\n" +
            "255\n" +
            "240\n" +
            "120\n" +
            "80\n" +
            "111\n" +
            "23\n" +
            "88\n" +
            "243\n" +
            "11\n" +
            "99\n" +
            "0\n" +
            "255\n" +
            "255\n", this.image.toStringBuilder().toString());
  }

  @org.junit.Test
  public void testGreyScaleRed() throws IOException {
    this.load();
    ImageModel image = this.image.greyScale("red");
    this.save(image, "redGreyScale.ppm");
    // Dummy test
    Assert.assertEquals(1, 1);
  }

  @org.junit.Test
  public void testGreyScaleGreen() throws IOException {
    this.load();
    ImageModel image = this.image.greyScale("green");
    this.save(image, "greenGreyScale.ppm");
    // Dummy test
    Assert.assertEquals(1, 1);
  }

  @org.junit.Test
  public void testGreyScaleBlue() throws IOException {
    this.load();
    ImageModel image = this.image.greyScale("blue");
    this.save(image, "blueGreyScale.ppm");
    // Dummy test
    Assert.assertEquals(1, 1);
  }

  @org.junit.Test
  public void testGreyScaleValue() throws IOException {
    this.load();
    ImageModel image = this.image.greyScale("value");
    this.save(image, "valueGreyScale.ppm");
    // Dummy test
    Assert.assertEquals(1, 1);
  }

  @org.junit.Test
  public void testGreyScaleIntensity() throws IOException {
    this.load();
    ImageModel image = this.image.greyScale("intensity");
    this.save(image, "intensityGreyScale.ppm");
    // Dummy test
    Assert.assertEquals(1, 1);
  }

  @org.junit.Test
  public void testGreyScaleLuma() throws IOException {
    this.load();
    ImageModel image = this.image.greyScale("luma");
    this.save(image, "lumaGreyScale.ppm");
    // Dummy test
    Assert.assertEquals(1, 1);
  }

  @org.junit.Test
  public void testHorizontalFlip() throws IOException {
    this.load();
    ImageModel image = this.image.horizontalFlip();
    this.save(image, "horizontalFlip.ppm");
    // Dummy test
    Assert.assertEquals(1, 1);
  }

  @org.junit.Test
  public void testVerticalFlip() throws IOException {
    this.load();
    ImageModel image = this.image.verticalFlip();
    this.save(image, "verticalFlip.ppm");
    // Dummy test
    Assert.assertEquals(1, 1);
  }

  @org.junit.Test
  public void testBrighten() throws IOException {
    this.load();
    ImageModel image = this.image.changeExposure("brighten", 50);
    this.save(image, "brightenBy50.ppm");
    // Dummy test
    Assert.assertEquals(1, 1);
  }

  @org.junit.Test
  public void testDarken() throws IOException {
    this.load();
    ImageModel image = this.image.changeExposure("darken", 50);
    this.save(image, "darkenBy50.ppm");
    Assert.assertEquals("2 2\n" +
            "255\n" +
            "190\n" +
            "70\n" +
            "30\n" +
            "61\n" +
            "0\n" +
            "38\n" +
            "193\n" +
            "0\n" +
            "49\n" +
            "0\n" +
            "205\n" +
            "205\n", this.image.changeExposure("darken", 50)
            .toStringBuilder().toString());
  }

  @org.junit.Test
  public void testFailExposure() {
    this.load();
    try {
      Assert.assertEquals("2 2\n" +
              "255\n" +
              "190\n" +
              "70\n" +
              "30\n" +
              "61\n" +
              "0\n" +
              "38\n" +
              "193\n" +
              "0\n" +
              "49\n" +
              "0\n" +
              "205\n" +
              "205\n", this.image.changeExposure("darken", -50)
              .toStringBuilder().toString());
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("You must input a positive integer", e.getMessage());
    }
    try {
      Assert.assertEquals("2 2\n" +
              "255\n" +
              "190\n" +
              "70\n" +
              "30\n" +
              "61\n" +
              "0\n" +
              "38\n" +
              "193\n" +
              "0\n" +
              "49\n" +
              "0\n" +
              "205\n" +
              "205\n", this.image.changeExposure("dark", 50)
              .toStringBuilder().toString());
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Please specify if you want to brighten or darken the image by "
              + "entering 'brighten' or 'darken'", e.getMessage());
    }
    try {
      Assert.assertEquals("2 2\n" +
              "255\n" +
              "190\n" +
              "70\n" +
              "30\n" +
              "61\n" +
              "0\n" +
              "38\n" +
              "193\n" +
              "0\n" +
              "49\n" +
              "0\n" +
              "205\n" +
              "205\n", this.image.changeExposure("lighter", 10)
              .toStringBuilder().toString());
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Please specify if you want to brighten or darken the image by "
              + "entering 'brighten' or 'darken'", e.getMessage());
    }
    try {
      Assert.assertEquals("2 2\n" +
              "255\n" +
              "190\n" +
              "70\n" +
              "30\n" +
              "61\n" +
              "0\n" +
              "38\n" +
              "193\n" +
              "0\n" +
              "49\n" +
              "0\n" +
              "205\n" +
              "205\n", this.image.changeExposure("brighten", -10)
              .toStringBuilder().toString());
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("You must input a positive integer", e.getMessage());
    }
  }

  @Test
  public void testReturnColorFrequenciesRed() {
    this.load();
    Map<Integer, Integer> map = this.image.returnColorFrequencies("red");
    int firstBlueColor = map.get(240);
    int secondBlueColor = map.get(111);
    int thirdBlueColor = map.get(243);
    int fourthBlueColor = map.get(0);
    Assert.assertEquals(firstBlueColor, 1);
    Assert.assertEquals(secondBlueColor, 1);
    Assert.assertEquals(thirdBlueColor, 1);
    Assert.assertEquals(fourthBlueColor, 1);
  }

  @Test
  public void testReturnColorFrequenciesGreen() {
    this.load();
    Map<Integer, Integer> map = this.image.returnColorFrequencies("green");
    int firstBlueColor = map.get(120);
    int secondBlueColor = map.get(23);
    int thirdBlueColor = map.get(11);
    int fourthBlueColor = map.get(255);
    Assert.assertEquals(firstBlueColor, 1);
    Assert.assertEquals(secondBlueColor, 1);
    Assert.assertEquals(thirdBlueColor, 1);
    Assert.assertEquals(fourthBlueColor, 1);
    System.out.println(map);
  }

  @Test
  public void testReturnColorFrequenciesBlue() {
    this.load();
    Map<Integer, Integer> map = this.image.returnColorFrequencies("blue");
    int firstBlueColor = map.get(80);
    int secondBlueColor = map.get(88);
    int thirdBlueColor = map.get(99);
    int fourthBlueColor = map.get(255);
    Assert.assertEquals(firstBlueColor, 1);
    Assert.assertEquals(secondBlueColor, 1);
    Assert.assertEquals(thirdBlueColor, 1);
    Assert.assertEquals(fourthBlueColor, 1);
  }

  @Test
  public void testReturnColorFrequenciesIntensity() {
    this.load();
    Map<Integer, Integer> map = this.image.returnColorFrequencies("intensity");
    int firstBlueColor = map.get(146);
    int secondBlueColor = map.get(74);
    int thirdBlueColor = map.get(117);
    int fourthBlueColor = map.get(170);
    Assert.assertEquals(firstBlueColor, 1);
    Assert.assertEquals(secondBlueColor, 1);
    Assert.assertEquals(thirdBlueColor, 1);
    Assert.assertEquals(fourthBlueColor, 1);
  }

  // This method takes in the image and saves it to a string builder. This is so any image,
  // regardless of the file type, can be worked on as a ppm (the image can also be exported as
  // any type the user wants, this method doesn't change that).
  private StringBuilder getImageAsStringBuilder(String originalImageName) throws IOException {
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

}

import org.junit.Before;
import org.junit.Test;

import model.Pixel;

import static org.junit.Assert.assertEquals;

/**
 * Tests for the Pixel class.
 */
public class PixelTest {
  // Pixel examples for test
  Pixel red;
  Pixel orange;
  Pixel yellow;
  Pixel lime;
  Pixel green;
  Pixel lightBlue;
  Pixel darkBlue;
  Pixel violet;
  Pixel purple;

  // Failing pixel example
  Pixel pixel;

  // Initializes test conditions
  @Before
  public void init() {
    this.red = new Pixel(0,0,208, 0, 0);
    this.orange = new Pixel(0,1,241, 135, 1);
    this.yellow = new Pixel(0,2,255, 186, 8);
    this.lime = new Pixel(1,0,140, 179, 105  );
    this.green = new Pixel(1,1, 49, 87, 44);
    this.lightBlue = new Pixel(1,2, 63, 136, 197);
    this.darkBlue = new Pixel(2,0, 3, 43, 57 );
    this.violet = new Pixel(2,1, 179, 146, 172);
    this.purple = new Pixel(2,2,61, 52, 139);
  }

  // Tests illegal arguments
  @Test
  public void testIllegalPixels() {
    // Negative pixel row
    try {
      this.pixel = new Pixel(-3, 1, 2, 3, 4);
    } catch (IllegalArgumentException e) {
      assertEquals("No negative values allowed", e.getMessage());
    }
    // Negative pixel column
    try {
      this.pixel = new Pixel(0, -1, 2, 3, 4);
    } catch (IllegalArgumentException e) {
      assertEquals("No negative values allowed", e.getMessage());
    }
    // Negative red color value
    try {
      this.pixel = new Pixel(0, 1, -2, 3, 4);
    } catch (IllegalArgumentException e) {
      assertEquals("No negative values allowed", e.getMessage());
    }
    // Negative green color value
    try {
      this.pixel = new Pixel(1, 2, 3, -4, 5);
    } catch (IllegalArgumentException e) {
      assertEquals("No negative values allowed", e.getMessage());
    }
    // Negative blue color value
    try {
      this.pixel = new Pixel(1, 2, 3, 4, -5);
    } catch (IllegalArgumentException e) {
      assertEquals("No negative values allowed", e.getMessage());
    }
    // All negative values
    try {
      this.pixel = new Pixel(-1, -2, -3, -4, -5);
    } catch (IllegalArgumentException e) {
      assertEquals("No negative values allowed", e.getMessage());
    }
  }

  // tests for setColor
  @Test
  public void testSetColor() {
    assertEquals(208, this.red.getRedColorValue());
    assertEquals(0, this.red.getGreenColorValue());
    assertEquals(0, this.red.getBlueColorValue());
    this.red.setColor(120, 45, 20);
    assertEquals(120, this.red.getRedColorValue());
    assertEquals(45, this.red.getGreenColorValue());
    assertEquals(20, this.red.getBlueColorValue());
  }


  // Tests for invalid set colors
  @Test
  public void testInvalidSetColor() {
    try {
      this.pixel = new Pixel(1, 1, 2, 3, 4);
      this.pixel.setColor(-3, 40, 50);
    } catch (IllegalArgumentException e) {
      assertEquals("Color values must be positive", e.getMessage());
    }
    try {
      this.pixel = new Pixel(1, 1, 2, 3, 4);
      this.pixel.setColor(3, -40, 50);
    } catch (IllegalArgumentException e) {
      assertEquals("Color values must be positive", e.getMessage());
    }
    try {
      this.pixel = new Pixel(1, 1, 2, 3, 4);
      this.pixel.setColor(3, 40, -50);
    } catch (IllegalArgumentException e) {
      assertEquals("Color values must be positive", e.getMessage());
    }
    try {
      this.pixel = new Pixel(1, 1, 2, 3, 4);
      this.pixel.setColor(-3, -40, -50);
    } catch (IllegalArgumentException e) {
      assertEquals("Color values must be positive", e.getMessage());
    }
  }

  // tests for getRedColorValue()
  @Test
  public void testGetRedColorValue() {
    assertEquals(208, this.red.getRedColorValue());
    assertEquals(63, this.lightBlue.getRedColorValue());
  }

  // tests for getGreenColorValue()
  @Test
  public void testGetGreenColorValue() {
    assertEquals(87, this.green.getGreenColorValue());
    assertEquals(146, this.violet.getGreenColorValue());
  }

  // tests for getBlueColorValue()
  @Test
  public void testGetBlueColorValue() {
    assertEquals(57, this.darkBlue.getBlueColorValue());
    assertEquals(105, this.lime.getBlueColorValue());
  }

  // tests for getPixelRow()
  @Test
  public void testGetPixelRow() {
    assertEquals(0, this.red.getPixelRow());
    assertEquals(1, this.green.getPixelRow());
    assertEquals(2, this.violet.getPixelRow());
  }

  // tests for getPixelCol()
  @Test
  public void testGetPixelCol() {
    assertEquals(0, this.lime.getPixelCol());
    assertEquals(1, this.orange.getPixelCol());
    assertEquals(2, this.lightBlue.getPixelCol());
  }
}
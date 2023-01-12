package controller;

/**
 * Represents the possible Operations a user can input.
 */
public class Operation {
  private final String operationType;
  private final int enhanceType;
  private final String originalImageName;
  private final String newImageName;

  /**
   * Constructor that take's in 4 different types of operations.
   *
   * @param operationType     type of operation (ex: "brighten")
   * @param enhanceType       integer representing how much the user wants to brighten/darken an
   *                          image by. If the operation is not changeExposure(), it sets the
   *                          value to -1 and ignores it.
   * @param originalImageName the file that the user is operating on
   * @param newImageName      the name of the file that the user wants to load/save.
   */
  public Operation(String operationType, int enhanceType, String originalImageName,
                   String newImageName) {
    this.operationType = operationType;
    this.enhanceType = enhanceType;
    this.originalImageName = originalImageName;
    this.newImageName = newImageName;
  }

  /**
   * Returns this operation type.
   *
   * @return this operation type
   */
  public String getOperationType() {
    return this.operationType;
  }

  /**
   * Returns this enhancement type.
   *
   * @return this enhancement typ
   */
  public int getEnhanceType() {
    return this.enhanceType;
  }

  /**
   * Returns this original image name.
   *
   * @return the original image name
   */
  public String getOriginalImageName() {
    return this.originalImageName;
  }

  /**
   * Returns this new image name.
   *
   * @return the new image name
   */
  public String getNewImageName() {
    return this.newImageName;
  }
}

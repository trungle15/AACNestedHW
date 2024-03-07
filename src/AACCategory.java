import structures.AssociativeArray;
import structures.KeyNotFoundException;
import structures.NullKeyException;

/**
 * Represents a category in an Augmentative and Alternative Communication (AAC) system.
 * Each category contains a mapping of image locations to corresponding text.
 */
public class AACCategory {
  
  String category;
  AssociativeArray<String, String> mapping;
  
  /**
   * Constructs a new AACCategory object with the specified name.
   * @param name the name of the category
   */
  public AACCategory (String name) {
    this.category = name;
    this.mapping = new AssociativeArray<>();
  }

  /**
   * Adds an item to the category's mapping.
   * @param imageLoc the location of the image
   * @param text the corresponding text
   */
  public void addItem(String imageLoc, String text) throws NullKeyException {
    this.mapping.set(imageLoc, text);
  }

  /**
   * Returns the name of the category.
   * @return the name of the category
   */
  public String getCategory() {
    return category;
  }

  /**
   * Returns the text corresponding to the specified image location.
   * @param imageLoc the location of the image
   * @return the corresponding text
   */
  public String getText(String imageLoc) throws KeyNotFoundException {
    return mapping.get(imageLoc);
  }

  /**
   * Checks if the category has an image with the specified location.
   * @param imageLoc the location of the image
   * @return true if the category has the image, false otherwise
   */
  public boolean hasImage(String imageLoc) {
    return mapping.hasKey(imageLoc);
  }

  /**
   * Returns an array of all image locations in the category's mapping.
   * @return an array of image locations
   */
  public String[] getImages() {
    return mapping.getKeys();
  }
}
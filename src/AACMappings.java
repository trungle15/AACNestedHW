import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import structures.AssociativeArray;
import structures.KeyNotFoundException;
import structures.NullKeyException;

/**
 * The `AACMappings` class represents a mapping of image locations to corresponding text in an Augmentative and Alternative Communication (AAC) system.
 * It provides methods to read, write, and manipulate the mappings.
 * 
 * @author Trung Le
 */
public class AACMappings {

  private String currentCategory = "";
  AssociativeArray<String, AACCategory> aac = new AssociativeArray<>();


  /**
   * Constructs an instance of AACMappings by reading data from a file.
   * 
   * @param filename the name of the file to read the data from
   */
	public AACMappings(String filename) throws IOException, NullKeyException, KeyNotFoundException {
    BufferedReader reader = new BufferedReader(new FileReader("AACMappings.txt"));
    String line;
    String categoryPath = null;
    String categoryName = null;

    while ((line = reader.readLine()) != null) {
      if (line.charAt(0) != '>') {
        String[] categoryLine = line.split(" ", 2);
        categoryPath = categoryLine[0];
        categoryName = categoryLine[1];
        aac.set(categoryPath, new AACCategory(categoryName));
      } else {
        String[] itemLine = line.substring(1).split(" ", 2);
        aac.get(categoryPath).addItem(itemLine[0], itemLine[1]);
      }
    }
    reader.close();
	}

  /**
   * Retrieves the text associated with the given image location.
   *
   * @param imageLoc the location of the image
   * @return the text associated with the image location
   */
	public String getText(String imageLoc) throws KeyNotFoundException {
    if (isCategory(imageLoc)) {
      currentCategory = imageLoc;
      return aac.get(imageLoc).getCategory();
    } else {
      if (currentCategory != "") {
        return aac.get(currentCategory).getText(imageLoc);
      } else {
        throw new KeyNotFoundException("Image not found");
      }
    }
	}

  /**
   * Returns an array of image locations based on the current category.
   * If the current category is empty, it returns all the keys from the AAC object.
   * Otherwise, it returns the images associated with the current category.
   *
   * @return an array of image locations
   */
	public String[] getImageLocs() throws KeyNotFoundException {
    if (currentCategory.equals("")) {
      return aac.getKeys();
    } else {
      return aac.get(currentCategory).getImages();
    }
  }

  /**
   * Resets the current category.
   */
	public void reset() {
    currentCategory = "";
	}

  /**
   * Returns the current category.
   *
   * @return the current category as a String.
   */
	public String getCurrentCategory() {
		return currentCategory;
	}

  /**
   * Checks if the given image location is a category.
   *
   * @param imageLoc the image location to check
   * @return true if the image location is a category, false otherwise
   */
	public boolean isCategory(String imageLoc) {
		return aac.hasKey(imageLoc);
	}

  /**
   * Writes the AAC mappings to a file.
   *
   * @param filename the name of the file to write the mappings to
   */
	public void writeToFile(String filename) throws IOException, KeyNotFoundException {
    BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
    for (String categoryPath : aac.getKeys()) {
      AACCategory category = aac.get(categoryPath);
      writer.write(categoryPath + " " + category.getCategory());
      writer.newLine();

      for (String imageLoc : category.getImages()) {
        writer.write(">" + imageLoc + " " + category.getText(imageLoc));
        writer.newLine();
      }
    }
    writer.close();
	}

  /**
   * Adds an item to the AAC mappings.
   * If the current category is not set, a new category is created and the item is added to it.
   * If the current category is set, the item is added to the existing category.
   *
   * @param imageLoc the location of the image for the item
   * @param text the text associated with the item
   */
	public void add (String imageLoc, String text) throws NullKeyException, KeyNotFoundException {
    if (currentCategory == "") {
      AACCategory newCategory = new AACCategory(imageLoc);
      newCategory.addItem(imageLoc, text);
    } else {
      aac.get(currentCategory).addItem(imageLoc, text);
    }
	}
}

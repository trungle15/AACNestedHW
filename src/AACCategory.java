import structures.AssociativeArray;
import structures.KeyNotFoundException;
import structures.NullKeyException;

public class AACCategory {
  
  String category;
  AssociativeArray<String, String> mapping;
  
    public AACCategory (String name) {
        // Constructor
    this.category = name;
    }

    public void addItem(String imageLoc, String text) throws NullKeyException {
    this.mapping.set(imageLoc, text);
    }

    public String getCategory() {
        return category;
    }

    public String getText(String imageLoc) throws KeyNotFoundException {
        return mapping.get(imageLoc);
    }

    public boolean hasImage(String imageLoc) {
        return mapping.hasKey(imageLoc);
    }

    public String[] getImages() {
      return mapping.getKeys();
    }
}
package dominio.imageLoader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageLoader {

  private BufferedImage image;

  public ImageLoader(String imagePath) {
    loadImage(imagePath);
  }

  private void loadImage(String filePath) {
    try{
      File file = new File(filePath);
      image = ImageIO.read(file);
      System.out.println("Imagen cargada con éxito");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public BufferedImage getImage() {
    return image;
  }
}

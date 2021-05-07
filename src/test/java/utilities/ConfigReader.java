package utilities;
import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {

    // ConfigReader classını .properties dosyasını okumak için oluşturuyoruz.
    public static Properties properties;
    // static yaptık çünkü nesne oluşturmadan doğrudan erişim sağlamak istedik
    static {

        String path = "C:\\Users\\samet\\IdeaProjects\\jdbclastproject\\src\\test\\java\\resources\\configuration.properties";

        try {
            FileInputStream fileInputStream = new FileInputStream(path);
            properties = new Properties();
            properties.load(fileInputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key){
        return properties.getProperty(key);
        //  properties.getProperty("browser")
    }
}


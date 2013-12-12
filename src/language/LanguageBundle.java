package language;

import java.util.ResourceBundle;

/**
 * 语言本地化
 * @author caoxin
 */
public class LanguageBundle {

    private ResourceBundle resourceBundle;

    public void initMethod() {
        this.resourceBundle = ResourceBundle.getBundle("language/translate");
    }

    public String getString(String key) {
        return this.resourceBundle.getString(key);
    }
}
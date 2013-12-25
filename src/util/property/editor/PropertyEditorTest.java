package util.property.editor;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 *
 * @author caoxin
 */
public class PropertyEditorTest {

    public static void main(String... args) {
        ApplicationContext ac = new FileSystemXmlApplicationContext("./config/property_editor.xml");
        UseItemPair useItemPair = ac.getBean(UseItemPair.class);
        System.out.println(useItemPair);
    }
}
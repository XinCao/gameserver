package gameserver.util.property.editor;

import java.beans.PropertyEditorSupport;

/**
 *
 * @author caoxin
 */
public class ItemPairEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        ItemPair itemPair = (ItemPair) getValue();
        return itemPair.getItemId() + ":" + itemPair.getItemNum();
    }

    @Override
    public void setAsText(String text) {
        String[] itemPairStr = text.split(":");
        ItemPair itemPair = new ItemPair(Integer.parseInt(itemPairStr[0]), Integer.parseInt(itemPairStr[1]));
        setValue(itemPair);
    }
}
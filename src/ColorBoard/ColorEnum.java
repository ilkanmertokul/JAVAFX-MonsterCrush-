package ColorBoard;

import javafx.scene.paint.Color;

/**Enum class for colors,
 * It is widely used in all classes like ColoredNode, CharacterStyles, etc.
 * @author ilkan Mert Okul, 1801042649*/
public enum ColorEnum {

    RED(Color.RED), BLUE(Color.BLUE), GREEN(Color.GREEN);

    /**Color from javafx.scene.paint.Color enum*/
    private Color color;

    private ColorEnum(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }
}

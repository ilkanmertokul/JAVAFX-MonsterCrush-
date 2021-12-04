package ColorBoard;

import javafx.animation.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**This represents the single block of the board.
 * @author  ilkan Mert Okul*/
public class ColoredNode extends Rectangle{

    /**Enum Color of the block, only R , G , B right now but new colors are supported*/
    public ColorEnum blockColor;

    public ColoredNode(ColorEnum blockColor, int xLoc, int yLoc, int width, int height) {
        super(xLoc,yLoc,width,height);
        this.blockColor = blockColor;

        //Colors the block with the given color of the ColorEnum, !see Src/ColorBoard/ColorEnum!
        this.setStroke(Color.BLACK);
        this.setFill(blockColor.getColor());
    }

    public String toString(){
        return "Block " + blockColor.toString() + " " + this.getX() + " " + this.getY() + "\n";
    }

    /** Returns true if given color is equal to this object's blockColor property
     * @param other Other node's color.
     * @return equality of colors.*/
    public boolean isEqualColor(ColoredNode other){
        return blockColor.equals(other.blockColor);
    }

    /**This is only a visual upgrade to make user sure what he/she has chosen.
     * It makes this block's stroke lighter, if it is already chosen, it turns it back off.*/
    public void toggleChosen(){

        if(this.getStroke() == Color.BLACK){
            this.setStroke(Color.YELLOW);
            this.setStrokeWidth(4);
            return;
        }

        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
    }

    /**Checks if other node is neighbor or not.
     * @param other The node to check neighborship.
     * @return true if neighbor, else false.*/
    public boolean isNeighbour(ColoredNode other){

        if(other.getX() == this.getX()){
            if(other.getY() == this.getY() + this.getWidth()) return true;
            if(other.getY() == this.getY() - this.getWidth()) return true;
        }
        else if(other.getY() == this.getY()){
            if(other.getX() == this.getX() + this.getWidth()) return true;
            if(other.getX() == this.getX() - this.getWidth()) return true;
        }

        return false;
    }

    /**Swaps the location with given Node.
     * @param other Node to swap location.*/
    public void swapAttributes(ColoredNode other){

            double x = other.getX() , y = other.getY();

            other.setX(this.getX());
            other.setY(this.getY());

            this.setX(x);
            this.setY(y);

            this.toggleChosen();

    }

    /**Gives the node new color.*/
    public void changeColor(ColorEnum newBlockColor){
        this.blockColor = newBlockColor;
        this.setFill(blockColor.getColor());
    }
}

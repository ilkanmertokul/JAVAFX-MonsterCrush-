package CharacterPack;

import ColorBoard.ColorEnum;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**Abstract Character Class that is the base class for all upcoming characters.
 * It is designed to handle more types of players.
 * @author ilkan Mert OKUL*/
public abstract class AbstractCharacter extends Circle {

        /**Type of the character, Red, Blue, etc*/
        protected CharacterTypes type;

        /**Type of the character, Atlantis, Valhalla etc*/
        protected CharacterStyles style;

        /**Boolean to check if this character is hostile*/
        protected boolean isEnemy;

        /**Health Points to track*/
        protected double hp;

        /**Is alive boolean to check avaiability.*/
        protected boolean isAlive;

        /**Information label presents style and type enums*/
        public Label information = new Label();

        /**Health label to present to user.*/
        public Label health = new Label();

        AbstractCharacter(CharacterTypes type, CharacterStyles style, boolean isEnemy, int xLoc, int yLoc){
            super(xLoc,yLoc,30,type.getColorEnum().getColor());
            this.type = type;
            this.style = style;
            this.isEnemy = isEnemy;

            this.isAlive = true;

            information.setTranslateX(xLoc-47);
            information.setTranslateY(yLoc+30);
            information.setText(this.toString());

            hp = type.getHp() * style.getHpModifier();

            health.setTranslateX(xLoc-47);
            health.setTranslateY(yLoc+50);
            health.setText("HP:" + String.valueOf(hp));
        }

        /**Attacks to another character.
         * @param rule Damaging rule to damage colorwise.
         * @param target To damaqe character.
         * @param attackColor Attack of the color, that comes from user, it decides it.*/
        abstract public void attack(DamageRule rule, AbstractCharacter target, ColorEnum attackColor);

        /**The damage for the character to take. It gets divided by character's agility,
         * which is came from type and style of the character.
         * @param amount amount of damage */
        abstract public void getDamage(double amount);

        /**Kills the character.*/
        abstract public void die();

        /**Refreshes all stats, health.*/
        abstract public void refreshStats();

        public String toString(){
            return type.toString() + " & " + style.toString();
        }

        /**Returns the alive boolean
         * @return isAlive*/
        public boolean getIsAlive(){
            return isAlive;
        }

        /**Returns the type enum.
         * @return type of the character. (Enum)*/
        public CharacterTypes getType(){
            return type;
        }

        /**Disables the character by making it invisible.*/
        public void setDisabled(){
            health.setTextFill(Color.TRANSPARENT);
            information.setTextFill(Color.TRANSPARENT);
        }
}

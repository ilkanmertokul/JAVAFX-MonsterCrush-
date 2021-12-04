package CharacterPack;

import ColorBoard.ColorEnum;
import javafx.scene.paint.Color;

/**A concrete character class.
 * @author ilkan Mert Okul*/
public class Character extends AbstractCharacter{

    public Character(CharacterTypes type, CharacterStyles style, boolean isEnemy, int xLoc, int yLoc) {
        super(type, style, isEnemy, xLoc, yLoc);
    }

    public void attack(DamageRule rule, AbstractCharacter target, ColorEnum attackColor) {
        target.getDamage(rule.getMultiplier(attackColor.getColor(),target.type.getColorEnum().getColor())
                * Math.pow((style.getStrModifier()*type.getStr()),(1.35)));
    }

    public void getDamage(double amount){

        if(isAlive){
            this.hp -= amount/(type.getAgi()*style.getAgiModifier());
            health.setText("HP:" + String.valueOf(hp));

            if(hp <= 0) die();
        }else health.setText("Stop, it's dead :(");
    }

    public void die() {
        this.setFill(Color.TRANSPARENT);
        health.setText("Dead...");
        isAlive = false;
    }

    public void refreshStats() {
        this.hp = type.getHp() * style.getHpModifier();
        health.setText(String.valueOf(hp));
        isAlive = true;
        this.setFill(type.getColorEnum().getColor());
    }

}

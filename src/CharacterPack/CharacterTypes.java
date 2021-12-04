package CharacterPack;

import ColorBoard.ColorEnum;

/**CharacterTypes enum to support characters by some characteristics.
 * @author ilkan Mert Okul*/
public enum CharacterTypes {
    Ice(125,75,100, ColorEnum.BLUE),
    Fire(100,125,75,ColorEnum.RED),
    Nature(75,100,125,ColorEnum.GREEN);

    /**Strength value*/
    private final double str;

    /**Agility value*/
    private final double agi;

    /**Health Points value*/
    private final double hp;

    /**ColorEnum to keep colors, It supports any update too.*/
    private final ColorEnum colorEnum;

    CharacterTypes(double str,double agi,double hp, ColorEnum colorEnum) {
        this.str = str;
        this.agi = agi;
        this.hp = hp;
        this.colorEnum = colorEnum;
    }

    public double getStr() {
        return str;
    }

    public double getAgi() {
        return agi;
    }

    public double getHp() {
        return hp;
    }

    public ColorEnum getColorEnum() {
        return colorEnum;
    }
}

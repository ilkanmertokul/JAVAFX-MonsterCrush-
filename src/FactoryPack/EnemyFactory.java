package FactoryPack;

import CharacterPack.AbstractCharacter;
import CharacterPack.Character;
import CharacterPack.CharacterStyles;
import CharacterPack.CharacterTypes;

import java.util.Random;

/**CharacterFactory to create characters.
 * This class generates characters TRULY RANDOMIZED,
 * because they are ambushing, they should be randomized.
 * sets the created characters as "ENEMY = true"
 * @author ilkan Mert Okul*/
public class EnemyFactory extends CharacterFactory{

    public AbstractCharacter summonRandomCharacter(int xLoc, int yLoc) {

        int types = CharacterTypes.values().length;
        CharacterTypes a = CharacterTypes.values()[new Random().nextInt(types)];

        int styles = CharacterStyles.values().length;
        CharacterStyles b = CharacterStyles.values()[new Random().nextInt(styles)];

        return new Character(a,b,true,xLoc,yLoc);
    }
}

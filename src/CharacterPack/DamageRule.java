package CharacterPack;

import javafx.scene.paint.Color;

import java.util.ArrayList;

/**DamageRule class to apply rules among the colors.
 * Like you can set Color X to deal Y damage to Color Z
 * There is a standard ruleset if you do not want to set up manually.
 * In this project standard ruleset is used.
 * @author ilkan Mert Okul*/
public class DamageRule {

    /**Scripts of rules to keep*/
    ArrayList<DamageScript> scripts = new ArrayList<DamageScript>();

    /**A single script of rules
     * @author İlkan Mert Okul*/
    private class DamageScript{

        /**color of attacker*/
        private final Color attacker;

        /**color of defender*/
        private final Color defender;

        /**multipler that the defender recieves the damage.*/
        private final double multiplier;

        private DamageScript(Color attacker, Color defender, double multiplier) {
            this.attacker = attacker;
            this.defender = defender;
            this.multiplier = multiplier;
        }

        /**This returns the multiplier if the wanted script is this.
         * @param attacker color of attacker
         * @param defender color of defender
         * @return double if the rule exists, else just return -1.*/
        public double getMultiplier(Color attacker, Color defender){
            if((this.attacker == attacker) && (this.defender == defender)){
                return multiplier;
            }
            else return -1;
        }
    }

    public DamageRule(boolean isDefault) {

        //Default attacks to same color.
        scripts.add(new DamageScript(Color.RED,Color.RED,1));
        scripts.add(new DamageScript(Color.BLUE,Color.BLUE,1));
        scripts.add(new DamageScript(Color.GREEN,Color.GREEN,1));

        if(isDefault){
            //Rule of colors.
            scripts.add(new DamageScript(Color.RED,Color.GREEN,2));
            scripts.add(new DamageScript(Color.RED,Color.BLUE,0.5));

            scripts.add(new DamageScript(Color.GREEN,Color.BLUE,2));
            scripts.add(new DamageScript(Color.GREEN,Color.RED,0.5));

            scripts.add(new DamageScript(Color.BLUE,Color.RED,2));
            scripts.add(new DamageScript(Color.BLUE,Color.GREEN,0.5));
        }
    }

    /**This returns the multiplier if wanted script exists.
     * @param attacker color of attacker
     * @param defender color of defender
     * @return double if the rule exists, else just return 1 (default val of multipliers.).*/
    public double getMultiplier(Color attacker, Color defender){

        double val;
        for(DamageScript curr: scripts){
            if( (val = curr.getMultiplier(attacker,defender)) != -1) return val;
        }

        //If it does not exist, return multiplier of 1.
        return 1;
    }
}

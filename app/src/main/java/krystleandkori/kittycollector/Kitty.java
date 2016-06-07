package krystleandkori.kittycollector;

import java.util.Random;

/**
 * Created by inky on 6/5/2016.
 */
public class Kitty {
    String mName;
    CatType mCat;

    public enum CatType{
        KITTEN(.5,10),
        CAT(.2,25),
        BOSSCAT(1,100);

        private double luck;
        private int love;

         CatType(double lu, int lo){
            this.love = lo;
            this.luck = lu;
        }

        public double getLuck() {
            return luck;
        }

        public int getLove() {
            return love;
        }
    }

    public Kitty(CatType cat, String name) {
        this.mCat = cat;
        this.mName = name;
    }
    public String getmName() {
        return mName;
    }
    public void setmName(String mName) {
        this.mName = mName;
    }

    public Kitty generateKitty(){
        //roll a number
        int rolled;
        Random r = new Random(10);
        rolled = r.nextInt();
        //kitten is 10% chance to spawn
        if(rolled == 0){
            Kitty newCat = new Kitty(CatType.KITTEN, "kitten name");
            return newCat;
        }
        //cat is 70% chance to spawn
        if(rolled >0 && rolled <8){
            Kitty newCat = new Kitty(CatType.CAT, "cat name");
            return newCat;
        }
        //bosscat is 20% chance to spawn
        if(rolled >7){
            Kitty newCat = new Kitty(CatType.BOSSCAT, "boss name");
            return newCat;
        }
        return new Kitty(CatType.CAT, "bad roll cat");
    }
}

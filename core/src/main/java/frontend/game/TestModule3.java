package frontend.game;

import com.badlogic.gdx.math.Rectangle;

public class TestModule3 {
    public static void main(String[] args) {

        // === Test Collidable (Question 3-6) ===
        System.out.println("--- Test Collidable ---");

        Player p = new Player("Yuji", 100, 10, 3);
        Rectangle core = p.getCoreHitbox();
        Rectangle graze = p.getGrazeHitbox();

        System.out.println("Core  : x=" + core.x + ", y=" + core.y
            + ", w=" + core.width + ", h=" + core.height);
        System.out.println("Graze : x=" + graze.x + ", y=" + graze.y
            + ", w=" + graze.width + ", h=" + graze.height);

        // === Test ItemType enum (Question 7) ===
        System.out.println();
        System.out.println("--- Test ItemType ---");

        System.out.println("POWER score : " + ItemType.POWER.getScoreValue()
            + " (expected 100)");
        System.out.println("POWER bonus : " + ItemType.POWER.getPowerBonus()
            + " (expected 1)");
        System.out.println("LIFE score  : " + ItemType.LIFE.getScoreValue()
            + " (expected 2000)");

        // === Test new Item constructor (Question 9) ===
        System.out.println();
        System.out.println("--- Test Item with ItemType ---");

        Item powerItem = new Item(300, 300, ItemType.POWER);
        System.out.println("enum    : " + powerItem.getItemTypeEnum()
            + " (expected POWER)");
        System.out.println("string  : " + powerItem.getItemType()
            + " (expected POWER)");
        System.out.println("score   : " + powerItem.getScoreValue()
            + " (expected 100)");

        // === Test BulletType enum (Question 8) ===
        System.out.println();
        System.out.println("--- Test BulletType ---");

        for (BulletType bt : BulletType.values()) {
            System.out.println("Bullet: " + bt);
        }
    }
}

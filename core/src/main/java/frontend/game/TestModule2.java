package frontend.game;

public class TestModule2 {
    public static void main(String[] args) {
        System.out.println("--- Test takeDamage ---");

        Fairy fairy1 = new Fairy("Fairy1", 50);
        boolean die = fairy1.takeDamage(50);
        System.out.println("Test 1 (expected true) : " + die);

        Fairy fairy2 = new Fairy("Fairy2", 50);
        boolean survived = fairy2.takeDamage(25);
        System.out.println("Test 2 (expected false): " + survived);

        boolean dieAgain = fairy1.takeDamage(50);
        System.out.println("Test 3 (expected false): " + dieAgain);

        System.out.println();
        System.out.println("--- Test addScore ---");

        Player p = new Player("Yuji", 100, 10, 3);

        p.addScore(500);
        System.out.println("After +500    : " + p.getScore() + " (expected 500)");

        p.addScore(-100);
        System.out.println("After +(-100) : " + p.getScore() + " (expected still 500)");

        p.addScore(300);
        System.out.println("After +300    : " + p.getScore() + " (expected 800)");
    }
}

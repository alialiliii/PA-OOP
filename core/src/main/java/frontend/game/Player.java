package frontend.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Rectangle;
import systems.BulletManager;
import frontend.game.patterns.ShootingPattern;

public class Player extends GameObject {
    private String name;
    private int hp;
    private int power;
    private int spellCards;
    private long score;
    private int currentDir = 0;


    private ShootingPattern shootingPattern;

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 48, 0, Color.RED);
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 48, 0, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public void addScore(long points) {
        if (points > 0) this.score += points;
    }

    public long getScore() { return score; }

    @Override
    public Rectangle getCoreHitbox() {
        return new Rectangle(x + (width / 2f) - 4f, y + (height / 2f) - 4f, 8f, 8f);
    }


    @Override
    public Rectangle getGrazeHitbox() {
        return new Rectangle(x, y, width, height);
    }


    public void shootBullet(BulletManager bulletManager) {
        if (bulletManager == null) return;

        if (shootingPattern != null) {
            shootingPattern.execute(x + width / 2f - 8f, y + height, bulletManager, true);
        } else {

            bulletManager.spawnPlayerBullet(x + width / 2f - 8f, y + height, 0, 400f, 10 + power);
        }
    }


    public ShootingPattern getShootingPattern() { return shootingPattern; }
    public void setShootingPattern(ShootingPattern shootingPattern) { this.shootingPattern = shootingPattern; }
}

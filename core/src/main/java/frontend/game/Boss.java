package frontend.game;

import com.badlogic.gdx.graphics.Color;
import systems.BulletManager;
import frontend.game.patterns.ShootingPattern;
import frontend.game.patterns.shootingStrategy.SpreadShot;

public class Boss extends Enemy {

    private ShootingPattern shootingPattern = new SpreadShot(200f, 3, 30f);

    public Boss(String name, int hp) {
        super(380, 400, 48, 48, Color.BLUE, name, hp, 5000L);
    }

    public Boss(float x, float y, String name, int hp) {
        super(x, y, 48, 48, Color.BLUE, name, hp, 5000L);
    }

    public void shootBullet(BulletManager bulletManager) {
        if (shootingPattern != null && !isDestroyed()) {
            shootingPattern.execute(x + width / 2 - 8, y, bulletManager, false);
        }
    }

    public ShootingPattern getShootingPattern() { return shootingPattern; }
    public void setShootingPattern(ShootingPattern shootingPattern) { this.shootingPattern = shootingPattern; }
}

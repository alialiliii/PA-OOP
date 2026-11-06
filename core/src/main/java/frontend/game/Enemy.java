package frontend.game;

import com.badlogic.gdx.graphics.Color;
import systems.AudioManager;

public class Enemy extends GameObject {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected long scoreValue;

    public Enemy(String name, int hp) {
        super(200, 380, 24, 24, 0, Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = 100L;
    }

    public Enemy(float x, float y, float width, float height, Color color,
                 String name, int hp, long scoreValue) {
        super(x, y, width, height, 0, color);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = scoreValue;
    }

    public boolean takeDamage(int damage) {
        boolean wasAlive = isAlive();
        this.hp -= damage;
        if (this.hp < 0) {
            this.hp = 0;
        }

        if (wasAlive && this.hp == 0) {
            AudioManager.getInstance().playSFX("sfx_enemy_die.mp3");
            destroy();
            return true;
        }
        return false;
    }

    public boolean isAlive() {
        return hp > 0 && !isDestroyed();
    }

    public long getScoreValue() {
        return scoreValue;
    }
}

package frontend.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet extends GameObject {
    private BulletType bulletType;
    private int damage;


    private float vx, vy;
    private float angle = 90f;
    private boolean isPlayerBullet;
    private boolean grazed = false;


    public Bullet(float x, float y, BulletType bulletType, int damage) {
        super(x, y, 16, 16, 400f, Color.YELLOW);
        this.bulletType = bulletType;
        this.damage = damage;
        this.vx = 0;
        this.vy = 400f;
        this.angle = 90f;
        this.isPlayerBullet = true;
        this.grazed = false;
    }

    public Bullet(float x, float y, float speed, BulletType bulletType, int damage) {
        super(x, y, 16, 16, speed, Color.YELLOW);
        this.bulletType = bulletType;
        this.damage = damage;
        this.vx = 0;
        this.vy = speed;
        this.angle = (speed < 0) ? 270f : 90f;
        this.isPlayerBullet = true;
        this.grazed = false;
    }


    public void init(float x, float y, float vx, float vy, BulletType bulletType, int damage, boolean isPlayerBullet) {
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.angle = (float) Math.toDegrees(Math.atan2(vy, vx));
        this.speed = (float) Math.hypot(vx, vy);
        this.bulletType = bulletType;
        this.damage = damage;
        this.isPlayerBullet = isPlayerBullet;
        this.active = true;
        this.stateTime = 0f;
        this.grazed = false;
    }


    @Override
    public void update(float delta) {
        super.update(delta);
        this.x += vx * delta;
        this.y += vy * delta;
    }


    @Override
    public void render(SpriteBatch batch) {
        if (batch != null && active) {
            if (sprite != null) {
                batch.draw(sprite, x, y, width / 2f, height / 2f, width, height, 1f, 1f, angle - 90f);
            } else {
                super.render(batch);
            }
        }
    }

    public boolean isPlayerBullet() { return isPlayerBullet; }
    public float getVx() { return vx; }
    public float getVy() { return vy; }
    public float getAngle() { return angle; }
    public void setAngle(float angle) { this.angle = angle; }
    public boolean hasBeenGrazed() { return grazed; }
    public void setGrazed(boolean grazed) { this.grazed = grazed; }

    public BulletType getBulletType() { return bulletType; }
    public int getDamage() { return damage; }
}

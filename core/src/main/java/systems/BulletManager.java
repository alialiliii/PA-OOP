package systems;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import frontend.game.Bullet;
import frontend.game.BulletType;
import frontend.game.GameObject;
import frontend.game.Item;
import frontend.game.ItemType;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BulletManager {
    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;
    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;

    public BulletManager() {
        playerBulletPool = new LinkedList<>();
        enemyBulletPool = new LinkedList<>();
        activePlayerBullets = new ArrayList<>();
        activeEnemyBullets = new ArrayList<>();
    }

    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        Bullet b;
        if (!playerBulletPool.isEmpty()) {
            b = playerBulletPool.poll();
            b.init(x, y, vx, vy, BulletType.AMULET, damage, true);
        } else {
            b = new Bullet(x, y, BulletType.AMULET, damage);
            b.init(x, y, vx, vy, BulletType.AMULET, damage, true);
            TextureRegion sprite = AssetManager.getInstance().getTextureRegion("bullet_danmaku");
            if (sprite != null) b.setSprite(sprite);
        }
        activePlayerBullets.add(b);
        return b;
    }

    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        Bullet b;
        if (!enemyBulletPool.isEmpty()) {
            b = enemyBulletPool.poll();
            b.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
        } else {
            b = new Bullet(x, y, BulletType.DANMAKU, damage);
            b.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
            TextureRegion sprite = AssetManager.getInstance().getTextureRegion("bullet_danmaku");
            if (sprite != null) b.setSprite(sprite);
        }
        activeEnemyBullets.add(b);
        return b;
    }

    public void update(float delta) {
        for (int i = activePlayerBullets.size() - 1; i >= 0; i--) {
            Bullet b = activePlayerBullets.get(i);
            b.update(delta);
            if (b.isDestroyed() || b.isOffScreen(800, 600)) {
                b.destroy();
                activePlayerBullets.remove(i);
                playerBulletPool.offer(b);
            }
        }

        for (int i = activeEnemyBullets.size() - 1; i >= 0; i--) {
            Bullet b = activeEnemyBullets.get(i);
            b.update(delta);
            if (b.isDestroyed() || b.isOffScreen(800, 600)) {
                b.destroy();
                activeEnemyBullets.remove(i);
                enemyBulletPool.offer(b);
            }
        }
    }

    public void render(SpriteBatch batch) {
        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) b.render(batch);
        }
    }

    public void clearEnemyBullets(List<GameObject> entities) {
        Iterator<Bullet> iter = activeEnemyBullets.iterator();
        while (iter.hasNext()) {
            Bullet bullet = iter.next();
            if (entities != null) {
                Item pointDrop = EntityFactory.createItem(bullet.getX(), bullet.getY(), ItemType.POINT);
                entities.add(pointDrop);
            }
            bullet.destroy();
            iter.remove();
            enemyBulletPool.offer(bullet);
        }
    }

    public void clearEnemyBullets() {
        clearEnemyBullets(null);
    }

    public void clearPlayerBullets() {
        Iterator<Bullet> pIter = activePlayerBullets.iterator();
        while (pIter.hasNext()) {
            Bullet bullet = pIter.next();
            bullet.destroy();
            pIter.remove();
            playerBulletPool.offer(bullet);
        }
    }

    public List<Bullet> getActivePlayerBullets() { return activePlayerBullets; }
    public List<Bullet> getActiveEnemyBullets() { return activeEnemyBullets; }
    public int getPlayerPoolSize() { return playerBulletPool.size(); }
    public int getEnemyPoolSize() { return enemyBulletPool.size(); }
}

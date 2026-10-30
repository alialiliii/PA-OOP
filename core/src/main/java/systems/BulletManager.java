package systems
import com.badlogic.frontend.objects.BulletType;
import com.badlogic.frontend.objects.bullets.Bullet;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Queue;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class BulletManager {

    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;

    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;

    private Texture playerBulletTexture;
    private Texture enemyBulletTexture;

    private TextureRegion playerBulletSprite;
    private TextureRegion enemyBulletSprite;

    public BulletManager() {

        playerBulletPool = new LinkedList<>();
        enemyBulletPool = new LinkedList<>();

        activePlayerBullets = new ArrayList<>();
        activeEnemyBullets = new ArrayList<>();

        playerBulletTexture = createBulletTexture(Color.YELLOW);
        enemyBulletTexture = createBulletTexture(Color.RED);

        playerBulletSprite =
            new TextureRegion(playerBulletTexture);

        enemyBulletSprite =
            new TextureRegion(enemyBulletTexture);
    }

    private Texture createBulletTexture(Color color) {

        Pixmap pixmap = new Pixmap(
            16, 16, Pixmap.Format.RGBA8888
        );

        pixmap.setColor(Color.CLEAR);
        pixmap.fill();

        pixmap.setColor(color);
        pixmap.fillCircle(8, 8, 5);

        Texture texture = new Texture(pixmap);

        pixmap.dispose();

        return texture;
    }

    public Bullet spawnPlayerBullet(
        float x, float y,
        float vx, float vy,
        int damage) {

        Bullet bullet = playerBulletPool.poll();

        if (bullet == null) {
            bullet = new Bullet(
                x, y, BulletType.AMULET, damage
            );
        }

        bullet.init(
            x, y, vx, vy,
            BulletType.AMULET,
            damage,
            true
        );

        bullet.setSprite(playerBulletSprite);

        activePlayerBullets.add(bullet);

        return bullet;
    }

    public Bullet spawnEnemyBullet(
        float x, float y,
        float vx, float vy,
        int damage) {

        Bullet bullet = enemyBulletPool.poll();

        if (bullet == null) {
            bullet = new Bullet(
                x, y, BulletType.AMULET, damage
            );
        }

        bullet.init(
            x, y, vx, vy,
            BulletType.AMULET,
            damage,
            false
        );

        bullet.setSprite(enemyBulletSprite);

        activeEnemyBullets.add(bullet);

        return bullet;
    }

    public void updateAndRecycle(
        float delta,
        float screenWidth,
        float screenHeight) {

        recycleBullets(
            activePlayerBullets,
            playerBulletPool,
            delta,
            screenWidth,
            screenHeight
        );

        recycleBullets(
            activeEnemyBullets,
            enemyBulletPool,
            delta,
            screenWidth,
            screenHeight
        );
    }

    private void recycleBullets(
        List<Bullet> activeBullets,
        Queue<Bullet> pool,
        float delta,
        float screenWidth,
        float screenHeight) {

        Iterator<Bullet> iterator = activeBullets.iterator();

        while (iterator.hasNext()) {

            Bullet bullet = iterator.next();

            if (!bullet.isDestroyed()) {
                bullet.update(delta);
            }

            if (bullet.isDestroyed()
                || bullet.isOffScreen(
                screenWidth,
                screenHeight
            )) {

                bullet.destroy();

                iterator.remove();

                pool.offer(bullet);
            }
        }
    }

    public void render(SpriteBatch batch) {

        for (Bullet b : activePlayerBullets) {
            if (!b.isDestroyed()) {
                b.render(batch);
            }
        }

        for (Bullet b : activeEnemyBullets) {
            if (!b.isDestroyed()) {
                b.render(batch);
            }
        }
    }

    public List<Bullet> getActivePlayerBullets() {
        return activePlayerBullets;
    }

    public List<Bullet> getActiveEnemyBullets() {
        return activeEnemyBullets;
    }

    public int getPlayerPoolSize() {
        return playerBulletPool.size();
    }

    public int getEnemyPoolSize() {
        return enemyBulletPool.size();
    }

    public void dispose() {

        if (playerBulletTexture != null) {
            playerBulletTexture.dispose();
        }

        if (enemyBulletTexture != null) {
            enemyBulletTexture.dispose();
        }
    }
}

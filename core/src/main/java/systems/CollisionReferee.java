package systems;

import frontend.game.Bullet;
import frontend.game.Enemy;
import frontend.game.Player;
import frontend.game.Item;

import java.util.List;

public class CollisionReferee {

    public void checkPlayerBulletsVsEnemies(List<Bullet> playerBullets, List<? extends Enemy> enemies, Player player) {
        for (Bullet b : playerBullets) {
            if (b.isDestroyed()) continue;

            for (Enemy e : enemies) {
                if (!e.isDestroyed() && b.getCoreHitbox().overlaps(e.getCoreHitbox())) {
                    boolean wasAlive = e.isAlive();
                    e.takeDamage(b.getDamage());
                    b.destroy();

                    if (wasAlive && e.isDestroyed()) {
                        player.addScore(e.getScoreValue());
                    }
                    break;
                }
            }
        }
    }

    public void checkEnemyBulletsVsPlayer(List<Bullet> enemyBullets, Player player) {
        for (Bullet b : enemyBullets) {
            if (b.isDestroyed()) continue;

            if (!b.hasBeenGrazed() && b.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                b.setGrazed(true);
                player.addGraze();
            }

            if (b.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                b.destroy();
                player.takeDamage(b.getDamage());
            }
        }
    }

    public void checkPlayerVsItems(Player player, List<Item> items) {
        for (Item item : items) {
            if (!item.isDestroyed() && player.getCoreHitbox().overlaps(item.getCoreHitbox())) {
                player.collectItem(item);
            }
        }
    }
}

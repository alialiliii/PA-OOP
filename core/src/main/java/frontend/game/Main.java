package frontend.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import frontend.game.ui.GameHUD;
import systems.AssetManager;
import systems.AudioManager;
import systems.BulletManager;
import systems.CollisionReferee;
import systems.EntityFactory;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private List<Fairy> fairies;
    private Boss boss;
    private Item powerItem, pointItem;
    private List<GameObject> entities;

    private BulletManager bulletManager;
    private CollisionReferee referee;
    private GameHUD hud;

    @Override
    public void create() {
        batch = new SpriteBatch();
        AssetManager.getInstance().init();
        AudioManager.getInstance().playBGM("bgm_touhou.mp3");

        entities = new ArrayList<>();
        fairies = new ArrayList<>();

        player = EntityFactory.createPlayer(200, 50, "Reimu Hakurei", 100, 15, 3);
        fairies.add(EntityFactory.createFairy(150, 380, "Red Fairy", 20));
        fairies.add(EntityFactory.createFairy(250, 380, "Blue Fairy", 20));
        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);
        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.add(player);
        entities.addAll(fairies);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);

        bulletManager = new BulletManager();
        referee = new CollisionReferee();
        hud = new GameHUD();
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();


        float moveX = 0, moveY = 0;
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) moveX = -1;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) moveX = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) moveY = 1;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) moveY = -1;

        player.move(moveX * delta, moveY * delta);


        player.setFocused(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT) || Gdx.input.isKeyPressed(Input.Keys.SHIFT_RIGHT));


        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            player.shootBullet(bulletManager);
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.X)) {
            player.useBomb(bulletManager, entities);
        }


        player.update(delta);
        bulletManager.update(delta);
        for (GameObject e : entities) {
            e.update(delta);
        }


        referee.checkPlayerBulletsVsEnemies(bulletManager.getActivePlayerBullets(), fairies, player);

        List<Item> items = new ArrayList<>();
        if (!powerItem.isDestroyed()) items.add(powerItem);
        if (!pointItem.isDestroyed()) items.add(pointItem);
        referee.checkPlayerVsItems(player, items);


        hud.onScoreChanged(player.getScore());
        hud.onLivesChanged(player.getLives());
        hud.onPowerChanged(15);


        Gdx.gl.glClearColor(0.05f, 0.05f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        hud.renderBackground();

        batch.begin();
        for (GameObject e : entities) {
            if (!e.isDestroyed()) {
                e.render(batch);
            }
        }
        bulletManager.render(batch);
        player.renderFocusIndicator(hud.getShapeRenderer());
        hud.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        AssetManager.getInstance().dispose();
        AudioManager.getInstance().dispose();
        hud.dispose();
    }
}

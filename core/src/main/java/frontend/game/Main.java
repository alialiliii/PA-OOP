package frontend.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import systems.AssetManager;
import systems.AudioManager;
import systems.BulletManager;
import systems.EntityFactory;

import java.util.ArrayList;
import java.util.List;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Player player;
    private BulletManager bulletManager;
    private BitmapFont font;

    @Override
    public void create() {
        batch = new SpriteBatch();
        AssetManager.getInstance().init();

        player = EntityFactory.createPlayer(280, 40, "Reimu", 100, 15, 3);
        bulletManager = new BulletManager();
        font = new BitmapFont();
        font.setColor(Color.WHITE);

        AudioManager.getInstance().playBGM("bgm_touhou.mp3");
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();
        float moveSpeed = 250f * delta;

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) player.x -= moveSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) player.x += moveSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) player.y += moveSpeed;
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) player.y -= moveSpeed;

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            player.shootBullet(bulletManager); // Panggil method baru
            AudioManager.getInstance().playSFX("sfx_shoot.mp3");
        }

        player.update(delta);

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        player.render(batch);
        bulletManager.render(batch);
        font.draw(batch, "Score: " + player.getScore(), 10, 470);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        AssetManager.getInstance().dispose();
        AudioManager.getInstance().dispose();


    }
}

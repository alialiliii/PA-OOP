package frontend.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameObject implements Collidable {
    protected float x, y, width, height, speed;
    protected Color color;
    protected boolean active = true;

    protected TextureRegion sprite;
    protected Animation<TextureRegion> animation;
    protected float stateTime = 0f;

    public GameObject(float x, float y, float width, float height, float speed, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
        this.color = color;
    }

    public void update(float delta) {
        stateTime += delta;
    }

    public void render(ShapeRenderer shapeRenderer) {
        if (shapeRenderer != null && color != null && active) {
            shapeRenderer.setColor(color);
            shapeRenderer.rect(x, y, width, height);
        }
    }

    public void render(SpriteBatch batch) {
        if (batch != null && active) {
            if (animation != null) {
                TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);
                batch.draw(currentFrame, x, y, width, height);
            } else if (sprite != null) {
                batch.draw(sprite, x, y, width, height);
            }
        }
    }

    @Override
    public Rectangle getCoreHitbox() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public Rectangle getGrazeHitbox() {
        return new Rectangle(x - 10, y - 10, width + 20, height + 20);
    }

    @Override
    public void onCollision(Collidable other) {
    }

    public boolean isDestroyed() {
        return !active;
    }

    public void destroy() {
        active = false;
    }

    public boolean isOffScreen(float screenWidth, float screenHeight) {
        return x < -50 || x > screenWidth + 50 || y < -50 || y > screenHeight + 50;
    }

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public TextureRegion getSprite() { return sprite; }
    public void setSprite(TextureRegion sprite) { this.sprite = sprite; }
    public Animation<TextureRegion> getAnimation() { return animation; }
    public void setAnimation(Animation<TextureRegion> animation) { this.animation = animation; }
}

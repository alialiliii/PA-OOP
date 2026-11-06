package frontend.game.commands;

import com.badlogic.gdx.Input;
import frontend.game.GameObject;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {
    private int keyShoot = Input.Keys.Z;
    private int keyBomb = Input.Keys.X;
    private int keyFocus = Input.Keys.SHIFT_RIGHT;

    private ShootCommand shootCommand = new ShootCommand();
    private BombCommand bombCommand = new BombCommand();
    private FocusCommand focusOn = new FocusCommand(true);
    private FocusCommand focusOff = new FocusCommand(false);
    private final MoveCommand moveCommand = new MoveCommand(0, 0);

    private final List<Command> activeCommands = new ArrayList<>();

    public InputHandler() {}

    public InputHandler(List<GameObject> entities) {
        this.bombCommand.setEntities(entities);
    }

    public void setEntities(List<GameObject> entities) {
        this.bombCommand.setEntities(entities);
    }

    public int getKeyShoot() { return keyShoot; }
    public void setKeyShoot(int keyShoot) { this.keyShoot = keyShoot; }
    public int getKeyBomb() { return keyBomb; }
    public void setKeyBomb(int keyBomb) { this.keyBomb = keyBomb; }
    public int getKeyFocus() { return keyFocus; }
    public void setKeyFocus(int keyFocus) { this.keyFocus = keyFocus; }

    public List<Command> handleInput(float delta) {
        return activeCommands;
    }
}

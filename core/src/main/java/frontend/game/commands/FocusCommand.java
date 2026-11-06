package frontend.game.commands;

import frontend.game.Player;
import systems.BulletManager;

public class FocusCommand implements Command {
    private boolean focused;

    public FocusCommand(boolean focused) {
        this.focused = focused;
    }

    @Override
    public void execute(Player player, BulletManager bulletManager) {
        player.setFocused(focused);
    }
}

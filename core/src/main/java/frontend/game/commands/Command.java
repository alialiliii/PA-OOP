package frontend.game.commands;

import frontend.game.Player;
import systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}

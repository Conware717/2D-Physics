package Game;

import Entities.EntityManager;

public class Handler {

    private Game game;

    public Handler(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public MouseManager getMouseManager() {
        return game.getMouseManager();
    }

    public KeyManager getKeyManager() {
        return game.getKeyManager();
    }

    public EntityManager getEntityManager() {
        return game.getEntityManager();
    }

}

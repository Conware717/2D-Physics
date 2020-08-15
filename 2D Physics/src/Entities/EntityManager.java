package Entities;

import Entities.Ball;
import Game.Button;
import Game.Handler;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class EntityManager {

    private boolean select = false;
    private Entity selectedBall;
    public ArrayList<Entity> entities;
    private Handler handler;

    public EntityManager(Handler handler) {
        entities = new ArrayList<Entity>();
        this.handler = handler;
    }

    public void tick() {
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            if (e.x > 4000 || e.x < -1000) {
                entities.remove(e);
            }
            if (e.y > 2500 || e.y < -1000) {
                entities.remove(e);
            }
            e.tick();
            if (e.getSelect()) {
                select = true;
                selectedBall = e;
            }
            if (!e.getSelect()) {
                select = false;
                selectedBall = null;
            }
        }
    }

    public void render(Graphics g) {
        for (Entity e : entities) {
            e.render(g);
        }
    }

    public boolean isSelect() {
        return select;
    }

    public Entity getSelectedBall() {
        return selectedBall;
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void clearEntity() {
        entities.clear();
    }
}

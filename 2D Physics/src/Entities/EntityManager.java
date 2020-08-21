package Entities;

import Game.Handler;

import java.awt.*;
import java.util.ArrayList;

public class EntityManager {

    private Entity selectedBall;
    public ArrayList<Entity> entities;
    public boolean[] select;
    boolean active;
    private Handler handler;
    public boolean time = false;

    public EntityManager(Handler handler) {
        entities = new ArrayList<>();
        select = new boolean[100];
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
                select[i] = true;
                selectedBall = e;
            }
            if (!e.getSelect()) {
                select[i] = false;
                selectedBall = null;
            }
            if (time) {
                entities.get(i).setSlowTime();
            }
            if (!time) {
                entities.get(i).resetSlowTime();
            }
        }
    }

    public void render(Graphics g) {
        for (Entity e : entities) {
            e.render(g);
        }
    }

    public boolean[] selectArray() {
        return select;
    }

    public Entity getSelectedBall(int i) {
        return entities.get(i);
    }

    public void addEntity(Entity e) {
        entities.add(e);
    }

    public void clearEntity() {
        entities.clear();
    }

    public boolean isSelect() {
        for (int i = 0; i < select.length; i++) {
            if (select[i]) {
                return active = true;
            }
        }
        return active = false;
    }

    public void resetCount () {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).resetCount();
        }
    }
}

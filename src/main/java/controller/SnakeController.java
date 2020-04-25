package controller;

import model.Arena;
import model.ModifiableArena;
import view.ArenaView;

import javax.swing.*;
import java.awt.*;

public class SnakeController {

    private static final int WIDTH = 550;
    private static final int HEIGHT = 570;
    private static final String TITLE = "Snake";
    private static final int DELAY_BETWEEN_STEPS = 1;

    private static final Dimension FRAME_SIZE = new Dimension(WIDTH, HEIGHT);

    private final JFrame frame = new JFrame(TITLE);
    private final Timer timer = new Timer(DELAY_BETWEEN_STEPS, new TimerAction(this));

    private ArenaView arenaView;

    private final ModifiableArena arena;

    public SnakeController(ModifiableArena arena) {
        this.arena = arena;
        initView(arena);
        frame.setVisible(true);
    }

    public void start() {
        timer.start();
    }

    public void step() {
        arena.move();
        arenaView.repaint();
    }

    public void stop() {
        timer.stop();
        arenaView.repaint();
    }

    private void initView(Arena arena) {
        this.arenaView = new ArenaView(arena);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(arenaView);
        frame.setSize(FRAME_SIZE);
        frame.setLocationRelativeTo(null);
    }

}

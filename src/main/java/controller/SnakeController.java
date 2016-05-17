package controller;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.Timer;

import model.Arena;
import model.ModifiableArena;
import view.ArenaView;

public class SnakeController {
    private static final String TITLE = "Snake";
    private static final int DELAY_BETWEEN_STEPS = 1;
    private static final Dimension FRAME_SIZE = new Dimension(550, 570);

    private JFrame frame = new JFrame(TITLE);
    private ModifiableArena arena;
    private ArenaView arenaView;
    private Timer timer = new Timer(DELAY_BETWEEN_STEPS, new TimerAction(this));

    public SnakeController(ModifiableArena arena) {
        this.arena = arena;
        initView(arena);
        frame.setVisible(true);
    }

    private void initView(Arena arena) {
        this.arenaView = new ArenaView(arena);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(arenaView);
        frame.setSize(FRAME_SIZE);
        frame.setLocationRelativeTo(null);
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
}

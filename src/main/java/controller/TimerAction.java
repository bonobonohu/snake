package controller;

import model.SnakeDeadException;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerAction implements ActionListener {

    private boolean stop = false;

    private final SnakeController snakeController;

    public TimerAction(SnakeController snakeController) {
        this.snakeController = snakeController;
    }

    public void actionPerformed(ActionEvent e) {
        try {
            if (!stop) {
                snakeController.step();
            }
        } catch (SnakeDeadException ex) {
            stop = true;
            snakeController.stop();
        }
    }

}

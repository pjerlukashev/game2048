package com.javarush.task.task35.task3513;

import sun.awt.SunHints;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

public class Controller extends KeyAdapter {

    private Model model;

    private View view;

    private static final int WINNING_TILE = 2048;


    public Controller(Model model) {
        this.model = model;


        this.view = new View(this);
    }

    public View getView() {
        return view;
    }

    public Tile[][] getGameTiles() {


        return model.getGameTiles();
    }

    public void resetGame() {

        model.score = 0;

        view.isGameLost = false;
        view.isGameWon = false;

        model.resetGameTiles();

    }

    public int getScore() {


        return model.score;
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int keyCode = e.getKeyCode();

        if(keyCode == VK_ESCAPE)
            resetGame();

        if (!model.canMove())
            view.isGameLost = true;

        if(!view.isGameLost && !view.isGameWon){

           if(keyCode == VK_LEFT)
               model.left();
            if(keyCode == VK_RIGHT)
                model.right();

            if(keyCode == VK_UP)
                model.up();
            if(keyCode == VK_DOWN)
                model.down();

            if(keyCode == VK_Z)
               model.rollback();

            if(keyCode == VK_R)
              model.randomMove();

            if(keyCode == VK_A)
                model.autoMove();

        }

        if(model.maxTile== 2048)
            view.isGameWon = true;


        view.repaint();



    }

}

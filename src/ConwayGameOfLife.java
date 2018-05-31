/**
 * Simple implementation of Conway's Game Of Life with an interactive menu using JPanel components.
 * User can set initial state by clicking on tiles and start/stop the simulation manually.
 * User can also progress through each generation manually using the "generation" button.
 *
 * Conway's Game Of Life is a zero-player game that follows these rules-
 * In each generation: 1. Any live cell with fewer than two live neighbors dies, as if by under population.
                       2. Any live cell with two or three live neighbors lives on to the next generation.
                       3. Any live cell with more than three live neighbors dies, as if by overpopulation.
                       4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 *                      (https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life)
 *
 * Functionality coming soon: -random generation of cells on the board
 *                            -cells that change colours with each generation
 */


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ConwayGameOfLife implements MouseListener, ActionListener, Runnable {

    private static int HEIGHT = 50;
    private static int WIDTH = 50;
    private int[][] grid = new int[WIDTH][HEIGHT];
    private JFrame frame = new JFrame("Conway's Game of Life");
    private Panel panel = new Panel(grid);
    private JButton start = new JButton("start");
    private JButton stop = new JButton("stop");
    private JButton generation = new JButton("generation");
    private Container buttons;
    private boolean running = false;

    //constructor
    private ConwayGameOfLife() {
        frame.setSize(1000, 1000);
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);

        buttons = new Container();
        buttons.setLayout(new GridLayout(1, 3));
        buttons.add(start);
        buttons.add(stop);
        buttons.add(generation);
        frame.add(buttons, BorderLayout.SOUTH);

        start.addActionListener(this);
        stop.addActionListener(this);
        generation.addActionListener(this);

        panel.addMouseListener(this);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
        new ConwayGameOfLife();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println(e.getX() + "," + e.getY());
        int row = Math.min(e.getY() / (panel.getHeight() / HEIGHT), HEIGHT - 1);
        int col = Math.min(e.getX() / (panel.getWidth() / WIDTH), WIDTH - 1);
        if (grid[col][row] == 0) {
            grid[col][row] = 1;
        } else {
            grid[col][row] = 0;
        }
        frame.repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //source of click is generation button
        if (e.getSource().equals(generation)) {
            if (!running) {
                next();
                frame.repaint();
            }
        }
        //source of click is start button
        if (e.getSource().equals(start)) {
            if (!running) {
                running = true;
                Thread t = new Thread(this);
                t.start(); //calls run, new thread
            }
        }
        //source of click is stop button
        if (e.getSource().equals(stop)) {
            running = false;
        }

    }

    private void next() {
        int[][] tempGrid = new int[grid.length][grid.length];
        for (int i = 0; i < grid.length; i++) { //x
            for (int j = 0; j < grid.length; j++) { //y
                int neighbours = 0;
                //top left
                if (i > 0 && j > 0 && grid[i - 1][j - 1] == 1) {
                    neighbours++;
                }
                //above
                if (i > 0 && grid[i-1][j] == 1) {
                    neighbours++;
                }
                //top right
                if (i > 0 && j < grid.length - 1 && grid[i - 1][j + 1] == 1) {
                    neighbours++;
                }
                //right
                if (i > 0 && j < grid.length - 1 && grid[i][j + 1] == 1) {
                    neighbours++;
                }
                //bottom right
                if (i < grid.length - 1 && j < grid.length - 1 && grid[i + 1][j + 1] == 1) {
                    neighbours++;
                }
                //below
                if (i < grid.length - 1 && grid[i + 1][j] == 1) {
                    neighbours++;
                }
                //bottom left
                if (i < grid.length - 1 && j > 0 && grid[i + 1][j - 1] == 1) {
                    neighbours++;
                }
                //left
                if (j > 0 && grid[i][j-1] == 1) {
                    neighbours++;
                }

                if (grid[i][j] == 1) { //if alive, check neighbours to see if it will be alive
                    if (neighbours == 2 || neighbours == 3) {
                        tempGrid[i][j] = 1;
                    } else {
                        tempGrid[i][j] = 0;
                    }

                } else { // if dead, with 3 neighbours, then back to life, else dead, rules of life
                    if (neighbours == 3) {
                        tempGrid[i][j] = 1;
                    }
                    if (neighbours < 2 || neighbours > 3) {
                        tempGrid[i][j] = 0;
                    }
                }
            }

        }
        //reference swap
        grid = tempGrid;
        panel.setGrid(tempGrid);
        frame.repaint();

    }

    @Override
    public void run() {
        while (running) {
            next();
            frame.repaint();
            try{
                Thread.sleep(400);   //pause between each refresh
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}


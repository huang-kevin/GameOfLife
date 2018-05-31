import javax.swing.*;
import java.awt.*;

public class Panel extends JPanel {

    private int[][] panelCells;

    //constructor
    public Panel(int[][] grid) {
        panelCells = grid;
    }

    public void setGrid(int[][] newGrid) {
        panelCells = newGrid;    //point panelCells to newGrid
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g); //clear screen every time

        g.setColor(Color.BLACK);

        double height = (double) this.getHeight() / panelCells.length;
        double width = (double) this.getWidth() / panelCells.length;
        //Grid
        for (int i = 0; i < panelCells.length; i++) {
            g.drawLine((int) (i * width), 0, (int) (i * width), this.getHeight());
        }
        for (int j = 0; j < panelCells.length; j++) {
            g.drawLine(0, (int) (j * height), this.getWidth(), (int) (j * height));
        }
        //Tiles
        for (int i = 0; i < panelCells.length; i++) {
            for (int j = 0; j < panelCells.length; j++) {
                if (panelCells[i][j] == 1) {
                    g.setColor(Color.black);
                    g.fillRect((int)(i * width), (int) (j * height), (int) width, (int) height);
                }

            }
        }
    }
}

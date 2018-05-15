package com.javarush.task.task35.task3513;

import java.io.FileReader;
import java.util.*;

public class Model {

  public  int score = 0;

 public int maxTile = 2;

    private final static int FIELD_WIDTH = 4;

    public Tile[][] gameTiles;

    Stack<Tile[][]>  previousStates = new Stack<>();
    Stack<Integer>  previousScores = new Stack<>();

    boolean isSaveNeeded = true;


    public Model() {

        resetGameTiles();
    }

    public Tile[][] getGameTiles() {
        return gameTiles;
    }

    public boolean canMove(){

        boolean result  = true;

        boolean canMerge = false;

        for (int i = 0; i < FIELD_WIDTH ; i++) {

            for (int j = 0; j < gameTiles[i].length-1; j++ ) {

                if (gameTiles[i][j].value == gameTiles[i][j+1].value ) {
                   canMerge = true;
                }
            }
        }

        for (int i = 0; i < FIELD_WIDTH ; i++) {

            for (int j = 0; j < gameTiles[i].length-1; j++ ) {

                if (gameTiles[j][i].value == gameTiles[j+1][i].value ) {
                    canMerge = true;
                }
            }
        }

        if(getEmptyTiles().isEmpty() && !canMerge )
            result = false;
        return result;
    }

    private ArrayList<Tile> getEmptyTiles() {

        ArrayList<Tile> result = new ArrayList<>();

        for (int i = 0; i < gameTiles.length; i++) {

            for (int j = 0; j < gameTiles[i].length; j++) {

                if (gameTiles[i][j].isEmpty()) {

                    result.add(gameTiles[i][j]);
                }

            }

        }

        return result;
    }

    private void addTile() {

        ArrayList<Tile> emptyTiles = getEmptyTiles();
        if (!emptyTiles.isEmpty()) {
            int randomTileIndex = (int) (Math.random() * emptyTiles.size());
            emptyTiles.get(randomTileIndex).value = (Math.random() < 0.9) ? 2 : 4;
        }

    }

    public void resetGameTiles() {

        gameTiles = new Tile[4][4];

        for (int i = 0; i < FIELD_WIDTH; i++) {

            for (int j = 0; j < FIELD_WIDTH; j++) {

                gameTiles[i][j] = new Tile();

            }
        }

        addTile();
        addTile();

    }

    private boolean compressTiles(Tile[] tiles) {

        boolean changes = false;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].value == 0 && i < tiles.length - 1 && tiles[i + 1].value != 0) {
                Tile temp = tiles[i];
                tiles[i] = tiles[i + 1];
                tiles[i + 1] = temp;
                i = -1;
                changes = true;
            }
        }
        return changes;


    }

    private boolean mergeTiles(Tile[] tiles) {

        boolean hasChanged = false;

      //  compressTiles(tiles);

        for (int i = 0; i < tiles.length - 1; i++ ) {

            if (tiles[i].value == tiles[i + 1].value && !tiles[i].isEmpty() ) {
                   hasChanged = true;

                tiles[i].value *= 2;
                tiles[i + 1] = new Tile();
                maxTile = maxTile > tiles[i].value ? maxTile : tiles[i].value;
                score += tiles[i].value;
                compressTiles(tiles);
            }
        }

        return  hasChanged;

    }

   void left(){

        if(isSaveNeeded) {
            saveState(gameTiles);
        }

       boolean isChanged = false;
       for (int i = 0; i < FIELD_WIDTH; i++) {
           if (compressTiles(gameTiles[i]) | mergeTiles(gameTiles[i])) {
               isChanged = true;
           }
       }
       if (isChanged) addTile();

       isSaveNeeded = true;
   }

    void up(){

            saveState(gameTiles);


        Tile [][] temp = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH ; i++) {

            for (int j = FIELD_WIDTH-1; j >= 0 ; j--) {

                temp[FIELD_WIDTH-1 -j][i] = gameTiles[i][j];

            }
        }

        gameTiles = temp;

        left();


        Tile [][] temp1 = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = FIELD_WIDTH-1; i >= 0 ; i--) {

            for (int j = 0; j < FIELD_WIDTH ; j++) {

                temp1[j][FIELD_WIDTH - 1 -i] = gameTiles[i][j];

            }
        }

        gameTiles = temp1;

    }

    void down(){


            saveState(gameTiles);


        Tile [][] temp = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = FIELD_WIDTH-1; i >= 0 ; i--) {

            for (int j = 0; j < FIELD_WIDTH ; j++) {

                temp[j][FIELD_WIDTH - 1 -i] = gameTiles[i][j];

            }
        }

        gameTiles = temp;

        left();


        Tile [][] temp1 = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH ; i++) {

            for (int j = FIELD_WIDTH-1; j >= 0 ; j--) {

                temp1[FIELD_WIDTH-1 -j][i] = gameTiles[i][j];

            }
        }

        gameTiles = temp1;


    }

    void right(){


            saveState(gameTiles);


        Tile [][] temp = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH ; i++) {

            for (int j = FIELD_WIDTH-1; j >= 0 ; j--) {

                temp[FIELD_WIDTH-1 -j][i] = gameTiles[i][j];

            }
        }

        gameTiles = temp;

        temp = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH ; i++) {

            for (int j = FIELD_WIDTH-1; j >= 0 ; j--) {

                temp[FIELD_WIDTH-1 -j][i] = gameTiles[i][j];

            }
        }

        gameTiles = temp;

        left();


        Tile [][] temp1 = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = FIELD_WIDTH-1; i >= 0 ; i--) {

            for (int j = 0; j < FIELD_WIDTH ; j++) {

                temp1[j][FIELD_WIDTH - 1 -i] = gameTiles[i][j];

            }
        }

        gameTiles = temp1;

        temp1 = new Tile[FIELD_WIDTH][FIELD_WIDTH];


        for (int i = FIELD_WIDTH-1; i >= 0 ; i--) {

            for (int j = 0; j < FIELD_WIDTH ; j++) {

                temp1[j][FIELD_WIDTH - 1 -i] = gameTiles[i][j];

            }
        }

        gameTiles = temp1;
    }

    private void saveState(Tile[][] tiles){

        Tile[][] toSave = new Tile[FIELD_WIDTH][FIELD_WIDTH];

        for (int i = 0; i < FIELD_WIDTH; i++) {

            for (int j = 0; j <FIELD_WIDTH ; j++) {

                toSave[i][j] = new Tile(tiles[i][j].value);

            }

        }

        previousStates.push(toSave);

        previousScores.push(score);

    isSaveNeeded = false;


    }

    public void rollback(){

        if(!previousScores.empty() && !previousStates.empty()) {

            gameTiles = previousStates.pop();

            score = previousScores.pop();

        }
    }

    public void randomMove(){

       int n =  ((int) (Math.random() * 100)) % 4;

       if(n == 0)
           left();
        if(n == 1)
            right();
        if(n == 2)
            up();
        if(n == 3)
            down();
    }

    public boolean hasBoardChanged(){

        int currentWeight = 0;
        int savedWeight = 0;

        for (int i = 0; i < FIELD_WIDTH ; i++) {

            for (int j = 0; j < FIELD_WIDTH ; j++) {

                currentWeight += gameTiles[i][j].value;

            }
        }

        for (int i = 0; i < FIELD_WIDTH ; i++) {

            for (int j = 0; j < FIELD_WIDTH ; j++) {

                savedWeight +=  previousStates.peek()[i][j].value;

            }
        }

        if (savedWeight != currentWeight) return  true;

        return false;

    }

   public MoveEfficiency getMoveEfficiency(Move move){

       move.move();

       int efficiencyScore = hasBoardChanged() ? score : 0;

       int efficiencyEmptyTilesCounter = hasBoardChanged() ? getEmptyTiles().size() : -1;

         MoveEfficiency moveEfficiency = new MoveEfficiency(efficiencyEmptyTilesCounter, efficiencyScore , move);

    rollback();

return  moveEfficiency;

   }

   public void autoMove(){

       Comparator<MoveEfficiency> cmp = Collections.reverseOrder();

       PriorityQueue<MoveEfficiency> moves  = new PriorityQueue<MoveEfficiency>(4, cmp );

       MoveEfficiency  leftEfficiency  = getMoveEfficiency(new Move() {
           @Override
           public void move() {
               left();
           }
       });

       moves.add(leftEfficiency);

       MoveEfficiency  rightEfficiency  = getMoveEfficiency(new Move() {
           @Override
           public void move() {
               right();
           }
       });

       moves.add(rightEfficiency);

       MoveEfficiency  upEfficiency  = getMoveEfficiency(new Move() {
           @Override
           public void move() {
               up();
           }
       });

       moves.add(upEfficiency);

       MoveEfficiency downEfficiency  = getMoveEfficiency(new Move() {
           @Override
           public void move() {
               down();
           }
       });

       moves.add(downEfficiency);

       moves.poll().getMove().move();

   }

}

package com.javarush.task.task35.task3513;

public class MoveEfficiency implements Comparable<MoveEfficiency> {

 private  int  numberOfEmptyTiles;

   private int score;

   private Move move;

    public MoveEfficiency(int numberOfEmptyTiles, int score, Move move) {
        this.numberOfEmptyTiles = numberOfEmptyTiles;
        this.score = score;
        this.move = move;
    }

    public Move getMove() {
        return move;
    }

    public int compareTo(MoveEfficiency moveEfficiency){

       if( this.numberOfEmptyTiles == moveEfficiency.numberOfEmptyTiles){
           if(this.score == moveEfficiency.score)
               return 0;
           else return this.score > moveEfficiency.score ? 1 : -1;


       }else{

           return this.numberOfEmptyTiles > moveEfficiency.numberOfEmptyTiles ? 1 : -1;

       }

    }
}

package com.example.puzzlecito;

import android.content.Context;
import androidx.appcompat.widget.AppCompatImageView;

//en vez de android.support.v7.widget.AppCompatImageView
//se usa esto
public class PuzzlePiece extends androidx.appcompat.widget.AppCompatImageView {

    public int xCoord;
    public int yCoord;
    public int pieceWidth;
    public int pieceHeight;
    public boolean canMove = true;

    public PuzzlePiece(Context context) {
        super(context);
    }
}

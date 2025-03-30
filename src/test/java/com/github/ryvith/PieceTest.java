package com.github.ryvith;

import com.github.ryvith.model.type.Piece;
import junit.framework.TestCase;


/**
 * Unit test for piece.
 */
public class PieceTest extends TestCase
{
   public void testSymbolValues(){
       assertEquals('○', Piece.WHITE.getSymbol());
       assertEquals('●', Piece.BLACK.getSymbol());
       assertEquals('·', Piece.EMPTY.getSymbol());
       assertEquals('+', Piece.VALID.getSymbol());
   }

   public void testEnumOrder(){
       assertEquals(0, Piece.WHITE.ordinal());
       assertEquals(1, Piece.BLACK.ordinal());
   }


}

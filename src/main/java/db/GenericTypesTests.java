/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db;

import basics.Artist;
import basics.Release;
import java.util.ArrayList;

/**
 *
 * @author spoting
 */
public class GenericTypesTests {
   // generic method printArray
   public static < E > void printArray( ArrayList<E> inputArray ) {
      // Display array elements
      for(E element : inputArray) {
        System.out.println (element.getClass().toString());
        System.out.println (element.toString());
      }
      System.out.println();
   }

   public static void main(String args[]) {
       
//      ArrayList<Artist> arrArt = new ArrayList();
//      ArrayList<Release> arrRel = new ArrayList();
//      
//      Artist art = new Artist();
//      Release rel = new Release();
//      
//      art.setName("SKATAartist");
//      rel.setTitle("SKATArelease");
//      
//      arrArt.add(art);
//      arrRel.add(rel);
//      
//      System.out.println("Array Artists contains:");
//      printArray(arrArt); 
//
//      System.out.println("\nArray Releases contains:");
//      printArray(arrRel); 
//      
   }
}


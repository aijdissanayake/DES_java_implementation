/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DES_logic;

import java.awt.List;
import java.util.ArrayList;

/**
 *
 * @author Achala PC
 */
public class Converter {
    
    private static ArrayList getParts(String string, int partitionSize) {
        ArrayList parts = new ArrayList<String>();
        int len = string.length();
        for (int i=0; i<len; i+=partitionSize)
        {
            parts.add(string.substring(i, Math.min(len, i + partitionSize)));
        }
        return parts;
    }
    
    public String stringToBinary(String txt){
        
        byte[] bytes = txt.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
           int val = b;
           for (int i = 0; i < 8; i++)
           {
              binary.append((val & 128) == 0 ? 0 : 1);
              val <<= 1;
           }
        }
        return binary.toString();
    }
    public String binaryToChar(String binary){
        
        ArrayList<String> byts = getParts(binary, 8);
//        int n = byts.length;
        StringBuilder result = new StringBuilder();
        
        for(String byt: byts) {
            byte dat = 0;
            for (int j = 0; j < byt.length(); j++){
                char byij = byt.charAt(j);
                dat <<= 0x01;
                if(byij != '0') {
                    dat |= 0x01;
                }
            }
            result.append((char) dat);
        }        
        return result.toString();
    }
}

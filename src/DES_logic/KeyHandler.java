/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DES_logic;


/**
 *
 * @author Achala PC
 */
//to create subkeys by giving one key, required for DES
public class KeyHandler {
    
    private final Converter converter = new Converter();
    
    //folloing varaiables hold the values in different stages of sub key generating process
    //initial key
    private String key;
    //converted key to binary
    private String BinaryCovertedKey;
    //16 keys required for the process
    private final String [] subkeys = new String [16];
    // Holds the key after the first permutation.
    private String permutedKey;
    // to hold the left and right half of the initial key
    private String leftHalf;
    private String rightHalf;
    // These two arrays keep the left shifted keys.
    private final String [] leftHalves = new String [16];
    private final String [] RightHalves = new String [16];
     // This array holds the shifted keys.
    private final String [] shiftedKeys = new String [16];
    
    //following variables holds the permutaion and shifting orders
    // This array keeps the initial permutation positions for the original key.
    private final int [] firstPerm = 
    {57,49,41,33,25,17,9,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,63,55,47,39,31,23,15,
     7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4};
    // This array keeps the values of left shifts to perform on each key pairs in 16 iterations.
    private final int [] leftShiftOrder = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
    // Shifted keys will be permutate finally.
    private final int [] finalPerm = 
    {14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,
    41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};

   //validate the length and the hexadecimal format,set the key and convert to binary
    private boolean validateAndSetKey(String key) {
        if (key.length() == 8) {
            this.key = key;
            //get the binary equivalent in textual representatiion
            String convertedKey = converter.stringToBinary(key);
            BinaryCovertedKey = convertedKey;
            return true;
        }
        else {
            return false;
        }
    }

    // Permutates the key
    private String permutateKey(String key, int[] permutationArray) {
        StringBuilder permutated = new StringBuilder();
        for(int position : permutationArray) {
            permutated.append(key.charAt(position - 1));
        }
        return permutated.toString();
    }

    // Separate into two halves.
    private void separateHalves(String key) {
        leftHalf = key.substring(0,28);
        rightHalf = key.substring(28);
    }

    // Left shift the bit string by one bit.
    private String shiftBits(String bits) {
        StringBuilder shiftedBits = new StringBuilder();
        shiftedBits.append(bits.substring(1));
        shiftedBits.append(bits.charAt(0));
        return shiftedBits.toString();
    }

    // Left shifts the permuted key halves.
    private void createShiftedKeys() {
        String shiftedLeftHalf = leftHalf;
        String shiftedRightHalf = rightHalf;

        for(int count1 = 0; count1 <= 15; ++count1) {
            int shiftCount = leftShiftOrder[count1];
            for(int count2 = 1; count2 <= shiftCount; ++count2) {
                shiftedLeftHalf = shiftBits(shiftedLeftHalf);
                shiftedRightHalf = shiftBits(shiftedRightHalf);
            }
            leftHalves[count1] = shiftedLeftHalf;
            RightHalves[count1] = shiftedRightHalf;
        }
    }

    // Concatenates pairs of left shifted keys.
    private void getFullSubKeys() {
        for(int count = 0; count <= 15; ++count) {
            StringBuilder shiftedKey = new StringBuilder();
            shiftedKey.append(leftHalves[count]);
            shiftedKey.append(RightHalves[count]);
            shiftedKeys[count] = shiftedKey.toString();
        }
    }

    public String [] createKeyPool(String inputKey) {
        if (validateAndSetKey(inputKey)) {
//            convertInputKey(this.key);
            permutedKey = permutateKey(this.BinaryCovertedKey, firstPerm);
            separateHalves(permutedKey);
            createShiftedKeys();
            getFullSubKeys();
            for(int count = 0; count <= 15; ++count) {
                subkeys[count] = permutateKey(shiftedKeys[count], finalPerm);
            }
            return subkeys;
        }
        else {
            System.out.println("Invalid Key! Follow the guide lines");
            return null;
        }
    }

}

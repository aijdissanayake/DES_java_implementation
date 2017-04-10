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

public class CryptoProcessor {

    private final Converter converter = new Converter();

    private final int[] firstPerm = 
    {58,50,42,34,26,18,10,2,60,52,44,36,28,20,12,4,62,54,46,38,30,22,14,6,64,56,48,40,32,24,16,8,
     57,49,41,33,25,17,9,1,59,51,43,35,27,19,11,3,61,53,45,37,29,21,13,5,63,55,47,39,31,23,15,7};

    private final int[] finalPerm =
    {40,8,48,16,56,24,64,32,39,7,47,15,55,23,63,31,38,6,46,14,54,22,62,30,37,5,45,13,53,21,61,29,
    36,4,44,12,52,20,60,28,35,3,43,11,51,19,59,27,34,2,42,10,50,18,58,26,33,1,41,9,49,17,57,25};

    private final int[] extendPerm = 
    {32,1,2,3,4,5,4,5,6,7,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,
     24,25,26,27,28,29,28,29,30,31,32,1};

    private final int[] contractPerm = 
    {32,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31};

    // Permutates the message
    private String permutateMsg(String bitStream, int [] permutationArray) {
        StringBuilder permutedKey = new StringBuilder();
        for(int position : permutationArray) {
            permutedKey.append(bitStream.charAt(position - 1));
        }
        return permutedKey.toString();
    }
    //for substitution with xor
    private String xorSubFunction(String right, String key) {
        String expanded = permutateMsg(right, extendPerm);
        String firstXOR = xor(expanded,key);
        String contracted = permutateMsg(firstXOR, contractPerm);
        return contracted;
    }
    //xor method
    private String xor(String first, String second) {
        StringBuilder out = new StringBuilder();
        for(int count = 0; count <= first.length() - 1; ++count) {
            if(first.charAt(count) == second.charAt(count)) {
                out.append("0");
            }
            else {
                out.append("1");
            }
        }
        return out.toString();
    }
    //encryption method
    public String encrypt(String msg, String key) {
        KeyHandler newSubKeyGenerator = new KeyHandler();        
        String [] subKeys = newSubKeyGenerator.createKeyPool(key);
        String binaryMsg = converter.stringToBinary(msg);
        String permutedMessage = permutateMsg(binaryMsg, firstPerm);
//        String permutedMessage = permutateMsg(msg, firstPerm);
        String l0 = permutedMessage.substring(0,32);
        String r0 = permutedMessage.substring(32);

        String [] ln = new String [17];
        String [] rn = new String [17];
        ln[0] = l0;
        rn[0] = r0;

        for(int count = 1; count <= 16; ++count) {
            ln[count] = rn[count - 1];
            rn[count] = xor(ln[count - 1], xorSubFunction(rn[count - 1], subKeys[count - 1]));
        }

        String finalLeft = rn[16];
        String finalRight = ln[16];
        String finalString = finalLeft + finalRight;
        String binaryEncryptedMsg = permutateMsg(finalString, finalPerm);
        System.out.println("test" + binaryEncryptedMsg);
//        String encryptedMsg = converter.binaryToChar(binaryEncryptedMsg);
//        return encryptedMsg;
        return binaryEncryptedMsg;

    }
    //decryption method
    public String decrypt(String msg, String key) {
        KeyHandler newSubKeyGenerator = new KeyHandler();
        String [] subKeys = newSubKeyGenerator.createKeyPool(key);
//        String binaryMsg = converter.stringToBinary(msg);
//        String permutedMessage = permutateMsg(binaryMsg, firstPerm);
        String permutedMessage = permutateMsg(msg, firstPerm);
        String l0 = permutedMessage.substring(0,32);
        String r0 = permutedMessage.substring(32);

        String [] ln = new String [17];
        String [] rn = new String [17];
        ln[0] = l0;
        rn[0] = r0;

        for(int count = 1; count <= 16; ++count) {
            ln[count] = rn[count - 1];
            rn[count] = xor(ln[count - 1], xorSubFunction(rn[count - 1], subKeys[15 - (count - 1)]));
        }

        String finalLeft = rn[16];
        String finalRight = ln[16];
        String finalString = finalLeft + finalRight;
        String binaryDecryptedMsg = permutateMsg(finalString, finalPerm);
        String decryptedMsg = converter.binaryToChar(binaryDecryptedMsg);
        return decryptedMsg;
//        return binaryDecryptedMsg;

    }
}


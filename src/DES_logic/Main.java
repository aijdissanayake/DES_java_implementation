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
public class Main {
    public static void main(String args[]) {
        
        Converter converter = new Converter();
        CryptoProcessor encryptor = new CryptoProcessor();
        
        String binary = converter.stringToBinary("4ﾍEj}ﾏs!");
        System.out.println(binary);
        System.out.println("0011010010001101010001010110101001111101100011110111001100100001");
        String txt = converter.binaryToChar("0110000101100011011010000110000101101100011000010110101001100100");
        System.out.println(txt);

        // Enter a 64 bit block.
        String message = "0000000100100011010001010110011110001001101010111100110111101111";
        //                  1111101000000010111010100011000111001111100110011000111110101011
        //                  0110000101100011011010000110000101101100011000010110101001100100
        //4ﾍEj}ﾏs!
        //0011010010001101010001010110101001111101100011110111001100100001
        System.out.println("Message: " + message);

        // Enter a 64 bit binary key in hexadecimal format.//F3365779
        String key = "9BBFDFF1";
        System.out.println("Key: " + key);

        // Encrypt Message;
        String encryptedMessage = encryptor.encrypt(message, key);
        System.out.println("Encrypted message: " + encryptedMessage);

        // Decrypt the encrypted message.
        String decryptedMessage = encryptor.decrypt(encryptedMessage,key);
        System.out.println("Decrypted message: " + decryptedMessage);

        if(message.equals(decryptedMessage)) {
            System.out.println("Original message and the decrypted message are equal.");
        }
    }
}

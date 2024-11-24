//Amitoj's Project
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;

public class SafeCipher {

    private static SecretKey secretKey;  //Variable for encryption key.
    private static String encryptedText; //Variable stores encrypted text.


   /**
    * This method gives us an Advanced Encryption Standard key for encrypting and decrypting
    * @return SecretKey for AES Encrypting
    * @throws Exception if there is an error while generating the key
    */
    private static SecretKey generateSecretKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance("AES"); //creating KeyGenerator instance to use for the algorithm
        keyGen.init(128); //The AES Key Size
        return keyGen.generateKey();
    }

    /**
     * This method encrypts the provided string using AES algorithm
     * @param plainText is the string being encrypted.
     * @return The encrypted text in Base64
     * @throws Exception if an error occurs
     */
    public static String encrypt(String plainText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedBytes = cipher.doFinal(plainText.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Decrypts encrypted text using AES decryption algorithm.
     *
     * @param encryptedText this is the string text to be decrypted
     * @return the original text when it is decrypted
     * @throws Exception if an error occurs
     */
    public static String decrypt(String encryptedText) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedText));
        return new String(decryptedBytes);
    }

    public static void main(String[] args) {
        try {
            //generate a secret key
            secretKey = generateSecretKey();

            Scanner scanner = new Scanner(System.in);
            boolean running = true; //Boolean value for the main loop. When set to true, the program is in a running state. When set to false, the program terminates.


            //The while loop manages the user interactions such as encrypting, decrypting, and quitting the program.
            while (running) {
                System.out.println("\n--- SafeCipher ---");
                System.out.println("1. Encrypt Text");
                System.out.println("2. Decrypt Text");
                System.out.println("3. Exit");
                System.out.print("Choose an option: ");

                //scanner accepts integer input
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1: //if variable "choice" value is 1, we do the following
                        System.out.print("Enter the text to encrypt: ");
                        String textToEncrypt = scanner.nextLine();
                        encryptedText = encrypt(textToEncrypt);
                        System.out.println("Encrypted Text: " + encryptedText);
                        break;

                    case 2: //if variable "choice" value is 2, we do the following
                        if (encryptedText == null) {
                            System.out.println("No Text Has Been Entered To Encrypt. Please Enter A Text To Encrypt!");
                        } else {
                            String decryptedText = decrypt(encryptedText);
                            System.out.println("Decrypted Text: " + decryptedText);
                        }
                        break;

                    case 3: //if variable "choice" value is 3, we do the following
                        System.out.println("Exiting SafeCipher!");
                        running = false;
                        break;

                    default: //An invalid input would lead to the following: 
                        System.out.println("Invalid choice. Please try again.");
                }
            }

            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

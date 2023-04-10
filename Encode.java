import java.util.Random;
import java.util.Scanner;

public class Encode {
    protected static final String table = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789()*+,-./";
    protected static final int setLength = table.length(); // Reduce calls to table.length()
    public static class Encoder {
        static final Random rnd = new Random();
        final int offSet = rnd.nextInt(setLength);

        public String encode(String plainText) {
            int textLength = plainText.length();
            char[] encoding = new char[textLength + 1]; // a list to store the encoded characters.
            encoding[0] = table.charAt(offSet); // first letter signifies that offset index
            int k = 1; // index for final char/String

            for (int i = 0; i < textLength; i++) {
                char character = plainText.charAt(i); // first letter to be matched
                int tableIndex = table.indexOf(character); // index of the matching letter.

                if (tableIndex >= 0) { // if found , else -1
                    if (tableIndex - offSet >= 0) { // if the encoding char is within range
                        encoding[k] = table.charAt(tableIndex - offSet);
                        k++;
                    } else {
                        int sum = tableIndex - offSet; // sum is a negative number
                        int fin = sum + setLength;
                        encoding[k] = table.charAt(fin);
                        k++;

                        /* Example:
                            tableIndex - offSet = 0 - 5 = -5
                            int fin = -5 + 44 = 39
                            encoding[k] = table.charAt(39)
                         */
                    }
                } else { // Not found in table just pass same char without encoding.
                    encoding[k] = plainText.charAt(i);
                    k++;
                }
            }
            return new String(encoding); // char [] array to string.
        }
    }

    public static class Decoder {
        public String decode(String encodedText) {
            char decoding[] = new char[encodedText.length() - 1]; // make an array for decoding the encoded
            int offset = table.indexOf(encodedText.charAt(0));
            int k = 0;

            if (offset == -1) { // offset not found.
                return "Unable to decode, invalid Offset given!";
            } else {
                for (int j = 1; j < encodedText.length(); j++) {
                    int tableIndex = table.indexOf(encodedText.charAt(j));

                    if (tableIndex == -1) { // If the char is !within table set replace with same
                        decoding[k] = encodedText.charAt(j);
                        k++;
                    } else {
                        if (tableIndex + offset >= setLength) { // exceed table index range
                            // System.out.println("added index: "+(tableIndex+offset)); // Testing
                            int fin = (tableIndex + offset - setLength) % setLength;
                            decoding[k] = table.charAt(fin);
                            k++;
                        } else {
                            decoding[k] = table.charAt(tableIndex + offset);
                            k++;
                        }
                    }
                }
            }
            return new String(decoding);
        }
    }

    public static void main(String[] args) {
        Encoder encoder = new Encoder();
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter a string to be encoded: ");
        String plainText = kb.nextLine();
        String encodedString = encoder.encode(plainText);
        System.out.println(encodedString);
        System.out.println("offSet: "+ encoder.offSet);
        Decoder decoder = new Decoder();
        System.out.println(decoder.decode(encodedString));
        //System.out.println("fin: " + decoder.fin);
    }
}

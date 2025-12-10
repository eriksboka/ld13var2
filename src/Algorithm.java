import java.util.Scanner;

public class Algorithm {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.print("original text: ");
        String original = sc.nextLine();
        System.out.print("compressed text: ");
        System.out.print(comp(original));

    }

    static String comp(String originalText) {
        String compressed = "";
        char[] characters = originalText.toCharArray();
        StringBuilder sb = new StringBuilder();
        int letterCounter = 0;
        
        for (int i = 0; i < originalText.length(); i++) {
            if (characters[i] == characters[i+1]) {
                  
            }
        }
        compressed = sb.toString();
        return compressed;
    }
}

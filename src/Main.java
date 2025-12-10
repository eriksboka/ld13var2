
//251RDB074 Ēriks Boka 7
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        run();
    }

    static void run() {
        String action = sc.nextLine();
        switch (action) {
            case "comp":
                comp();
                break;
            case "decomp":
                decomp();
                break;
            case "size":
                size();
                break;
            case "equal":
                equal();
                break;
            case "about":
                System.out.println("251RDB074 Ēriks Boka 7");
                break;
            default:
                break;
        }
        sc.close();
    }

    static void comp() {
        System.out.print("source file name:");
        String sourceName = sc.nextLine();

        System.out.println("archive name:");
        String archiveName = sc.nextLine();

        
        // Map<Character, Integer> lineLetterAmountMap = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(sourceName));
            BufferedWriter bw = new BufferedWriter(new FileWriter(archiveName));

            for(String line : br.lines().toList()) {
                
                char[] characters = line.toCharArray();
                int letterCounter = 0;

                for (int i = 0; i < line.length(); i++) {
                    if (characters[i] == characters[i+=2]) {
                        letterCounter++;
                    }
                }
                // if, while going through the line - more than 2 letters are next to each other:
                // ++letterCounter
                // to lineLetterAmountMap add (Character)'letter' and (Integer)letterCounter
                // with BufferedWriter rewrite the same line but with letterCounter+Letter inside a word.
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // COMPRESS
    }


    static void decomp() {
        System.out.println("archive name:");
        String archiveName = sc.nextLine();

        System.out.print("file name:");
        String sourceName = sc.nextLine();
        
        // DECOMPRESS

        // if letterCounter+Letter case is found - turn it into Letter*letterCounter case
    }

    static void size() {
        int fileSizeInBytes = 0;
        
    }

    static void equal() {

    }
}

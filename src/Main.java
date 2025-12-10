// 251RDB074 Ēriks Boka
// 241RMB016 Sofja Spicina

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String choiseStr;
        String sourceFile, resultFile, firstFile, secondFile;

        loop: while (true) {

            choiseStr = sc.next();

            switch (choiseStr) {
                case "comp":
                    System.out.print("source file name: ");
                    sourceFile = sc.next();
                    System.out.print("archive name: ");
                    resultFile = sc.next();
                    comp(sourceFile, resultFile);
                    break;
                case "decomp":
                    System.out.print("archive name: ");
                    sourceFile = sc.next();
                    System.out.print("file name: ");
                    resultFile = sc.next();
                    decomp(sourceFile, resultFile);
                    break;
                case "size":
                    System.out.print("file name: ");
                    sourceFile = sc.next();
                    size(sourceFile);
                    break;
                case "equal":
                    System.out.print("first file name: ");
                    firstFile = sc.next();
                    System.out.print("second file name: ");
                    secondFile = sc.next();
                    System.out.println(equal(firstFile, secondFile));
                    break;
                case "about":
                    about();
                    break;
                case "exit":
                    break loop;
            }
        }

        sc.close();
    }

    public static void comp(String sourceFile, String resultFile) {

//         FUNCTION compressRLE(InputString)
//     // InputString is the HTML content to be compressed.
//     // We will build the CompressedOutput character by character.

//     IF InputString IS NULL OR length(InputString) == 0 THEN
//         RETURN InputString
//     END IF

//     // Initialize the structure that will hold the compressed data.
//     // In Java, you'd use a StringBuilder (part of java.lang) or a char array.
//     Initialize CompressedOutput

//     // Initialize variables to track the current run
//     SET CurrentCharacter = first character of InputString
//     SET RunLength = 1

//     // Start iteration from the second character (index 1)
//     FOR Index FROM 1 TO length(InputString) - 1 DO
//         SET NextCharacter = character at Index in InputString

//         IF NextCharacter IS EQUAL TO CurrentCharacter THEN
//             // Case 1: The run continues
//             INCREMENT RunLength
//         ELSE
//             // Case 2: The run has ended

//             // A. Append the run length (count) to the output
//             //    (You'll need a way to convert the integer RunLength to a string/char)
//             APPEND RunLength TO CompressedOutput

//             // B. Append the character to the output
//             APPEND CurrentCharacter TO CompressedOutput

//             // C. Start a new run
//             SET CurrentCharacter = NextCharacter
//             SET RunLength = 1
//         END IF
//     END FOR

//     // --- Handling the Final Run ---
//     // The loop finishes after processing the last character, but the final
//     // run's length and character haven't been appended yet.

//     APPEND RunLength TO CompressedOutput
//     APPEND CurrentCharacter TO CompressedOutput

//     RETURN CompressedOutput
// END FUNCTION
    }

    public static void decomp(String sourceFile, String resultFile) {

    }

    public static void size(String sourceFile) {
        try {
            FileInputStream f = new FileInputStream(sourceFile);
            System.out.println("size: " + f.available());
            f.close();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public static boolean equal(String firstFile, String secondFile) {
        try {
            FileInputStream f1 = new FileInputStream(firstFile);
            FileInputStream f2 = new FileInputStream(secondFile);
            int k1, k2;
            byte[] buf1 = new byte[1000];
            byte[] buf2 = new byte[1000];
            do {
                k1 = f1.read(buf1);
                k2 = f2.read(buf2);
                if (k1 != k2) {
                    f1.close();
                    f2.close();
                    return false;
                }
                for (int i = 0; i < k1; i++) {
                    if (buf1[i] != buf2[i]) {
                        f1.close();
                        f2.close();
                        return false;
                    }

                }
            } while (!(k1 == -1 && k2 == -1));
            f1.close();
            f2.close();
            return true;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    public static void about() {
        System.out.println("251RDB074 Ēriks Boka");
        System.out.println("241RMB016 Sofja Spicina");
    }
}

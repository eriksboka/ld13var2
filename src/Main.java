// 251RDB074 Ēriks Boka 7
// 241RMB016 Sofja Spicina 7

//#region Proj. Info.
/*
Huffman Encoding Algorithm without using custom Node class + no PriorityQueue

// Pseudo code / algorithm's step by step explanation:
    // step 1: calculate the frequency of each symbol
    // - create a hashmap to asign the symbol and it's frequency (in times how much
    // it appeared throughout the file)

    // step 2: pick two lowest frequencies from the hashmap, the sum of these will
    // be its parent 'node'
    // - sum smallest value 1 and smallest value 2 entires in the hashmap, delete
    // them from the hashmap and add their parent's summed up frequency instead

    // step 3: loop for every possible pair of smallest entries to the map until
    // only 1 entry will stay
    // - the root node of the tree will be the total number of symbol containing in
    // the text we're compressing
    // - assign every smallest frequency (of the pair) the value of 0, and the
    // largest (of the pair) - 1

    // step 4: assign each symbol from the hashmap it's new value - read down from
    // the root to the "leaf" of the huffman's tree we've created

    sources: 
    https://www.youtube.com/watch?v=21_bJLB7gyU - Huffman Coding Algorithm Explained and Implemented in Java | Data Compression | Geekific
    https://www.youtube.com/watch?v=zSsTG3Flo-I - Java How-To : Huffman Encoding (Part I)
    https://www.w3schools.com/dsa/dsa_ref_huffman_coding.php - Data Structures and Algorithms: Huffman Coding
    https://www.geeksforgeeks.org/dsa/text-file-compression-and-decompression-using-huffman-coding/ - ext File Compression And Decompression Using Huffman Coding
    https://www.geeksforgeeks.org/java/huffman-coding-java/ - Huffman Coding Java
*/
//#endregion
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static void about() {
        System.out.println("251RDB074 Ēriks Boka 7");
        System.out.println("241RMB016 Sofja Spicina 7");
    }

    // #region Alg. Components
    public static int findSmallest(List<Map.Entry<Character, Integer>> frequencyList) {
        int smallest = 0;
        for (int i = 1; i < frequencyList.size(); i++) {
            if (frequencyList.get(i).getValue() < frequencyList.get(smallest).getValue()) {
                smallest = i;
            }
        }
        return smallest;
    }

    private static void buildCodes(char node, String code, Map<Character, Character> leftChild,
            Map<Character, Character> rightChild, Map<Character, String> leftCode, Map<Character, String> rightCode,
            Map<Character, String> huffmanCodes, java.util.Set<Character> originalChars) {

        if (originalChars.contains(node)) { // if leaf node
            huffmanCodes.put(node, code.isEmpty() ? "0" : code);
            return;
        }

        if (leftChild.containsKey(node)) { // if left internal node
            buildCodes(leftChild.get(node), code + leftCode.get(node), // appends 0
                    leftChild, rightChild, leftCode, rightCode, huffmanCodes, originalChars);
        }

        if (rightChild.containsKey(node)) { // if right internal node
            buildCodes(rightChild.get(node), code + rightCode.get(node), // appends 1
                    leftChild, rightChild, leftCode, rightCode, huffmanCodes, originalChars);
        }
    }

    private static void assignHuffmanCodes(Map<Character, Integer> frequencies, Map<Character, String> huffmanCodes) {
        if (frequencies.size() == 1) {
            for (Character symbol : frequencies.keySet()) {
                huffmanCodes.put(symbol, "0");
            }
            return;
        }

        List<Map.Entry<Character, Integer>> nodes = new ArrayList<>(frequencies.entrySet());

        Map<Character, Character> leftChild = new HashMap<>();
        Map<Character, String> leftCode = new HashMap<>();

        Map<Character, Character> rightChild = new HashMap<>();
        Map<Character, String> rightCode = new HashMap<>();

        char internalNodeId = '\uFFFF'; // id of the initial internal node (each node - they;re subtracting by 1 for
                                        // each next node)

        while (nodes.size() > 1) { // while not only root node stays

            // delete smallest nodes
            int firstSmallest = findSmallest(nodes);
            Map.Entry<Character, Integer> first = nodes.remove(firstSmallest);
            int secondSmallest = findSmallest(nodes);
            Map.Entry<Character, Integer> second = nodes.remove(secondSmallest);

            // create parent node, which is a sum of samllest nodes' frequencies
            int combinedFreq = first.getValue() + second.getValue();
            char parentId = internalNodeId--;

            // store children in childs maps and their corresponding codes in codes map
            leftChild.put(parentId, first.getKey());
            rightChild.put(parentId, second.getKey());
            leftCode.put(parentId, "0");
            rightCode.put(parentId, "1");

            // add parent to list instead of 2 deleted children
            nodes.add(new HashMap.SimpleEntry<>(parentId, combinedFreq));
        }

        // root is the last remaining node - it holds max amount of data stored in the
        // file compressing
        char root = nodes.get(0).getKey();

        buildCodes(root, "", leftChild, rightChild, leftCode, rightCode, huffmanCodes, frequencies.keySet());
    }
    // #endregion

    public static void comp(String sourceFile, String resultFile) {
        try {
            FileInputStream fis = new FileInputStream(sourceFile);
            StringBuilder content = new StringBuilder();
            int data;
            while ((data = fis.read()) != -1) {
                content.append((char) data);
            }
            fis.close();

            char[] symbols = content.toString().toCharArray();

            // Calculate frequencies
            Map<Character, Integer> frequencies = new LinkedHashMap<>();
            for (char symbol : symbols) {
                frequencies.put(symbol, frequencies.getOrDefault(symbol, 0) + 1);
            }

            // Build Huffman codes
            Map<Character, String> huffmanCodes = new HashMap<>();
            assignHuffmanCodes(frequencies, huffmanCodes);

            // Encode data
            StringBuilder encodedData = new StringBuilder();
            for (char symbol : symbols) {
                encodedData.append(huffmanCodes.get(symbol));
            }

            FileOutputStream fos = new FileOutputStream(resultFile);

            // #region Binary header 
            // (stores unique symbols)

            // Write frequency table size
            fos.write((frequencies.size() >> 8) & 0xFF);
            fos.write(frequencies.size() & 0xFF);

            // Write frequency table
            for (Map.Entry<Character, Integer> entry : frequencies.entrySet()) {
                fos.write((entry.getKey() >> 8) & 0xFF);
                fos.write(entry.getKey() & 0xFF);
                fos.write((entry.getValue() >> 24) & 0xFF);
                fos.write((entry.getValue() >> 16) & 0xFF);
                fos.write((entry.getValue() >> 8) & 0xFF);
                fos.write(entry.getValue() & 0xFF);
            }
            // #endregion

            //#region binary body
            // "allocate space" for the byte when bits we've got will be converted to bytes
            // Write number of padding bits
            String bits = encodedData.toString();
            int padding = 8 - (bits.length() % 8);
            if (padding == 8)
                padding = 0;
            fos.write(padding);

            // Convert binary string to bytes
            for (int i = 0; i < bits.length(); i += 8) {
                String byteStr = bits.substring(i, Math.min(i + 8, bits.length()));
                while (byteStr.length() < 8) {
                    byteStr += "0"; // Add padding
                }
                int byteValue = Integer.parseInt(byteStr, 2);
                fos.write(byteValue);
            }

            //#endregion
            fos.close();
            System.out.println("done");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void decomp(String sourceFile, String resultFile) {
        try (FileInputStream fis = new FileInputStream(sourceFile);
            FileOutputStream fos = new FileOutputStream(resultFile)) {

        // #region 1. Read Header (Frequency Table)

        // Read frequency table size (2 bytes)
        int sizeHigh = fis.read();
        int sizeLow = fis.read();
        if (sizeHigh < 0 || sizeLow < 0) {
            System.err.println("Error: Corrupt file (cannot read frequency size).");
            return;
        }
        int frequencyTableSize = (sizeHigh << 8) | sizeLow;

        // Read frequency table
        Map<Character, Integer> frequencies = new LinkedHashMap<>();
        for (int i = 0; i < frequencyTableSize; i++) {
            // Read Character (2 bytes)
            int charHigh = fis.read();
            int charLow = fis.read();
            if (charHigh < 0 || charLow < 0) {
                System.err.println("Error: Corrupt file (cannot read symbol).");
                return;
            }
            char symbol = (char) ((charHigh << 8) | charLow);

            // Read Frequency (4 bytes)
            int freqB3 = fis.read();
            int freqB2 = fis.read();
            int freqB1 = fis.read();
            int freqB0 = fis.read();
            if (freqB3 < 0 || freqB2 < 0 || freqB1 < 0 || freqB0 < 0) {
                System.err.println("Error: Corrupt file (cannot read frequency value).");
                return;
            }
            // Construct the integer from 4 bytes (Big-endian)
            int frequency = (freqB3 << 24) | (freqB2 << 16) | (freqB1 << 8) | freqB0;

            frequencies.put(symbol, frequency);
        }
        // #endregion

        // #region 2. Read Padding and Encoded Data

        // Read number of padding bits (1 byte)
        int padding = fis.read();
        if (padding < 0) {
            System.err.println("Error: Corrupt file (cannot read padding).");
            return;
        }

        // If the original file was empty, we return immediately.
        if (frequencyTableSize == 0) {
            return;
        }

        // Read the rest of the file (encoded bytes)
        List<Byte> encodedBytes = new ArrayList<>();
        int byteRead;
        while ((byteRead = fis.read()) != -1) {
            encodedBytes.add((byte) byteRead);
        }

        // Convert bytes to binary string
        StringBuilder encodedData = new StringBuilder();
        for (int i = 0; i < encodedBytes.size(); i++) {
            byte b = encodedBytes.get(i);
            // Convert byte to 8-bit binary string, ensuring leading zeros are kept
            // `b & 0xFF` treats the byte as an unsigned value.
            String byteStr = String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');

            if (i == encodedBytes.size() - 1 && padding > 0) {
                // Remove the padding zeros that were added to the last byte during compression
                if (byteStr.length() >= padding) {
                    byteStr = byteStr.substring(0, byteStr.length() - padding);
                }
            }
            encodedData.append(byteStr);
        }
        String compressedBits = encodedData.toString();
        // #endregion

        // #region 3. Rebuild Huffman Codes and Decode

        // Rebuild Huffman Codes using the frequencies (must use the same logic as comp)
        Map<Character, String> huffmanCodes = new HashMap<>();
        assignHuffmanCodes(frequencies, huffmanCodes);

        // Create the reverse map for decoding (Code -> Character)
        Map<String, Character> huffmanCodeToChar = new HashMap<>();
        for (Map.Entry<Character, String> entry : huffmanCodes.entrySet()) {
            huffmanCodeToChar.put(entry.getValue(), entry.getKey());
        }

        // Decode data by traversing the compressed bits
        StringBuilder decodedData = new StringBuilder();
        String currentCode = "";

        for (int i = 0; i < compressedBits.length(); i++) {
            currentCode += compressedBits.charAt(i);
            if (huffmanCodeToChar.containsKey(currentCode)) {
                char decodedChar = huffmanCodeToChar.get(currentCode);
                decodedData.append(decodedChar);
                currentCode = ""; // Reset for the next character
            }
        }

        // #endregion

        // #region 4. Write Decoded Data

        // Write the decoded characters to the result file.
        fos.write(decodedData.toString().getBytes());
        System.out.println("done");

        // #endregion

    } catch (Exception e) {
        e.printStackTrace();
    }
        // TODO: implement the reverse-engineered version of the compression code
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
}

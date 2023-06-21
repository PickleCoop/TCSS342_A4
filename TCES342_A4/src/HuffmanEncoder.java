
import java.io.FileOutputStream;
import java.io.IOException;

public class HuffmanEncoder {
    protected String inputFileName = "WarAndPeace.txt";
    protected String outputFileName = "./WarAndPeace-compressed.bin";
    protected String codesFileName = "./WarAndPeace-codes.txt";
    protected BookReader book;
    protected MyOrderedList<FrequencyNode> frequencies;
    protected HuffmanNode huffmanTree;
    protected MyOrderedList<CodeNode> codes;
    protected byte[] encodedText;



    public HuffmanEncoder() throws IOException {
        book = new BookReader("WarAndPeace.txt");
        frequencies = new MyOrderedList<>();
        huffmanTree = new HuffmanNode();
        codes = new MyOrderedList<>();
        encodedText = new byte[0];
        countFrequency();
        buildTree();
        encode();
        writeFiles();
    }

    protected void countFrequency(){
        System.out.println("\nCounting Frequencies...");
        long startTime = System.currentTimeMillis();
        FrequencyNode search = new FrequencyNode();
        for (int i = 0; i < book.book.length(); i++) {
                search.character = book.book.charAt(i);
                if (frequencies.binarySearch(search) == null) {
                    FrequencyNode temp = new FrequencyNode();
                    temp.character = search.character;
                    temp.count++;
                    frequencies.add(temp);
                } else {
                    frequencies.binarySearch(search).count++;
                }
        }
        long stopTime = System.currentTimeMillis();
        System.out.println("Time to Count Frequencies: " + (stopTime-startTime) + " milliseconds.");
        System.out.println("Number of Unique Characters: " + frequencies.size());
    }

    protected void buildTree(){
        System.out.println("\nCreating Huffman Tree...");
        long startTime = System.currentTimeMillis();
        MyPriorityQueue<HuffmanNode> storeHuffman = new MyPriorityQueue<>();
        for(int i = 0; i < frequencies.size(); i++){
            HuffmanNode node = new HuffmanNode();
            node.character = frequencies.get(i).character;
            node.weight = frequencies.get(i).count;
            storeHuffman.insert(node);
        }
        while(storeHuffman.size() > 1){
            HuffmanNode leftChild = storeHuffman.removeMin();
            HuffmanNode rightChild = storeHuffman.removeMin();
            HuffmanNode merged = new HuffmanNode(leftChild, rightChild);
            storeHuffman.insert(merged);
        }
        huffmanTree = storeHuffman.removeMin();
        extractCodes(huffmanTree, "");
        long stopTime = System.currentTimeMillis();
        System.out.println("Time to Create Huffman Tree: " + (stopTime - startTime) + " milliseconds.");
    }

    protected void extractCodes(HuffmanNode node, String code){
        if(node.right == null && node.left == null){
            CodeNode store = new CodeNode();
            store.code = code;
            store.character = node.character;
            codes.add(store);
        } else {
            extractCodes(node.left, code + "0");
            extractCodes(node.right, code + "1");
        }
    }

    protected void encode(){
        System.out.println("\nEncoding Message...");
        long startTime = System.currentTimeMillis();
        StringBuilder encoder = new StringBuilder();
        CodeNode search = new CodeNode();
        StringBuilder last = new StringBuilder();
        for (int i = 0; i < book.book.length(); i++){
            search.character = book.book.charAt(i);
            for (int j = 0; j < codes.size() - 1; j++){
                if (search.compareTo(codes.get(j)) == 0){
                    encoder.append(codes.get(j).code);
                }
            }
        }
        int padZeros = encoder.length() % 8;
        last.append("0".repeat(padZeros));
        last.append(codes.get(codes.size() - 1).code);
        encoder.append(last);
        byte[] temp = new byte[encoder.length() / 8];
        for (int i = 0, j = 0; i < temp.length; i += 8, j++){
            byte b = (byte)Integer.parseInt((encoder.substring(i, i+8)),2);
            temp[j] = b;
        }

        encodedText = temp;
        long endTime = System.currentTimeMillis();
        System.out.println("Time to Encode: " + (endTime - startTime) + " milliseconds.");
    }

    protected void writeFiles() throws IOException {
        System.out.println("\nWriting Files...");
        long startTime = System.currentTimeMillis();
        FileOutputStream encoded = new FileOutputStream("./WarAndPeace-compressed.bin");
        encoded.write(encodedText);
        encoded.close();

        FileOutputStream codesFile = new FileOutputStream("./WarAndPeace-codes.txt");
        codesFile.write(codes.toString().getBytes());
        codesFile.close();
        long stopTime = System.currentTimeMillis();
        System.out.println("Time to Write Files: " + (stopTime - startTime) + " milliseconds.");
        System.out.println("Total Bytes Written: " + encodedText.length);
    }


}

class FrequencyNode implements Comparable<FrequencyNode>{
    public Character character = null;
    public Integer count = 0;

    @Override
    public int compareTo(FrequencyNode other) {
        return character.compareTo(other.character);
    }

    public String toString(){
        return character + ":" + count;
    }

}

class HuffmanNode implements Comparable<HuffmanNode>{
    public Character character = null;
    public Integer weight = 0;
    public HuffmanNode left;
    public HuffmanNode right;

    public HuffmanNode() {
        this.left = null;
        this.right = null;
    }

    public HuffmanNode(HuffmanNode left, HuffmanNode right){
        this.weight = left.weight + right.weight;
        this.left = left;
        this.right = right;
    }

    @Override
    public int compareTo(HuffmanNode other) {
        if (weight.compareTo(other.weight) == 0){
            return 0;
        } else if (weight < other.weight){
            return -1;
        } else {
            return 1;
        }
    }

    public String toString(){
        return character + ":" + weight;
    }

}

class CodeNode implements Comparable<CodeNode>{
    public Character character = null;
    public String code = "";

    public int compareTo(CodeNode other){
        return character.compareTo(other.character);
    }

    public String toString(){
        return character + ":" + code;
    }

}

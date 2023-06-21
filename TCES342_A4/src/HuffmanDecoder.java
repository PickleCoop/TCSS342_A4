import java.io.*;

public class HuffmanDecoder {
    protected String compressedFileName;
    protected String outputFileName;
    protected String codesFileName;
    protected String codesString;
    protected byte[] encodedText;
    protected MyOrderedList<CodeNode> codes;
    protected String book;

    public HuffmanDecoder(){
        compressedFileName = "./WarAndPeace-compressed.bin";
        codesFileName = "./WarAndPeace-codes.txt";
        outputFileName = ".WarAndPeace-decompressed.txt";
    }

    protected void readFiles() throws IOException {
        File codesFile = new File(codesFileName);
        FileInputStream compressedFile = new FileInputStream(compressedFileName);
        StringBuilder codes = new StringBuilder();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        FileReader readCodes = new FileReader(codesFile);
        BufferedReader read = new BufferedReader(readCodes);
        int character = readCodes.read();
        while(character != -1){
            codes.append(character);
            character = readCodes.read();
        }
        readCodes.close();
        codesString = codes.toString();
        byte[] temp = new byte[compressedFile.readAllBytes().length];
        int bytes = re
    }

    protected void buildCodes(){

    }

    protected String getBit(int pos){

    }

    protected void rebuildText(){

    }

    protected void writeFile(){

    }



}

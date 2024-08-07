

import java.util.Objects;


public class ColumnarTranspositionCipher {

    private static String keyword;
    private static Object[][] table;
    private static String abecedarium;
    public static final String ABECEDARIUM = "abcdefghijklmnopqrstuvwxyzABCDEFG"
        + "HIJKLMNOPQRSTUVWXYZ0123456789,.;:-@";
    private static final String ENCRYPTION_FIELD = "≈";
    private static final char ENCRYPTION_FIELD_CHAR = '≈';

    public static String encrpyter(String word, String keyword) {
        ColumnarTranspositionCipher.keyword = keyword;
        abecedariumBuilder(500);
        table = tableBuilder(word);
        Object[][] sortedTable = sortTable(table);
        StringBuilder wordEncrypted = new StringBuilder();
        for (int i = 0; i < sortedTable[i].length; i++) {
            for (int j = 1; j < sortedTable.length; j++) {
                wordEncrypted.append(sortedTable[j][i]);
            }
        }
        return wordEncrypted.toString();
    }

 
    public static String encrpyter(String word, String keyword, String abecedarium) {
        ColumnarTranspositionCipher.keyword = keyword;
        ColumnarTranspositionCipher.abecedarium = Objects.requireNonNullElse(abecedarium, ABECEDARIUM);
        table = tableBuilder(word);
        Object[][] sortedTable = sortTable(table);
        StringBuilder wordEncrypted = new StringBuilder();
        for (int i = 0; i < sortedTable[0].length; i++) {
            for (int j = 1; j < sortedTable.length; j++) {
                wordEncrypted.append(sortedTable[j][i]);
            }
        }
        return wordEncrypted.toString();
    }

    
    public static String decrypter() {
        StringBuilder wordDecrypted = new StringBuilder();
        for (int i = 1; i < table.length; i++) {
            for (Object item : table[i]) {
                wordDecrypted.append(item);
            }
        }
        return wordDecrypted.toString().replaceAll(ENCRYPTION_FIELD, "");
    }


    private static Object[][] tableBuilder(String word) {
        Object[][] table = new Object[numberOfRows(word) + 1][keyword.length()];
        char[] wordInChards = word.toCharArray();
        // Fils in the respective numbers
        table[0] = findElements();
        int charElement = 0;
        for (int i = 1; i < table.length; i++) {
            for (int j = 0; j < table[i].length; j++) {
                if (charElement < wordInChards.length) {
                    table[i][j] = wordInChards[charElement];
                    charElement++;
                } else {
                    table[i][j] = ENCRYPTION_FIELD_CHAR;
                }
            }
        }
        return table;
    }

   
    private static int numberOfRows(String word) {
        if (word.length() / keyword.length() > word.length() / keyword.length()) {
            return (word.length() / keyword.length()) + 1;
        } else {
            return word.length() / keyword.length();
        }
    }

    
     
    private static Object[] findElements() {
        Object[] charValues = new Object[keyword.length()];
        for (int i = 0; i < charValues.length; i++) {
            int charValueIndex = abecedarium.indexOf(keyword.charAt(i));
            charValues[i] = charValueIndex > -1 ? charValueIndex : null;
        }
        return charValues;
    }

    private static Object[][] sortTable(Object[][] table) {
        Object[][] tableSorted = new Object[table.length][table[0].length];
        for (int i = 0; i < tableSorted.length; i++) {
            System.arraycopy(table[i], 0, tableSorted[i], 0, tableSorted[i].length);
        }
        for (int i = 0; i < tableSorted[0].length; i++) {
            for (int j = i + 1; j < tableSorted[0].length; j++) {
                if ((int) tableSorted[0][i] > (int) table[0][j]) {
                    Object[] column = getColumn(tableSorted, tableSorted.length, i);
                    switchColumns(tableSorted, j, i, column);
                }
            }
        }
        return tableSorted;
    }

  
    private static Object[] getColumn(Object[][] table, int rows, int column) {
        Object[] columnArray = new Object[rows];
        for (int i = 0; i < rows; i++) {
            columnArray[i] = table[i][column];
        }
        return columnArray;
    }

    private static void switchColumns(Object[][] table, int firstColumnIndex, int secondColumnIndex, Object[] columnToSwitch) {
        for (int i = 0; i < table.length; i++) {
            table[i][secondColumnIndex] = table[i][firstColumnIndex];
            table[i][firstColumnIndex] = columnToSwitch[i];
        }
    }


    private static void abecedariumBuilder(int value) {
        StringBuilder t = new StringBuilder();
        for (int i = 0; i < value; i++) {
            t.append((char) i);
        }
        abecedarium = t.toString();
    }

    private static void showTable() {
        for (Object[] table1 : table) {
            for (Object item : table1) {
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String keywordForExample = "asd215";
        String wordBeingEncrypted = "This is a test of the Columnar Transposition Cipher";
        System.out.println("### Example of Columnar Transposition Cipher ###\n");
        System.out.println("Word being encryped ->>> " + wordBeingEncrypted);
        System.out.println("Word encrypted ->>> " + ColumnarTranspositionCipher.encrpyter(wordBeingEncrypted, keywordForExample));
        System.out.println("Word decryped ->>> " + ColumnarTranspositionCipher.decrypter());
        System.out.println("\n### Encrypted Table ###");
        showTable();
    }
}

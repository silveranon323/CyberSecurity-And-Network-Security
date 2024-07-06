public class ModifiedDES {

    public static void main(String[] args) {
        String message = "Hi";
        String binaryMessage = stringToBinary(message);
        if (binaryMessage.length() < 16) {
            binaryMessage = String.format("%16s", binaryMessage).replace(' ', '0');
        } else if (binaryMessage.length() > 16) {
            binaryMessage = binaryMessage.substring(0, 16);
        }

        System.out.println("Original Binary Message: " + binaryMessage);
        String permutedMessage = new StringBuilder(binaryMessage).reverse().toString();
        System.out.println("Initial Permutation: " + permutedMessage);

        String leftPart = permutedMessage.substring(0, 8);
        String rightPart = permutedMessage.substring(8, 16);
        System.out.println("Left Part: " + leftPart + ", Right Part: " + rightPart);

        String key = "0010011010110110";
        String key12Bit = convert16BitKeyTo12Bit(key);
        System.out.println("12-bit Key: " + key12Bit);

        for (int i = 0; i < 4; i++) {
            String expandedRightPart = expand8BitTo12Bit(rightPart);
            String xorResult = xorBinaryStrings(expandedRightPart, key12Bit);
            String substituted = sBoxSubstitution(xorResult);
            String permuted = pBoxPermutation(substituted);
            String newRightPart = xorBinaryStrings(leftPart, permuted);
            leftPart = rightPart;
            rightPart = newRightPart;
        }

        String combined = leftPart + rightPart;
        String finalPermutation = new StringBuilder(combined).reverse().toString();
        System.out.println("Encrypted Message: " + finalPermutation);

        String digitalSignature = generateDigitalSignature(finalPermutation);
        System.out.println("Digital Signature: " + digitalSignature);
    }

    private static String stringToBinary(String input) {
        StringBuilder binary = new StringBuilder();
        for (char character : input.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(character)).replaceAll(" ", "0"));
        }
        return binary.toString();
    }

    private static String convert16BitKeyTo12Bit(String key) {
        return key.substring(0, 3) + key.substring(4, 7) + key.substring(8, 11) + key.substring(12, 15);
    }

    private static String expand8BitTo12Bit(String input) {
        return input.charAt(0) + "" + input.charAt(1) + input.charAt(3) + input.charAt(2) + input.charAt(3) + input.charAt(2)
                + input.charAt(4) + input.charAt(5) + input.charAt(7) + input.charAt(6) + input.charAt(7) + input.charAt(6);
    }

    private static String xorBinaryStrings(String a, String b) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < a.length(); i++) {
            result.append(a.charAt(i) ^ b.charAt(i));
        }
        return result.toString();
    }

    private static String sBoxSubstitution(String input) {
        int[][] sBox = {
            {10, 6, 9, 3, 7, 11, 8, 14, 12, 13, 14, 15, 5, 4, 2, 1},
            {0, 1, 15, 12, 13, 4, 2, 5, 10, 6, 9, 3, 7, 11, 8, 14},
            {7, 11, 8, 14, 12, 13, 14, 15, 10, 6, 9, 3, 0, 1, 15, 12},
            {13, 4, 2, 5, 10, 6, 9, 3, 0, 1, 15, 12, 7, 11, 8, 14}
        };

        StringBuilder output = new StringBuilder();
        for (int i = 0; i < input.length(); i += 4) {
            int row = Integer.parseInt(input.substring(i, i + 2), 2);
            int col = Integer.parseInt(input.substring(i + 2, i + 4), 2);
            output.append(String.format("%4s", Integer.toBinaryString(sBox[row][col])).replace(' ', '0'));
        }
        return output.toString();
    }

    private static String pBoxPermutation(String input) {
        StringBuilder permuted = new StringBuilder();
        for (int i = 0; i < input.length(); i += 2) {
            permuted.append(input.charAt(i + 1)).append(input.charAt(i));
        }
        return permuted.toString();
    }

    private static String generateDigitalSignature(String data) {
        int decimalValue = Integer.parseInt(data, 2);
        int shiftedValue = decimalValue >> 2;
        return Integer.toBinaryString(shiftedValue);
    }
}

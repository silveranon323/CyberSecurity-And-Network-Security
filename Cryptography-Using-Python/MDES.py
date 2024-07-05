def reverse_bits(bits):
    return bits[::-1]

def s_box_substitution(bits, category):
    s_box = {
        "00": {
            "0000": "1010", "0001": "0110", "0010": "1009", "0011": "0011",
            "0100": "0117", "0101": "1011", "0110": "1000", "0111": "1110",
            "1000": "1010", "1001": "0116", "1010": "1009", "1011": "0011",
            "1100": "0117", "1101": "1011", "1110": "1008", "1111": "1110"
        },
        "01": {
            "0000": "0000", "0001": "0001", "0010": "1111", "0011": "1100",
            "0100": "1101", "0101": "0100", "0110": "0010", "0111": "0105",
            "1000": "0000", "1001": "0001", "1010": "1111", "1011": "1100",
            "1100": "1101", "1101": "0100", "1110": "0010", "1111": "0105"
        },
        "10": {
            "0000": "1010", "0001": "0110", "0010": "1009", "0011": "0011",
            "0100": "0117", "0101": "1011", "0110": "1000", "0111": "1110",
            "1000": "1010", "1001": "0116", "1010": "1009", "1011": "0011",
            "1100": "0117", "1101": "1011", "1110": "1008", "1111": "1110"
        },
        "11": {
            "0000": "0000", "0001": "0001", "0010": "1111", "0011": "1100",
            "0100": "1101", "0101": "0100", "0110": "0010", "0111": "0105",
            "1000": "0000", "1001": "0001", "1010": "1111", "1011": "1100",
            "1100": "1101", "1101": "0100", "1110": "0010", "1111": "0105"
        }
    }
    return ''.join(s_box[category][bits[i:i+4]] for i in range(0, len(bits), 4))

def p_box_permutation(bits):
    return ''.join(bits[i+1] + bits[i] for i in range(0, len(bits), 2))

def xor_operation(bits1, bits2):
    return ''.join('1' if b1 != b2 else '0' for b1, b2 in zip(bits1, bits2))

def pad_message(message):
    while len(message) % 2 != 0:
        message += " "
    return message

def modified_des_encrypt(text, encryption_key):
    padded_text = pad_message(text)
    binary_text = ''.join(format(ord(c), '08b') for c in padded_text)
    binary_text = reverse_bits(binary_text)
    left_part, right_part = binary_text[:8], binary_text[8:]
    encryption_key = encryption_key[:12]
    for _ in range(4):
        expanded_right = right_part + "0000"
        xor_result = xor_operation(expanded_right, encryption_key)
        category = encryption_key[:2]
        substituted_right = s_box_substitution(xor_result, category)
        permuted_right = p_box_permutation(substituted_right)
        left_part, right_part = right_part, xor_operation(permuted_right, left_part)
    final_binary = right_part + left_part
    final_binary = reverse_bits(final_binary)
    return final_binary

def binary_to_string(binary):
    string = ""
    for i in range(0, len(binary), 8):
        byte = binary[i:i+8]
        string += chr(int(byte, 2))
    return string

def create_digital_signature(data):
    binary_data = ''.join(format(ord(c), '08b') for c in data)
    return binary_data[:-2]

text = "Hi"
encryption_key = "1111000000111111"
encrypted_binary = modified_des_encrypt(text, encryption_key)
decrypted_text = binary_to_string(encrypted_binary)
digital_signature = create_digital_signature(encrypted_binary)

print("Encrypted Message (Binary):", encrypted_binary)
print("Encrypted Message (Text):", decrypted_text)
print("Digital Signature:", digital_signature)

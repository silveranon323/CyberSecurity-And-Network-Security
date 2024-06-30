import numpy as np

def mod_inverse(matrix, modulus):
    det = int(np.round(np.linalg.det(matrix))) % modulus
    inv_det = pow(det, -1, modulus)
    matrix_modulus = np.matrix(matrix) % modulus
    adjugate = np.round(inv_det * np.linalg.det(matrix) * np.linalg.inv(matrix)).astype(int) % modulus
    return adjugate

def encrypt(text, key_matrix):
    text_vector = [ord(char) - 65 for char in text.upper()]
    text_vector = np.array(text_vector).reshape(-1, key_matrix.shape[0])
    encrypted_vector = np.dot(text_vector, key_matrix) % 26
    encrypted_text = ''.join(chr(int(char) + 65) for char in encrypted_vector.flatten())
    return encrypted_text

def decrypt(cipher_text, key_matrix):
    inv_key_matrix = mod_inverse(key_matrix, 26)
    cipher_vector = [ord(char) - 65 for char in cipher_text.upper()]
    cipher_vector = np.array(cipher_vector).reshape(-1, key_matrix.shape[0])
    decrypted_vector = np.dot(cipher_vector, inv_key_matrix) % 26
    decrypted_text = ''.join(chr(int(char) + 65) for char in decrypted_vector.flatten())
    return decrypted_text


key_matrix = np.array([[6, 24, 1], [13, 16, 10], [20, 17, 15]])
text = "HELLO"


padding_length = len(key_matrix) - len(text) % len(key_matrix)
text += 'X' * padding_length if padding_length != len(key_matrix) else ''

encrypted = encrypt(text, key_matrix)
decrypted = decrypt(encrypted, key_matrix)

print("Encrypted:", encrypted)
print("Decrypted:", decrypted)

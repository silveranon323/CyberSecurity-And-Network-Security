from Crypto.Cipher import AES
from Crypto.Random import get_random_bytes

def pad(data):
    return data + (16 - len(data) % 16) * chr(16 - len(data) % 16).encode()

def unpad(data):
    return data[:-data[-1]]

def encrypt(plain_text, key):
    cipher = AES.new(key, AES.MODE_CBC)
    iv = cipher.iv
    encrypted_text = cipher.encrypt(pad(plain_text))
    return iv + encrypted_text

def decrypt(encrypted_text, key):
    iv = encrypted_text[:16]
    cipher = AES.new(key, AES.MODE_CBC, iv)
    decrypted_text = unpad(cipher.decrypt(encrypted_text[16:]))
    return decrypted_text

key = get_random_bytes(16)
plain_text = b'Hello, World!'
encrypted_text = encrypt(plain_text, key)
decrypted_text = decrypt(encrypted_text, key)

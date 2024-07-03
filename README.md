# Generate a 256-bit AES key
openssl rand -out aes_key.bin 32

# Encode the key in Base64 and save it
base64 aes_key.bin > aes_key_base64.txt

# Generate a 16-byte IV
openssl rand -out iv.bin 16

# Encode the IV in Base64 and save it
base64 iv.bin > iv_base64.txt


Generate a random AES key:

openssl rand -hex 32
This command generates 32 random bytes, which is appropriate for a 256-bit AES key. If you need a different key size, adjust the number of bytes:

Might output something like:
3b0f123f5a678c9c6b2934508f59a8a3a3e2fdb9188a1ecf7a8eaf8eec01f4d2

# SpeMajorBackend
Repo for spe major project backend

Documentaion of the project
https://docs.google.com/document/d/1fb16XEJ-2oDqY1UdpzP7I1xnMp1JLpdtxcJ1XgDxZMw/edit?usp=sharing

# Steps of how we achieved encryption

1. This create a plain text file which we will encrypt lated using ansible-vault  
echo "PASSWORD=prototype1">encrypt.txt

2. password.txt contains out vault access password  

3. we encrypted the emcrypt.txt using ansible-vault  
ansible-vault encrypt --vault-password-file=password.txt encrypt.txt

4. Added extra task in playbbok to decrypt this encrypted.txt file and store it in environment variable  
5. This command runs on playbook and stores decrypted password  
ansible-vault decrypt --vault-password-file=password.txt encrypt.txt --output=.env

5. The docker-compose file can access the created .env file to access the database password

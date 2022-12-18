# JB-medium-Encryption-Decryption
Project: [Encryption-Decryption](https://hyperskill.org/projects/46) from JetBrains Academy.


### args for testing:
**Example 1**: reading and writing to files; the arguments are:
```
-mode enc -in road_to_treasure.txt -out protected.txt -key 5 -alg unicode
```
This command must get data from road_to_treasure.txt, encrypt the data with the key of 5, create
protected.txt, and write ciphertext into it.


**Example 2**: encryption with the unicode algorithm; the arguments are:
```
-mode enc -key 5 -data "Welcome to hyperskill!" -alg unicode
```
Output: `\jqhtrj%yt%m~ujwxpnqq&`


**Example 3**: decryption with the unicode algorithm; the arguments are:
```
-key 5 -alg unicode -data "\jqhtrj%yt%m~ujwxpnqq&" -mode dec
```
Output: `Welcome to hyperskill!`


**Example 4**: encryption with the shift algorithm; the arguments are:
```
-key 5 -alg shift -data "Welcome to hyperskill!" -mode enc
```
Output: `Bjqhtrj yt mdujwxpnqq!`


**Example 5**: decryption with the shift algorithm; the arguments are:
```
-key 5 -alg shift -data "Bjqhtrj yt mdujwxpnqq!" -mode dec
```
Output: `Welcome to hyperskill!`


**Example 6**:
```
-mode enc -key 5 -in "./Encryption-Decryption/task/res/text" -data "Welcome!" -out "./Encryption-Decryption/task/res/result"
```

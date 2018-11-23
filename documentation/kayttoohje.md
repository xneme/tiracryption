# Käyttöohje
## RSA komentorivikomennot

```
java -jar tiracryption.jar RSAgenerate [key path]
java -jar tiracryption.jar RSAencrypt <key path> <file path>
java -jar tiracryption.jar RSAdecrypt <key path> <file path>
java -jar tiracryption.jar AESencrypt <file path>
java -jar tiracryption.jar AESdecrypt <file path>
```

## RSA komentoriviesimerkki

Generoi jar komennolla 
```
./gradlew jar 
```
Jos tämä on ensimmäinen kerta kuin ajat _./gradlew_, latautuu gradle koneellesi, tässä saattaa kestää hetki.

#  

Luo salattava tiedosto esim komennolla
```
echo "1234567890mockAESkey123456789012" > mockAESkey
```

#  

Generoi RSA-avaimet komennolla
```
java -jar build/libs/tiracryption.jar RSAgenerate
```
Avaimet luodaan oletusavoisesti tiedostoihin _/keys/rsakey_ ja _/keys/rsakey.pub_

Huom! tällä hetkellä generointi tuottaa toimivat mock-avaimet, ei satunnaisesti laskettuja!

#  

Salaa tiedosto julkisella RSA-avaimella komennolla
```
java -jar build/libs/tiracryption.jar RSAencrypt keys/rsakey.pub mockAESkey
```
Salattu tiedosto luodaan samaan hakemistoon alkuperäisen tiedoston kanssa, samalla tiedostonimellä ja loppuliitteellä _.encrypted_

#  

Tarkasta salatun tiedoston sisältö esimerkiksi komennolla
```
cat -A mockAESkey.encrypted
```

#  

Pura tiedoston salaus yksityisellä RSA-avaimella komennolla
```
java -jar build/libs/tiracryption.jar RSAdecrypt keys/rsakey mockAESkey.encrypted
```
Epäsalattu tiedosto luodaan samaan hakemistoon alkuperäisen tiedoston kanssa, samalla tiedostonimella ja loppuliite vaihdettuja tai lisättynä _.decrypted_

#  

Tarkasta tiedoston lukukelpoisuus komennolla
```
cat mockAESkey.decrypted
```

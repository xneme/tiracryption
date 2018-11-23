# Käyttöohje

RSA-salaus on generoiduilla avaimilla toimiva epäsymmetrinen salaus, joka soveltuu hitautensa takia lähinnä todella pienten tiedostojen, esimerkiksi AES-avainten salaukseen. AES on symmetrinen, mutta huomattavasti nopeampi salausmenetelmä. 
Molempien parhaat puolet saa yhdistettyä kun salaa itse tiedostot ensin AES:lla ja tähän käytetyn salausavaimen vastaanottajan julkisella RSA-avaimella. Vastaanottaja taas purkaa AES-avaimen salauksen omalla privaatilla RSA-avaimellaa ja käyttää tätä tiedostojen salauksen purkamiseen.

10Mt kuvan salaaminen AES-salauksella kestää muutamia sekunteja, RSA-salauksella useamman minuutin.

## Komentorivikomennot

```
java -jar tiracryption.jar RSAgenerate [key path]
java -jar tiracryption.jar RSAencrypt <key path> <file path>
java -jar tiracryption.jar RSAdecrypt <key path> <file path>
java -jar tiracryption.jar AESencrypt <file path>
java -jar tiracryption.jar AESdecrypt <file path>
```

## Aloitus

Generoi jar komennolla 
```
./gradlew jar 
```
Jos tämä on ensimmäinen kerta kuin ajat _./gradlew_, latautuu gradle koneellesi, tässä saattaa kestää hetki.

## RSA komentoriviesimerkki

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

## AES komentoriviesimerkki

Salaa esimerkiksi kuvatiedosto komennolla
```
java -jar build/libs/tiracryption.jar AESencrypt kurkkumopo.jpg
```
Komento luo automaattisesti uuden AES-avaimen tiedostoon _<tiedostonnimi>.key_, ja salatun tiedoston _<tiedostonnimi>.encrypted_. Tiedostot luodaan samaan hakemistoon alkuperäisen tiedoston kanssa.

#  

Salaus puretaan komennolla
```
java -jar build/libs/tiracryption.jar AESdecrypt kurkkumopo.jpg.encrypted
```
Komento etsii automaattisesti tiedostoon liittyvän avaimen ja luo puretun tiedoston samaan hakemistoon, tiedostopäätteellä _.decrypted_.

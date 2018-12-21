# Toteutusdokumentti

### Ohjelman yleisrakenne

Ohjelmaa käytetään komentoriviltä, main-luokka on vastuussa halutun toiminnallisuuden valitsemisesta ja tiedosto-osoitteiden välityksestä. Varsinaisen tiedostonkäsittelyn ja salauksen hoitavat pakkaukseen _methods_ sijoitetut luokat, jotka alustetaan salausavainten avulla. Samoja AES-lookup-tauluja käytetään sekä avainten laajentamisessa että varsinaisessa salauksessa ja salauksen purussa.

Luokka/pakkauskaavio:

<img src="https://raw.githubusercontent.com/xneme/tiracryption/master/documentation/structure.png">

### Aika- ja tilavaativuudet

AES-salaus toimii vakioaikaisesti ja -tilaisesti jokaista 16 tavun lohkoa kohden, joten salauksen aikavaativuus on luokkaa O(n), jossa n on salattavan tiedoston pituus. Lohkot kirjoitetaan suoraan levylle, eikä edellisestä lohkosta pidetä mitään muistissa, joten varsinaisen ohjelman tilavaativuus on luokkaa O(1).

RSA-salaus toimii polynomisessa ajassa avaimen pituuden suhteen, generointi on luokkaa O(k^4) ja salaus ja purku luokkaa O(k^3), missä k on avaimen pituus. Salauksessa käytetään kuitenkin vakiomittaisia avaimia ja aikavaativuus avaimen mittaista lohkoa kohden on vakio. Salauksen aikavaativuus on siis luokkaa O(n), missä n on salattavan tiedoston pituus. On kuitenkin huomattava, että RSA:n vakiokerroin on tuhansia kertoja AES-salausta suurempi.

### Puuteet ja parannusehddotukset

RSA-salaus on hyvin perusmallinen, ilman satunnaista paddingia ja muita kehittyneempiä ominaisuuksia, joten avaimet on mahdollista laskea julkisista avaimista ja suuresta määrästä salattua dataa.

TiraBigInteger-luokka jäi kesken kompleksisuuden virhearvioinnin vuoksi. Jakolaskun naiivi toteutus on aivan liian hidas ollakseen käyttökelpoinen ja osaa paremmista algoritmeista ei saanut käytettyä negatiivisten lukujen puuttumisen vuoksi. Toimiva ratkaisu saattaisi olla jakolaskun implementointi bittitason long divisionilla, jolloin vähennettäviä määriä ei tarvitsisi arvioida karkeasti.

Sekä AES- että RSA-luokilla on samoilla syötteillä toimivat metodit tiedostoja varten, joten näiden käyttöä main-luokasta voisi selkeyttää yhteisellä interfacella.

### Lähteet

[Specification for the ADVANCED ENCRYPTION STANDARD (AES)](https://csrc.nist.gov/csrc/media/publications/fips/197/final/documents/fips-197.pdf)

[RSA (cryptosystem) - Wikipedia The Free Encyclopedia](https://en.wikipedia.org/wiki/RSA_(cryptosystem) )

[Pretty Good Privacy - Wikipedia The Free Encyclopedia](https://en.wikipedia.org/wiki/Pretty_Good_Privacy)


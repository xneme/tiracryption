# Testausdokumentti

## Yksikkötestaus
Ohjelma on soveltuvin osin kattavasti yksikkötestattu Junitilla. Iso osa pienistä apumetodeista on privateja, joten ne tulevat testatuiksi luokkaa normaalisti käytettäessä. Main-luokka on testattu komentoriviltä käyttämällä. TiraBigInteger-luokalla ei ole Junit-testejä, mutta kaikki implementoidut ominaisuudet on testattu kattavasti erilaisilla syötteillä, vertaamalla tulosta Javan omaan BigInteger-toteutukseen.

## Integraatiotestaus
Osa Junit-testeistä on suoritettu niin, että ne käyttävät usempaa luokkaa ja testaavat tietyn toiminnallisuuden esimerkiksi suoraan tiedoston salaamalla.

## Järjestelmätestaus
Ohjelma käsittelee syötetiedostoja tavutasolla, joten tiedoston tyypillä ei ole väliä. Ohjelma on testattu toimivaksi eri kokoisilla- ja tyyppisillä tiedostoilla. Salauksen kryptografisessa tehokkuudessa on luotettu lähteisiin, eikä salausta ole tämän projektin puitteissa yritetty murtaa.

## Suorituskykytestaus
Alustava vertaileva suorituskykytestaus on suoritettu 16Mt jpg-tiedostoa salaamalla. Testissä huomaa selkeästi miksei RSA-salausta käytetä kuin hyvin pienille tiedostoille. PGP-salaus toimii käytännössä yhtä nopeasti kuin AES-256, mikäli avaimet on valmiiksi generoitu. Kaikki testatus salausalgoritmit toimivat huomattavasta nopeuserosta huolimatta lineaarisessa ajassa tiedoston koon suhteen.

### 16.3Mt JPG
AES-256: salaus ~8s, purku ~8s.
RSA-2048: salaus ~250s, purku ~250s

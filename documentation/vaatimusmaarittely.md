# Vaatimusmäärittely

## Yleistä
Ohjelman on tarkoitus tarjota muutama eritasoinen salausalgoritmi joko tiedostojen tai plaintextin salaamiseen.

## Käytettävät algoritmit ja tietorakenteet
Ohjelma käyttää ainakin seuraavia algoritmeja: ROT13, ROT47, RSA ja mahdollisesti RSA:ta ja jotakin symmetristä salausalgoritmia yhdistävä PGP.

Tärkeimpänä tietorakenteena RSA vaatii BigInteger-luokan, joka mahdollistaa paljon Long-kokonaislukuja pidempien kokonaislukujen käsittelyn. Tätä tarvitaan, sillä RSA perustuu erittäin pitkien alkulukujen eksponenttilaskutoimituksiin.

RSA:n avainten generointiin tarvitaan luonnollisesti myös satunnaislukugeneraattori.

## Ratkaistava ongelma
Ratkaistavana ongelmana on tiedostojen tai lyhyiden tekstimuotoisten viestien salaus tehokkaasti. RSA on algoritmina oletetusti vaikeasti murrettava, mutta se ei sovellu hitaudensa takia suurten datamäärien salaamiseen. Tähän ongelmaan auttaa RSA:ta ja nopeampia, symmetrisiä salausalgoritmeja yhdistävä PGP. PGP:ssä varsinainen data salataan nopeammalla algoritmilla, esim. AES-128, ja salauksessa käytetty avain salataan edelleen asymmetrisellä RSA:lla. Tällöin symmetrisen salauksen avain voidaan lähettää datan mukana turvallisesti.

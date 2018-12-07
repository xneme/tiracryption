# Viikkoraportti 6

## työaikakirjanpito
| päivä | aika | mitä tein  |
| :----:|:-----| :-----|
| 01.12. | 1   | Javadoc |
| 03.12. | 1   | Randomgeneraattori AESKeygenille soveltuvin osin |
| 05.12. | 3   | TiraBigIntegerin toteutuksen suunnittelua |
| 07.12. | 5   | TiraBigIntegerin toteutus aloitettu |

Tällä vikolla projekti eteni haluttua vähemmän lähinnä perhesyiden takia. BigInteger on tarkoitus toteuttaa pitämällä luvut etumerkittöminä 32-bittisinä kokonaislukuina taulukossa. Taulukko muodostaa käytännössä rajoittamattoman kokoisen base-4,294,967,296 luvun. Erityisesti päänvaivaa on aiheuttanut Integer-luokan käsittely etumerkittömänä (uusi ominaisuus Java8:ssa) ja lukujen parsiminen byte arraysta, sillä Java tulkitsee myös byte-alkeismuuttujat oletuksena etumerkillisinä ja muuttaa ne aina laskutoimituksia varten ensin int-muuttujiksi. Tämä aiheuttaa sen, että mahdollista negatiivista etumerkkiä kuvaava ensimmäinen bitti karkaa koko 32bittisen esitysmuodon alkuun.

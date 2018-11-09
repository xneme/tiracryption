## Viikkoraportti 2

# työaikakirjanpito
| päivä | aika | mitä tein  |
| :----:|:-----| :-----|
| 07.11. | 3   | RSA:n toimintaan syventyminen |
| 08.11. | 3   | Lisää RSA:n ja PGP:n toimintaan syventymistä |
| 09.11. | 1   | RSA keygen mockup |
| 09.11. | 3   | RSA encryptaus BigInteger-kokonaisluvuilla lisätty |
| 09.11. | 1   | Yksikkötestit, dokumentointia |

Tällä viikolla suurin osa ajasta on mennyt aiheen opiskeluun, erityisesti RSA ja BigIntegerin toiminta. Myös PGP:tä tutkittu, sillä RSA sopisi sen avaimien salaamiseen mainiosti. Itse ohjelmaan lisättu RSA-keygen mockup ja alkeellinen, kokonaisluvuilla toimiva RSA-salaus. Viime hetkellä huomasin ettei salaus toimi vielä pitkillä kokonaisluvuilla, mikäli käyttää mockupin tarjoamia hyvin lyhyitä avaimia, sillä mod on liian pieni pitkiin viesteihin. Pidemmän avaimen antamalla pitäisi toimia.

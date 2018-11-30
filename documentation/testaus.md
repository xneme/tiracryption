# Testausdokumentti

## Yksikkötestaus
Ohjelma on soveltuvin osin kattavasti yksikkötestattu Junitilla. Iso osa pienistä apumetodeista on privateja, joten ne tulevat testatuiksi luokkaa normaalisti käytettäessä.

## Integraatiotestaus
Osa Junit-testeistä on suoritettu niin, että ne käyttävät usempaa luokkaa ja testaavat tietyn toiminnallisuuden esimerkiksi suoraan tiedoston salaamalla

## Suorituskykytestaus
Alustava vertaileva suorituskykytestaus on suoritettu 16Mt jpg-tiedostoa salaamalla. Testissä huomaa selkeästi miksei RSA-salausta käytetä kuin hyvin pienille tiedostoille.

### 16.3Mt JPG
AES-256: salaus ~8s, purku ~8s.
RSA-2048: salaus ~250s, purku ~250s

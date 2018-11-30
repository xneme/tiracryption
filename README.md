# tiracryption
Tietorakenteet ja algoritmit -  harjoitustyö.

## Dokumentaatio
[Käyttöohje](/documentation/kayttoohje.md)

[Määrittelydokumentti](/documentation/vaatimusmaarittely.md)

<!--[Arkkitehtuurikuvaus](/documentation/arkkitehtuuri.md)
-->
[Testausdokumentti](/documentation/testaus.md)

## Viikkoraportit
[Viikko 1](/documentation/viikkoraportti1.md)

[Viikko 2](/documentation/viikkoraportti2.md)

[Viikko 3](/documentation/viikkoraportti3.md)

[Viikko 4](/documentation/viikkoraportti4.md)

[Viikko 5](/documentation/viikkoraportti5.md)

[Viikko 6](/documentation/viikkoraportti6.md)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla

```
./gradlew test
```

Testikattavuusraportti luodaan komennolla

```
./gradlew test jacocoTestReport
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _build/reports/tests/test/index.html_

### Suoritettavan jarin generointi

Komento

```
./gradlew jar
```

generoi hakemistoon _build/libs/_ suoritettavan jar-tiedoston _tiracryption.jar_
<!--
### JavaDoc

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_ -->
<!--
### Checkstyle

Tiedoston [checkstyle.xml](/config/checkstyle/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
./gradlew checkstyleMain
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _build/reports/checkstyle/main.html_
-->

package tiracryption.methods;

import java.math.BigInteger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import tiracryption.keys.RSAKeygen;

public class RSATest {
    
    private RSAKeygen gen = new RSAKeygen();
    
    @Test
    public void encryptionWorks() {
        RSA encrypt = new RSA(gen.getPublic());
        assertEquals("35052111338673026690212423937053328511880760811579981620642802346685810623109850235943049080973386241113784040794704193978215378499765413083646438784740952306932534945195080183861574225226218879827232453912820596886440377536082465681750074417459151485407445862511023472235560823053497791518928820272257787786"
                , encrypt.encrypt(new BigInteger("1976620216402300889624482718775150")));
    }
    
    @Test
    public void decryptionWorks() {
        RSA decrypt = new RSA(gen.getPrivate());
        assertEquals("1976620216402300889624482718775150", decrypt.decrypt(new BigInteger("35052111338673026690212423937053328511880760811579981620642802346685810623109850235943049080973386241113784040794704193978215378499765413083646438784740952306932534945195080183861574225226218879827232453912820596886440377536082465681750074417459151485407445862511023472235560823053497791518928820272257787786")));
    }
    
}
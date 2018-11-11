package tiracryption.keys;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;

public class RSAKeygen {

    private BigInteger p;
    private BigInteger q;
    private BigInteger mod;
    private BigInteger privateKey;
    private BigInteger publicKey;

    public RSAKeygen(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        this.mod = p.multiply(q);
        this.generate();
    }

    public RSAKeygen() {
        this.generate();
    }

    private void generate() {
        this.privateKey = new BigInteger("89489425009274444368228545921773093919669586065884257445497854456487674839629818390934941973262879616797970608917283679875499331574161113854088813275488110588247193077582527278437906504015680623423550067240042466665654232383502922215493623289472138866445818789127946123407807725702626644091036502372545139713");
        this.publicKey = new BigInteger("65537");
        this.mod = new BigInteger("145906768007583323230186939349070635292401872375357164399581871019873438799005358938369571402670149802121818086292467422828157022922076746906543401224889672472407926969987100581290103199317858753663710862357656510507883714297115637342788911463535102712032765166518411726859837988672111837205085526346618740053");
    }

    public RSAKey getPublic() {
        return new RSAKey(publicKey, mod);
    }

    public RSAKey getPrivate() {
        return new RSAKey(privateKey, mod);
    }

    public void writeKeyFiles(Path path) throws IOException {
        if (path.getParent() != null) {
            File directory = new File(path.getParent().toUri());
            directory.mkdirs();
        }
        Files.write(path, String.format("tiracryption private key:\n%s\n%s\n", this.privateKey.toString(), this.mod.toString()).getBytes());
        Files.write(path.resolveSibling(path.getFileName() + ".pub"), String.format("tiracryption public key:\n%s\n%s\n", this.publicKey.toString(), this.mod.toString()).getBytes());
    }

}

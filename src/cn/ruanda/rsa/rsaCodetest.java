package cn.ruanda.rsa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class rsaCodetest {
    public PrivateKey loadPriByDer(String file_name){
        try {
            byte[] priBytes = Files.readAllBytes(Paths.get(file_name));
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(priBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePrivate(encodedKeySpec);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PublicKey loadPubByDer(String file_name){
        byte[] pubBytes = new byte[0];
        try {
            pubBytes = Files.readAllBytes(Paths.get(file_name));
            X509EncodedKeySpec encodedKeySpec = new X509EncodedKeySpec(pubBytes);
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            return keyFactory.generatePublic(encodedKeySpec);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}

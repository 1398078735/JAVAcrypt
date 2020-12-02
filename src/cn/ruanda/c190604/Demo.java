package cn.ruanda.c190604;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Demo {
    String key = "c1906041";
    String data = "大河之剑天上来";


    //属性,方法的调用：java中属性和方法被包裹在class中，但是并不能直接调用属性和方法
    //通过new关键字可以实例化一个类的对象，然后通过对象才能调用
    //被static修饰，则不需要new实例化对象，可以直接通过类名.方法名 进行调用
    public static void main(String[] args) {
        Demo demo = new Demo();
        System.out.println(demo.key);
        System.out.println(demo.data);
        //加密
        byte[] cipherText = demo.encrypt(demo.key.getBytes(), demo.data.getBytes());
        byte[] originText = demo.Decrypt(cipherText, demo.key.getBytes());
        System.out.println(originText);

    }

    /**
     * des的代码封装
     * @param key des算法的密钥
     * @param data des算法的密钥
     * @param mode des算法的操作模式，cipher.encrypt_mode.....
     * @return
     */
    public byte[] desOperation(byte[] key, byte[] data, int mode) {
        try {
            //des的密钥生成
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
            //执行cipher加密动作的实例
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(mode, secretKey);
            //执行加密
            return cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }




    //方法一:加密
    public byte[] encrypt(byte[] key, byte[] data) {
        try {
            //des的密钥生成
            DESKeySpec desKeySpec = new DESKeySpec(key);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
            //执行cipher加密动作的实例
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            //执行加密
            return cipher.doFinal(data);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }

    //方法二:解密
    public byte[] Decrypt(byte[] cipherText, byte[] key) {
        try {
            //des的密钥生成
            DESKeySpec Spec = new DESKeySpec(key);
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = secretKeyFactory.generateSecret(Spec);
            //执行cipher加密动作的实例
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //执行加密
            return cipher.doFinal(cipherText);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } finally {

        }
        return null;
    }
}

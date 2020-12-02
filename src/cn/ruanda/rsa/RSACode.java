package cn.ruanda.rsa;

import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

//私钥的生成命令:genrsa -out file_name.pem
//公钥的生成命令:rsa -in file_name.pem -pubout -out file_name_pub.pem
//pem格式和der格式互转(使用openssl命令)pkcs8
//该类用于实现rsa算法的操作，包括密钥生成，加解密，签名验证操作
public class RSACode {
    static String data = "南昌";
    public static void main(String[] args) {
        //加密
        RSACode code = new RSACode();
        KeyPair keyPair = code.createKey(1024);
        byte[] cipherTxt = code.encrypt(data.getBytes(),keyPair.getPublic());
        System.out.println(cipherTxt);

        //解密
        byte[] originTxt = code.decrypt(cipherTxt, keyPair.getPrivate());
        System.out.println(new String(originTxt));
    }

    //====================通过读取密钥文件恢复公钥和私钥=======================

    /**
     * 生成pem格式的文件,使用了base64解码
     * @param file_name
     * @return
     */
    public PrivateKey readPriByPem(String file_name){
        try {
            byte[] priBytes = Files.readAllBytes(Paths.get(file_name));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            //将BASE64编码的私钥字符串进行解码
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] encodeByte = base64Decoder.decodeBuffer(String.valueOf(priBytes));
            //将BASE64解码后的字节数组，构造成PKCS8EncodedKeySpec对象，生成私钥对象
            return factory.generatePrivate(new PKCS8EncodedKeySpec(encodeByte));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public PublicKey readPubByPem(String file_name){
        try {
            byte[] pubBytes = Files.readAllBytes(Paths.get(file_name));
            KeyFactory factory = KeyFactory.getInstance("RSA");
            //将BASE64编码的私钥字符串进行解码
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] encodeByte = base64Decoder.decodeBuffer(String.valueOf(pubBytes));
            //将BASE64解码后的字节数组，构造对象，生成公钥钥对象
            return factory.generatePublic(new X509EncodedKeySpec(encodeByte));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据der公钥文件,恢复公钥
     * @param file_name 文件名
     * @return公钥对象
     */
    public PrivateKey loadPriByDer(String file_name){
        // TODO: 2020/11/30  
        //字节流:任意的文件，把文件内容读成byte[]
        //字符流:只针对文档/文本
        try {
            //从文件当中读取私钥字节
            byte[] priBytes = Files.readAllBytes(Paths.get(file_name));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(priBytes);
            //工厂类
            KeyFactory factory = KeyFactory.getInstance("RSA");
            //生成私钥
            return factory.generatePrivate(spec);
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
        try {
            byte[] pubBytes = Files.readAllBytes(Paths.get(file_name));
            X509EncodedKeySpec spec = new X509EncodedKeySpec(pubBytes);
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(spec);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }





    //=====================MD5哈希计算=======================
    //对原文数据进行哈希计算
    public byte[] md5Hash(byte[] data){
        //hash算法:消息摘要,message Digest
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            return digest.digest(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }


    //===========私钥签名,公钥验签==================
    public byte[] sign(byte[] data,PrivateKey pri){
        try {
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(pri);//初始化私钥
            //对原文进行hash计算
            byte[] hash = md5Hash(data);
            signature.update(hash);//更新签名的数据
            return signature.sign();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    //验证签名返回一个boolean类型
    public boolean verify(byte[] data,PublicKey pub,byte[] sign){
        try {
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(pub);//初始化公钥
            byte[] hash = md5Hash(data);
            //对原文进行hash计算
            signature.update(hash);//原文hash
            return signature.verify(sign);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
        return false;
    }



    //===============生成密钥对==================
    /**
     *
     * @param keysize 密钥的长度
     * @return 返回生成的密钥对
     */
    public KeyPair createKey(int keysize){
        //instance:实例
        try {
            //密钥生成器
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(keysize);//密钥生成的长度
            KeyPair keyPair = generator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    //=======公钥加密,私钥解密=============
    /**
     * 使用rsa的公钥对数据进行加密
     * @param data 要加密的数据
     * @param pub 公钥
     */
    //=====================加密=====================
    public byte[] encrypt(byte[] data, PublicKey pub){
        //直接执行加密
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE,pub);
            return cipher.doFinal(data);//真正的加密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
    //====================解密===================
    public byte[] decrypt(byte[] cipherTxt, PrivateKey pri){
        //直接执行解密
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE,pri);
            return cipher.doFinal(cipherTxt);//真正的解密
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}

//静态与非静态的调用：static：java中的一个修饰符，static修饰属性，方法，类
//被static修饰的成员在内存中只存在一份是共享的
//static修饰的方法可以直接通过：类名.方法名进行调用
//非静态方法只能通过new的实例进行调用
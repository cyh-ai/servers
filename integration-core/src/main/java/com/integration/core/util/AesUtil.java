package com.integration.core.util;

import org.springframework.util.Assert;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * @author cyh
 * 加解密工具类
 */
public class AesUtil {

    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * AES CBC PKCS5Padding 加解密秘钥
     */
    public static final String DEFAULT_KEY = "0102030405060708";
    /**
     * AES/CBC/PKCS5Padding 加解密方式
     */
    private static final String ALGORITHM_CBC = "AES/CBC/PKCS5Padding";


    private static final String CHARACTER_CODING = "UTF-8";
    // 透明化密钥
    private static final String TOUMINGHUA_KEY = "sdalflkwerqdfsa1";


    /**
     * 加密
     *
     * @param str      需要加密的报文
     * @param fromBase 判断加密方式
     * @return 加密后的报文
     */
    public static String aesCbcPKCS5PaddingEncrypt(String str, boolean fromBase) {
        return aesCbcPKCS5PaddingEncrypt(str, DEFAULT_KEY, DEFAULT_KEY, fromBase);
    }

    /**
     * 解密
     *
     * @param str      需要解密的报文
     * @param fromBase 判断解密的方式
     * @return 解密后的报文
     */
    public static String aesCbcPKCS5PaddingDecrypt(String str, boolean fromBase) {
        return aesCbcPKCS5PaddingDecrypt(str, DEFAULT_KEY, DEFAULT_KEY, fromBase);
    }


    /**
     * 加密
     *
     * @param str      需要加密的报文
     * @param sKey     秘钥
     * @param iv       秘钥
     * @param formBase 判断加密的方式
     * @return 加密后的报文
     */
    public static String aesCbcPKCS5PaddingEncrypt(String str, String sKey, String iv, boolean formBase) {
        try {
            byte[] result = aesCbcPKCS5PaddingEncrypt(str.getBytes(), sKey.getBytes(), iv.getBytes());
            if (formBase) {
                return new String(Base64.getEncoder().encode(result));
            } else {
                return bytes2hex(result);
            }
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }


    /**
     * 解密
     *
     * @param str      需要解密的报文
     * @param sKey     秘钥
     * @param iv       秘钥
     * @param fromBase 判断解密的方式
     * @return 解密后的报文
     */
    public static String aesCbcPKCS5PaddingDecrypt(String str, String sKey, String iv, boolean fromBase) {
        try {
            byte[] source;
            if (fromBase) {
                source = Base64.getDecoder().decode(str.getBytes());
            } else {
                source = hex2bytes(str);
            }
            return new String(aesCbcPKCS5PaddingDecrypt(source, sKey.getBytes(), iv.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 解密出错", e);
        }
    }


    /**
     * 加密
     *
     * @param str  需要加密的报文
     * @param sKey 秘钥
     * @param iv   秘钥
     * @return 加密后的报文
     */
    public static byte[] aesCbcPKCS5PaddingEncrypt(byte[] str, byte[] sKey, byte[] iv) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM_CBC);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivParameterSpec);
            return cipher.doFinal(str);
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }

    /**
     * 解密
     *
     * @param str  需要解密的报文
     * @param sKey 秘钥
     * @param iv   秘钥
     * @return 解密后的报文
     */
    public static byte[] aesCbcPKCS5PaddingDecrypt(byte[] str, byte[] sKey, byte[] iv) {
        try {
            SecretKeySpec keySpec = new SecretKeySpec(sKey, "AES");
            Cipher cipher = Cipher.getInstance(ALGORITHM_CBC);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivParameterSpec);
            return cipher.doFinal(str);
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 解密出错", e);
        }
    }

    /**
     * 2进制转16进制
     *
     * @param data a byte[] to convert to Hex characters
     * @return A char[] containing hexadecimal characters
     */
    public static String bytes2hex(final byte[] data) {
        Assert.notNull(data, "data不能为null");
        final int l = data.length;
        final char[] out = new char[l << 1];
        for (int i = 0; i < l; i++) {
            out[i * 2] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[i * 2 + 1] = DIGITS_LOWER[0x0F & data[i]];
        }
        return new String(out);
    }

    /**
     * 16进制转2进制
     *
     * @param hexStr 需要解密的报文
     * @return 需要解密的报文
     */
    public static byte[] hex2bytes(String hexStr) {
        Assert.notNull(hexStr, "hexStr不能为空");
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++) {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println("----------测试加解密方法1----------");
        String jiami1 = aesCbcPKCS5PaddingEncrypt("123456789", false);
        System.out.println("加密方法 1 加密数据：" + jiami1);
        String jiemi1 = aesCbcPKCS5PaddingDecrypt(jiami1, false);
        System.out.println("解密方法 1 解密数据" + jiemi1);

        System.out.println("----------测试加解密方法2----------");

        //把key进行加密后再进行加解密 也可以直接使用指定key，不用加密
        SecretKeySpec cyh = getAesKey("cyh");
        System.out.println("加密的秘钥：" + cyh);
        //加密解密方法参数类型为：String byte boolean
        String jiami2 = encryptAESECB("987654321", cyh.getEncoded(), false);
        System.out.println("加密方法 2 加密数据：" + jiami2);
        String jiemi2 = decryptAESECB(jiami2, cyh.getEncoded(), false);
        System.out.println("解密方法 2 解密数据" + jiemi2);

        System.out.println("----------测试加解密方法3----------");

        //不加密的key要满足16位
        String c = "asdfghjklzxcvbnm";
        //加密解密方法参数类型为：String String boolean
        String jiami3 = encryptAESECB("111222333", c, false);
        System.out.println("加密方法 3 加密数据：" + jiami3);
        String jiemi3 = decryptAESECB(jiami3, c, false);
        System.out.println("解密方法 3 解密数据" + jiemi3);

        System.out.println("----------方法测试解密----------");
        String name = "381a5262adfc671774bced1d1abb5b89a3bd3ae2b03ab4ef33e0d36bf00839759c86b1475f1fd5bb68e239df9d3f942b84deffab0ebe3f7700527de694489bcbf24d5a832a0ad4eb8364ca265726caad7c882481a2d046d20f3a1278da0d29fb9ba8288d6cfa80c4938b4ddcd2cf0bf1967ff82322035d396915784077bea18f936f427151fe109f2b45a061f7aa499acc7db375b7fa69cc94b7d0b32c6f48af1ff94c5a235cb4c5e9d5be7ad6de8572d79593fbacaa47c9f660c3dc3eb3f6c7b30783b5644502270fd690c536b2908b5a6f850fdcf190e3dc3914f280fa7047fd5eab89d7a3bee59b748d9dcc827b93ad28641682749c11155851ac3eb9f7c289d1099464e9a95763fa59567fe99006f674012c4fec7f59b73d382dedca922fc32148736371d84dae9a6707c896f498d4d5fe503738a3f8808653bc4bd8cc5bbfe1b11b59f57ac975706929b790b30d67a3ff6fa73dc972299c65e9b134143536cc212be2c4bb592209339419aac2308ceea55275a0e367a587932d00582715a00ddf9cd312f9785a6ef6363c48fa11d34dd63a8704720b7ff869b40f903b7fed8093210d302727c87c75258460806cdb1e8b7f5af8fdd6ce113e6656684874dd1068c99d10acaae1d9c30f9ed94482ff692d1fb6f3bd04927ddc982908b701057b3a28fc6f7826ba8eae71a445cc275580f01a0ac57996d81c23cb39f13167518a8f10f5022b097523593e3ccdc041567393767fad027c9fe66e78dc772dcf75d1a8dd47158fefb3f8f296c42e0e5c68700e2428cb9b27c57ab5fcf486fe71995f12ee5c75bd9646548f80b32f6328ec474af3add3c53b0ebb8513d6c30f457892d885cb905b7e35e5c0029fb9ddfec664b6fdc7bca75b63c26ac733d9d76467f16d99b07d3d9ae57b532715f836b24eeb85b58efdc7ff3b72822211b4bf85475547992cf93fcacb5c67cf993b0b36dc5c94fcb59f9fec8304d2cc9e2ec06ea798d10e8324f582b8978e1283c573c4df75cd9c7d724757a1206536205e8b286e97c4ee0706ca154cdd4e2c26e7e07afe6d7dfbc9f7196595aa99f2c4f3bb750845ff55f989c623078302b6e53a7d9ce6b2aed7de7b67ffdc19e53e794f09d13859c4b68d2cec8161e6e561f3c6cc5a312e98b1e232b64188e544a1bd57f6a1e02650c17454d187d337acc5219283d3f872d2026772b163f884a0dc23aa9ee38d62eef72ec5d5aa32593f4854ad929beb094515b3c687bf0c50144395e2f089c296a4d361f296612db497c4e71a1740a21c3b006569fccaf3f716e3c4737954104f4a52a363c315e0536794ab0d0cc914dc8afadf8a6e88dd97a7955ba903a18c391bc5e3fbee006ec3342825a85880c8f5fe6136dcee3417d11b09665633ca671912b290cfa089cf32e3a454ea125086703eff91d3dfb5b5abccb711c05e119e88b8b634aae0388001bb13b0e5bcd3ad25f9e4e65266cc7bd08e30a17feda89b9ab9390e68072438795249f3b5c8169172a98f6cb481b207203b737943199694eed45a85a1d8b9e426cbd753a01db4b22808ef571051f32e9121618eae462afa0c109b9555c9191d9b49e100f898d4c65964dc9277cee48d6160977f9b500294f3a42dad9f2f29998b99e76bbeed085e8fb3e0082d35b3cc292ea2c804bb0600f0ea9992a28f99c718f73c2665637aefa0c14a5d8d073493ae876528387a7b0604b2163f5f3e7b1e38c0d9beb6e5b03662197eafd718154af686056ff73efaba39260bbdc66afe1ecdd338bb8e6648ef8ef78ca6ba6f473153bd49eac2a1d956d64646b03501e798f4d46114c5b49ed3fb7b31849b3c4ea9376e5175940a85b75709a86d5aaf9f6672214ac4fa3604e015221e0c921b52eb98850a5841f4293b9faf86e376e4de40572f3636fd95f76b762698a1109c4130ce86154692290baa2291776d2d9dd1518e2a62f8601bfc7605c6e1cbb2d23eb55b48615b961b348e61005deb9600784a9a45e60021e95bf90622d12b400af5ff60518e24d04689fc732b35e75391adbc670949465b728a3ce97f874a2fea0e3484c30b9f75191a1ac82f953a70d1c7bb62018730a1dd2739ccb87e6e53740e8e863b6e9f12f16473048e037890ba09fd3f5d37679f2beee830dd7f664f17f587184ed2051230f45dcb50672ec513170f73b62ddfbf9154aae0c18a6c8c24fe2d0cdf42a1cbac2f0e126c4cd175dad2920401cd94d1cd4b658a03b0401b94cfd67c063ede23cc4289d9421d720eef1f372c5e83caad3b282ba26d29868ac547cff81e161c91c35cbcaf5872f7d877eb3f1f777453a9cd4a1b39f82279f161c41d95945b38703db813db7388029e630f762c2f3bb1d7de2d12cb6539854a0a4f190f96d54ec070328ac508e569c4594a587911e0467ed097fa0cbad37fb834f20a4ca4345a30d584dec2b6ce17c8c71ad254554df570acfdc0d3083112654bca1a475426121537cfe4d7698ff231d3b5318a2706507ad7adc381139c1ef17beba6614a689c65746ef8dd7deb2b399254f5143da8421179b8e9a74b08101442950bb3360cb77864bb939946eedf719e5f35155c0c9b8ea225dd0a92b6c7572d346f034ea431b6c9dcec18f0352883026359d5d81fc6dd7ea2cf906d81aceae8886db721b300fe6a52777437668d3645ea266f3d24cd7e7ff5ebcf979cafddb7c54b19bf23066da58b51f4babf11f5f1301c864d0e59cd31c8eff61d7b007e725ef3a4c295db9263024b86952db2024be903d7a9a8e96c2647e1a501eb90b1a9d04e64ae3bedec4945230b5e72d7b7fd249b049dd96fabbd9c775edf10d3081ed1ee79105a076fcc87b0e584f368f337a6293d2f8aac4d6dc190756b9e3ac75501524967915f78c3ae2cb9ccdc0faf6fd0a05c9c46fdb85285d8acdda8090bef5ab57bbdd5dec3b381834bc390f4e2e7f064741f38720f337b4cf816ff21de1ddc4f5b3151eb640cc904c79bdd0a47592a60a5503ba7bbe67115c15bfa90e0ed71b58dbcd035c702fd1aec212b9b4fd14ec776e488ffb1096091d04166d363841d36f4e62ab9b7ec05ebf0c9e3fa70e434f148e6ea334594a5aead46901c90f70275ad607debe02b761fc62896586ed09cc9c4e6bf6ee48f3da29af329187697fe25ca925a512cd2493355f0ddbbc730131f8e449ddf1b2259f7ef32cd792cd07700c586dad8cf92666696dc256ba221a066a927605d733bc64dcc39d9deb8c50b006bb46fe13a29d88cb6313252d398bace05b29bc475d418a55c9e3c34201cc878269e9e0bb3684682b8f65794d67aa162406b6c5c0b9611716e42d1a90f995a80f9bd74bdc338c2f44631b24a0b02ca33f5855751d382ca43161efde30ca4c519b21973fd8a0ece4f150188332dd384e238566a48c903b7bd9aaf3206e9ea7e29ce8e985e6a1af4ffa2d262f2b1795ca3da08e59355642e2bb890ee2d59a3edd5a856beb2ac810f8b6ed6c51c3a9dd0767fe241b213ddc84629579234f4f5e512479adfa3e756dcadaab6638def01b624f2a3117067834bc2481413e4be0f4870fded4fa5d1e24ae543730dc2d12f05afd216a89cc953c501d0cb8677b00520dec57a79791222b9a4e7d5020806d895462752a3128d9d93c54f875f4b298e0cf89019cd46c3e64286c5920d2eeab1ff6a724e182dfc7ae81c19abad49db8fdbb586c1a326190c9fdb2c2100bcf6b5b6df8644510233d599ac9feffced38e5d1ac79e87329a556991d857f84b89f6792d0689dd835eb1d7d85810402459ee05f3eb7597cb451214c3ae27b090421d8489d9b6164f169e1c42bc7196f415ee652f08a2e643ca3663d2ea74fcd8811847c859a2ed81a698c059b10675722eef834be6ec8c780beccbad10ca1652adfaa0a8b6c0f6ecd33850114804a7477124ed6a43d6e16d5e1efdd553395cde5700340567ce3c842e690d9081e7dbbda0303a367cc7f518c7469ec265f87884ba3ab32edffd6ef1d18cd93ced661fdeba25694dad5898a0065f8f63e743a47200ee646c051d149d76be8e73eb5704d1b8bf3c214ba11a8ff3d19fbee2ee7160324634430f8761989d58300e433a2569bb6e588e4da101fff471d480f8f692710753b50ff78ffc76e89d3d7a5747828f9eb230993557f7ddbb108902236d7cb51b9c32306f4c8a920449414f6d1740a78841e45998fcfc36c5d62030c47671cc0baba791d6a6142cfabecc2277f3fd638793777bcfde0978837e8d88a5631f55a1cd9863b85fd87f82dc9e2853216f69b108ea9fea0d3dc6157279fddeaffd0c997ea0151d0cb59191bac4c3fe779b04be37b04cec732404e02699ff3cac107ec92e50038de692ebeb27645aaaff2490d014ec842c84f9810df2c043b8d6c87dbebd9bed602702eb38cf0f16bd3c2a356b6c142ec9ec7888649835fbc05c33233b25304a24a213b32c13a7d60cde5326708df3f4638a6436f0429285ccd1984e7545ce24f80a83cb38d773a6612d49936ecbf71929ef60c12d6827da0ed994ee616686eba26832aeb3eb63745f239bf1d259b4542b5c765c44b6b1dfbaa7d8f56ef984e3178c925307e15b4219ace4a0eed7b8fc1347a6b310d9378bb964ff9293a6b791fcf20d55e99e1bba2b34c909a0642f10abbec094dedb775527457ae2ac05990492abe1299002d8f243f0d871b657d1427f04e09dce9c4d12d873addd33cb2572e536fc0244558516b2fc733d0f8fea36b98b8485387d058185c6f525b979dbfbb9aefa5132154607fe1051a0bd620d30f74900cb0464560d5a551a2cb3e8445e01979de847f76a72b023a5c2bc608c5614c75f61ab4d554b1d0fcc6ec4d308961706fde8770092da02c56ffbb9a1b5ed01258f4bf573f0b4fbb377b4e4ce2a81bca97cf4af283fea1094c608672a8b3149c0acc529296364afc800124819baf90792cc3ef5d8a5f2de5f05e4f684f9342ef13d34a6d329a84b4eed5b1835708702f2c61a73fa821133301301a2ca78fa0592ae436653736d381f7de44b5fec4cb02ec5fcbab7e43562dd2d1e1f36eba149e5026e570acc8a119bce34ba48c06c79bec2ab295da197b35130ece2086a1d82ece17b3c50301860f937a46f08c9411c42410546e32f6822982f2e46d93a5d2e0b022580c3cc4e501b52d7a88a95cf95e8f32f07ab59971fcf91eb03394542c0d56833d5e6da56908d806a0e6b8e8b8939462da697de5eb9f257c2c1b90ced1a8c7d57381701f2e1f6bd5c9cee73739a0d8aa6501331a082916d5cf0d1af196c3227638dd72485d0add0a9f6e18d252771255ce0e0470ffba2edd0b6d717b61bfb9ccab093e8a1af8e6bf8d6b90ba3b6cf634bfa3933b3aa61d47b510a13f1eeca6c05d2422cfa1f68ff4e3ba3eb95c2f36ef1936d78feb090da4bfc1283604f29e012073edfc166455c1f103e275290168156b98c63fcc176a20e6fc7cda2b0f9b02f0716a8d3f5909b89efe47c94af5b4e705307d5c1d713bbfc450912951f15b79f01e28300b51fc52c45a8d9168eacfbd06d29904644c44956a03069cc2796eacbdaab4fb8e0edf194a93a2724743c91d07de773918a380b47e74f53a1263119d4aef0edcb3e4d0febec0aab3ee2bd2e30159d6879dafab29e463766faf46d93a19dc91e37ba3b822e3416f4372cdd441d4b73ffd342d534956f61d397660ee196331f9fe2c3a97561af629060711544cc37353ad0969a1c4079b2a50c7a75eab2e47b77c6defad4fa922a9facc03f46edb0ceda49e1156f9de6a25310178f0532efd389ad709f1bb6a1e7e87a07dc1ee75ba6a0bc73721c3f35fbdc679ab36944b80fe57fe4e11771c13096d371f305270908e9efedd9a343f606199ceadaf67cac633d1e208c99ff608c69185bd3a2a4364ec702889ab62b9cb638cb43729770c166596df1928d6d370455d5ed9a637390e0d2e7bc621426987697125b854ce978e1433";
        String jiemi = aesCbcPKCS5PaddingDecrypt(name, false);
        System.out.println("解密方法对应解密的数据:" + jiemi);
//        List list = JsonUtil.toObject(jiemi, List.class);
//        System.out.println(list);
    }


    /**
     * 生成加密秘钥
     *
     * @param key 需要加密的key
     * @return 加密的key
     */
    public static SecretKeySpec getAesKey(final String key) {
        // 返回生成指定算法密钥生成器的 KeyGenerator 对象
        KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance("AES");
            // AES 要求密钥长度为 128
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
            secureRandom.setSeed(key.getBytes());
            kg.init(128, secureRandom);
            // 生成一个密钥
            SecretKey secretKey = kg.generateKey();
            return new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }


    /**
     * AES ECB加密
     *
     * @param str      需要加密的报文
     * @param key      加密需要的key
     * @param fromBase 加密后是否做base64转换
     * @return 加密的报文
     */
    public static String encryptAESECB(String str, byte[] key, boolean fromBase) {
        try {
            byte[] result = encryptAESECB(str.getBytes(), key);
            if (fromBase) {
                return new String(Base64.getEncoder().encode(result));
            } else {
                return bytes2hex(result);
            }
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }

    /**
     * AES ECB加密
     *
     * @param str      需要解密的报文
     * @param key      解密需要的key
     * @param fromBase 解密后是否做base64转换
     * @return 解密的报文
     */
    public static String decryptAESECB(String str, byte[] key, boolean fromBase) {
        byte[] src = (fromBase ? Base64.getDecoder().decode(str) : hex2bytes(str));
        return new String(decryptAESECB(src, key));
    }


    /**
     * AES ECB加密
     *
     * @param str      需要加密的报文
     * @param key      加密需要的key
     * @param fromBase 加密后是否做base64转换
     * @return 加密的报文
     */
    public static String encryptAESECB(String str, String key, boolean fromBase) {
        try {
            byte[] result = encryptAESECB(str.getBytes(), key.getBytes());
            if (fromBase) {
                return new String(Base64.getEncoder().encode(result));
            } else {
                return bytes2hex(result);
            }
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }

    /**
     * AES ECB加密
     *
     * @param str      需要解密的报文
     * @param key      解密需要的key
     * @param fromBase 解密后是否做base64转换
     * @return 解密的报文
     */
    public static String decryptAESECB(String str, String key, boolean fromBase) {
        byte[] src = (fromBase ? Base64.getDecoder().decode(str) : hex2bytes(str));
        return new String(decryptAESECB(src, key.getBytes()));
    }


    /**
     * AES加密 128 AES/ECB/PKCS5Padding
     *
     * @param source 需要加密的字符串
     * @param key    需要加密的秘钥
     * @return 返回加密结果
     */
    public static byte[] encryptAESECB(byte[] source, byte[] key) {
        Assert.isTrue(null != key && key.length == 16, "key的长度需要16");
        try {
            return encrypt(source, key);
        } catch (Exception e) {
            throw new RuntimeException("AES ECB 加密出错", e);
        }
    }

    public static byte[] decryptAESECB(byte[] source, byte[] key) {
        Assert.isTrue(null != key && key.length == 16, "key的长度需要16");
        try {
            return decrypt(source, key);
        } catch (Exception e) {
            throw new RuntimeException("AES CBC 加密出错", e);
        }
    }


    /**
     * 太好店对接透明化加密
     *
     * @param str 需要加密的报文
     * @return 加密的报文
     */
    public static String encodingByAES(String str) {
        try {
            byte[] raw = TOUMINGHUA_KEY.getBytes(CHARACTER_CODING);
            byte[] encrypt = encrypt(str, raw);
            return Base64.getEncoder().encodeToString(encrypt);
        } catch (Exception e) {
            throw new RuntimeException("加密报错：", e);
        }
    }


    public static byte[] encrypt(String data, byte[] key) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        return encrypt(data.getBytes(), key, "AES/ECB/PKCS5Padding");
    }

    public static byte[] encrypt(byte[] data, byte[] key) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        return encrypt(data, key, "AES/ECB/PKCS5Padding");
    }

    public static byte[] decrypt(byte[] data, byte[] key) throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, NoSuchPaddingException {
        return decrypt(data, key, "AES/ECB/PKCS5Padding");
    }


    public static Key toKey(byte[] key) {
        return new SecretKeySpec(key, "AES");
    }

    public static byte[] encrypt(byte[] data, byte[] key, String cipherAlgorithm) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Key k = toKey(key);
        return encrypt(data, k, cipherAlgorithm);
    }

    public static byte[] decrypt(byte[] data, byte[] key, String cipherAlgorithm) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        Key k = toKey(key);
        return decrypt(data, k, cipherAlgorithm);
    }


    public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(1, key);
        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance(cipherAlgorithm);
        cipher.init(2, key);
        return cipher.doFinal(data);
    }

}

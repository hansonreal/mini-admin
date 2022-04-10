package com.github.mini.common.properties;

import com.github.mini.common.util.RsaUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;

@Data
@Slf4j
@ConfigurationProperties(prefix = "mini.rsa.key")
public class RsaKeyProperties {
    private String pubKeyFilePath;
    private String priKeyFilePath;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void initRsaKey() throws Exception {
        log.info("pubKeyFile:{}", pubKeyFilePath);
        log.info("priKeyFile:{}", priKeyFilePath);
        ClassPathResource pubKeyFileResource = new ClassPathResource(pubKeyFilePath);
        InputStream pubKeyFileInputStream = pubKeyFileResource.getInputStream();
        publicKey = RsaUtil.getPublicKey(pubKeyFileInputStream);
        ClassPathResource priKeyFileResource = new ClassPathResource(priKeyFilePath);
        InputStream priKeyFileInputStream = priKeyFileResource.getInputStream();
        privateKey = RsaUtil.getPrivateKey(priKeyFileInputStream);

    }
}

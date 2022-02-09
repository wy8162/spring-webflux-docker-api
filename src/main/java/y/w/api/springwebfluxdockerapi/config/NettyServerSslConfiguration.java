package y.w.api.springwebfluxdockerapi.config;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.Ssl;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NettyServerSslConfiguration {
    /**
     * We will not use the following because we use the server configuration in application.yml
     * to do the same.
     *
     * But this is also an option to configure the SSL programatically.
     *
    @Bean
    public WebServerFactoryCustomizer<NettyReactiveWebServerFactory> customizer() {
        return new WebServerFactoryCustomizer<NettyReactiveWebServerFactory>() {
            @Override
            public void customize(NettyReactiveWebServerFactory factory) {
                Ssl ssl = new Ssl();
                ssl.setEnabled(true);
                ssl.setKeyAlias("myAlias");
                ssl.setKeyStore("path to keystore");
                ssl.setKeyStoreType("jks");
                ssl.setKeyStorePassword("password");
                ssl.setTrustStore("path to trust store");
                ssl.setTrustStorePassword("password");
                factory.setSsl(ssl);
                factory.setPort(8443);
            }
        };
    }
    */
}

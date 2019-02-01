package io.enmasse.auth.service.embedded;

import io.enmasse.user.api.UserApi;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.net.NetSocket;
import io.vertx.proton.ProtonConnection;
import io.vertx.proton.ProtonServer;
import io.vertx.proton.sasl.ProtonSaslAuthenticator;
import io.vertx.proton.sasl.ProtonSaslAuthenticatorFactory;
import org.apache.qpid.proton.engine.Transport;

public class EmbeddedAuthService {

    public static void main(String [] args) {
        Vertx vertx = Vertx.vertx();

        ProtonServer server = ProtonServer.create(vertx);

        UserApi userApi;

        server.saslAuthenticatorFactory(new ProtonSaslAuthenticatorFactory() {
            @Override
            public ProtonSaslAuthenticator create() {
                return new ProtonSaslAuthenticator() {
                    @Override
                    public void init(NetSocket netSocket, ProtonConnection protonConnection, Transport transport) {

                    }

                    @Override
                    public void process(Handler<Boolean> handler) {

                    }

                    @Override
                    public boolean succeeded() {
                        return false;
                    }
                }
            }
        })
        server.listen(5671)
    }
}

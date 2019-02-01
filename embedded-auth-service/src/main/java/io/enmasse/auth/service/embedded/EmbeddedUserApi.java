package io.enmasse.auth.service.embedded;

import io.enmasse.user.api.UserApi;
import io.enmasse.user.model.v1.User;
import io.enmasse.user.model.v1.UserList;
import io.fabric8.kubernetes.api.model.Secret;
import io.fabric8.openshift.client.OpenShiftClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EmbeddedUserApi implements UserApi {
    private final OpenShiftClient client;

    public EmbeddedUserApi(OpenShiftClient client) {
        this.client = client;
    }

    @Override
    public Optional<User> getUserWithName(String realm, String name) throws Exception {
        Secret secret = client.secrets().withName(userSecretName(realm, name)).get();
        if (secret == null) {
            return Optional.empty();
        }

        User user = buildUserFromSecretData(secret.getStringData());
    }

    public interface Fields {
    }
    private User buildUserFromSecretData(Map<String, String> data) {

    }

    public Map<String, String> buildSecretDataFromUser(User user) {
        Map<String, String> userProperties = new HashMap<>();
        userProperties.put()
    }

    private static String userSecretName(String realm, String name) {
        return "userapi." + realm + "." + name;
    }

    @Override
    public void createUser(String realm, User user) throws Exception {
    }

    @Override
    public boolean replaceUser(String realm, User user) throws Exception {
        return false;
    }

    @Override
    public void deleteUser(String realm, User user) throws Exception {

    }

    @Override
    public boolean realmExists(String realm) {
        return false;
    }

    @Override
    public UserList listUsers(String namespace) {
        return null;
    }

    @Override
    public UserList listUsersWithLabels(String namespace, Map<String, String> labels) {
        return null;
    }

    @Override
    public UserList listAllUsers() {
        return null;
    }

    @Override
    public UserList listAllUsersWithLabels(Map<String, String> labels) {
        return null;
    }

    @Override
    public void deleteUsers(String namespace) {

    }
}

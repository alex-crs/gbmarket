package com.gb.market.auth.services;

import com.gb.market.api.dtos.RegistrationUserDto;
import com.gb.market.auth.configs.KeyCloakConfig;
import com.gb.market.auth.entities.Credentials;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;
import java.util.Collections;

import static com.gb.market.auth.configs.KeyCloakConfig.realm;
import static com.gb.market.auth.entities.Credentials.createPasswordCredentials;

@Service
public class KeyCloakService {
    private final KeyCloakConfig kcProvider;

    public KeyCloakService(KeyCloakConfig kcProvider) {
        this.kcProvider = kcProvider;
    }

    public Response addUser(RegistrationUserDto userDTO){
        UsersResource usersResource = kcProvider.getInstance().realm(realm).users();
        CredentialRepresentation credentialRepresentation = createPasswordCredentials(userDTO.getPassword());

        UserRepresentation kcUser = new UserRepresentation();
        kcUser.setUsername(userDTO.getEmail());
        kcUser.setCredentials(Collections.singletonList(credentialRepresentation));
        kcUser.setFirstName(userDTO.getFirstName());
        kcUser.setLastName(userDTO.getLastName());
        kcUser.setEmail(userDTO.getEmail());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(false);

        Response response = usersResource.create(kcUser);
        return response;
    }
}

package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private CredentialMapper credentialMapper;

    private EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential> getAllCredentials() {

        List<Credential> credentials = credentialMapper.getAllCredentials();
        for (Credential credential: credentials) {
            credential.setOriginalpassword(encryptionService.decryptValue(credential.getPassword(), credential.getKey_()));
        }

        return credentials;
    }

    public void addCredential(Credential credential) {
        credential.setKey_("1234567890123456");
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey_()));
        credentialMapper.addCredential(credential);
    }

    public void deleteCredential(Integer credentialid) {
        credentialMapper.deleteCredential(credentialid);
    }

    public void updateCredential(Credential credential) {
        credential.setKey_("1234567890123456");
        credential.setPassword(encryptionService.encryptValue(credential.getPassword(), credential.getKey_()));
        credentialMapper.updateCredential(credential);
    }

}

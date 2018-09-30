package ru.otus.springframework.hw21.service;

import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.acls.domain.BasePermission.ADMINISTRATION;
import static org.springframework.security.acls.domain.BasePermission.DELETE;
import static org.springframework.security.acls.domain.BasePermission.READ;
import static org.springframework.security.acls.domain.BasePermission.WRITE;

@Service
public class AuthorizeService {
    private final Sid adminAuthority = new GrantedAuthoritySid("ROLE_ADMIN");

    private final MutableAclService aclService;

    public AuthorizeService(MutableAclService aclService) {
        this.aclService = aclService;
    }

    @Transactional
    public void createAcl(Object entity, Authentication ownerAuthentication) {
        final Sid owner = new PrincipalSid(ownerAuthentication);
        final ObjectIdentity oid = new ObjectIdentityImpl(entity);
        final MutableAcl acl = aclService.createAcl(oid);
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), READ, owner, true);
        acl.insertAce(acl.getEntries().size(), WRITE, owner, true);
        acl.insertAce(acl.getEntries().size(), DELETE, owner, true);
        acl.insertAce(acl.getEntries().size(), READ, adminAuthority, true);
        acl.insertAce(acl.getEntries().size(), WRITE, adminAuthority, true);
        acl.insertAce(acl.getEntries().size(), DELETE, adminAuthority, true);
        acl.insertAce(acl.getEntries().size(), ADMINISTRATION, adminAuthority, true);
        aclService.updateAcl(acl);
    }
}

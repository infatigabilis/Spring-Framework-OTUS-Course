package ru.otus.springframework.hw31.security.acl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.security.acls.domain.AclAuthorizationStrategy;
import org.springframework.security.acls.domain.ConsoleAuditLogger;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw31.exception.ServiceUnavailableException;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

/*
 * use composition instead of inheritance, because all
 * BasicLookupStrategy methods are final...
 */
@Service
public class HystrixBasicLookupStrategy implements LookupStrategy {
    private final BasicLookupStrategy basicLookupStrategy;

    public HystrixBasicLookupStrategy(DataSource dataSource, AclCache aclCache, AclAuthorizationStrategy aclAuthorizationStrategy) {
        this.basicLookupStrategy = new BasicLookupStrategy(dataSource, aclCache, aclAuthorizationStrategy, new ConsoleAuditLogger());
        basicLookupStrategy.setLookupObjectIdentitiesWhereClause("(acl_object_identity.object_id_identity::text = ? and acl_class.class = ?)");
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public Map<ObjectIdentity, Acl> readAclsById(List<ObjectIdentity> objects, List<Sid> sids) {
        return basicLookupStrategy.readAclsById(objects, sids);
    }

    private Map<ObjectIdentity, Acl> fallback(List<ObjectIdentity> objects, List<Sid> sids, Throwable cause) {
        throw new ServiceUnavailableException("Acl DB", cause);
    }
}

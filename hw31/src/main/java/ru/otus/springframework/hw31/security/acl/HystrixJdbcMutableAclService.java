package ru.otus.springframework.hw31.security.acl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclCache;
import org.springframework.security.acls.model.AlreadyExistsException;
import org.springframework.security.acls.model.ChildrenExistException;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.stereotype.Service;
import ru.otus.springframework.hw31.exception.ServiceUnavailableException;

import javax.sql.DataSource;

@Service
public class HystrixJdbcMutableAclService extends JdbcMutableAclService {
    public HystrixJdbcMutableAclService(DataSource dataSource, LookupStrategy lookupStrategy, AclCache aclCache) {
        super(dataSource, lookupStrategy, aclCache);
        setSidIdentityQuery("SELECT currval('acl_sid_id_seq')");
        setClassIdentityQuery("SELECT currval('acl_class_id_seq')");
    }

    @Override
    @HystrixCommand(fallbackMethod = "createAclFallback")
    public MutableAcl createAcl(ObjectIdentity objectIdentity) throws AlreadyExistsException {
        return super.createAcl(objectIdentity);
    }

    @Override
    @HystrixCommand(fallbackMethod = "deleteAclFallback")
    public void deleteAcl(ObjectIdentity objectIdentity, boolean deleteChildren) throws ChildrenExistException {
        super.deleteAcl(objectIdentity, deleteChildren);
    }

    @Override
    @HystrixCommand(fallbackMethod = "updateAclFallback")
    public MutableAcl updateAcl(MutableAcl acl) throws NotFoundException {
        return super.updateAcl(acl);
    }


    public MutableAcl createAclFallback(ObjectIdentity objectIdentity, Throwable cause) {
        throw new ServiceUnavailableException("Acl DB", cause);
    }

    public void deleteAclFallback(ObjectIdentity objectIdentity, boolean deleteChildren, Throwable cause) {
        throw new ServiceUnavailableException("Acl DB", cause);
    }

    public MutableAcl updateAclFallback(MutableAcl acl, Throwable cause) {
        throw new ServiceUnavailableException("Acl DB", cause);
    }
}

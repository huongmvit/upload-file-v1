package com.upload.api.v1.ecm_auth.repo;

import com.upload.api.v1.ecm_auth.entity.EcmAuth;
import java.lang.Long;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcmAuthRepos extends JpaRepository<EcmAuth, Long> {
}

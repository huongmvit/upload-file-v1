package com.upload.api.v1.ecm_folder.repo;

import com.upload.api.v1.ecm_folder.entity.EcmFolder;
import java.lang.Long;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EcmFolderRepos extends JpaRepository<EcmFolder, Long> {
}

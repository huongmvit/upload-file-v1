package com.upload.api.v1.ecm_folder.entity;

import com.upload.api.v1.enums.FolderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(
        name = EcmFolder.TABLE_NAME
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EcmFolder implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String TABLE_NAME = "ecm_folder";

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    @Column(
            name = "id"
    )
    private Long id;

    @Column(
            name = "business_type"
    )
    private String businessType;

    @Column(
            name = "folder_path"
    )
    private String folderPath;

    @Column(
            name = "status"
    )
    @Enumerated(EnumType.STRING)
    private FolderStatus status;

    @Column(
            name = "created_by"
    )
    private String createdBy;

    @Column(
            name = "created_at"
    )
    private Instant createdAt;

    @Column(
            name = "updated_at"
    )
    private Instant updatedAt;

    @Column(
            name = "folder_path_sub"
    )
    private String folderPathSub;

    @Column(
            name = "is_exist"
    )
    private Integer isExist;

    @Column(
            name = "is_del"
    )
    private Integer isDel;

    @Column(
            name = "tenant_id"
    )
    private Long tenantId;

    @Column(
            name = "store_id"
    )
    private Long storeId;

    @Column(
            name = "brand_id"
    )
    private Long brandId;
}

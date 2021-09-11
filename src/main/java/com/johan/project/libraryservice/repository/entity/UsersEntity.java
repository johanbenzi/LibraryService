package com.johan.project.libraryservice.repository.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "USERS")
@Entity(name = "USERS")
@EntityListeners(AuditingEntityListener.class)
public class UsersEntity {

    @Id
    @Column(name = "USER_ID", unique = true, nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.EXTRA)
    private Set<BooksEntity> books;

    @CreatedDate
    @Column(name = "CREATED_DATE_TIME", nullable = false, updatable = false)
    private Timestamp createdDateTime;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE_TIME", nullable = false)
    private Timestamp lastModifiedDateTime;
}

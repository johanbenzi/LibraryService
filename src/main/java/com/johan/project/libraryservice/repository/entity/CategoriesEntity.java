package com.johan.project.libraryservice.repository.entity;

import lombok.*;
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
@Table(name = "CATEGORIES")
@Entity(name = "CATEGORIES")
@EntityListeners(AuditingEntityListener.class)
public class CategoriesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CategoriesSeq")
    @SequenceGenerator(name = "CategoriesSeq", sequenceName = "CATEGORIES_SEQ", allocationSize = 1)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<BooksEntity> books;

    @CreatedDate
    @Column(name = "CREATED_DATE_TIME", nullable = false, updatable = false)
    private Timestamp createdDateTime;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE_TIME", nullable = false)
    private Timestamp lastModifiedDateTime;
}

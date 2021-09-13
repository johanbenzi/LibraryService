package com.johan.project.libraryservice.repository.entity;

import com.johan.project.libraryservice.model.response.BookResponse;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "BOOKS")
@Entity(name = "BOOKS")
@Setter
@EntityListeners(AuditingEntityListener.class)
public class BooksEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BooksSeq")
    @SequenceGenerator(name = "BooksSeq", sequenceName = "BOOKS_SEQ", allocationSize = 1)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @Column(name = "IS_LOANED", nullable = false)
    private boolean isLoaned;

    @ManyToMany(cascade = {CascadeType.MERGE})
    @JoinTable(name = "categories_books",
            joinColumns = @JoinColumn(name = "books_id"),
            inverseJoinColumns = @JoinColumn(name = "categories_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Set<CategoriesEntity> categories;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private UsersEntity user;

    @CreatedDate
    @Column(name = "CREATED_DATE_TIME", nullable = false, updatable = false)
    private Timestamp createdDateTime;

    @LastModifiedDate
    @Column(name = "LAST_MODIFIED_DATE_TIME", nullable = false)
    private Timestamp lastModifiedDateTime;

    public BookResponse toResponse() {
        return BookResponse.of(id, title, author, categories.stream().map(CategoriesEntity::getCategory).collect(Collectors.toSet()));
    }
}

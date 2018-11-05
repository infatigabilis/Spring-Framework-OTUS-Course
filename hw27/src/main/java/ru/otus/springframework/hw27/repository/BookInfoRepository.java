package ru.otus.springframework.hw27.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.otus.springframework.hw27.domain.BookInfo;

@RepositoryRestResource(path = "book")
public interface BookInfoRepository extends JpaRepository<BookInfo, Long> {
}

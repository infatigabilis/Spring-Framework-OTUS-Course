package ru.otus.springframework.hw15.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.springframework.hw15.domain.BookInfo;

@Repository
public interface BookInfoRepository extends JpaRepository<BookInfo, Long>, BookInfoRepositoryCustom {
}

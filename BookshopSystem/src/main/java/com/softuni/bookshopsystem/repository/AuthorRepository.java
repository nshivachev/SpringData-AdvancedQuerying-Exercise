package com.softuni.bookshopsystem.repository;


import com.softuni.bookshopsystem.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

//    @Query("SELECT a FROM Author a ORDER BY a.books.size DESC")
//    List<Author> findAllByBooksSizeDESC();

    List<Author> findAllByFirstNameEndsWithIgnoreCase(String firstName);
}

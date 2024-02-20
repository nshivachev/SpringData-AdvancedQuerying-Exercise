package com.softuni.bookshopsystem.repository;

import com.softuni.bookshopsystem.model.dto.BookInformation;
import com.softuni.bookshopsystem.model.entity.AgeRestriction;
import com.softuni.bookshopsystem.model.entity.Book;
import com.softuni.bookshopsystem.model.entity.EditionType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, int copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal priceLessThan, BigDecimal priceGreaterThan);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate startDate, LocalDate endDate);

    List<Book> findAllByTitleContainsIgnoreCase(String text);

    List<Book> findAllByAuthorLastNameStartsWith(String prefix);

    @Query("select count(b) from Book b where length(b.title) > :length")
    Integer findCountByTitleGreaterThan(int length);

    List<Book> findAllByAuthorFirstNameAndAuthorLastName(String firstName, String lastName);

    @Query("select new com.softuni.bookshopsystem.model.dto.BookInformation(b.title, b.editionType, b.ageRestriction, b.price) " +
            "from Book b where b.title = :title")
    Optional<BookInformation> findByTitle(String title);

    @Modifying
    @Transactional
    @Query("update Book b set b.copies = b.copies + :increasedCopies where b.releaseDate > :dateAfter")
    int increaseCopiesWithReleaseDateAfter(LocalDate dateAfter, int increasedCopies);

    @Transactional
    int deleteByCopiesLessThan(int copies);

    @Procedure(procedureName = "usp_get_books_count_by_author_name")
    int getCountByAuthorName(String firstName, String lastName);
}

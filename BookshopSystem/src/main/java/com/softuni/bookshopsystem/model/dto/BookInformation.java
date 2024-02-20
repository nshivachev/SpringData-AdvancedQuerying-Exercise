package com.softuni.bookshopsystem.model.dto;

import com.softuni.bookshopsystem.model.entity.AgeRestriction;
import com.softuni.bookshopsystem.model.entity.EditionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BookInformation {
    private String title;
    private EditionType editionType;
    private AgeRestriction ageRestriction;
    private BigDecimal price;

    @Override
    public String toString() {
        return String.format("%s %s %s %.2f", title, editionType.name(), ageRestriction.name(), price);
    }
}

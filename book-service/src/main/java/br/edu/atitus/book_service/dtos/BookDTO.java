package br.edu.atitus.book_service.dtos;

import java.math.BigDecimal;
import java.util.List;

public record BookDTO(String title, Integer numberOfPages, BigDecimal price, Integer bookConditionId, String publisher,
		Integer numberOfYears, List<Integer> genresId, String isbn, String description) {

}

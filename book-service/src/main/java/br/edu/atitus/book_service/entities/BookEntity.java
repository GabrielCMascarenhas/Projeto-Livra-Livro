package br.edu.atitus.book_service.entities;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="tb_book")
public class BookEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	@Column(nullable = false, length = 255)
	private String title;
	
	@Column
	private String photo;
	
	@Column(nullable = false)
	private List<String> genre;
	
	@Column(name="number_of_pages")
	@Positive(message="O número de páginas deve ser um valor positivo")
	@Max(value=10000, message="O número de págnas não pode exceder 10.000")
	private Integer numberOfPages;
	
	@Column(name="book_condition")
	private String bookCondition;
	
	@Column(nullable=false, precision = 7, scale = 2)
	private BigDecimal price;
	
	@Column(name="number_of_years")
	@Min(value=0, message="A quantidade de anos não pode ser negativa")
	@Max(value=100, message="A quantidade de anos parece ser excessiva")
	private Integer numberOfYears;
	
	@Column(unique=true, length=13)
	private String isbn;
	
	@Column(nullable=false)
	private String publisher;
	
	@Column(columnDefinition="TEXT")
	@Size(max=2000, message="A descrição não pode exceder 2000 caracteres")
	private String description;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<String> getGenre() {
		return genre;
	}

	public void setGenre(List<String> genre) {
		this.genre = genre;
	}

	public Integer getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(Integer numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public String getBookCondition() {
		return bookCondition;
	}

	public void setBookCondition(String bookCondition) {
		this.bookCondition = bookCondition;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getNumberOfYears() {
		return numberOfYears;
	}

	public void setNumberOfYears(Integer numberOfYears) {
		this.numberOfYears = numberOfYears;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

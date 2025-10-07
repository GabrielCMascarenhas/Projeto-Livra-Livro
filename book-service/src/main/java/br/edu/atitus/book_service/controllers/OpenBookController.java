package br.edu.atitus.book_service.controllers;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.atitus.book_service.dtos.BookDTO;
import br.edu.atitus.book_service.entities.BookEntity;
import br.edu.atitus.book_service.repositories.BookRepository;
import br.edu.atitus.book_service.services.BookService;

@RestController
@RequestMapping("/books")
public class OpenBookController {

	private final BookRepository repository;
	private final BookService service;

	public OpenBookController(BookRepository repository, BookService service) {
		super();
		this.repository = repository;
		this.service = service;
	}

	@GetMapping("/{idBook}/")
	public ResponseEntity<BookEntity> getBook(@PathVariable UUID idBook) throws Exception {
		BookEntity book = repository.findById(idBook).orElseThrow(() -> new Exception("Book not found"));
		return ResponseEntity.ok(book);
	}

	@PostMapping
	public ResponseEntity<BookEntity> save(@RequestBody BookDTO dto) throws Exception {
		BookEntity newBookCreated = service.bookRegistration(dto);

		return ResponseEntity.status(201).body(newBookCreated);
	}

}

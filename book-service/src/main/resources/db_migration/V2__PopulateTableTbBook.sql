INSERT INTO tb_book_condition (condition)
VALUES ('NOVO'), ('USADO');

INSERT INTO tb_book_genre (genre)
VALUES ('AÇÃO'), ('DRAMA'), ('AVENTURA'), ('FICÇÃO'), ('FANTASIA'), ('BIOGRAFIA');

WITH inserted_book as (
	INSERT INTO tb_book (title, number_of_pages, price, publisher, number_of_years, isbn, description, book_condition_id)
	VALUES ('A parede', 346, 34.90, 'Minha editora', 3, '3765768234456', 'O livro fala sobre paredes', 1)
	RETURNING id
)

INSERT INTO tb_book_genres (book_id, genre_id)
VALUES
	((SELECT id FROM inserted_book), 3),
	((SELECT id FROM inserted_book), 4);
--Talvez usar o union all
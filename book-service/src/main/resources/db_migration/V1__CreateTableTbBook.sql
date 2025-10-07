CREATE TABLE tb_book_condition (
	id SERIAL PRIMARY KEY,
	condition VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE tb_book_genre (
	id SERIAL PRIMARY KEY,
	genre VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE tb_book (
	id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
	title VARCHAR(255) NOT NULL,
	number_of_pages INTEGER not null,
	price NUMERIC(7,2) NOT NULL CHECK (price > 0),
	publisher VARCHAR(255) not null,
	number_of_years INTEGER not null,
	isbn VARCHAR(13) UNIQUE,
	seller UUID DEFAULT gen_random_uuid() NOT NULL,
	description TEXT CHECK (length(description) <= 2000),
	book_condition_id INTEGER,
	
	CONSTRAINT fk_book_to_condition FOREIGN KEY (book_condition_id) REFERENCES tb_book_condition (id)
);

CREATE TABLE tb_book_genres (
	book_id UUID NOT NULL,
	genre_id INTEGER NOT NULL, 
	PRIMARY KEY (book_id, genre_id),
	FOREIGN KEY (book_id) REFERENCES tb_book (id),
	FOREIGN KEY (genre_id) REFERENCES tb_book_genre (id)
);


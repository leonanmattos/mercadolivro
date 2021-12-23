CREATE TABLE purchase(
	id int auto_increment primary key,
	customer_id int NOT NULL,
    nfe varchar(255),
    price decimal(10,2) NOT NULL,
    created_at datetime NOT NULL,
	FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE purchase_book(
	purchase_id int NOT NULL,
	book_id int NOT NULL,
	FOREIGN KEY (purchase_id) REFERENCES purchase(id),
	FOREIGN KEY (book_id) REFERENCES book(id),
	PRIMARY KEY (purchase_id, book_id)
);
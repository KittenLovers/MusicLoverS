INSERT INTO MSL_PRODUCT (weight, price, name, description, status, online, inexpensive, for_child, min_age, professional, used, brand_id) values 
	(10.1, 200, 'prodotto 1', 'prodotto disponibile, online', 'true', 'true', NULL, NULL, NULL, NULL, NULL, 1),
	(10.1, 300, 'prodotto 2', 'prodotto non disponibile, online', 'false', 'true', NULL, NULL, NULL, NULL, NULL, 1),
	(10.1, 250, 'prodotto 3', 'prodotto disponibile, non online', 'true', 'false', NULL, NULL, NULL, NULL, NULL, 1),
	(10.1, 100, 'prodotto 4', 'prodotto non disponibile, non online', 'false', 'false', NULL, NULL, NULL, NULL, NULL, 2),
	(10.1,  80, 'prodotto 5', 'prodotto disponibile, online, economico', 'true', 'true', 'true', NULL, NULL, NULL, NULL, 2),
	(10.1, 120, 'prodotto 6', 'prodotto disponibile, online, per bambini', 'true', 'true', NULL, 'true', 3, NULL, NULL, 2),
	(10.1, 700, 'prodotto 7', 'prodotto disponibile, online, professionale', 'true', 'true', NULL, NULL, NULL, 'true', NULL, 2);

INSERT INTO MSL_PRODUCT_IMAGES(image, product_id) values
	('img/image-not-found.png', 1),
	('img/image-not-found.png', 2),
	('img/image-not-found.png', 3),
	('img/image-not-found.png', 4),
	('img/image-not-found.png', 5),
	('img/image-not-found.png', 6),
	('img/image-not-found.png', 7);
-- Tworzenie tabel

CREATE TABLE roles (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(50) NOT NULL
);

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255) NOT NULL,
                       last_name VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles (
                            user_id BIGINT,
                            role_id BIGINT,
                            PRIMARY KEY (user_id, role_id),
                            FOREIGN KEY (user_id) REFERENCES users(id),
                            FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE movies (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        length_in_mins INT NOT NULL,
                        director VARCHAR(255) NOT NULL,
                        title VARCHAR(255) NOT NULL,
                        description TEXT,
                        photo_url VARCHAR(255)
);

CREATE TABLE cinema_halls (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              name VARCHAR(255) NOT NULL,
                              rows INT NOT NULL,
                              seats_in_rows INT NOT NULL
);

CREATE TABLE screenings (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            movie_id BIGINT,
                            cinema_hall_id BIGINT,
                            date_of_beginning TIMESTAMP,
                            FOREIGN KEY (movie_id) REFERENCES movies(id),
                            FOREIGN KEY (cinema_hall_id) REFERENCES cinema_halls(id)
);

CREATE TABLE tickets (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         row_number INT NOT NULL,
                         seat_number INT NOT NULL,
                         status VARCHAR(50) NOT NULL,
                         screening_id BIGINT,
                         FOREIGN KEY (screening_id) REFERENCES screenings(id)
);

-- Wstawianie danych

INSERT INTO roles (name) VALUES ('ROLE_TICKET_COLLECTOR');

INSERT INTO users (email, first_name, last_name, password)
VALUES ('user@example.com', 'Jan', 'Kowalski', '$2a$10$MA5syQTfeQ1We/rgdIpofuR9gAUqKUb28O5HmYZ3grWA58IxYI3O.');

INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);

INSERT INTO movies (length_in_mins, director, title, description, photo_url)
VALUES
    (120, 'Peter Jackson', 'The Lord of the Rings: The Fellowship of the Ring (2001)', 'A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.', 'https://m.media-amazon.com/images/M/MV5BN2EyZjM3NzUtNWUzMi00MTgxLWI0NTctMzY4M2VlOTdjZWRiXkEyXkFqcGdeQXVyNDUzOTQ5MjY@._V1_FMjpg_UX1000_.jpg'),
    (140, 'Derek Cianfrance', 'The Place Beyond the Pines (2012)', 'Two men and their sons must deal with the unforeseen consequences of their actions.', 'https://m.media-amazon.com/images/M/MV5BMjc1OTEwNjU4N15BMl5BanBnXkFtZTcwNzUzNDIwOQ@@._V1_FMjpg_UX1000_.jpg'),
    (166, 'Denis Villeneuve', 'Dune: Part Two (2024)', 'Paul Atreides unites with Chani and the Fremen while seeking revenge against the conspirators who destroyed his family.', 'https://m.media-amazon.com/images/M/MV5BN2QyZGU4ZDctOWMzMy00NTc5LThlOGQtODhmNDI1NmY5YzAwXkEyXkFqcGdeQXVyMDM2NDM2MQ@@._V1_.jpg');

INSERT INTO cinema_halls (name, rows, seats_in_rows)
VALUES
    ('Hall A', 8, 20),
    ('Hall B', 10, 15),
    ('Hall C', 7, 10);

INSERT INTO screenings (movie_id, cinema_hall_id, date_of_beginning)
VALUES
    (1, 1, DATEADD('HOUR', 3, CURRENT_TIMESTAMP)),
    (1, 2, DATEADD('HOUR', 5, CURRENT_TIMESTAMP)),
    (2, 3, DATEADD('HOUR', 8, CURRENT_TIMESTAMP)),
    (3, 3, DATEADD('DAY', 8, CURRENT_TIMESTAMP)),
    (1, 3, DATEADD('DAY', 9, CURRENT_TIMESTAMP));

-- passwords are in the format : Password<UserLetter>123. Unless specified otherwise
-- Encrypted using https://www.javainuse.com/onlineBcrypt#google_vignette
INSERT INTO local_user (email, firstname, lastname, password, username) Values('userA@Junit.com', 'UserAFirstname', 'UserALastname', '$2a$10$b3OA.AwnvQUSKbTDJQ25HuEQE4JWiT0F73Y1xQNnL44ENL14IyyyC','UserA', true)
    , ('UserB@Junit.com', 'UserBFirstname', 'UserBLastname','$2a$10$4rolj4gP3IeZAB6HoI5OseFp/OGB8Ug9WDd42JFLyy4BJY9keZ3VW','UserB', true)

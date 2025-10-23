

-- Users Table
CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY,
    user_password VARCHAR(100) NOT NULL,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) UNIQUE NOT NULL,
    user_regdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Boards Table
CREATE TABLE boards (
    board_id INT AUTO_INCREMENT PRIMARY KEY, -- SERIAL 대신 INT AUTO_INCREMENT 사용
    title VARCHAR(255) NOT NULL,
    content TEXT,
    user_id VARCHAR(50) NOT NULL,
    board_regdate TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_boards_to_users
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE CASCADE
);

-- Comments Table
CREATE TABLE comments (
  comment_id INT AUTO_INCREMENT PRIMARY KEY, -- SERIAL 대신 INT AUTO_INCREMENT 사용
  content TEXT NOT NULL,
  user_id VARCHAR(50) NOT NULL,
  board_id INT NOT NULL, -- boards.board_id 타입과 일치 (INT)
  created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, -- now() 대신 CURRENT_TIMESTAMP 사용
  CONSTRAINT fk_comments_to_users
    FOREIGN KEY (user_id)
    REFERENCES users(user_id)
    ON DELETE CASCADE,
  CONSTRAINT fk_comments_to_boards
    FOREIGN KEY (board_id)
    REFERENCES boards(board_id)
    ON DELETE CASCADE
);

-- Theatres Table
CREATE TABLE theatres (
    theatre_id INT AUTO_INCREMENT PRIMARY KEY, -- SERIAL 대신 INT AUTO_INCREMENT 사용
    title VARCHAR(255) NOT NULL,
    genre VARCHAR(100),
    content TEXT,
    playtime INT,
    poster_url VARCHAR(255),
    performance_datetime TIMESTAMP NULL, -- TIMESTAMP는 NULL 허용 여부 명시 권장
    rating DECIMAL(3, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Reviews Table
CREATE TABLE reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY, -- SERIAL 대신 INT AUTO_INCREMENT 사용
    user_id VARCHAR(50) NOT NULL,
    theatre_id INT NOT NULL,
    title text,
    content TEXT,
    rating DECIMAL(2, 1) NOT NULL CHECK (rating >= 0.5 AND rating <= 5.0),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_reviews_to_users
        FOREIGN KEY (user_id)
        REFERENCES users(user_id)
        ON DELETE cascade,
    CONSTRAINT fk_reviews_to_theatres
        FOREIGN KEY (theatre_id)
        REFERENCES theatres(theatre_id)
        ON DELETE cascade
);
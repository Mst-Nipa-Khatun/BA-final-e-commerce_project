CREATE DATABASE Final_E_Commerce;
USE Final_E_Commerce;

CREATE TABLE Users (
                       id INT PRIMARY KEY AUTO_INCREMENT,
                       name VARCHAR(255) NOT NULL,
                       phone varchar(20) not null ,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       status integer,
                       password_hash VARCHAR(255) NOT NULL,
                       address varchar(255),
                       role ENUM('customer', 'admin') DEFAULT 'customer',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Products (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          name VARCHAR(255) NOT NULL,
                          description TEXT,
                          price DECIMAL(10,2) NOT NULL,
                          stock INT NOT NULL,
                          status integer,
                          category VARCHAR(255),
                          image_url VARCHAR(255),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE categories (
                            id INT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,
                            parent_id INT NULL,
                            status integer,
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                            FOREIGN KEY (parent_id) REFERENCES categories(id) ON DELETE CASCADE
);


CREATE TABLE product_categories (
                                    id INT PRIMARY KEY AUTO_INCREMENT,
                                    product_id INT NOT NULL,
                                    category_id INT NOT NULL,
                                    status integer,
                                    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE,
                                    FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE
);



CREATE TABLE Orders (
                        id INT PRIMARY KEY AUTO_INCREMENT,
                        user_id INT,
                        total_price DECIMAL(10,2),
--     status ENUM('pending', 'shipped', 'delivered', 'cancelled') DEFAULT 'pending',
                        status integer,
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE Order_Items (
                             id INT PRIMARY KEY AUTO_INCREMENT,
                             order_id INT,
                             product_id INT,
                             quantity INT,
                             status integer,
                             price DECIMAL(10,2),
                             FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
                             FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);

CREATE TABLE Shopping_Cart (
                               id INT PRIMARY KEY AUTO_INCREMENT,
                               user_id INT,
                               product_id INT,
                               status integer,
                               quantity INT,
                               FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
                               FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);


CREATE TABLE Payments (
                          id INT PRIMARY KEY AUTO_INCREMENT,
                          order_id INT,
                          user_id INT,
                          payment_method ENUM('PayPal', 'Stripe', 'Credit Card','Cash On'),
                          amount DECIMAL(10,2),
--     status ENUM('pending', 'completed', 'failed') DEFAULT 'pending',
                          status integer,
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (order_id) REFERENCES Orders(id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE
);


CREATE TABLE Reviews (
                         id INT PRIMARY KEY AUTO_INCREMENT,
                         user_id INT,
                         product_id INT,
                         status integer,
                         rating INT CHECK (rating BETWEEN 1 AND 5),
                         comment TEXT,
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         FOREIGN KEY (user_id) REFERENCES Users(id) ON DELETE CASCADE,
                         FOREIGN KEY (product_id) REFERENCES Products(id) ON DELETE CASCADE
);

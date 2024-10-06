CREATE TABLE order_table (
  id int NOT NULL AUTO_INCREMENT,
  status varchar(255) NOT NULL,
  name_of_order varchar(255),
  order_price int NOT NULL,
  date_of_order date NOT NULL,
  description varchar(255),
  receiver varchar(255) NOT NULL,
  sender varchar(255) NOT NULL,
  PRIMARY KEY (id)
);
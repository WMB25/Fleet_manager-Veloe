INSERT INTO customers (id, name, document, email, phone) VALUES
(1, 'joão Silva', '13684603281', 'joaosilva@gmail.com', '(11)95672411'),
(2, 'Mario toledo', '12454378905', 'mariotoledo@hotmail.com', '(11)963214512'),
(3, 'Mario Silva', '76544231212', 'mariosilva@outlook.com', '(11)974323221');

INSERT INTO vehicles (id, brand, model, plate, type, customer_id) VALUES
(1, 'Toyota', 'Corolla', 'ABC1234', 'CAR', 1),
(2, 'Honda', 'Civic', 'DEF5678', 'CAR', 1),
(3, 'Ford', 'Ranger', 'GHI9012', 'CAR', 2),
(4, 'Volkswagen', 'Gol', 'JKL3456', 'CAR', 3),
(5, 'Yamaha', 'MT-07', 'MNO7890', 'MOTOCYCLE', 2);
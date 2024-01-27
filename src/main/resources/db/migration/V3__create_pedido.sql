CREATE TABLE IF NOT EXISTS pedido (
                                      id INTEGER PRIMARY KEY,
                                      cliente_id INTEGER,
                                      data_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      status_pedido_id INTEGER,
                                      valor NUMERIC(10, 2) NOT NULL,
                                      FOREIGN KEY (cliente_id) REFERENCES cliente (id)
);

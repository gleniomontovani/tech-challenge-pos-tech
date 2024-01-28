CREATE TABLE IF NOT EXISTS historico_pagamento (
                                      id SERIAL PRIMARY KEY,
                                      pagamento_id INTEGER,
                                      data_historico TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      data_pagamento TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                      numero_tentativas INTEGER,
                                      descricao VARCHAR(255),
                                      FOREIGN KEY (pagamento_id) REFERENCES pagamento (id)
);
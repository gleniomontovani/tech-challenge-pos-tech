INSERT INTO categoria (id, nome) VALUES (1, 'Lanche' ) ON CONFLICT DO NOTHING;
INSERT INTO categoria (id, nome) VALUES (2, 'Acompanhamento') ON CONFLICT DO NOTHING;
INSERT INTO categoria (id, nome) VALUES (3, 'Bebida') ON CONFLICT DO NOTHING;
INSERT INTO categoria (id, nome) VALUES (4, 'Sobremesa') ON CONFLICT DO NOTHING;

INSERT INTO status_pedido (id, nome) VALUES (1, 'Recebido') ON CONFLICT DO NOTHING;
INSERT INTO status_pedido (id, nome) VALUES (2, 'Em preparação') ON CONFLICT DO NOTHING;
INSERT INTO status_pedido (id, nome) VALUES (3, 'Pronto') ON CONFLICT DO NOTHING;
INSERT INTO status_pedido (id, nome) VALUES (4, 'Finalizado') ON CONFLICT DO NOTHING;

INSERT INTO status_pagamento (id, nome) VALUES (1, 'Pendente') ON CONFLICT DO NOTHING;
INSERT INTO status_pagamento (id, nome) VALUES (2, 'Aprovado') ON CONFLICT DO NOTHING;
INSERT INTO status_pagamento (id, nome) VALUES (3, 'Recusado') ON CONFLICT DO NOTHING;
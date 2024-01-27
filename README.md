<h1 align="center">Tech Challenge Fase 2 - Turma 4SOAT - Grupo 68</h1>

<span style="font-family:Times New Roman; font-size:13px;">

<div align="justify">
Há uma lanchonete de bairro que está expandindo devido seu grande sucesso. Porém, com a expansão e sem um sistema de controle de pedidos, o atendimento aos clientes pode ser caótico e confuso. Por exemplo, imagine que um cliente faça um pedido complexo, como um hambúrguer personalizado com ingredientes específicos, acompanhado de batatas fritas e uma bebida. O atendente pode anotar o pedido em um papel e entregá-lo à cozinha, mas não há garantia de que o pedido será preparado corretamente.<br/>
Sem um sistema de controle de pedidos, pode haver confusão entre os atendentes e a cozinha, resultando em atrasos na preparação e entrega dos pedidos. Os pedidos podem ser perdidos, mal interpretados ou esquecidos, levando à insatisfação dos clientes e a perda de negócios.<br/>
Em resumo, um sistema de controle de pedidos é essencial para garantir que a lanchonete possa atender os clientes de maneira eficiente, gerenciando seus pedidos e estoques de forma adequada. Sem ele, expandir a lanchonete pode acabar não dando certo, resultando em clientes insatisfeitos e impactando os negócios de forma negativa.<br/>
Para solucionar o problema, a lanchonete irá investir em um sistema de autoatendimento de fast food, que é composto por uma série de dispositivos e interfaces que permitem aos clientes selecionar e fazer pedidos sem precisar interagir com um atendente.<br/><br/>
Este projeto é uma dos modulos que fará parte dessa solução.
</div>

##### 1. Informações do projeto:

1. **Módulo:** Backend.
1. **Arquitetura:** Clean architecture.
1. **Padrões de Codificação:** Clean code.
1. **Linguagem:** Java versão 17.
1. **Banco de Dados:** PostgreSQL. 
1. **Container:** Docker.
1. **Orquestrador:** Kubernet.


##### 2. Configuração e Execução: 

1. Clone o [repositório](https://github.com/pietroow/tech-challenge-pos-tech/blob/main/Tech-challenge.postman_collection.json) para sua máquina local.
1. Certifique-se de ter o Docker instalado e configurado.
1. Certifique-se de ter o kubectl configurado corretamente para acessar o seu cluster Kubernetes local. <br>
3.1 Caso você esteja no ambiente Windows, lembre-se de habilita-lo no Docker ([veja aqui](https://birthday.play-with-docker.com/kubernetes-docker-desktop/))

##### 3. Construir e Rodar a Aplicação Localmente:

1. Entre na pasta onde você baixou o codigo fonte. <br/>
 [2]&nbsp;-&nbsp;`Configuração e Execução` 
2. Após isso, construa a imagem Docker localmente (no mesmo diretório do `Dockerfile`) **:**

```
# docker build -t tech-challenge-pos-tech:latest . 
```

Execulte o seguintes comandos (na seguinte ordem) para implantar os manifestos do Kubernetes:

```
# kubectl apply -f postgres-deployment.yaml
# kubectl apply -f postgres-service.yaml
# kubectl apply -f deployment.yaml
# kubectl apply -f service.yaml
# kubectl apply -f hpa.yaml
```

Caso queira ver os logs da aplicação basta execultar:

```
# kubectl get pods 
```

|NAME         							    |READY |STATUS |RESTARTS    |AGE  |
|-------------------------------------------|------|-------|------------|-----|
|postgresql-64567ff6fd-bmfk8 			    | 1/1  |Running|1 (105m ago)|5d16h|    
|tech-challenge-pos-tech-5785c6dbf4-86g9j   | 1/1  |Running|1 (105m ago)|5d16h|
|tech-challenge-pos-tech-5785c6dbf4-mkbcx	| 1/1  |Running|1 (105m ago)|5d16h|
|tech-challenge-pos-tech-5785c6dbf4-mkct4	| 1/1  |Running|1 (105m ago)|5d16h|




Obtenha o nome do container no campo/coluna `NAME` do resultado do comando acima. Então, execulte o seguinte comando para ver os log da aplicação:

```
# kubectl logs -f nome_do_pod (tech-challenge-pos-tech-5785c6dbf4-86g9j)
```


##### 4. As APIs da aplicação ficaram acessíveis no endereço: 

- **URL:** [http://localhost:32000](http://localhost:32000)

&nbsp;

---
##### 5. As APIs disponíveis são: &nbsp;

1. Cliente:   
 1.1. Criação;    
 1.2. Edição;  
1. Produto:   
 2.1. Criação;   
 2.2. Edição;  
 2.3. Remoção;   
 2.4. Busca por número;   
 2.5. Busca por Categoria.
1. Pedidos: <br>
 3.1. Criaação(Com e sem identificação do cliente); <br>
 3.2. Consultar; <br>
 3.3. Alterar Status
1. Pagamentos:   
 4.1. Consultar por Pedido; <br>
 4.2. Histórico de tentativas de pagamento (Webhook).

&nbsp;

---

>Para utilizar as APIs, baixe o arquivo [JSON](https://github.com/pietroow/tech-challenge-pos-tech/blob/main/Tech-challenge.postman_collection.json) e faça a importação no Postaman.

&nbsp;&nbsp;

</span>

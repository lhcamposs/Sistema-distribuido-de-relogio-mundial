# Sistema Distribuído de Relógio Mundial (TCP/UDP)

## Resumo do Projeto
Este projeto tem como objetivo demonstrar a comunicação entre processos em Java utilizando Sockets. A aplicação simula um Relógio Mundial distribuído, onde os clientes solicitam a hora exata de regiões geográficas específicas (ex: `America/Sao_Paulo`, `Asia/Tokyo`) e o servidor responde com a data e hora formatadas usando a API `java.time.ZonedDateTime`.

O projeto implementa três abordagens diferentes para evidenciar características de redes:
- **Versão 1 (UDP):** Comunicação sem conexão, focada em economia de recursos e lidando com a natureza não confiável da rede via Timeouts.
- **Versão 2 (TCP Single-Thread):** Comunicação orientada à conexão, garantindo entrega, mas processando clientes em fila (sequencial).
- **Versão 3 (TCP Multithread):** Servidor concorrente, melhorando a escalabilidade ao abrir uma Thread por cliente.

## Instruções de Execução

Abra o terminal e navegue até a raiz do projeto. Utilize o `javac` para compilar e o `java` para executar.

### Versão 1: UDP (`/udp-clock`)
1. Compile os arquivos: `javac udpclock/*.java`
2. Em um terminal, inicie o servidor: `java udpclock.UdpServer`
3. Em outro terminal, inicie o cliente: `java udpclock.UdpClient`

### Versão 2: TCP Simple (`/tcp-clock-simple`)
1. Compile os arquivos: `javac tcpclocksimple/*.java`
2. Em um terminal, inicie o servidor: `java tcpclocksimple.TcpSimpleServer`
3. Em outro terminal, inicie o cliente: `java tcpclocksimple.TcpClient`

### Versão 3: TCP Multithread (`/tcp-clock-multithread`)
1. Compile os arquivos: `javac tcpclockmultithread/*.java`
2. Em um terminal, inicie o servidor: `java tcpclockmultithread.TcpMultiServer`
3. Em outro terminal, inicie o cliente: `java tcpclocksimple.TcpClientMultithread`

## Análise Técnica: Performance TCP Simple vs. Multithread

A principal diferença de performance percebida entre a **Versão 2 (Single-Thread)** e a **Versão 3 (Multithread)** ocorre em cenários de alta concorrência (múltiplos clientes conectando simultaneamente).

- **TCP Single-Thread (Versão 2):** O servidor atende um cliente por vez. Enquanto a requisição do Cliente A está sendo processada (ou se houver lentidão na rede/processamento), as conexões dos Clientes B, C e D ficam retidas na fila de espera (backlog) do Sistema Operacional. Isso cria um gargalo significativo, aumentando o tempo de resposta e causando a percepção de "travamento" para os clientes subsequentes.
- **TCP Multithread (Versão 3):** O servidor resolve esse gargalo aceitando conexões quase que instantaneamente no seu loop principal. Ao receber um novo `accept()`, o processamento é delegado a uma nova `Thread` concorrente. Isso permite que os Clientes A, B, C e D sejam atendidos de forma assíncrona e simultânea, aproveitando melhor os múltiplos núcleos de CPU do servidor e eliminando a fila de espera síncrona, resultando em escalabilidade e baixa latência.
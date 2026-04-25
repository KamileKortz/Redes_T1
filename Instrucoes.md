# Mensagens em grupo – Instruções

## 1. Compilar os arquivos

Execute isso no terminal da pasta onde estão os arquivos:

```bash
javac Server.java
javac ClientEnvio.java
javac ClientRecebimento.java
```

---

## 2. Iniciar o Servidor

Em um terminal, execute:

```bash
java Server
```

O servidor vai ficar aguardando os clientes conectarem.

---

## 3. Conectar o ClientRecebimento

Abra um terminal e execute:

```bash
java ClientRecebimento
```

Esse cliente não digita nada, só exibe na tela todas as mensagens enviadas no chat.

---

## 4. Conectar o(s) ClientEnvio

Abra um terminal para cada pessoa que quiser enviar mensagens:

```bash
java ClientEnvio
```

- O cliente vai pedir um nome.
- Depois digitar o nome, escreva e pressione Enter para enviar.
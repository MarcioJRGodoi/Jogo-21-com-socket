<p align="center">
  <img src="https://imgur.com/wUQ9EZo.png" style="width: 120px; height: 120px;"/>
</p>
<h1 align="center">Jogo do 21 (Blackjack) </h1>

# Introdução
<div style='text-align: justify;'>
    Este projeto é válido para a terceira nota da disciplina de Redes de Computadores na Universidade do Extremo Sul Catarinense (UNESC). 
    Tem-se como objetivo a reprodução do jogo do vinte e um (Blackjack) com algumas alterações nas regras.
</div>

# Regras

O jogo é jogado com um baralho de 52 cartas. O valor das cartas é o seguinte:
- As cartas de 2 a 10 valem o número de pontos que elas representam.
- As cartas de J, Q e K valem 10 pontos cada.
- O Ás vale 1 ou 11 pontos, dependendo de qual valor for mais vantajoso para a mão.

Ambos os jogadores começarão com uma carta a amostra para o outro jogador. A partir disso, os jogadores deverão escolher se desejam ou não pegar uma carta aleatória. O objetivo é chegar o mais próximo possível de 21 pontos sem estourar. Caso o jogador estoure, o outro jogador ganha automaticamente. Caso nenhum dos jogadores estoure, ganha quem tiver mais pontos.

# Instalação
Para instalar o projeto, é necessário ter o Java JDK instalado. Caso não tenha, siga as instruções de instalação no site do [Java](https://www.oracle.com/br/java/technologies/downloads/). Após a instalação, clone o repositório e execute o arquivo `Servidor.java` para iniciar o Servidor que irá gerenciar as conexões e o jogo. Em seguida, execute o arquivo `ConectarGUI.java` para iniciar o Cliente.


# Funcionamento
<div style='text-align: justify;'>
O jogo funciona da seguinte forma: o `Servidor` fica aguardando a conexão de dois jogadores. Quando os dois jogadores se conectam, o `Servidor` inicia o jogo e envia uma carta aleatória para cada jogador. 

Em seguida, algum dos `Jogadores` escolhe se deseja ou não pegar uma carta. Caso um dos `Jogadores` deseje pegar uma carta, o `Servidor` envia uma carta aleatória para o Cliente. Caso contrário, ele ignora e exibe a ambos o que foi alterado.

O jogo continua até ambos `Jogadores` decidam parar de pegar cartas. Quando o jogo termina, o Servidor envia uma mensagem para os `Clientes` informando quem ganhou, a pontuação do jogador e do adversário e fecha a conexão com o `Servidor`.
</div>


# Exemplo de funcionamento

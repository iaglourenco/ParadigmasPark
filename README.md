# Sistema para controle de Estacionamento

Basic applet of an Car Park (Paradigmas B)

# Objetivo
Desenvolver um sistema em Java controle de Estacionamento.

# Estrutura do estacionamento
O estacionamento foi construído em dois pisos (térreo e primeiro piso), e tem capacidade para
duzentos veículos (cento e sessenta carros, vinte caminhonetes e vinte motos), alocados da
seguinte forma:
- Os carros ocupam o primeiro piso (Piso 1), divididos em dez filas de dez lugares cada e, ainda,
no térreo divididos em seis filas de dez lugares cada;
- As caminhonetes ocupam o térreo divididas em duas filas de dez lugares cada;
- As motos, tal como as caminhonetes, ocupam o térreo e encontram-se dispostos em duas filas
de dez lugares cada.

# Regulamento do parque
De forma a otimizar o funcionamento do estacionamento a sua direção implementou o seguinte
sistema:
- Um veículo só pode entrar se existir um lugar vago para a sua categoria;
- Ao entrar são guardados os seguintes os dados: placa e hora de entrada (hh:mm:ss);
- O veículo estaciona sempre no primeiro lugar disponível para a sua categoria, de acordo com a
vaga disponibilizada pelo sistema;
- Quando um veículo sai do estacionamento, é atualizada a sua hora de saída, calculado o
montante a pagar pelo estacionamento e liberado o respectivo espaço de estacionamento que
ocupava.

# Requisitos mínimos do sistema
O sistema deve ter uma interface de configuração, que será utilizada pelo responsável pelo
estacionamento. Nesta interface, as seguintes telas deverão existir:
- Tela para inserção de informações de preço para cada tipo de veículo por hora de utilização
(tela de configuração).
- Tela com status das vagas do estacionamento: uma tela que mostre em cores as vagas livres
e ocupadas. Esta tela deverá ser a principal do sistema.
- Tela para entrada de um novo veículo: Nesta tela, o responsável insere o dia, a hora, a placa
e escolhe o tipo de veículo. O sistema apresenta o número da vaga onde o veículo deverá
estacionar,e a vaga passa para o status de ocupada.
- Tela para saída de um veículo: Nesta tela, o gerente insere a placa, o dia e a hora de saída, e
o sistema calcula o valor a ser pago, além de liberar a vaga.
- Tela para contabilidade do estacionamento, com número de veículos que saíram, e o total de
dinheiro recebido, em um determinado período de tempo (dias).
O sistema deverá armazenar em arquivo todas as suas informações, de forma que, ao ser
reiniciado, o sistema possa ser restaurado.

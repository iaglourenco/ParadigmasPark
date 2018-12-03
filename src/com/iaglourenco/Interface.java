package com.iaglourenco;

import com.iaglourenco.exceptions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

class Interface  {

    private static Interface instance;

    private Sistema sistema = Sistema.getInstance();
    private JFrame saidaVeiculos = new JFrame();
    private JFrame entradaVeiculos = new JFrame();
    private JFrame setupEstacionamento = new JFrame();
    private JFrame contabilidade = new JFrame();
    private JFrame status = new JFrame();
    private JFrame pagamento = new JFrame();
    private Dimension frameDimension = new Dimension(1000,720);//TODO ajustar resolução
    private Dimension popupDimension = new Dimension(300,260);

    private JButton buttonEntrada=new JButton("Registrar entrada");//registrar entrada
    private JButton buttonSaida=new JButton("Registrar saida");//registrar saida
    private JButton buttonContabilidade = new JButton("Contabilidade");
    private JButton buttonSetup = new JButton("Configurar preços");
    private JButton buttonExit=new JButton("Sair");//sair do programa
    private JButton buttonTerreo =new JButton("Terreo");//visualizar piso terreo
    private JButton buttonPriPiso =new JButton("1° Piso");//visualizar primeiro piso
    private JTextArea infoCarro = new JTextArea();
    private JTextArea infoMoto = new JTextArea();
    private JTextArea infoCaminhonete = new JTextArea();
    private JPanel panel1Status = new JPanel(new FlowLayout());
    private JPanel panel2Status = new JPanel(new FlowLayout());

    private JPanel carroStatus = new JPanel(new FlowLayout());
    private JPanel caminhoneteStatus = new JPanel(new FlowLayout());
    private JPanel motoStatus = new JPanel(new FlowLayout());

    private JButton[] buttonEstacCarroT = new JButton[60];
    private JButton[] buttonEstacCarroP = new JButton[100];
    private JButton[] buttonEstacCaminhonete = new JButton[20];
    private JButton[] buttonEstacMoto = new JButton[20];

    private JPanel panel1Setup = new JPanel(new GridLayout(4,0,10,10));
    private JTextField precoCaminhonete = new JTextField();
    private JTextField precoCarro = new JTextField();
    private JTextField precoMotocicleta = new JTextField();
    private JButton buttonOKSetup = new JButton("OK");
    private JButton buttonClearSetup = new JButton("Limpar");

    private final String[] names = {"Selecione...","Carro","Caminhonete","Motocicleta"};
    private JComboBox<String> categoriaEntrada = new JComboBox<>(names); //Para painel Entrada
    private JComboBox<String> categoriaSaida = new JComboBox<>(names); //Para painel Saida


    private JPanel panelSaida = new JPanel(new GridLayout(9,0,10,10));
    private JTextField placaSaida = new JTextField();
    private JTextField horaSaida = new JTextField();
    private JTextField diaSaida = new JTextField();
    private JButton buttonOKSaida = new JButton("OK");
    private JButton buttonBackSaida = new JButton("Voltar");


    private JPanel panelEntrada = new JPanel(new GridLayout(9,0,10,10));
    private JTextField placaEntrada = new JTextField();
    private JTextField horaEntrada = new JTextField();
    private JTextField diaEntrada = new JTextField();
    private JButton buttonOKEntrada = new JButton("OK");
    private JButton buttonBackEntrada = new JButton("Voltar");


    private JPanel panelContabilidade = new JPanel(new FlowLayout());
    private JTextArea contabArea = new JTextArea();
    private JButton buttonOKContabilidade = new JButton("OK");

    private JPanel panelPagamento = new JPanel();
    private JTextArea infoPlaca = new JTextArea();
    private JTextArea infoPreco = new JTextArea();
    private JTextArea infoTipo = new JTextArea();
    private JButton buttonOKPagamento = new JButton("OK");



    private  Interface(){
        initialize();
        initSetup();
        setupEstacionamento.setVisible(true);
        sistema.setup();
        try {
            sistema.updateVagasFile();
        } catch (WriteFileException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(),"ERRO AO ESCREVER ARQUIVO",JOptionPane.ERROR_MESSAGE);
        }
        initEntrada();
        initSaida();
        initPagamento();
        initContabilidade();
        addAllHandlers();
    }

    private void addAllHandlers(){

        buttonEntrada.addActionListener(new StatusHandler());
        buttonSaida.addActionListener(new StatusHandler());
        buttonContabilidade.addActionListener(new StatusHandler());
        buttonSetup.addActionListener(new StatusHandler());
        buttonExit.addActionListener(new StatusHandler());
        buttonTerreo.addActionListener(new StatusHandler());
        buttonPriPiso.addActionListener(new StatusHandler());

        status.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        status.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(JOptionPane.showConfirmDialog
                        (null,"Tem certeza?",
                                "Sair",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    System.exit(0);

                }

            }
        });

        buttonOKSetup.addActionListener(new SetupHandler());
        buttonClearSetup.addActionListener(new SetupHandler());

        setupEstacionamento.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setupEstacionamento.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if(precoCaminhonete.getText().isEmpty() || precoCarro.getText().isEmpty() || precoMotocicleta.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos!","ERRO",JOptionPane.ERROR_MESSAGE);
                }else{

                    sistema.setPrecoCaminhonete(Double.parseDouble(precoCaminhonete.getText()));
                    sistema.setPrecoCarro(Double.parseDouble(precoCarro.getText()));
                    sistema.setPrecoMoto(Double.parseDouble(precoMotocicleta.getText()));
                    JOptionPane.showMessageDialog(null,"Os preços digitados foram salvos!","INFO",JOptionPane.INFORMATION_MESSAGE);
                    setupEstacionamento.dispose();
                }
            }
        });
        setupEstacionamento.addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowLostFocus(WindowEvent e) {
                setupEstacionamento.requestFocus();
            }
        });

        buttonOKEntrada.addActionListener(new EntradaHandler());
        buttonBackEntrada.addActionListener(new EntradaHandler());

        buttonOKSaida.addActionListener(new SaidaHandler());
        buttonBackSaida.addActionListener(new SaidaHandler());

        buttonOKContabilidade.addActionListener(new ContabileHandler());

    }

    private void initialize(){
        //LAYOUT PRINCIPAL
        status.setLayout(new BorderLayout());
        status.setSize(frameDimension);
        status.setResizable(false);
        status.setLocationRelativeTo(null);
        status.setTitle("Paradigmas-B [Controle de Estacionamento]");

        //STATUS TERREO
        panel2Status.add(buttonTerreo);

        //STATUS 1°PISO
        panel2Status.add(buttonPriPiso);

        //Visualização Geral
        panel2Status.add(new JLabel("                                              Carros "));
        panel2Status.add(infoCarro);
        infoCarro.setEditable(false);

        panel2Status.add(new JLabel("      Caminhonetes "));
        panel2Status.add(infoCaminhonete);
        infoCaminhonete.setEditable(false);

        panel2Status.add(new JLabel("      Motocicletas "));
        panel2Status.add(infoMoto);
        infoMoto.setEditable(false);

        infoCaminhonete.setBackground(null);
        infoCarro.setBackground(null);
        infoMoto.setBackground(null);
        infoCarro.setText(Integer.toString(sistema.sizePiso1()+sistema.sizeTerreoCarros()));
        infoCaminhonete.setText(Integer.toString(sistema.sizeTerreoCaminhonetes()));
        infoMoto.setText(Integer.toString(sistema.sizeTerreoMotos()));

        panel1Status.add(buttonEntrada);
        panel1Status.add(buttonSaida);
        panel1Status.add(buttonContabilidade);
        panel1Status.add(buttonSetup);
        panel1Status.add(buttonExit);

        //TODO inicializar a visualizacao de vagas disponiveis
        ImageIcon carro = new ImageIcon(getClass().getResource("carro.png"));
        carro.setImage(carro.getImage().getScaledInstance(40, 40, 100));
        ImageIcon caminhonete = new ImageIcon(getClass().getResource("caminhonete.png"));
        caminhonete.setImage(caminhonete.getImage().getScaledInstance(40, 40, 100));
        ImageIcon moto = new ImageIcon(getClass().getResource("moto.png"));
        moto.setImage(moto.getImage().getScaledInstance(40, 40, 100));

        carroStatus.setPreferredSize(new Dimension(300, 100));
        for(int i = 0; i < 60; i++) {
            buttonEstacCarroT[i] = new JButton();
            buttonEstacCarroT[i].setName(Integer.toString(i+101));
            buttonEstacCarroT[i].setPreferredSize(new Dimension(50, 45));
            buttonEstacCarroT[i].setBackground(Color.green);
            buttonEstacCarroT[i].setIcon(carro);
            buttonEstacCarroT[i].setVisible(true);
            carroStatus.add(buttonEstacCarroT[i]);
        }

        for(int i = 0; i < 100; i++) {
            buttonEstacCarroP[i] = new JButton();
            buttonEstacCarroP[i].setName(Integer.toString(i+1));
            buttonEstacCarroP[i].setPreferredSize(new Dimension(50, 45));
            buttonEstacCarroP[i].setBackground(Color.green);
            //buttonEstacCarro[i].setText(" "+i);
            buttonEstacCarroP[i].setIcon(carro);
            buttonEstacCarroP[i].setVisible(true);
            carroStatus.add(buttonEstacCarroP[i]);
        }

        caminhoneteStatus.setPreferredSize(new Dimension(150, 100));
        for(int i = 0; i < 20; i++) {
            buttonEstacCaminhonete[i] = new JButton();
            buttonEstacCaminhonete[i].setName(Integer.toString(i+181));
            buttonEstacCaminhonete[i].setPreferredSize(new Dimension(50, 50));
            buttonEstacCaminhonete[i].setBackground(Color.green);
            //buttonEstacCaminhonete[i].setText(" "+i);
            buttonEstacCaminhonete[i].setIcon(caminhonete);
            buttonEstacCaminhonete[i].setVisible(true);
            caminhoneteStatus.add(buttonEstacCaminhonete[i]);
        }

        motoStatus.setPreferredSize(new Dimension(150, 100));
        for(int i = 0; i < 20; i++) {
            buttonEstacMoto[i] = new JButton();
            buttonEstacMoto[i].setName(Integer.toString(i+161));
            buttonEstacMoto[i].setPreferredSize(new Dimension(50, 50));
            buttonEstacMoto[i].setBackground(Color.green);
            //buttonEstacMoto[i].setText(" "+i);
            buttonEstacMoto[i].setIcon(moto);
            buttonEstacMoto[i].setVisible(true);
            motoStatus.add(buttonEstacMoto[i]);
        }

        panel1Status.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));
        panel2Status.setBorder(BorderFactory.createStrokeBorder(new BasicStroke(3.0f)));

        status.add(panel1Status,BorderLayout.SOUTH);
        status.add(panel2Status,BorderLayout.NORTH);
        status.add(carroStatus,BorderLayout.CENTER);
        status.add(caminhoneteStatus,BorderLayout.LINE_START);
        status.add(motoStatus,BorderLayout.LINE_END);
        status.setVisible(true);
    }

    private void initSetup(){
        //LAYOUT DE CONFIGURAR PREÃ‡OS
        setupEstacionamento.setLayout(new BorderLayout());
        setupEstacionamento.setSize(popupDimension);
        setupEstacionamento.setResizable(false);
        setupEstacionamento.setLocationRelativeTo(null);
        setupEstacionamento.setTitle("Configurar Preços");

        panel1Setup.add(new JLabel("Carros / R$:"));
        panel1Setup.add(precoCarro);

        panel1Setup.add(new JLabel("Caminhonetes / R$:"));
        panel1Setup.add(precoCaminhonete);

        panel1Setup.add(new JLabel("Motocicletas / R$:"));
        panel1Setup.add(precoMotocicleta);

        precoCarro.setText(Double.toString(sistema.getPrecoCarro()));
        precoCaminhonete.setText(Double.toString(sistema.getPrecoCaminhonete()));
        precoMotocicleta.setText(Double.toString(sistema.getPrecoMoto()));

        panel1Setup.add(buttonOKSetup);
        panel1Setup.add(buttonClearSetup);

        setupEstacionamento.add(panel1Setup,BorderLayout.CENTER);

    }

    private void initEntrada(){
        //LAYOUT REGISTRO DE ENTRADA
        entradaVeiculos.setLayout(new BorderLayout());
        entradaVeiculos.setSize(popupDimension);
        entradaVeiculos.setResizable(false);
        entradaVeiculos.setLocationRelativeTo(null);
        entradaVeiculos.setTitle("Registrar entrada");

        panelEntrada.add(new JLabel("Placa do Veiculo"));
        panelEntrada.add(placaEntrada);
        panelEntrada.add(new JLabel("Horario de Entrada"));
        panelEntrada.add(horaEntrada);
        panelEntrada.add(new JLabel("Dia de Entrada"));
        panelEntrada.add(diaEntrada);
        categoriaEntrada.setMaximumRowCount(names.length);
        panelEntrada.add(categoriaEntrada);
        panelEntrada.add(buttonOKEntrada);
        panelEntrada.add(buttonBackEntrada);

        entradaVeiculos.add(panelEntrada);

    }

    private void initSaida(){
        //LAYOUT REGISTRO DE SAIDA
        saidaVeiculos.setLayout(new BorderLayout());
        saidaVeiculos.setSize(popupDimension);
        saidaVeiculos.setResizable(false);
        saidaVeiculos.setLocationRelativeTo(null);
        saidaVeiculos.setTitle("Registrar saida");

        panelSaida.add(new JLabel("Placa do Veiculo"));
        panelSaida.add(placaSaida);
        panelSaida.add(new JLabel("Horario de Saida"));
        panelSaida.add(horaSaida);
        panelSaida.add(new JLabel("Dia de Saida"));
        panelSaida.add(diaSaida);
        categoriaSaida.setMaximumRowCount(names.length);
        panelSaida.add(categoriaSaida);
        panelSaida.add(buttonOKSaida);
        panelSaida.add(buttonBackSaida);

        saidaVeiculos.add(panelSaida);

    }

    private void initContabilidade(){
        //LAYOUT CONTROLE DA CONTABILIDADE
        contabilidade.setLayout(new BorderLayout());
        contabilidade.setSize(frameDimension);
        contabilidade.setResizable(false);
        contabilidade.setLocationRelativeTo(null);
        contabilidade.setTitle("Contabilidade");
        contabArea.setEditable(false);
        contabArea.append("String de teste");


        panelContabilidade.add(buttonOKContabilidade);
        contabilidade.add(contabArea);
        contabilidade.add(panelContabilidade,BorderLayout.SOUTH);

    }

    private void initPagamento(){

        pagamento.setLayout(new BorderLayout());
        pagamento.setSize(popupDimension);
        pagamento.setResizable(false);
        pagamento.setLocationRelativeTo(null);
        pagamento.setTitle("Pagamento");
        infoPlaca.setEditable(false);
        infoTipo.setEditable(false);
        infoPreco.setEditable(false);
        panelPagamento.setLayout(new GridLayout(7,0,10,10));
        panelPagamento.add(new JLabel("Placa"));
        panelPagamento.add(infoPlaca);
        panelPagamento.add(new JLabel("Tipo"));
        panelPagamento.add(infoTipo);
        panelPagamento.add(new JLabel("Valor"));
        panelPagamento.add(infoPreco);
        panelPagamento.add(buttonOKPagamento);
        buttonOKPagamento.addActionListener(e -> pagamento.dispose());

        pagamento.add(panelPagamento,BorderLayout.CENTER);

    }

    //singleton
    synchronized static Interface getInstance(){

        if(instance == null){
            instance = new Interface();
        }
        return instance;
    }

    private class StatusHandler implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == buttonEntrada){
                placaEntrada.setText("");
                horaEntrada.setText(new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));
                diaEntrada.setText(new SimpleDateFormat("dd/MM/yy").format(new Date(System.currentTimeMillis())));
                entradaVeiculos.setVisible(true);
            }else if(e.getSource() == buttonSaida){
                placaSaida.setText("");
                horaSaida.setText(new SimpleDateFormat("HH:mm").format(new Date(System.currentTimeMillis())));
                diaSaida.setText(new SimpleDateFormat("dd/MM/yy").format(new Date(System.currentTimeMillis())));
                saidaVeiculos.setVisible(true);
            }else if(e.getSource() == buttonContabilidade){
                contabilidade.setVisible(true);
            }else if(e.getSource() == buttonSetup){
                setupEstacionamento.setVisible(true);
            }else if(e.getSource() == buttonExit){
                if(JOptionPane.showConfirmDialog
                        (null,"Tem certeza?",
                                "Sair",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
                {
                    System.exit(0);

                }

            } /*else if(e.getSource() == buttonTerreo) {
            	motoStatus.setVisible(true);
            	caminhoneteStatus.setVisible(true);
            	carroStatus.setVisible(false);


            } else if(e.getSource() == buttonPriPiso) {
            	carroStatus.setVisible(true);
            	motoStatus.setVisible(false);
            	caminhoneteStatus.setVisible(false);

            }*/

        }
    }

    private class SetupHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == buttonOKSetup){
                if(precoCaminhonete.getText().equals("0.0")){
                    precoCaminhonete.setText("");
                }if (precoCarro.getText().equals("0.0")){
                    precoCarro.setText("");
                }if(precoMotocicleta.getText().equals("0.0")){
                    precoCaminhonete.setText("");
                }
                if(precoCaminhonete.getText().isEmpty() || precoCarro.getText().isEmpty() || precoMotocicleta.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"Preencha todos os campos!","ERRO",JOptionPane.ERROR_MESSAGE);
                    precoCarro.setText(Double.toString(sistema.getPrecoCarro()));
                    precoCaminhonete.setText(Double.toString(sistema.getPrecoCaminhonete()));
                    precoMotocicleta.setText(Double.toString(sistema.getPrecoMoto()));

                }else{

                    sistema.setPrecoCaminhonete(Double.parseDouble(precoCaminhonete.getText()));
                    sistema.setPrecoCarro(Double.parseDouble(precoCarro.getText()));
                    sistema.setPrecoMoto(Double.parseDouble(precoMotocicleta.getText()));
                    JOptionPane.showMessageDialog(null,"Os preços digitados foram salvos!","INFO",JOptionPane.INFORMATION_MESSAGE);
                    setupEstacionamento.dispose();
                }
            } else if (e.getSource() == buttonClearSetup) {
                //limpa os campos
                precoCaminhonete.setText("");
                precoCarro.setText("");
                precoMotocicleta.setText("");
            }

        }
    }

    private class EntradaHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == buttonOKEntrada) {

                String data = diaEntrada.getText()+"&"+horaEntrada.getText();

                int IDRetorno;

                try {
                    if(placaEntrada.getText().isEmpty() || horaEntrada.getText().isEmpty() || diaEntrada.getText().isEmpty()){
                        throw new ValorInvalidoException();
                    }

                    switch (Objects.requireNonNull(categoriaEntrada.getSelectedItem()).toString()) {

                        case "Carro":

                            IDRetorno = Integer.parseInt(sistema.registraEntrada(new Automovel(placaEntrada.getText(), Automovel.CARRO), data));

                            if(IDRetorno >= 1 && IDRetorno <= 100) {
                                for(int i = 0; i < 100; i++) {
                                    if(buttonEstacCarroP[i].getName().equals(Integer.toString(IDRetorno))) {
                                        buttonEstacCarroP[i].setBackground(Color.red);
                                    }
                                }
                            }
                            if(IDRetorno >= 101 && IDRetorno <= 160) {
                                for(int i = 0; i < 100; i++) {
                                    if(buttonEstacCarroT[i].getName().equals(Integer.toString(IDRetorno))) {
                                        buttonEstacCarroT[i].setBackground(Color.red);
                                    }
                                }
                            }
                            infoCarro.setText(Integer.toString(sistema.sizePiso1()+sistema.sizeTerreoCarros()));
                            entradaVeiculos.dispose();
                            break;

                        case "Caminhonete":
                            IDRetorno = Integer.parseInt(sistema.registraEntrada(new Automovel(placaEntrada.getText(), Automovel.CAMINHONETE), data));

                            if(IDRetorno >= 181 && IDRetorno <= 200) {
                                for(int i = 0; i < 20; i++) {
                                    if(buttonEstacCaminhonete[i].getName().equals(Integer.toString(IDRetorno))) {
                                        buttonEstacCaminhonete[i].setBackground(Color.red);
                                    }
                                }
                            }
                            infoCaminhonete.setText(Integer.toString(sistema.sizeTerreoCaminhonetes()));
                            entradaVeiculos.dispose();
                            break;

                        case "Motocicleta":
                            IDRetorno = Integer.parseInt(sistema.registraEntrada(new Automovel(placaEntrada.getText(), Automovel.MOTO), data));

                            if(IDRetorno >= 161 && IDRetorno <= 180) {
                                for (int i = 0; i < 20; i++) {
                                    if (buttonEstacMoto[i].getName().equals(Integer.toString(IDRetorno))) {
                                        buttonEstacMoto[i].setBackground(Color.red);
                                    }
                                }
                            }
                            infoMoto.setText(Integer.toString(sistema.sizeTerreoMotos()));
                            entradaVeiculos.dispose();
                            break;
                        default:
                            JOptionPane.showMessageDialog(null, "SELECIONE O TIPO DE VEICULO", "ERRO", JOptionPane.WARNING_MESSAGE);
                            break;
                    }


                } catch (WriteFileException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "ERRO AO ESCREVER NO ARQUIVO", JOptionPane.ERROR_MESSAGE);
                } catch (ValorInvalidoException e2) {
                    JOptionPane.showMessageDialog(null, "DIGITE UM VALOR VALIDO", "ERRO", JOptionPane.ERROR_MESSAGE);
                } catch (VagaOcupadaException e3) {
                    JOptionPane.showMessageDialog(null, "VEICULO JA CADASTRADO", "ERRO", JOptionPane.ERROR_MESSAGE);
                }catch (EstacionamentoCheioException e4){
                    JOptionPane.showMessageDialog(null, "TODAS AS VAGAS DISPONIVEIS PARA ESTE VEICULO OCUPADAS", "ERRO", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == buttonBackEntrada) {
                entradaVeiculos.dispose();
            }

        }
    }

    private class SaidaHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == buttonOKSaida){
                String ret;
                String[] s;
                int IDRetorno;

                try{
                    if(placaSaida.getText().isEmpty() || horaSaida.getText().isEmpty() || diaSaida.getText().isEmpty()){
                        throw new ValorInvalidoException();
                    }

                    String data = diaSaida.getText()+"&"+horaSaida.getText();

                    switch (Objects.requireNonNull(categoriaSaida.getSelectedItem()).toString()){

                        case "Carro":
                           ret = sistema.registraSaida(new Automovel(placaSaida.getText(),Automovel.CARRO),data);
                           s = ret.split(";");
                           infoTipo.setText("Carro");
                           infoPreco.setText(s[0]);
                           infoPlaca.setText(placaSaida.getText());
                            IDRetorno = Integer.parseInt(s[1]);
                            if(IDRetorno >= 1 && IDRetorno <= 100) {
                                for(int i = 0; i < 100; i++) {
                                    if(buttonEstacCarroP[i].getName().equals(Integer.toString(IDRetorno))) {
                                        buttonEstacCarroP[i].setBackground(Color.green);
                                    }
                                }
                            }
                            if(IDRetorno >= 101 && IDRetorno <= 160) {
                                for(int i = 0; i < 100; i++) {
                                    if(buttonEstacCarroT[i].getName().equals(Integer.toString(IDRetorno))) {
                                        buttonEstacCarroT[i].setBackground(Color.green);
                                    }
                                }
                            }
                            infoCarro.setText(Integer.toString(sistema.sizeTerreoMotos()));
                            saidaVeiculos.dispose();
                            pagamento.setVisible(true);
                            break;

                        case "Caminhonete":

                            ret = sistema.registraSaida(new Automovel(placaSaida.getText(),Automovel.CAMINHONETE),data);
                            s = ret.split(";");
                            infoTipo.setText("Carro");
                            infoPreco.setText(s[0]);
                            infoPlaca.setText(placaSaida.getText());
                            IDRetorno = Integer.parseInt(s[1]);
                            if(IDRetorno >= 181 && IDRetorno <= 200) {
                                for(int i = 0; i < 100; i++) {
                                    if(buttonEstacCarroP[i].getName().equals(Integer.toString(IDRetorno))) {
                                        buttonEstacCarroP[i].setBackground(Color.green);
                                    }
                                }
                            }
                            infoCaminhonete.setText(Integer.toString(sistema.sizeTerreoMotos()));
                            saidaVeiculos.dispose();
                            pagamento.setVisible(true);
                            break;

                        case "Motocicleta":
                            ret = sistema.registraSaida(new Automovel(placaSaida.getText(),Automovel.MOTO),data);
                            s = ret.split(";");
                            infoTipo.setText("Carro");
                            infoPreco.setText(s[0]);
                            infoPlaca.setText(placaSaida.getText());
                            IDRetorno = Integer.parseInt(s[1]);
                            if(IDRetorno >= 161 && IDRetorno <= 180) {
                                for(int i = 0; i < 100; i++) {
                                    if(buttonEstacCarroP[i].getName().equals(Integer.toString(IDRetorno))) {
                                        buttonEstacCarroP[i].setBackground(Color.green);
                                    }
                                }
                            }
                            infoMoto.setText(Integer.toString(sistema.sizeTerreoMotos()));
                            saidaVeiculos.dispose();
                            pagamento.setVisible(true);
                            break;

                        default:
                            JOptionPane.showMessageDialog(null, "SELECIONE O TIPO DE VEICULO", "ERRO", JOptionPane.WARNING_MESSAGE);
                            break;
                    }

                }catch (WriteFileException e1){
                    JOptionPane.showMessageDialog(null, e1.getMessage(), "ERRO AO ESCREVER NO ARQUIVO", JOptionPane.ERROR_MESSAGE);
                }catch (ReadFileException e2){
                    JOptionPane.showMessageDialog(null, e2.getMessage(), "ERRO AO LER O ARQUIVO", JOptionPane.ERROR_MESSAGE);
                }catch (PlacaInexistenteException e3){
                    JOptionPane.showMessageDialog(null, "PLACA INEXISTENTE", "ERRO", JOptionPane.ERROR_MESSAGE);
                } catch (ValorInvalidoException e4) {
                    JOptionPane.showMessageDialog(null, "DIGITE UM VALOR VALIDO", "ERRO", JOptionPane.ERROR_MESSAGE);
                }
            }else if(e.getSource() == buttonBackSaida){
                saidaVeiculos.dispose();
            }
        }
    }

    private class ContabileHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource() == buttonOKContabilidade){
                contabilidade.dispose();
            }
        }
    }
}
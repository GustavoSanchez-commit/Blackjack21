package com.Blackjack.Regra;

import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JOptionPane;

public class Regra {

    private ArrayList<String> deck;
    private ArrayList<String> maoJogador;
    private ArrayList<String> maoBanca;

    public Regra() {
        deck = new ArrayList<>();
        maoJogador = new ArrayList<>();
        maoBanca = new ArrayList<>();
        iniciarJogo();
        embaralharBaralho();
    }

    public void iniciar() {
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja jogar novamente?", "Jogar novamente", JOptionPane.YES_NO_OPTION);
        if (resposta == JOptionPane.YES_OPTION) {
            reset();
        } else if (resposta != JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private void reset() {
        deck = new ArrayList<>();
        maoJogador = new ArrayList<>();
        maoBanca = new ArrayList<>();
        iniciarJogo();
        embaralharBaralho();
    }

    private void iniciarJogo() {
        String[] cartas = {"Ás", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valete", "Rainha", "Rei"};
        for (int i = 0; i < 4; i++) { //
            Collections.addAll(deck, cartas);
        }
    }

    private void embaralharBaralho() {
        Collections.shuffle(deck);
    }

    @SuppressWarnings("fallthrough")
    private int calcularPontuacao(ArrayList<String> mao) {
        int pontuacao = 0;
        int ases = 0;
        for (String carta : mao) {
            switch (carta) {
                case "Ás":
                    pontuacao += 11;
                case "Valete":
                case "Rainha":
                case "Rei":
                    pontuacao += 10;
                    break;
                default:
                    pontuacao += Integer.parseInt(carta);
                    break;
            }
        }
        while (pontuacao > 21 && ases > 0) {
            pontuacao -= 10;
            ases--;
        }

        return pontuacao;
    }

    private void distribuirCarta(ArrayList<String> mao) {
        mao.add(deck.remove(0));
    }

    public void jogar() {
        distribuirCarta(maoJogador);
        distribuirCarta(maoBanca);
        distribuirCarta(maoJogador);
        distribuirCarta(maoBanca);

        System.out.println("Sua mão: " + maoJogador);
        System.out.println("Mão da banca: [" + maoBanca.get(0) + ", ??]");

        if (calcularPontuacao(maoJogador) == 21) {
            JOptionPane.showMessageDialog(null, "Blackjack! Você venceu!");
            return;
        }

        while (true) {
            String[] options = {"Mais [ + ]", "Parar [ ? ]"};
            int escolha = JOptionPane.showOptionDialog(null, "Deseja 'Continuar' ou 'Parar'?", "Escolha", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (escolha == 0) { // comprar mais
                distribuirCarta(maoJogador);
                System.out.println("Sua mão: " + maoJogador);
                if (calcularPontuacao(maoJogador) > 21) {
                    JOptionPane.showMessageDialog(null, "Você estourou! Perdeu.");
                    return;
                } else if (escolha == 1) {
                    break;
                }
            }
            System.out.println("Mão da banca: " + maoBanca);
            while (calcularPontuacao(maoBanca) < 17) {
                distribuirCarta(maoBanca);
                System.out.println("Mão da banca: " + maoBanca);
            }
            int pontuacaoJogador = calcularPontuacao(maoJogador);
            int pontuacaoBanca = calcularPontuacao(maoBanca);

            System.out.println("Sua pontuação: " + pontuacaoJogador + "\nPontuação da banca: " + pontuacaoBanca);
            System.out.println();
            System.out.println();
            System.out.println();

            if (pontuacaoBanca > 21 || pontuacaoJogador > pontuacaoBanca) {
                JOptionPane.showMessageDialog(null, "Você venceu!");
                iniciar();
            } else if (pontuacaoJogador < pontuacaoBanca) {
                JOptionPane.showMessageDialog(null, "Você perdeu!");
                iniciar();
            } else {
                JOptionPane.showMessageDialog(null, "Empate!");
                iniciar();
            }
        }
    }

}

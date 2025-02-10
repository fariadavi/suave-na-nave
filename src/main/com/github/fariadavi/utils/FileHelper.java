package main.com.github.fariadavi.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileHelper {
    public static void escreve(String nomeEntrado, int pts) {
        BufferedWriter file;
        try {
            file = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("highscores.dat", true)));
            file.append(nomeEntrado + "|" + pts + "\n");
            file.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

    public static int le() {
        int i = 0, menorValor = 0;
        String[] nomes = new String[20];
        int[] pontos = new int[20];

        BufferedReader fileread;
        try {
            fileread = new BufferedReader(new InputStreamReader(new FileInputStream("highscores.dat")));
            String linha;
            while ((linha = fileread.readLine()) != null) {
                System.out.println(linha);
                nomes[i] = linha.substring(0, linha.indexOf('|'));
                pontos[i] = Integer.parseInt(linha.substring(linha.indexOf('|') + 1, linha.length()));
                i++;
            }
            menorValor = pontos[9];
            fileread.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return menorValor;
    }

    public static String[] getNomes() {
        int i = 0;
        String[] nomes = new String[15];
        int[] pontos = new int[15];
        BufferedReader fileread;
        try {
            fileread = new BufferedReader(new InputStreamReader(new FileInputStream("highscores.dat")));
            String linha;
            while ((linha = fileread.readLine()) != null) {
                nomes[i] = linha.substring(0, linha.indexOf('|'));
                pontos[i] = Integer.parseInt(linha.substring(linha.indexOf('|') + 1, linha.length()));
            }
            fileread.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return nomes;
    }

    public static int[] getPts() {
        int contador = 0;
        String[] nomes;
        int[] pontos = null;
        BufferedReader fileread;
        try {
            fileread = new BufferedReader(new InputStreamReader(new FileInputStream("highscores.dat")));
            String linha;
            while ((linha = fileread.readLine()) != null) {
                contador++;
            }
            nomes = new String[contador];
            pontos = new int[contador];
            for (int i = 0; i < contador; i++) {
                if ((linha = fileread.readLine()) != null) {
                    nomes[i] = linha.substring(0, linha.indexOf('|'));
                    pontos[i] = Integer.parseInt(linha.substring(linha.indexOf('|') + 1, linha.length()));
                }
            }
            fileread.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
        return pontos;
    }

    public static void leitura() {
        int i = 0;
        String[] nomes = new String[20];
        int[] pontos = new int[20];

        BufferedReader fileread;
        try {
            fileread = new BufferedReader(new InputStreamReader(new FileInputStream("highscores.dat")));
            String linha;
            while ((linha = fileread.readLine()) != null) {
                nomes[i] = linha.substring(0, linha.indexOf('|'));
                pontos[i] = Integer.parseInt(linha.substring(linha.indexOf('|') + 1, linha.length()));
                i++;
            }
            fileread.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Erro: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("Erro: " + ex.getMessage());
        }
    }

}

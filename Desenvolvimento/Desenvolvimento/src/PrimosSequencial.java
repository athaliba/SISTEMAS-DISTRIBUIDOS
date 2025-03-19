import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PrimosSequencial {

    public static boolean ePrimo(int n) {
        if (n < 2) return false;
        if (n == 2 || n == 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;
        
        int i = 5;
        while (i * i <= n) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
            i += 6;
        }
        return true;
    }

    public static void processarSequencialmente(String arquivoEntrada, String arquivoSaida) {
        
        List<Integer> numeros = new ArrayList<>();
        List<String> primos = new ArrayList<>();
        long tempoInicial = System.currentTimeMillis();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoEntrada))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                numeros.add(Integer.parseInt(linha.trim()));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }
        
        

        for (int num : numeros) {
            if (ePrimo(num)) {
                primos.add(String.valueOf(num));
            }
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoSaida))) {
            for (String primo : primos) {
                writer.write(primo);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
        
        long tempoFinal = System.currentTimeMillis();
        System.out.printf("Tempo de execução (sequencial): %.4f segundos\n", (tempoFinal - tempoInicial) / 1000.0);
    }

    public static void main(String[] args) {
        processarSequencialmente("Entrada01.txt", "SaidaSequencial.txt");
    }
}

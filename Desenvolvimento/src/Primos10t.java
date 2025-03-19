import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Primos10t
 {

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

    public static void processarEmParalelo(String arquivoEntrada, String arquivoSaida, int numThreads) {
        
        List<Integer> numeros = new ArrayList<>();
        List<String> primos = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoEntrada))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                numeros.add(Integer.parseInt(linha.trim()));
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            return;
        }
        
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Boolean>> resultados = new ArrayList<>();
        long tempoInicial = System.currentTimeMillis();
        
        for (int num : numeros) {
            resultados.add(executor.submit(() -> ePrimo(num)));
        }
        
        for (int i = 0; i < numeros.size(); i++) {
            try {
                if (resultados.get(i).get()) {
                    primos.add(String.valueOf(numeros.get(i)));
                }
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Erro ao processar número: " + e.getMessage());
            }
        }
        
        executor.shutdown();
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoSaida))) {
            for (String primo : primos) {
                writer.write(primo);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
        
        long tempoFinal = System.currentTimeMillis();
        System.out.printf("Tempo de execução (%d threads): %.4f segundos\n", numThreads, (tempoFinal - tempoInicial) / 1000.0);
    }

    public static void main(String[] args) {
        processarEmParalelo("Entrada01.txt", "SaidaParalela_10Threads.txt", 10);
    }
}


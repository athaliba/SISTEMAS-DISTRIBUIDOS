import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class PrimosParalelo5t {

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

    // método que faz o processamento dos números paralelamente para identificar os que são primos
    public static void processarParalelamente(String arquivoEntrada, String arquivoSaida, int numThreads) {
        List<Integer> numeros = new ArrayList<>();
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

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<List<Integer>>> resultados = new ArrayList<>();
        
        int tamanhoParte = (int) Math.ceil((double) numeros.size() / numThreads);
        
        for (int i = 0; i < numThreads; i++) {
            int inicio = i * tamanhoParte;
            int fim = Math.min(inicio + tamanhoParte, numeros.size());
            List<Integer> parte = numeros.subList(inicio, fim);

            Callable<List<Integer>> tarefa = () -> {
                List<Integer> primos = new ArrayList<>();
                for (int num : parte) {
                    if (ePrimo(num)) {
                        primos.add(num);
                    }
                }
                return primos;
            };
            
            resultados.add(executor.submit(tarefa));
        }

        List<Integer> primosFinais = new ArrayList<>();
        for (Future<List<Integer>> futuro : resultados) {
            try {
                primosFinais.addAll(futuro.get());
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Erro na execução da thread: " + e.getMessage());
            }
        }

        executor.shutdown();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoSaida))) {
            for (int primo : primosFinais) {
                writer.write(String.valueOf(primo));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
        }

        long tempoFinal = System.currentTimeMillis();
        System.out.printf("Tempo de execucao (paralelo com %d threads): %.4f segundos\n",
                numThreads, (tempoFinal - tempoInicial) / 1000.0);
    }

    public static void main(String[] args) {
        // define 5 threads para processar os números
        processarParalelamente("Entrada01.txt", "SaidaParalelo5t.txt", 5);
    }
}

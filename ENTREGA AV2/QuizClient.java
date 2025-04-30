import com.google.gson.Gson;
import java.rmi.Naming;
import java.util.Scanner;
import java.util.Map;

/**
 * Cliente que se conecta ao servidor para jogar o Quiz.
 */
public class QuizClient {
    public static void main(String[] args) {
        try {
            QuizService quiz = (QuizService) Naming.lookup("rmi://localhost/QuizService");
            Gson gson = new Gson();

            // Solicita o nome do jogador
            Scanner scanner = new Scanner(System.in);
            System.out.print("Digite seu nome: ");
            String nomeJogador = scanner.nextLine();

            // Iterar pelas 10 perguntas
            for (int i = 0; i < 10; i++) {
                String perguntaJson = quiz.obterPergunta();
                Pergunta pergunta = gson.fromJson(perguntaJson, Pergunta.class);

                System.out.println("Pergunta: " + pergunta.texto);
                for (int j = 0; j < pergunta.opcoes.length; j++) {
                    System.out.println((j + 1) + ": " + pergunta.opcoes[j]);
                }

                // Lê a resposta do usuário
                System.out.print("Escolha sua resposta (número): ");
                int escolha = scanner.nextInt();

                // Verifica se a resposta está correta e envia a resposta para o servidor
                String respostaSelecionada = pergunta.opcoes[escolha - 1];
                boolean correta = quiz.enviarResposta(pergunta.id, respostaSelecionada, nomeJogador);
                if (correta) {
                    System.out.println("Resposta correta! +1 ponto!");
                } else {
                    System.out.println("Resposta incorreta.");
                }

                // Aguarda 1 segundo antes da próxima pergunta
                Thread.sleep(1000); // Espera 1 segundo antes de exibir a próxima pergunta
            }

            // Exibir o ranking após o quiz
            Map<String, Integer> ranking = quiz.obterRanking();
            System.out.println("\nRanking:");
            ranking.forEach((nome, pontos) -> {
                System.out.println(nome + ": " + pontos + " pontos");
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Collections;
import java.util.Map;
import java.util.HashMap;

/**
 * Servidor que implementa o serviço de Quiz.
 */
public class QuizServer extends UnicastRemoteObject implements QuizService {
    private static final long serialVersionUID = 1L;
    private List<Pergunta> perguntas;
    private List<Pergunta> perguntasRestantes;  // Lista para gerenciar perguntas restantes
    private Map<String, Integer> ranking;  // Mapa para armazenar o nome do jogador e sua pontuação
    private Gson gson = new Gson();
    private Random random = new Random();

    protected QuizServer() throws RemoteException {
        super();
        perguntas = carregarPerguntas();  // Carregar as perguntas do arquivo JSON
        Collections.shuffle(perguntas);  // Embaralha as perguntas para garantir que não se repitam
        perguntasRestantes = perguntas;  // A lista de perguntas restantes
        ranking = new HashMap<>();  // Inicializa o ranking
    }

    // Método para carregar as perguntas do arquivo JSON
    private List<Pergunta> carregarPerguntas() {
        try {
            FileReader reader = new FileReader("perguntas.json");
            return gson.fromJson(reader, new TypeToken<List<Pergunta>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String obterPergunta() throws RemoteException {
        // Verifica se há perguntas restantes
        if (perguntasRestantes.isEmpty()) {
            perguntasRestantes = perguntas;  // Se todas as perguntas foram feitas, reinicia a lista
            Collections.shuffle(perguntasRestantes);  // Embaralha novamente
        }

        // Pega a próxima pergunta
        Pergunta pergunta = perguntasRestantes.remove(0);
        return gson.toJson(pergunta);  // Retorna a pergunta em formato JSON
    }

    @Override
    public boolean enviarResposta(String idPergunta, String resposta, String nomeJogador) throws RemoteException {
        boolean correta = false;
        for (Pergunta pergunta : perguntas) {
            if (pergunta.id.equals(idPergunta)) {
                correta = pergunta.respostaCorreta.equalsIgnoreCase(resposta);
                break;
            }
        }

        // Atualiza a pontuação do jogador no ranking
        if (correta) {
            ranking.put(nomeJogador, ranking.getOrDefault(nomeJogador, 0) + 1);
        }

        return correta;
    }

    public Map<String, Integer> obterRanking() {
        // Retorna o ranking ordenado pela pontuação (decrescente)
        Map<String, Integer> sortedRanking = new HashMap<>(ranking);
        sortedRanking.entrySet()
            .stream()
            .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))  // Ordena em ordem decrescente
            .forEachOrdered(x -> sortedRanking.put(x.getKey(), x.getValue()));
        return sortedRanking;
    }

    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            QuizServer servidor = new QuizServer();
            Naming.rebind("QuizService", servidor);
            System.out.println("Servidor de Quiz rodando...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

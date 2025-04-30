import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;  // Importando Map

/**
 * Interface remota para o serviço de Quiz.
 */
public interface QuizService extends Remote {
    // Método para obter uma pergunta do servidor
    String obterPergunta() throws RemoteException;
    
    // Método para enviar a resposta e verificar se está correta
    boolean enviarResposta(String idPergunta, String resposta, String nomeJogador) throws RemoteException;

    // Método para obter o ranking de todos os jogadores
    Map<String, Integer> obterRanking() throws RemoteException;  // Retorna um Map com o ranking
}

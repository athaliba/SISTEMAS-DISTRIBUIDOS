public class Pergunta {
    public String id;
    public String texto;
    public String[] opcoes;
    public String respostaCorreta;

    public Pergunta(String id, String texto, String[] opcoes, String respostaCorreta) {
        this.id = id;
        this.texto = texto;
        this.opcoes = opcoes;
        this.respostaCorreta = respostaCorreta;
    }
}

package exemplografo;

import java.util.ArrayList;


public class Grafo<TIPO> {
    private ArrayList<Vertice<TIPO>> vertices;
    private ArrayList<Aresta<TIPO>> arestas;

    public Grafo() {
        this.vertices = new ArrayList<Vertice<TIPO>>();
        this.arestas = new ArrayList<Aresta<TIPO>>();
    }

    public void adicionarVertice(TIPO dado) {
        Vertice<TIPO> novoVertice = new Vertice<TIPO>(dado);
        this.vertices.add(novoVertice);
    }

    public Vertice<TIPO> getVertice(TIPO dado) {
        Vertice<TIPO> vertice = null;
        for (int i = 0; i < this.vertices.size(); i++) {
            if (this.vertices.get(i).getDado().equals(dado)) {
                vertice = this.vertices.get(i);
                break;
            }
        }
        return vertice;
    }

    public void adicionarAresta(Double peso, TIPO dadoInicio, TIPO dadoFim) {
        Vertice<TIPO> inicio = this.getVertice(dadoInicio);
        Vertice<TIPO> fim = this.getVertice(dadoFim);
        Aresta<TIPO> aresta = new Aresta<TIPO>(peso, inicio, fim);
        inicio.adicionarArestaSaida(aresta);
        fim.adicionarArestaEntrada(aresta);
        this.arestas.add(aresta);
    }

    // Método para verificar se existe uma rua que faz o caminho de volta entre dois pontos
    public boolean existeRetorno(Vertice<TIPO> origem, Vertice<TIPO> destino) {
        for (Aresta<TIPO> aresta : destino.getArestasSaida()) {
            if (aresta.getFim().equals(origem)) {
                return true;
            }
        }
        return false;
    }

    public void BuscaEmLargura(TIPO dadoInicio) {
        Vertice<TIPO> inicio = this.getVertice(dadoInicio);
        if (inicio == null) {
            System.out.println("Vértice inicial não encontrado!");
            return;
        }

        ArrayList<Vertice<TIPO>> marcados = new ArrayList<Vertice<TIPO>>();
        ArrayList<Vertice<TIPO>> fila = new ArrayList<Vertice<TIPO>>();

        marcados.add(inicio);
        System.out.println("Iniciando varredura a partir de: " + inicio.getDado() + "\n");
        fila.add(inicio);

        while (fila.size() > 0) {
            Vertice<TIPO> visitado = fila.get(0);
            
            for (int i = 0; i < visitado.getArestasSaida().size(); i++) {
                Aresta<TIPO> arestaSaida = visitado.getArestasSaida().get(i);
                Vertice<TIPO> proximo = arestaSaida.getFim();
                
                // Verifica se há uma rua de retorno voltando para a rua atual
                boolean temRetorno = existeRetorno(visitado, proximo);
                
                System.out.println(" ➔ Passa por [" + visitado.getDado() + "] e vai para ➔ [" + proximo.getDado() + "] (Distância: " + arestaSaida.getPeso() + "m)");
                if (temRetorno) {
                    System.out.println("   [ATENÇÃO]: Existe rua de retorno ligando [" + proximo.getDado() + "] de volta para [" + visitado.getDado() + "]!");
                } else {
                    System.out.println("   [ATENÇÃO]: Não há rua direta de retorno de [" + proximo.getDado() + "] para [" + visitado.getDado() + "].");
                }

                if (!marcados.contains(proximo)) {
                    marcados.add(proximo);
                    fila.add(proximo);
                }
            }
            fila.remove(0);
        }
    }
}
package uece.maxmatching;

/**
 * Created by jeffrodrigo on 06/07/16.
 *
 * Representa a aresta de um grafo
 */
public class Aresta {
    public int peso;
    public Status status = Status.LIVRE;
    public Vertice origem;
    public Vertice destino;
    
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
      
}

package boaviagem.casadocodigo.com.br.boaviagem.boaviagem.casadocodigo.com.br.boaviagem.model;

/**
 * Created by Jorge on 2/8/15.
 */
public class Viagem {

    private String destino;
    private String dataSaida;
    private String orcamento;
    private int tipoViagem;
    private String dataChegada;
    private String quantidadePessoas;

    public int getTipoViagem() {
        return tipoViagem;
    }

    public void setTipoViagem(int tipoViagem) {
        this.tipoViagem = tipoViagem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getDataChegada() {
        return dataChegada;
    }

    public void setDataChegada(String dataChegada) {
        this.dataChegada = dataChegada;
    }

    public String getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(String dataSaida) {
        this.dataSaida = dataSaida;
    }

    public String getQuantidadePessoas() {
        return quantidadePessoas;
    }

    public void setQuantidadePessoas(String quantidadePessoas) {
        this.quantidadePessoas = quantidadePessoas;
    }

    public String getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(String orcamento) {
        this.orcamento = orcamento;
    }
}

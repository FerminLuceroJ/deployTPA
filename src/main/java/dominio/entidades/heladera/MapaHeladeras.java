package dominio.entidades.heladera;

import java.util.List;

public class MapaHeladeras {

    private List<Heladera> heladeras;

    public MapaHeladeras(List<Heladera> heladeras) {
        this.heladeras = heladeras;
    }

    public List<String> listarHeladeras() {
        return heladeras.stream().map(heladera -> heladera.toString()).toList();
    }
}

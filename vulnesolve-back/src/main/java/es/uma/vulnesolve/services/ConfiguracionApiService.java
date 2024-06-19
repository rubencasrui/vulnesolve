package es.uma.vulnesolve.services;

import es.uma.vulnesolve.models.dto.ConfiguracionApi;
import es.uma.vulnesolve.repositories.ConfiguracionApiRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConfiguracionApiService {

    private ConfiguracionApiRepository configuracionApiRepository;

    public ConfiguracionApiService(ConfiguracionApiRepository configuracionApiRepository) {
        this.configuracionApiRepository = configuracionApiRepository;
    }

    // CREATE

    public ConfiguracionApi crearConfiguracionApi(ConfiguracionApi configuracionApi) {
        return configuracionApiRepository.save(configuracionApi);
    }

    // READ

    public List<ConfiguracionApi> buscarTodos() {
        return configuracionApiRepository.findAll();
    }

    public Optional<ConfiguracionApi> buscarPorId(Long id) {
        return configuracionApiRepository.findById(id);
    }

    public Optional<ConfiguracionApi> buscarPorNombre(String nombre) {
        return configuracionApiRepository.buscarPorNombre(nombre);
    }

    // UPDATE

    public ConfiguracionApi actualizarConfiguracionApi(ConfiguracionApi configuracionApi) {
        return configuracionApiRepository.save(configuracionApi);
    }

    // DELETE

    public void eliminarConfiguracionApi(Long id) {
        configuracionApiRepository.deleteById(id);
    }

}

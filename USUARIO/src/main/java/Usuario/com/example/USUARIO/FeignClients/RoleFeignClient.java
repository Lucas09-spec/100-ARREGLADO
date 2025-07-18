package Usuario.com.example.USUARIO.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "roles-service", url = "http://localhost:8081/api/v1/roles")
public interface RoleFeignClient {

    @GetMapping("/{id}")
    RoleResponse getRoleById(@PathVariable("id") Long id);

    // Método extra para obtener solo el nombre del rol (útil para el controller)
    default String obtenerNombreRol(Long id) {
        RoleResponse rol = getRoleById(id);
        return (rol != null) ? rol.getNombre() : null;
    }

    class RoleResponse {
        private Integer id;
        private String nombre;

        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }
}

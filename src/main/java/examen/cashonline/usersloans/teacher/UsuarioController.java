package examen.cashonline.usersloans.teacher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class UsuarioController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    List<Usuario> getTeachers() {
        return usuarioRepository.findAll();
    }

    @PostMapping
    Usuario createTeacher(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}

package examen.cashonline.usersloans.subject;

import examen.cashonline.usersloans.teacher.Usuario;
import examen.cashonline.usersloans.teacher.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subjects")
public class PrestamoController {

    @Autowired
    PrestamoRepository prestamoRepository;


    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping
    List<Prestamo> getSubjects() {
        return prestamoRepository.findAll();
    }

    @PostMapping
    Prestamo createSubject(@RequestBody Prestamo prestamo) {
        return prestamoRepository.save(prestamo);
    }

    @PutMapping("/{subjectId}/students/{studentId}")
    Prestamo addStudentToSubject(
            @PathVariable Long subjectId,
            @PathVariable Long studentId
    ) {
        Prestamo prestamo = prestamoRepository.findById(subjectId).get();
        return prestamoRepository.save(prestamo);
    }

    @PutMapping("/{subjectId}/teacher/{teacherId}")
    Prestamo assignTeacherToSubject(
            @PathVariable Long subjectId,
            @PathVariable Long teacherId
    ) {
        Prestamo prestamo = prestamoRepository.findById(subjectId).get();
        Usuario usuario = usuarioRepository.findById(teacherId).get();
        prestamo.setUsuario(usuario);
        return prestamoRepository.save(prestamo);
    }
}

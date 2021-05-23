package examen.cashonline.usersloans.controller;

import examen.cashonline.usersloans.entity.User;
import examen.cashonline.usersloans.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable(value = "id") int id){
        final Optional<User> user = userService.getUser(id);
        if(!user.isPresent()) return new ResponseEntity<String>(format("Usuario %d no encontrado", id), HttpStatus.NOT_FOUND);
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "id") int id){
        if(userService.deleteUser(id)) return new ResponseEntity<String>(format("Usuario %d eliminado", id), HttpStatus.OK);
        else return new ResponseEntity<String>(format("Usuario %d no encontrado", id), HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<String> saveUser(@RequestBody User user){
        final User savedUser = userService.saveUser(user);
        return new ResponseEntity<String>(format("Usuario con Id %d creado", savedUser.getId()), HttpStatus.CREATED);
    }

}

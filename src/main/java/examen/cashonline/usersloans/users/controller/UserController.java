package examen.cashonline.usersloans.users.controller;

import examen.cashonline.usersloans.users.entity.User;
import examen.cashonline.usersloans.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    List<User> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable(value = "user_id") Long user_id){
        final Optional<User> user = userService.getOneUser(user_id);
        if(!user.isPresent()) return new ResponseEntity<String>(format("Usuario %d no encontrado", user_id), HttpStatus.NOT_FOUND);
        return new ResponseEntity<User>(user.get(), HttpStatus.OK);
    }

    @DeleteMapping("/{user_id}")
    ResponseEntity<?> deleteUser(@PathVariable(name = "user_id") Long user_id) {
        if(userService.deleteUser(user_id)) return new ResponseEntity<String>(format("Usuario %d eliminado", user_id), HttpStatus.OK);
        else return new ResponseEntity<String>(format("Usuario %d no encontrado", user_id), HttpStatus.NOT_FOUND);
    }

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody User user) {
        final User savedUser = userService.saveUser(user);
        return new ResponseEntity<String>(format("Usuario con Id %d creado", savedUser.getId()), HttpStatus.CREATED);
    }


}

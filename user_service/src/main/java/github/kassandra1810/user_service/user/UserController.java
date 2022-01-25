package github.kassandra1810.user_service.user;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/users")
public class UserController {

    private final UserService userService;
    private final Environment environment;

    @Autowired
    public UserController(final UserService userService, Environment environment) {
        this.userService = userService;
        this.environment = environment;
    }

    @ApiOperation(value = "Show all users", notes = "provide information about all users")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "test")
    public String getTest() {
        return "Test";
    }

    @ApiOperation(value = "Find user by id", notes = "provide information about user by id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<User> getUserById(@ApiParam(value = "unique id of user", example = "1") @PathVariable String id) {
        System.out.println("Accessing user-service on port:" +  environment.getProperty("local.server.port"));
        final Optional<User> userOptional = userService.getUserById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Validated final UserDetails userDetails) {
        final User createdUser = userService.createUser(userDetails);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> updateUser(@PathVariable final String id, @RequestBody @Validated final UserDetails userDetails) {
        final User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser != null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable final String id) {
        if (userService.deleteUser(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }









}

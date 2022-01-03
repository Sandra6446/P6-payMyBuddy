package com.api.payMyBuddy.service;


/*
@ExtendWith(SpringExtension.class)
class LoginServiceTest {

    @MockBean
    private UserEntityRepository userEntityRepository;

    private LoginService loginService;

    private static UserEntity userEntity;

    @BeforeAll
    private static void setUp() {
        // UserEntity
        userEntity = new UserEntity(2,"user@email.com", "Prenom", "Nom", "pwd",  20.5, "bank", "iban", "bic", null, null, null);
    }

    @BeforeEach
    private void setUpPerTest() {
        loginService = new LoginService(userEntityRepository);
    }

    @Test
    void checkLogin() {

        // Login
        Login login = new Login("user@email.com","pwd",null);
        String response = "User " + userEntity.getEmail() + " authorized.";

        // Test to check an authorized user
        Mockito.when(userEntityRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(userEntity));
        Assertions.assertEquals(ResponseEntity.ok(response), loginService.checkLogin(login));

        // Test to check a user with bad email
        response = "Email or password incorrect";
        Mockito.when(userEntityRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.empty());
        Assertions.assertEquals(new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED), loginService.checkLogin(login));

        // Test to check a user with bad password
        login.setPassword("badPassword");
        Mockito.when(userEntityRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(userEntity));
        Assertions.assertEquals(new ResponseEntity<>(response,HttpStatus.UNAUTHORIZED), loginService.checkLogin(login));

    }
}
 */
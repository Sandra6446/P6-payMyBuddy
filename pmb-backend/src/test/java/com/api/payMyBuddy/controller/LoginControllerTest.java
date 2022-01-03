package com.api.payMyBuddy.controller;

/*

@WebMvcTest(controllers = LoginController.class)
class LoginControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    private static Login login;

    @BeforeAll
    private static void setUp() {
        login = new Login("user@email.com", "pwd",null);
    }

    @Test
    void checkLogin() throws Exception {

        String response = "User authorized";

        // Test to check a good Login
        Mockito.when(loginService.checkLogin(ArgumentMatchers.any(Login.class))).thenReturn(ResponseEntity.ok(response));
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(login.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(response));

        // Test to check an empty Login
        Login loginEmpty = new Login();
        mockMvc.perform(MockMvcRequestBuilders.post("/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(loginEmpty.toString()))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}

 */
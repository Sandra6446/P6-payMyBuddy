package com.api.payMyBuddy.service;

/*
@Service
public class LoginService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
    private final UserEntityRepository userEntityRepository;

    public LoginService(UserEntityRepository userEntityRepository) {
        this.userEntityRepository = userEntityRepository;
    }

    @Transactional(readOnly = true, isolation = Isolation.READ_COMMITTED)
    public ResponseEntity<String> checkLogin(Login login) {
        boolean authorized = false;
        Optional<UserEntity> userEntityOptional = userEntityRepository.findByEmail(login.getEmail());
        if (userEntityOptional.isPresent()) {
            authorized = (userEntityOptional.get().getPassword().equals(login.getPassword()));
        }
        if (authorized) {
            logger.info(String.format("User %s authorized", login.getEmail()));
            return ResponseEntity.ok("User authorized");
        } else {
            logger.error("Email or password incorrect");
            throw new UnauthorizedException("Email or password incorrect");
        }
    }


}

 */


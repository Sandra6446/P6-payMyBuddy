import http from '../http-common'

class LoginService {

    submit(data) {
        return http.post('/login', data)
    }

}

export default new LoginService()
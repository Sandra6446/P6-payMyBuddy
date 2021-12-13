import http from '../http-common'

class LoginService {

    submit(data) {
        return http.post('/login', data)
            .then((response) => {
                return response;
            })
            .catch((e) => {
                throw e;
            });
    }

}

export default new LoginService()
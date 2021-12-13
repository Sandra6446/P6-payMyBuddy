import http from '../http-common'

class UserService {

    add(data) {
        return http.post('/user', data)
    }

    getUser(email) {
        return http.get('/user/'+email)
            .then((response) => {
                return response;
            })
            .catch((e) => {
                throw e;
            });
    }

}

export default new UserService()
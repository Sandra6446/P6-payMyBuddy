import http from '../http-common'

class UserService {

    submit(data) {
        return http.post('/user', data)
    }

}

export default new UserService()
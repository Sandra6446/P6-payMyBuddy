import http from '../http-common'

class ConnectionService {

    submit(data) {
        return http.post('/connection', data)
    }

}

export default new ConnectionService()
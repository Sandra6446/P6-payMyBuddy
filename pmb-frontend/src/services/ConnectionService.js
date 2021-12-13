import http from '../http-common'

class ConnectionService {

    getConnections(email) {
        return http.get('/connection/'+email)
            .then((response) => {
                return response;
            })
            .catch((e) => {
                throw e;
            });
    }

    addConnection(data) {
        return http.post('/connection',data)
            .then((response) => {
                return response;
            })
            .catch((e) => {
                throw e;
            });
    }

}

export default new ConnectionService()
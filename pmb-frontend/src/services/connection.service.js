import axios from 'axios';
import authHeader from './auth.header'

const API_URL = 'http://localhost:8080/api/connection';

class ConnectionService {

    getConnections(email) {
        return axios.get(API_URL + '/' + email, { headers: authHeader() })
    }

    addConnection(data) {
        return axios.post(API_URL, data, { headers: authHeader() })
    }

}

export default new ConnectionService()
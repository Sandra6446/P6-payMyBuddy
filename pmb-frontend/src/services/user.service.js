import axios from 'axios';
import authHeader from './auth.header'

const API_URL = 'http://localhost:8080/api/user';

class UserService {

    getUser(email) {
        return axios.get(API_URL + '/' + email, { headers: authHeader() })
    }

    update(email, data) {
        return axios.put(API_URL + '/' + email, data, { headers: authHeader() })
    }

}

export default new UserService()
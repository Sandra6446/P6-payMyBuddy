import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService {

    login(user) {
        return axios
            .post(API_URL + 'login', user)
            .then(response => {
                if (response.data.accessToken) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(data) {
        return axios
            .post(API_URL + 'signup', data);
}

}

export default new AuthService()
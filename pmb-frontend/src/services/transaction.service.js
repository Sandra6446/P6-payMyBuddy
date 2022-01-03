import axios from 'axios';
import authHeader from './auth.header'

const API_URL = 'http://localhost:8080/api/transaction';

class TransactionService {

    getTransactions(email) {
        return axios.get(API_URL + '/' + email, { headers: authHeader() })
    }

    getMyTransactions(email) {
        return axios.get(API_URL + '/' + email + "/myTransactions", { headers: authHeader() })
    }

    addTransaction(data) {
        return axios.post(API_URL, data, { headers: authHeader() })
    }

}

export default new TransactionService()
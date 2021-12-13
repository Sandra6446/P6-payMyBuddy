import http from '../http-common'

class TransactionService {

    getTransactions(email) {
        return http.get('/transaction/'+email)
            .then((response) => {
                return response;
            })
            .catch((e) => {
                throw e;
            });
    }

    getMyTransactions(email) {
        return http.get('/transaction/'+email+"/myTransactions")
            .then((response) => {
                return response;
            })
            .catch((e) => {
                throw e;
            });
    }

    addTransaction(data) {
        return http.post('/transaction/',data)
            .then((response) => {
                return response;
            })
            .catch((e) => {
                throw e;
            });
    }

}

export default new TransactionService()
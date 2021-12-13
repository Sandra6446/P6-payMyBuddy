<template>
  <div class="container-fluid" id="transfer">
    <Header></Header>

    <nav aria-label="breadcrumb" class="bg-light">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <router-link
            :to="{ name: 'Home', params: { email: this.$route.params.email } }"
            >Home</router-link
          >
        </li>
        <li class="breadcrumb-item active" aria-current="page">Transfer</li>
      </ol>
    </nav>

    <div class="row justify-content-center">
      <div class="col-9">
        <div class="row align-items-center justify-content-between">
          <div class="col-auto text-center">Send Money</div>
          <div class="col-auto">
            <router-link
              :to="{
                name: 'Connection',
                params: { email: this.$route.params.email },
              }"
            >
              <button type="button" class="btn btn-primary">
                Add Connection
              </button>
            </router-link>
          </div>
        </div>

        <form class="needs-validation">
          <div
            class="card h-100 text-dark bg-light mb-3 justify-content-center"
          >
            <div class="card-body">
              <div class="row align-items-center justify-content-center">
                <div class="col-4">
                  <select
                    class="form-select form-select-md"
                    aria-label="Select connection"
                    v-model="form.connectionEmail"
                  >
                    <option value="" selected>Connections</option>
                    <option
                      v-for="connection in connections"
                      :key="connection.name"
                      :value="connection.email"
                    >
                      {{ connection.name }}
                    </option>
                  </select>
                </div>
                <div class="col-2">
                  <input
                    type="number"
                    class="form-control"
                    value="0"
                    aria-label="Amount"
                    v-model="form.amount"
                  />
                </div>
                <div class="d-grid col-2">
                  <button
                    class="btn col-12"
                    id="payButton"
                    type="submit"
                  >
                    Pay
                  </button>
                </div>
              </div>
            </div>
          </div>
        </form>

        <table
          id="last_transactions"
          class="table text-center table-striped caption-top"
        >
          <caption>
            My transactions
          </caption>

          <thead>
            <tr>
              <th scope="col">Connection</th>
              <th scope="col">Description</th>
              <th scope="col">Amount</th>
            </tr>
          </thead>
          <tbody>
            <tr
              v-for="transaction in transactions"
              v-bind:key="transaction.date"
            >
              <td>{{ transaction.connection.name }}</td>
              <td>{{ transaction.description }}</td>
              <td>{{ transaction.amount }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

 <script>
import Header from "../components/Header.vue";
import ConnectionService from "../services/ConnectionService";
import TransactionService from "../services/TransactionService";

export default {
  name: "Transfer",
  components: {
    Header,
  },
  data() {
    return {
      connections: [],
      transactions: [],
      form: {
        connectionEmail: "",
        amount: 0,
      },
    };
  },
  methods: {
    getConnections(email) {
      ConnectionService.getConnections(email)
        .then((response) => {
          this.connections = response.data;
        })
        .catch((e) => {
          if (e.response) {
            alert(e.response.data);
          } else {
            alert(e);
          }
        });
    },
    getTransactions(email) {
      TransactionService.getMyTransactions(email)
        .then((response) => {
          this.transactions = response.data;
        })
        .catch((e) => {
          if (e.response) {
            alert(e.response.data);
          } else {
            alert(e);
          }
        });
    },
  },
  mounted() {
    if (this.$route.params.email !== undefined) {
      this.getConnections(this.$route.params.email);
      this.getTransactions(this.$route.params.email);
    } else {
      this.$router.replace({ name: "Login" });
    }
  },
};
</script>

 <style scoped>
form {
  height: 8rem;
}

form .row {
  height: 8rem;
}

.input-symbol-euro {
  position: relative;
}
.input-symbol-euro input {
  padding-left: 18px;
}

.input-symbol-euro:before {
  position: absolute;
  top: 8px;
  content: "€";
  right: 40px;
}

input[type="number"]::-webkit-inner-spin-button,
input[type="number"]::-webkit-outer-spin-button {
  opacity: 1;
}

#payButton {
  background: #28a745;
  color: white;
}
</style>


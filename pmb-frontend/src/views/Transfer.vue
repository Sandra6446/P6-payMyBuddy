<template>
  <div class="container-fluid" id="transfer">
    <Header></Header>

    <nav aria-label="breadcrumb" class="bg-light">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <router-link to="/home">Home</router-link>
        </li>
        <li class="breadcrumb-item active" aria-current="page">Transfer</li>
      </ol>
    </nav>

    <div class="row justify-content-center">
      <div class="col-9">
        <div class="row align-items-center justify-content-between">
          <div class="col-auto text-center">Send Money</div>
          <div class="col-auto">
            <router-link to="/connection">
              <button type="button" class="btn btn-primary">
                Add Connection
              </button>
            </router-link>
          </div>
        </div>

        <form class="needs-validation" v-on:submit.prevent="onPay">
          <div
            class="card h-100 text-dark bg-light mb-3 justify-content-center"
          >
            <div class="card-body">
              <div class="row align-items-center justify-content-center">
                <div class="col-4">
                  <select
                    class="form-select form-select-md"
                    id="connection-select"
                    aria-label="Select connection"
                  >
                    <option value="">Connections</option>
                    <option
                      v-for="connection in connections"
                      :key="connection.name"
                      :value="connection.connectionEmail"
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
                    v-model="amount"
                  />
                </div>
                <div class="d-grid col-2">
                  <button class="btn col-12" id="payButton" type="submit">
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
              <td>{{ transaction.connectionName }}</td>
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
import ConnectionService from "../services/connection.service";
import TransactionService from "../services/transaction.service";

export default {
  name: "Transfer",
  components: {
    Header,
  },
  data() {
    return {
      connections: [],
      transactions: [],
      amount: 0,
    };
  },
  methods: {
    onPay() {
      var selection = document.querySelector("#connection-select");
      var index = selection.selectedIndex;
      var connection = {
        name: selection[index].text,
        connectionEmail: selection[index].value,
      };
      if (connection.connectionEmail === "" || this.amount <= 0) {
        alert("A connection and a positive amount are required");
      } else {
        this.$router.push({
          name: "Summary",
          params: {
            connection: connection,
            amount: this.amount,
          },
        });
      }
    },
  },
  mounted() {
    if (this.$store.state.auth.user !== null) {
      ConnectionService.getConnections(this.userEmail).then(
        (response) => {
          this.connections = response.data;
        },
        (error) => {
          alert(error.response.data.error || error.message || error.toString());
          this.$store.dispatch("auth/logout");
          this.$router.push("/login");
        }
      );
      TransactionService.getMyTransactions(this.userEmail).then(
        (response) => {
          this.transactions = response.data;
        },
        (error) => {
          alert(error.response.data.error || error.message || error.toString());
          if (error.response.data.status === 401) {
            this.$store.dispatch("auth/logout");
            this.$router.push("/login");
          } else {
            this.$router.push("/home");
          }
        }
      );
    } else {
      this.$router.push("/login");
    }
  },
  computed: {
    userEmail: function () {
      return this.$store.state.auth.user.email;
    },
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


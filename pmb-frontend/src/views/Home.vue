<template>
  <div class="container-fluid" id="home">
    <Header></Header>

    <nav aria-label="breadcrumb" class="bg-light">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">Home</li>
      </ol>
    </nav>

    <div class="col col-10 mx-auto">
      <div class="row justify-content-md-center my-5">
        <h1>Welcome {{ fullName }}</h1>
      </div>
      <div class="card text-center my-5">
        <div class="card-header">Available balance</div>
        <div class="card-body">
          <p class="card-text">{{ this.currentUser.balance }} €</p>
        </div>
      </div>

      <table
        id="last_transactions"
        class="table text-center table-striped caption-top"
      >
        <caption>
          Last transactions
        </caption>

        <thead>
          <tr>
            <th scope="col">Connection</th>
            <th scope="col">Description</th>
            <th scope="col">Amount</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="transaction in transactions" v-bind:key="transaction.date">
            <td>{{ transaction.connection.name }}</td>
            <td>{{ transaction.description }}</td>
            <td>{{ transaction.amount }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

 <script>
import Header from "../components/Header.vue";
import UserService from "../services/UserService";
import TransactionService from "../services/TransactionService";

export default {
  name: "Home",
  components: {
    Header,
  },
  data() {
    return {
      currentUser: {
        firstName: "",
        lastName: "",
      },
      transactions: [],
    };
  },
  methods: {
    getUser(email) {
      UserService.getUser(email)
        .then((response) => {
          this.currentUser = response.data;
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
      TransactionService.getTransactions(email)
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
      this.getUser(this.userEmail);
      this.getTransactions(this.userEmail);
    } else {
      this.$router.replace({ name: "Login" });
    }
  },
  computed: {
    fullName: function () {
      return this.currentUser.firstName + " " + this.currentUser.lastName;
    },
    userEmail() {
      return this.$route.params.email
    }
  },
};
</script>

<style scoped>
.card-text {
  color: #28a745;
}
</style>

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
        <h1>
          Welcome {{ this.currentUser.firstName }}
          {{ this.currentUser.lastName }}
        </h1>
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
            <td>{{ transaction.connectionName }}</td>
            <td>{{ transaction.description }}</td>
            <td v-if="transaction.type === 'CREDIT'">
              + {{ transaction.amount }}
            </td>
            <td v-else>- {{ transaction.amount }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

 <script>
import Header from "../components/Header.vue";
import UserService from "../services/user.service";
import TransactionService from "../services/transaction.service";

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
        balance: 0,
      },
      transactions: [],
    };
  },
  computed: {
    userEmail: function() {
      return this.$store.state.auth.user.email;
    },
  },
  mounted() {
    if (this.$store.state.auth.user !== null) {
      UserService.getUser(this.userEmail).then(
        (response) => {
          this.currentUser = response.data;
        },
        (error) => {
          alert(error.response.data || error.message || error.toString());
          this.$store.dispatch("auth/logout");
          this.$router.push("/login");
        }
      );
      TransactionService.getTransactions(this.userEmail).then(
        (response) => {
          this.transactions = response.data;
        },
        (error) => {
          alert(error.response.data || error.message || error.toString());
          this.$store.dispatch("auth/logout");
          this.$router.push("/login");
        }
      );
    } else {
      this.$router.push("/login");
    }
  },
};
</script>

<style scoped>
.card-text {
  color: #28a745;
}
</style>

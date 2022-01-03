<template>
  <div class="container-fluid" id="summary">
    <Header></Header>

    <nav aria-label="breadcrumb" class="bg-light">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <router-link to="/home">Home</router-link>
        </li>
        <li class="breadcrumb-item">
          <router-link to="/transfer">Transfer</router-link>
        </li>
        <li class="breadcrumb-item active" aria-current="page">Summary</li>
      </ol>
    </nav>

    <div class="row justify-content-center">
      <div class="col-9">
        <form class="needs-validation" v-on:submit.prevent="sendPaiement">
          <div class="card my-5">
            <div class="card-header text-start fw-bold">Connection</div>
            <div class="card-body">
              <p class="card-text">{{ this.connection.name }}</p>
              <p class="card-text">{{ this.connection.connectionEmail }}</p>
            </div>
          </div>

          <div class="card my-5">
            <div class="card-header text-start fw-bold">Description</div>
            <div class="card-body">
              <p class="card-text">
                <textarea
                  class="form-control"
                  rows="3"
                  v-model="description"
                  maxlength="25"
                  required
                ></textarea>
              </p>
            </div>
          </div>

          <div class="card my-5">
            <div class="card-header text-start fw-bold">Amount</div>
            <div class="card-body">
              <p class="card-text" id="amount">{{ this.amount }}€</p>
            </div>
          </div>

          <div class="col-8 col-sm-3 mx-auto">
            <SubmitButton lib="Send"></SubmitButton>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

 <script>
import Header from "../components/Header.vue";
import SubmitButton from "../components/SubmitButton.vue";
import TransactionService from "../services/transaction.service";

export default {
  name: "Summary",
  components: {
    Header,
    SubmitButton,
  },
  data() {
    return {
      description: "",
    };
  },
  mounted() {
    if (this.$store.state.auth.user === null) {
      this.$router.push("/login");
    } else if (
      this.$route.params.connection === undefined ||
      this.$route.params.amount === undefined
    ) {
      this.$router.push("/transfer");
    }
  },
  methods: {
    sendPaiement() {
      var data = {
        userEmail: this.userEmail,
        connectionEmail: this.connection.connectionEmail,
        connectionName: this.connection.name,
        description: this.description.trim(),
        amount: this.amount,
      };
      TransactionService.addTransaction(data).then(
        () => {
          alert("Transfer done");
          this.$router.push("/transfer");
        },
        (error) => {
          alert(error.response.data || error.message || error.toString());
          if (error.response.data.status === 401) {
            this.$store.dispatch("auth/logout");
            this.$router.push("/login");
          }
        }
      );
    },
  },
  computed: {
    userEmail: function () {
      return this.$store.state.auth.user.email;
    },
    connection: function () {
      return this.$route.params.connection;
    },
    amount: function () {
      return this.$route.params.amount;
    },
  },
};
</script>

<style scoped>
.card-text {
  color: #28a745;
}
</style>


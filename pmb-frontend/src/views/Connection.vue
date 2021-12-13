<template>
  <div class="container-fluid" id="connection">
    <Header></Header>

    <nav aria-label="breadcrumb" class="bg-light">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <router-link to="/home">Home</router-link>
        </li>
        <li class="breadcrumb-item">
          <router-link to="/transfer">Transfer</router-link>
        </li>
        <li class="breadcrumb-item active" aria-current="page">Connection</li>
      </ol>
    </nav>

    <div class="card col-8 mx-auto py-5">
      <div class="row justify-content-center">
        <MainTitle></MainTitle>
      </div>

      <form class="needs-validation" @submit="onSubmit">
        <div class="py-5">
          <div class="col-9 mx-auto">
            <label for="email" class="form-label col-11 text-start"
              >Please enter the email of the new contact :
            </label>
            <EmailInput
              v-model="form.email"
              label="Please enter the email of the new contact"
            ></EmailInput>
          </div>
        </div>

        <div class="col-8 col-sm-3 mx-auto">
          <SubmitButton lib="Add"></SubmitButton>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import MainTitle from "../components/MainTitle.vue";
import SubmitButton from "../components/SubmitButton.vue";
import EmailInput from "../components/EmailInput.vue";
import ConnectionService from "../services/ConnectionService";

export default {
  name: "Connection",
  components: {
    Header,
    MainTitle,
    EmailInput,
    SubmitButton,
  },
  data() {
    return {
      form: {
        email: "",
      },
    };
  },
  methods: {
    onSubmit() {
      var data = {
        userEmail: this.$route.params.email,
        connectionEmail: this.form.email
      };
      ConnectionService.addConnection(data)
        .then((response) => {
          if (response.status === 201) {
            alert(response.data);
            this.$router.push({
              name: "Transfer",
              params: { email: this.$route.params.email },
            });
          }
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
    if (this.$route.params.email === undefined) {
      this.$router.replace({ name: "Login" });
    }
  },
};
</script>

<style scoped>
.card {
  height: 80vh;
  width: 80vw;
  position: fixed;
  left: 10vw;
  right: 10vw;
}
</style>

<template>
  <div class="container-sm" id="signup">
    <div class="row justify-content-center">
      <MainTitle></MainTitle>
    </div>

    <form class="needs-validation py-5" @submit="onSubmit">
      <div class="accordion" id="accordion">
        <div class="accordion-item">
          <h2 class="accordion-header" id="headingLogin">
            <button
              class="accordion-button"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#collapseLogin"
              aria-expanded="true"
              aria-controls="collapseLogin"
            >
              Identity
            </button>
          </h2>
          <div
            id="collapseLogin"
            class="accordion-collapse collapse show"
            aria-labelledby="headingLogin"
          >
            <div class="accordion-body">
              <IdentityInput
                id="firstName"
                placeholder="Firstname"
                v-model="form.firstName"
              ></IdentityInput>

              <IdentityInput
                id="lastName"
                placeholder="Lastname"
                v-model="form.lastName"
              ></IdentityInput>
              <EmailInput v-model="form.email"></EmailInput>

              <PasswordInput
                id="password"
                placeholder="Password"
                v-model="form.password"
              ></PasswordInput>

              <PasswordInput
                id="confirmPassword"
                placeholder="Confirm password"
                v-model="form.confirmPassword"
              ></PasswordInput>
            </div>
          </div>
        </div>

        <div class="accordion-item">
          <h2 class="accordion-header" id="headingBank">
            <button
              class="accordion-button"
              type="button"
              data-bs-toggle="collapse"
              data-bs-target="#collapseBank"
              aria-expanded="true"
              aria-controls="collapseBank"
            >
              Bank
            </button>
          </h2>
          <div
            id="collapseBank"
            class="accordion-collapse collapse show"
            aria-labelledby="headingBank"
          >
            <div class="accordion-body">
              <RibInput
                class="my-3"
                id="bank"
                placeholder="Bank"
                v-model="form.bankAccount.bank"
              ></RibInput>
              <RibInput
                class="my-3"
                id="iban"
                placeholder="IBAN"
                v-model="form.bankAccount.iban"
              ></RibInput>
              <RibInput
                class="my-3"
                id="bic"
                placeholder="BIC"
                v-model="form.bankAccount.bic"
              ></RibInput>
            </div>
          </div>
        </div>
      </div>

      <div class="d-grid col-8 col-sm-4 mx-auto my-5">
        <SubmitButton lib="Sign up"></SubmitButton>
      </div>
    </form>
  </div>
</template>

<script>
import MainTitle from "../components/MainTitle.vue";
import SubmitButton from "../components/SubmitButton.vue";
import IdentityInput from "../components/IdentityInput.vue";
import EmailInput from "../components/EmailInput.vue";
import PasswordInput from "../components/PasswordInput.vue";
import RibInput from "../components/RibInput.vue";
import UserService from "../services/UserService.js"

export default {
  name: "Signup",
  components: {
    MainTitle,
    SubmitButton,
    IdentityInput,
    EmailInput,
    PasswordInput,
    RibInput,
  },
  data() {
    return {
      form: {
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: "",
        bankAccount: { bank: "", iban: "", bic: "" },
      },
    };
  },
  methods: {
    onSubmit() {
      var data = {
        firstName: this.firstName,
        lastName: this.lastName,
        email: this.email,
        password: this.password,
        confirmPassword: this.confirmPassword,
        bankAccount: { bank: this.bank, iban: this.iban, bic: this.bic },
      };
      alert(JSON.stringify(this.form));
      UserService.submit(data)
        .then((response) => {
          alert(response);
        })
        .catch((e) => {
          alert(e);
          this.$router.push("/home");
        });
    },
  },
};
</script>

<style scoped>
.accordion-item {
  border-radius: 0px;
}
.accordion button {
  color: white;
  background: #28a745;
}
.accordion-body {
  padding: 0rem;
}
</style>
<template>
  <div class="container-fluid" id="profile">
    <Header></Header>

    <nav aria-label="breadcrumb" class="bg-light">
      <ol class="breadcrumb">
        <li class="breadcrumb-item">
          <router-link to="/home" replace>Home</router-link>
        </li>
        <li class="breadcrumb-item active" aria-current="page">Profile</li>
      </ol>
    </nav>

    <form
      class="needs-validation py-5 col-11 col-md-9 mx-auto"
      @submit="onSubmit"
    >
      <div class="accordion" id="accordion">
        <div class="accordion-item bg-light">
          <h2 class="accordion-header" id="headingLogin">
            <button
              class="accordion-button fw-bold"
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
            <div class="accordion-body col-11 col-md-9 mx-auto">
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

        <div class="accordion-item bg-light">
          <h2 class="accordion-header" id="headingBank">
            <button
              class="accordion-button fw-bold"
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
            class="accordion-collapse collapse"
            aria-labelledby="headingBank"
          >
            <div class="accordion-body col-11 col-md-9 mx-auto">
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
        <SubmitButton lib="Save"></SubmitButton>
      </div>
    </form>
  </div>
</template>

<script>
import Header from "../components/Header.vue";
import SubmitButton from "../components/SubmitButton.vue";
import IdentityInput from "../components/IdentityInput.vue";
import EmailInput from "../components/EmailInput.vue";
import PasswordInput from "../components/PasswordInput.vue";
import RibInput from "../components/RibInput.vue";
/*import UserService from "../services/UserService.js";*/

export default {
  name: "Profile",
  components: {
    Header,
    SubmitButton,
    IdentityInput,
    EmailInput,
    PasswordInput,
    RibInput,
  },
  data() {
    return {
      form: {
        firstName: "Laure",
        lastName: "BRUN",
        email: "laure.brun@email.com",
        password: "",
        confirmPassword: "",
        bankAccount: { bank: "maBanque", iban: "XXXX", bic: "XXX" },
      },
    };
  },
  methods: {
    onSubmit() {
      /*
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
          
        });
        */
      this.$router.push("/home");
    },
  },
};
</script>

<style scoped>
.accordion-item {
  border-radius: 0px;
}
.accordion-button,
.accordion-button:not(.collapsed) {
  color: white;
  background: #28a745;
  height: 2vh;
}

.accordion-button::after {
  background-image: url(../../src/assets/barrow.png);
}

.accordion-button:not(.collapsed)::after {
  background-image: url(../../src/assets/barrow.png);
}

.accordion-body {
  padding: 0px;
}
</style>
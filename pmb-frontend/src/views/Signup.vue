<template>
  <div class="container-fluid" id="signup">
    <div class="card main-card">
      <div class="card-body">
        <div class="row justify-content-center">
          <MainTitle></MainTitle>
        </div>

        <form
          class="needs-validation py-5 mx-auto col-11 col-md-9"
          v-on:submit.prevent="handleRegister"
        >
          <div class="accordion" id="accordion">
            <div class="accordion-item bg-light" id="identity">
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
                    v-model="user.firstName"
                  ></IdentityInput>

                  <IdentityInput
                    id="lastName"
                    placeholder="Lastname"
                    v-model="user.lastName"
                  ></IdentityInput>
                  <EmailInput v-model="user.email"></EmailInput>

                  <PasswordInput
                    id="password"
                    placeholder="Password"
                    v-model="user.password"
                  ></PasswordInput>

                  <PasswordInput
                    id="confirmPassword"
                    placeholder="Confirm password"
                    v-model="user.confirmPassword"
                  ></PasswordInput>
                </div>
              </div>
            </div>

            <div class="accordion-item bg-light" id="bankAccount">
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
                class="accordion-collapse collapse show"
                aria-labelledby="headingBank"
              >
                <div class="accordion-body col-11 col-md-9 mx-auto">
                  <RibInput
                    class="my-3"
                    id="bank"
                    placeholder="Bank"
                    v-model="user.bankAccount.bank"
                  ></RibInput>
                  <RibInput
                    class="my-3"
                    id="iban"
                    placeholder="IBAN"
                    v-model="user.bankAccount.iban"
                  ></RibInput>
                  <RibInput
                    class="my-3"
                    id="bic"
                    placeholder="BIC"
                    v-model="user.bankAccount.bic"
                  ></RibInput>
                </div>
              </div>
            </div>
          </div>

          <div class="col-8 col-sm-3 mx-auto my-5">
            <SubmitButton lib="Sign up"></SubmitButton>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import MainTitle from "../components/MainTitle.vue";
import SubmitButton from "../components/SubmitButton.vue";
import IdentityInput from "../components/IdentityInput.vue";
import EmailInput from "../components/EmailInput.vue";
import PasswordInput from "../components/PasswordInput.vue";
import RibInput from "../components/RibInput.vue";

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
      user: {
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
    handleRegister() {
      var currentUser =  {
        email: this.user.email,
        password: this.user.password
      }
      this.$store.dispatch("auth/register", this.user).then(
        () => {
           this.$store.dispatch("auth/login", currentUser).then(
          () => {
            this.$router.push("/home");
          },
          (error) => {
            alert(
              error.response.data.error || error.message || error.toString()
            );
          }
        );
        },
        (error) => {
          alert(
              error.response.data || error.message || error.toString()
            );
        }
      );
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
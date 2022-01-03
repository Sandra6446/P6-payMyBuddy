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
      v-on:submit.prevent="onSave"
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
                v-model="currentUser.firstName"
              ></IdentityInput>

              <IdentityInput
                id="lastName"
                placeholder="Lastname"
                v-model="currentUser.lastName"
              ></IdentityInput>

              <EmailInput v-model="currentUser.email"></EmailInput>

              <PasswordInput
                id="password"
                placeholder="Password"
                v-model="currentUser.password"
              ></PasswordInput>

              <PasswordInput
                id="confirmPassword"
                placeholder="Confirm password"
                v-model="currentUser.confirmPassword"
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
                v-model="currentUser.bankAccount.bank"
              ></RibInput>
              <RibInput
                class="my-3"
                id="iban"
                placeholder="IBAN"
                v-model="currentUser.bankAccount.iban"
              ></RibInput>
              <RibInput
                class="my-3"
                id="bic"
                placeholder="BIC"
                v-model="currentUser.bankAccount.bic"
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
import UserService from "../services/user.service.js";

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
      currentUser: {
        firstName: "",
        lastName: "",
        email: "",
        password: "",
        confirmPassword: "",
        bankAccount: {
          bank: "",
          iban: "",
          bic: "",
        },
      },
    };
  },
  methods: {
    onSave() {
      if (this.currentUser.password === this.currentUser.confirmPassword) {
        UserService.update(this.userEmail, this.currentUser).then(
          () => {
            this.$store.dispatch("auth/logout");
            var user = {
              email: this.currentUser.email,
              password: this.currentUser.password,
            };
            this.$store.dispatch("auth/login", user).then(
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
            alert(error.response.data || error.message || error.toString());
          }
        );
      } else {
        alert("The two passwords have to be equals");
      }
    },
  },
  computed: {
    userEmail: function () {
      return this.$store.state.auth.user.email;
    },
  },
  mounted() {
    if (this.$store.state.auth.user !== null) {
      UserService.getUser(this.userEmail).then(
        (response) => {
          var profile = {
            firstName: response.data.firstName,
            lastName: response.data.lastName,
            email: response.data.email,
            password: "",
            confirmPassword: "",
            bankAccount: {
              bank: response.data.bankAccount.bank,
              iban: response.data.bankAccount.iban,
              bic: response.data.bankAccount.bic,
            },
          };
          this.currentUser = profile;
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
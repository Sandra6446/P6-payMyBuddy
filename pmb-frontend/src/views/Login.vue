<template>
  <div class="container-fluid" id="login">
    <div class="card main-card">
      <div class="card-body">
        <div class="row justify-content-center">
          <MainTitle></MainTitle>
        </div>

        <form
          class="needs-validation my-5 col-11 col-md-9 mx-auto"
          v-on:submit.prevent="handleLogin"
        >
          <EmailInput class="my-4" v-model="user.email"></EmailInput>

          <PasswordInput
            v-model="user.password"
            id="password"
            placeholder="Password"
          ></PasswordInput>

          <div class="row justify-content-center">
            <div class="form-check col-auto mb-5 shadow-none">
              <input
                class="form-check-input fs-5"
                type="checkbox"
                id="check-remember"
                value="isChecked"
                aria-checked="false"
              />
              <label class="form-check-label fs-5" for="check-remember">
                Remember me
              </label>
            </div>
          </div>

          <div class="col-8 col-sm-3 mx-auto">
            <SubmitButton lib="Login"></SubmitButton>
          </div>
        </form>

        <div class="row justify-content-center mt-3">
          <router-link to="/signup">Sign up</router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import MainTitle from "../components/MainTitle.vue";
import SubmitButton from "../components/SubmitButton.vue";
import EmailInput from "../components/EmailInput.vue";
import PasswordInput from "../components/PasswordInput.vue";

export default {
  name: "Login",
  components: {
    MainTitle,
    EmailInput,
    PasswordInput,
    SubmitButton,
  },
  data() {
    return {
      user: {
        email: "",
        password: "",
      },
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/home");
    } else {
      this.user.email = this.$cookies.get('email');
      this.user.password = this.$cookies.get('pwd');
    }
  },
  methods: {
    handleLogin() {
      if (this.user.email && this.user.password) {
        this.$store.dispatch("auth/login", this.user).then(
          () => {
            if (document.querySelector('#check-remember').checked) {
              this.$cookies.set('email',this.user.email);
              this.$cookies.set('pwd',this.user.password);
            } else {
              this.$cookies.remove('email');
              this.$cookies.remove('pwd');
            }
            this.$router.push("/home");
          },
          (error) => {
            alert(
              error.response.data.error || error.message || error.toString()
            );
          }
        );
      }
    },
  },
};
</script>

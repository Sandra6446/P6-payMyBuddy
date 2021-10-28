<template>
  <div class="container-fluid" id="login">
    <div class="card main-card">
      <div class="card-body">
        <div class="row justify-content-center">
          <MainTitle></MainTitle>
        </div>

        <form
          class="needs-validation my-5 col-11 col-md-9 mx-auto"
          @submit="onSubmit"
        >
          <EmailInput class="my-4" v-model="form.email"></EmailInput>
          <PasswordInput
            v-model="form.password"
            id="password"
            placeholder="Password"
            passwordRequired="passwordRequired"
          ></PasswordInput>

          <div class="row justify-content-center">
            <div class="form-check col-auto mb-5 shadow-none">
              <input
                class="form-check-input fs-5"
                type="checkbox"
                value=""
                id="check-remember"
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
/*import LoginService from "../services/LoginService";*/

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
      form: {
        email: "",
        password: "",
      },
      passwordRequired: true
    };
  },
  methods: {
    onSubmit() {
      /* var data = {
        email: this.form.email,
        password: this.form.password,
      };
      alert(JSON.stringify(data));
     LoginService.submit(data)
        .then((response) => {
          alert(response);
        })
        .catch((e) => {
          alert(e);
        });
        */
      this.$router.replace("/home");
    },
  },
};
</script>

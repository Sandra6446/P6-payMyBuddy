<template>
  <div class="container-sm" id="login">
    <div class="row justify-content-center">
      <MainTitle></MainTitle>
    </div>

    <form class="needs-validation" @submit="onSubmit">
      <div class="py-5">
        <EmailInput class="my-5" v-model="form.email"></EmailInput>
        <PasswordInput
          class="my-5"
          v-model="form.password"
          id="password"
          placeholder="Password"
        ></PasswordInput>
      </div>

      <div class="row justify-content-center">
        <div class="row justify-content-center">
          <div class="form-check my-5 col-auto">
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
      </div>

      <div class="d-grid col-8 col-sm-4 mx-auto">
        <SubmitButton lib="Login"></SubmitButton>
      </div>
    </form>

    <div class="row justify-content-center my-5">
      <LinkButton lib="Sign up" page="/signup"></LinkButton>
    </div>
  </div>
</template>

<script>
import MainTitle from "../components/MainTitle.vue";
import SubmitButton from "../components/SubmitButton.vue";
import EmailInput from "../components/EmailInput.vue";
import PasswordInput from "../components/PasswordInput.vue";
import LinkButton from "../components/LinkButton.vue";
import LoginService from "../services/LoginService";

export default {
  name: "Login",
  components: {
    MainTitle,
    EmailInput,
    PasswordInput,
    SubmitButton,
    LinkButton,
  },
  data() {
    return {
      form: {
        email: "",
        password: "",
      },
    };
  },
  methods: {
    onSubmit() {
      var data = {
        email: this.form.email,
        password: this.form.password,
      };
      alert(JSON.stringify(this.form));
      LoginService.submit(data)
        .then((response) => {
          alert(response);
        })
        .catch((e) => {
          alert(e);
        });
      this.$router.push("home");
    },
  },
};
</script>

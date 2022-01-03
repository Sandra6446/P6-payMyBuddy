<template>
    <nav class="navbar navbar-expand-lg navbar-light">
      <div class="container-fluid">
        <span class="navbar-brand"><MainTitle></MainTitle></span>
        <button
          class="navbar-toggler ms-auto"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarHeader"
          aria-controls="navbarHeader"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="d-flex">
          <div class="collapse navbar-collapse" id="navbarHeader">
            <div class="navbar-nav">
              <router-link
                class="nav-link"
                to='/home'
                >Home</router-link
              >
              <router-link
                class="nav-link"
                to='/transfer'
                >Transfer</router-link
              >
              <router-link
                class="nav-link"
                to='/profile'
                >Profile</router-link
              >
              <a class="nav-link" href="mailto:contact@email.com">Contact</a>
              <a class="nav-link" href @click="logOut">Log off</a>
            </div>
          </div>
        </div>
      </div>
    </nav>
</template>

<script>
import MainTitle from "../components/MainTitle.vue";

export default {
  name: "Header",
  components: {
    MainTitle,
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
};
</script>

<style scoped>
.nav-link {
  color: gray;
}

.nav-link .active {
  color: rgb(0, 102, 255);
}
</style>
